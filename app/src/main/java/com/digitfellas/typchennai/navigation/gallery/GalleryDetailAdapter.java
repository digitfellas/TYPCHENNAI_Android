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
import com.digitfellas.typchennai.common.customtextview.LatoRegularTextview;
import com.digitfellas.typchennai.network.response.PhotoAlbum;
import com.digitfellas.typchennai.network.response.Photos;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by administrator on 08/06/18.
 */

public class GalleryDetailAdapter extends RecyclerView.Adapter<GalleryDetailAdapter.GalleryViewHolder> {

    private Context mContext;
    private String mImagePath;
    ArrayList<Photos> mPhotoalbum;

    public GalleryDetailAdapter(Context context, List<Photos> photoalbum, String imagePath) {
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
    public void onBindViewHolder(GalleryViewHolder holder, int position) {
        Picasso.with(mContext).load(mImagePath + "/" + mPhotoalbum.get(position).getFilename() + "." + mPhotoalbum.get(position).getFilename_ext()).into(holder.mIvConverImage);
        holder.mTvTitle.setVisibility(View.GONE);
        holder.mTvPhotoCount.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return mPhotoalbum.size();
    }

    public class GalleryViewHolder extends RecyclerView.ViewHolder {

        private ImageView mIvConverImage;
        private LatoRegularTextview mTvTitle, mTvPhotoCount;

        public GalleryViewHolder(View itemView) {
            super(itemView);
            mIvConverImage = (ImageView) itemView.findViewById(R.id.iv_cover_image);
            mTvTitle = (LatoRegularTextview) itemView.findViewById(R.id.tv_title);
            mTvPhotoCount = (LatoRegularTextview) itemView.findViewById(R.id.tv_photo_count);
        }
    }
}
