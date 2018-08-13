package com.digitfellas.typchennai.navigation.working;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.digitfellas.typchennai.R;
import com.digitfellas.typchennai.common.customtextview.LatoBoldTextview;
import com.digitfellas.typchennai.common.customtextview.LatoRegularTextview;
import com.digitfellas.typchennai.network.response.WorkingCommittee;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by administrator on 07/06/18.
 */

public class WorkingAdapter extends RecyclerView.Adapter<WorkingAdapter.WorkingViewHolder> {

    private ArrayList<WorkingCommittee> mWorkingCommitteeList;
    private Context mContext;

    public WorkingAdapter(Context context, List<WorkingCommittee> workingCommitteeList) {
        mContext = context;
        mWorkingCommitteeList = new ArrayList<>(workingCommitteeList);
    }

    @Override
    public WorkingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View workingView = LayoutInflater.from(mContext).inflate(R.layout.row_working_view, parent, false);
        return new WorkingViewHolder(workingView);
    }

    @Override
    public void onBindViewHolder(WorkingViewHolder holder, int position) {

        holder.mTvInitials.setText(mWorkingCommitteeList.get(position).getName().substring(0, 1));
        holder.mTvName.setText(mWorkingCommitteeList.get(position).getName());
        holder.mTvRole.setText(mWorkingCommitteeList.get(position).getRole());
        holder.mTvNumber.setText(mWorkingCommitteeList.get(position).getMobile_no());

    }

    @Override
    public int getItemCount() {
        return mWorkingCommitteeList.size();
    }

    public class WorkingViewHolder extends RecyclerView.ViewHolder {

        private LatoRegularTextview mTvName, mTvRole, mTvNumber;
        private LatoBoldTextview mTvInitials;

        public WorkingViewHolder(View itemView) {
            super(itemView);

            mTvInitials = (LatoBoldTextview) itemView.findViewById(R.id.tv_initials);
            mTvName = (LatoRegularTextview) itemView.findViewById(R.id.tv_name);
            mTvRole = (LatoRegularTextview) itemView.findViewById(R.id.tv_role);
            mTvNumber = (LatoRegularTextview) itemView.findViewById(R.id.tv_mobile);
        }
    }
}
