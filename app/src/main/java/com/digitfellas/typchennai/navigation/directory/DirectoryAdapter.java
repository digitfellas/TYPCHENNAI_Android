package com.digitfellas.typchennai.navigation.directory;

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
import com.digitfellas.typchennai.network.response.Heads;

import java.util.List;

public class DirectoryAdapter extends RecyclerView.Adapter<DirectoryAdapter.DirectoryViewHolder> {

    private Context mContext;
    private List<Heads> mHeads;

    public DirectoryAdapter(Context context, List<Heads> heads) {
        mContext = context;
        mHeads = heads;
    }

    @Override
    public DirectoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_directory, parent, false);
        return new DirectoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DirectoryViewHolder holder, final int position) {
        holder.mTvInitials.setText(mHeads.get(position).getName().substring(0, 1));
        holder.mTvName.setText(mHeads.get(position).getName() + " " + mHeads.get(position).getSurname());
        holder.mTvLocation1.setText(mHeads.get(position).getCity_name() + ", " + mHeads.get(position).getRes_pin_code());
        holder.mTvLocation2.setText(mHeads.get(position).getmNative());
        holder.mCvRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, FamilyDetailActivity.class);
                intent.putExtra(IntentConstant.FAMILY_ID, mHeads.get(position).getId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mHeads.size();
    }

    public class DirectoryViewHolder extends RecyclerView.ViewHolder {

        private CardView mCvRoot;
        private LatoBoldTextview mTvInitials;
        private LatoRegularTextview mTvName, mTvLocation1, mTvLocation2;

        public DirectoryViewHolder(View itemView) {
            super(itemView);
            mCvRoot = (CardView) itemView.findViewById(R.id.cv_root);
            mTvName = (LatoRegularTextview) itemView.findViewById(R.id.tv_name);
            mTvInitials = (LatoBoldTextview) itemView.findViewById(R.id.tv_initials);
            mTvLocation1 = (LatoRegularTextview) itemView.findViewById(R.id.tv_location1);
            mTvLocation2 = (LatoRegularTextview) itemView.findViewById(R.id.tv_location2);
        }
    }

    public void filterList(List<Heads> filteredHeadsList) {
        this.mHeads = filteredHeadsList;
        notifyDataSetChanged();
    }
}
