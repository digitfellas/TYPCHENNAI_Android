package com.digitfellas.typchennai.navigation.events;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.digitfellas.typchennai.R;
import com.digitfellas.typchennai.SessionStorage;
import com.digitfellas.typchennai.common.IntentConstant;
import com.digitfellas.typchennai.common.customtextview.LatoBoldTextview;
import com.digitfellas.typchennai.common.customtextview.LatoRegularTextview;
import com.digitfellas.typchennai.network.response.Events;

import java.util.List;

public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.EventViewHolder> {

    private Context mContext;
    private List<Events> mEventList;

    public EventListAdapter(Context context, List<Events> eventsList) {
        mContext = context;
        mEventList = eventsList;
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_events, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EventViewHolder holder, final int position) {
        holder.mTvEventName.setText(mEventList.get(position).getTitle());
        holder.mTvDescription.setText(mEventList.get(position).getShort_description());
        holder.mTvDate.setText(mEventList.get(position).getEvent_date() + " at " + mEventList.get(position).getEvent_time());
        holder.mTvLocation.setText(mEventList.get(position).getLocation());

        holder.mCvRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, EventDetailActivity.class);
                intent.putExtra(IntentConstant.ID, mEventList.get(position).getId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mEventList.size();
    }

    public class EventViewHolder extends RecyclerView.ViewHolder {

        private CardView mCvRoot;
        private LatoBoldTextview mTvEventName;
        private LatoRegularTextview mTvDescription, mTvDate, mTvLocation;

        public EventViewHolder(View itemView) {
            super(itemView);

            mTvEventName = (LatoBoldTextview) itemView.findViewById(R.id.tv_event_name);
            mTvDescription = (LatoRegularTextview) itemView.findViewById(R.id.tv_description);
            mTvDate = (LatoRegularTextview) itemView.findViewById(R.id.tv_date);
            mTvLocation = (LatoRegularTextview) itemView.findViewById(R.id.tv_location);
            mCvRoot = (CardView) itemView.findViewById(R.id.cv_root);
        }
    }
}
