package com.digitfellas.typchennai.navigation.directory;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.digitfellas.typchennai.R;
import com.digitfellas.typchennai.common.customtextview.LatoBoldTextview;
import com.digitfellas.typchennai.common.customtextview.LatoRegularTextview;
import com.digitfellas.typchennai.network.response.Members;

import java.util.ArrayList;
import java.util.List;

public class FamilyDetailAdapter extends RecyclerView.Adapter<FamilyDetailAdapter.FamilyDetailViewHolder> {

    private Context mContext;
    private ArrayList<Members> mMembersList;
    private String mDetail;

    public FamilyDetailAdapter(Context context, List<Members> members) {
        mContext = context;
        mMembersList = new ArrayList<>(members);
    }

    @Override
    public FamilyDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_family_detail, parent, false);
        return new FamilyDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FamilyDetailViewHolder holder, int position) {

        holder.mTvName.setText(mMembersList.get(position).getName());

        mDetail = "";

        if (mMembersList.get(position).getRelation() != null && mMembersList.get(position).getRelation().length() > 0) {
            mDetail = mMembersList.get(position).getRelation();
        }

        if (mMembersList.get(position).getBusiness() != null && mMembersList.get(position).getBusiness().length() > 0) {
            mDetail = mDetail + ", " + mMembersList.get(position).getBusiness();
        }

        if (mMembersList.get(position).getDob() != null && mMembersList.get(position).getDob().length() > 0) {
            mDetail = mDetail + "\nDOB: " + mMembersList.get(position).getDob();
        }

        if (mMembersList.get(position).getQualification() != null && mMembersList.get(position).getQualification().length() > 0) {
            mDetail = mDetail + "\nQualification: " + mMembersList.get(position).getQualification();
        }

        if (mMembersList.get(position).getEmail() != null && mMembersList.get(position).getEmail().length() > 0) {
            mDetail = mDetail + "\nEmail: " + mMembersList.get(position).getEmail();
        }

        if (mMembersList.get(position).getMobile_no() != null && mMembersList.get(position).getMobile_no().length() > 0) {
            mDetail = mDetail + "\nMobile: " + mMembersList.get(position).getMobile_no();
        }

        if (mMembersList.get(position).getMarital_status() != null && mMembersList.get(position).getMarital_status().length() > 0) {

            if (mMembersList.get(position).getMarital_status().equals("0")) {
                mDetail = mDetail + "\nMarital Status: Single";
            } else {
                mDetail = mDetail + "\nMarital Status: Married";
            }
        }

        holder.mTvFamilyDetails.setText(mDetail);

    }

    @Override
    public int getItemCount() {
        return mMembersList.size();
    }

    public class FamilyDetailViewHolder extends RecyclerView.ViewHolder {

        private LatoBoldTextview mTvName;
        private LatoRegularTextview mTvFamilyDetails;

        public FamilyDetailViewHolder(View itemView) {
            super(itemView);
            mTvFamilyDetails = (LatoRegularTextview) itemView.findViewById(R.id.tv_famil_details);
            mTvName = (LatoBoldTextview) itemView.findViewById(R.id.tv_family_detail_name);
        }
    }
}
