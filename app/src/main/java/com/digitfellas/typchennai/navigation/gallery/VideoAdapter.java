package com.digitfellas.typchennai.navigation.gallery;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import com.digitfellas.typchennai.R;
import com.digitfellas.typchennai.common.Constant;
import com.digitfellas.typchennai.network.response.Video;
import com.digitfellas.typchennai.utils.Logger;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * import com.digitfellas.typchennai.R;
 * import com.digitfellas.typchennai.common.Constant;
 * <p>
 * /**
 * Created by administrator on 09/06/18.
 */

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    private Context mContext;
    private ArrayList<Video> mVideos;

    public VideoAdapter(Context context, List<Video> videos) {
        mContext = context;
        mVideos = new ArrayList<>(videos);
    }

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_video, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final VideoViewHolder holder, final int position) {
        final YouTubeThumbnailLoader.OnThumbnailLoadedListener onThumbnailLoadedListener = new YouTubeThumbnailLoader.OnThumbnailLoadedListener() {
            @Override
            public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {

            }

            @Override
            public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
                youTubeThumbnailView.setVisibility(View.VISIBLE);
                holder.relativeLayoutOverYouTubeThumbnailView.setVisibility(View.VISIBLE);

            }
        };

        holder.youTubeThumbnailView.initialize(Constant.API_KEY, new YouTubeThumbnailView.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader youTubeThumbnailLoader) {
                String filename = mVideos.get(position).getFilename();
                filename = filename.substring(filename.lastIndexOf("/"), filename.length());
                Logger.i("TAG", "Filename = " + filename);
                youTubeThumbnailLoader.setVideo(filename);
                youTubeThumbnailLoader.setOnThumbnailLoadedListener(onThumbnailLoadedListener);
                Picasso.with(mContext)
                        .load(mVideos.get(position).getVideo_banner())
                        .into(holder.youTubeThumbnailView);
            }

            @Override
            public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {
                //write something for failure
            }
        });
    }

    @Override
    public int getItemCount() {
        return mVideos.size();
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        protected RelativeLayout relativeLayoutOverYouTubeThumbnailView;
        YouTubeThumbnailView youTubeThumbnailView;
        protected ImageView playButton;
        CardView mCvCard;

        public VideoViewHolder(View itemView) {
            super(itemView);
            playButton = (ImageView) itemView.findViewById(R.id.btnYoutube_player);
            playButton.setOnClickListener(this);
            relativeLayoutOverYouTubeThumbnailView = (RelativeLayout) itemView.findViewById(R.id.relativeLayout_over_youtube_thumbnail);
            youTubeThumbnailView = (YouTubeThumbnailView) itemView.findViewById(R.id.youtube_thumbnail);
            mCvCard = (CardView) itemView.findViewById(R.id.cv_card);
        }

        @Override
        public void onClick(View v) {
            String filename = mVideos.get(getLayoutPosition()).getFilename();
            filename = filename.substring(filename.lastIndexOf("/")+1, filename.length());
            Intent intent = YouTubeStandalonePlayer.createVideoIntent((Activity) mContext, Constant.API_KEY, filename);
            mContext.startActivity(intent);
        }
    }
}
