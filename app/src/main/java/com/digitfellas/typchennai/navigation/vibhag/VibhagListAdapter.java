package com.digitfellas.typchennai.navigation.vibhag;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.digitfellas.typchennai.R;

import java.io.Serializable;
import java.util.ArrayList;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Created by ubuntu on 7/6/18.
 */

public class VibhagListAdapter extends RecyclerView.Adapter<VibhagListAdapter.ViewHolder> {
        private ArrayList<VibhagData> vibhagDataList;
        private Context context;

    public VibhagListAdapter(Context context,ArrayList<VibhagData> vibhagData) {
            this.vibhagDataList = vibhagData;
            this.context = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_vibhag_item, viewGroup, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int i) {

            viewHolder.iv_vibhag.setImageResource(vibhagDataList.get(i).getImage());
            viewHolder.tv_vibhag.setText(vibhagDataList.get(i).getName());
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    /*VibhagData vibhagData = new VibhagData();
                    vibhagData = vibhagDataList.get(i);*/
                    Intent detailIntent = new Intent(context,VibhagDetailActivity.class);
                    detailIntent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                    detailIntent.putExtra("VIBHAG_DETAIL", (Serializable) vibhagDataList.get(i));
                    context.startActivity(detailIntent);
                }
            });


        }

        @Override
        public int getItemCount() {
            return vibhagDataList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            private TextView tv_vibhag;
            private ImageView iv_vibhag;
            public ViewHolder(View view) {
                super(view);

                tv_vibhag = (TextView)view.findViewById(R.id.tv_vibhag);
                iv_vibhag = (ImageView) view.findViewById(R.id.iv_vibhab);
            }
        }
}
