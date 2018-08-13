package com.digitfellas.typchennai.navigation.gallery;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.digitfellas.typchennai.R;
import com.digitfellas.typchennai.common.IntentConstant;
import com.digitfellas.typchennai.common.customtextview.LatoRegularTextview;
import com.digitfellas.typchennai.network.response.PhotoAlbum;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by administrator on 08/06/18.
 */

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder> {

    private Context mContext;
    private String mImagePath;
    ArrayList<PhotoAlbum> mPhotoalbum;

    public GalleryAdapter(Context context, List<PhotoAlbum> photoalbum, String imagePath) {
        mContext = context;
        mPhotoalbum = new ArrayList<>(photoalbum);
        mImagePath = imagePath;
    }

    @Override
    public GalleryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.row_gallery, parent, false);
        return new GalleryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GalleryViewHolder holder, final int position) {

        holder.mTvTitle.setText(mPhotoalbum.get(position).getTitle());
        holder.mTvPhotoCount.setText(mPhotoalbum.get(position).getPhoto_count() + " Photos");
        Picasso.with(mContext).load(mImagePath + "/" + mPhotoalbum.get(position).getId() + "/" + mPhotoalbum.get(position).getCover_photo() + "." + mPhotoalbum.get(position).getCover_photo_ext()).into(holder.mIvConverImage);

        holder.mLlRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, GalleryDetailActivity.class);
                intent.putExtra(IntentConstant.ID, mPhotoalbum.get(position).getId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPhotoalbum.size();
    }

    public class GalleryViewHolder extends RecyclerView.ViewHolder {

        private ImageView mIvConverImage;
        private LatoRegularTextview mTvTitle, mTvPhotoCount;
        private LinearLayout mLlRoot;

        public GalleryViewHolder(View itemView) {
            super(itemView);
            mIvConverImage = (ImageView) itemView.findViewById(R.id.iv_cover_image);
            mTvTitle = (LatoRegularTextview) itemView.findViewById(R.id.tv_title);
            mTvPhotoCount = (LatoRegularTextview) itemView.findViewById(R.id.tv_photo_count);
            mLlRoot = (LinearLayout) itemView.findViewById(R.id.ll_root);
        }
    }
}
