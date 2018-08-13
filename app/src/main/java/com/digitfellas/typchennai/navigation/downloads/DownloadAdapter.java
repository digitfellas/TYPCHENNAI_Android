package com.digitfellas.typchennai.navigation.downloads;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.digitfellas.typchennai.R;
import com.digitfellas.typchennai.common.IntentConstant;
import com.digitfellas.typchennai.common.customtextview.LatoRegularTextview;
import com.digitfellas.typchennai.network.response.Pdf;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by administrator on 08/06/18.
 */

public class DownloadAdapter extends RecyclerView.Adapter<DownloadAdapter.DownloadViewHolder> {

    private Context mContext;
    private ArrayList<Pdf> mPdfList;
    private String mPdfPath;

    public DownloadAdapter(Context context, List<Pdf> pdfList, String path) {
        mContext = context;
        mPdfList = new ArrayList<>(pdfList);
        mPdfPath = path;
    }

    @Override
    public DownloadAdapter.DownloadViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_pdf, parent, false);
        return new DownloadViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DownloadAdapter.DownloadViewHolder holder, final int position) {
        holder.mTvPdfName.setText(mPdfList.get(position).getTitle());

        holder.mTvPdfName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, ViewPdf.class);
                intent.putExtra(IntentConstant.PDF_URL, mPdfPath + "/" + mPdfList.get(position).getFilename());
                intent.putExtra(IntentConstant.NAME, mPdfList.get(position).getTitle());
                mContext.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mPdfList.size();
    }

    public class DownloadViewHolder extends RecyclerView.ViewHolder {

        LatoRegularTextview mTvPdfName;

        public DownloadViewHolder(View itemView) {
            super(itemView);

            mTvPdfName = (LatoRegularTextview) itemView.findViewById(R.id.tv_pdf_name);
        }
    }
}
