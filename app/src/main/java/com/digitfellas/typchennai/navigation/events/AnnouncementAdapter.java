package com.digitfellas.typchennai.navigation.events;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.digitfellas.typchennai.R;
import com.digitfellas.typchennai.SessionStorage;
import com.digitfellas.typchennai.common.customtextview.LatoBoldTextview;
import com.digitfellas.typchennai.common.customtextview.LatoRegularTextview;
import com.digitfellas.typchennai.network.response.Announcements;

import java.util.ArrayList;
import java.util.List;

public class AnnouncementAdapter extends RecyclerView.Adapter<AnnouncementAdapter.AnnouncementViewHolder> {

    private Context mContext;
    private List<Announcements> mAnnouncementList;

    public AnnouncementAdapter(Context context, List<Announcements> announcements) {
        mContext = context;
        mAnnouncementList = new ArrayList<>(announcements);
    }

    @Override
    public AnnouncementViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_announcement, parent, false);
        return new AnnouncementViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AnnouncementViewHolder holder, final int position) {

        holder.mTvEventName.setText(mAnnouncementList.get(position).getTitle());
        holder.mTvDescription.setText(Html.fromHtml(mAnnouncementList.get(position).getDescription()));

        holder.mCvRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AnnouncementDetailActivity.class);
                SessionStorage.getInstance().setAnnouncements(mAnnouncementList.get(position));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mAnnouncementList.size();
    }

    public class AnnouncementViewHolder extends RecyclerView.ViewHolder{

        private CardView mCvRoot;
        private LatoBoldTextview mTvEventName;
        private LatoRegularTextview mTvDescription;

        public AnnouncementViewHolder(View itemView) {
            super(itemView);

            mTvEventName = (LatoBoldTextview) itemView.findViewById(R.id.tv_announcement_name);
            mTvDescription = (LatoRegularTextview) itemView.findViewById(R.id.tv_description);
            mCvRoot = (CardView) itemView.findViewById(R.id.cv_root);
        }
    }
}
