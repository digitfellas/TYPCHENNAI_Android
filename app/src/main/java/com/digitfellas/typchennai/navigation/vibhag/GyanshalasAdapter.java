package com.digitfellas.typchennai.navigation.vibhag;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.digitfellas.typchennai.R;
import com.digitfellas.typchennai.common.IntentConstant;
import com.digitfellas.typchennai.common.customtextview.LatoBoldTextview;
import com.digitfellas.typchennai.common.customtextview.LatoRegularTextview;
import com.digitfellas.typchennai.navigation.events.EventDetailActivity;
import com.digitfellas.typchennai.navigation.events.EventListAdapter;
import com.digitfellas.typchennai.network.response.Events;
import com.digitfellas.typchennai.network.response.Gyanshalas;

import java.util.List;

/**
 * Created by ubuntu on 8/6/18.
 */

public class GyanshalasAdapter extends RecyclerView.Adapter<GyanshalasAdapter.EventViewHolder> {

    private Context mContext;
    private List<Gyanshalas> gyanshalasList;

    public GyanshalasAdapter(Context context, List<Gyanshalas> gyanshalas) {
        mContext = context;
        gyanshalasList = gyanshalas;
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_centers, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EventViewHolder holder, final int position) {
        holder.mTvAddress.setText(gyanshalasList.get(position).getPlace());
        holder.mTvContactNumber.setText(gyanshalasList.get(position).getMobile_no());
        holder.mTvSamjok.setText(gyanshalasList.get(position).getSanyojak());
        holder.mTvTime.setText(gyanshalasList.get(position).getDay_time());
        holder.mTvName.setText(gyanshalasList.get(position).getName());

        /*holder.mCvRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, EventDetailActivity.class);
                intent.putExtra(IntentConstant.ID, mEventList.get(position).getId());
                mContext.startActivity(intent);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return gyanshalasList.size();
    }

    public class EventViewHolder extends RecyclerView.ViewHolder {

        private CardView mCvRoot;
        private LatoRegularTextview mTvAddress, mTvSamjok, mTvContactNumber,mTvTime;
        private LatoBoldTextview mTvName;
        public EventViewHolder(View itemView) {
            super(itemView);

            mTvAddress = (LatoRegularTextview) itemView.findViewById(R.id.tv_address);
            mTvSamjok = (LatoRegularTextview) itemView.findViewById(R.id.tv_sanjok);
            mTvContactNumber = (LatoRegularTextview) itemView.findViewById(R.id.tv_contact_number);
            mTvTime = (LatoRegularTextview) itemView.findViewById(R.id.tv_time);
            mTvName = (LatoBoldTextview) itemView.findViewById(R.id.tv_event_name);
        }
    }
}
