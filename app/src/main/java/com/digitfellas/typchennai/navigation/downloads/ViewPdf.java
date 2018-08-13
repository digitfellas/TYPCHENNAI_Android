package com.digitfellas.typchennai.navigation.downloads;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.digitfellas.typchennai.R;
import com.digitfellas.typchennai.RootActivity;
import com.digitfellas.typchennai.common.IntentConstant;
import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by administrator on 08/06/18.
 */

public class ViewPdf extends RootActivity {

    private Bundle bundle;
    private File mOutputDir = null;
    private File mOutputFile = null;
    private PDFView mPdfViewer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMainContentView(R.layout.activity_view_pdf);
        mPdfViewer = (PDFView) findViewById(R.id.pdfview);
        bundle = getIntent().getExtras();

        if(bundle != null){
            showProgressDialog(getResources().getString(R.string.please_wait));
            loadPDF(bundle.getString(IntentConstant.PDF_URL));
            mToolBarTitle.setText(bundle.getString(IntentConstant.NAME));
        }

        mActionBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void loadPDF(String url) {
        mOutputDir = getCacheDir();
        try {
            mOutputFile = File.createTempFile("test", "pdf", mOutputDir);
            new PDFDownloader().execute(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private class PDFDownloader extends AsyncTask<String, Void, Boolean> {

        private static final int MEGABYTE = 1024 * 1024;

        @Override
        protected Boolean doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                FileOutputStream fileOutputStream = new FileOutputStream(mOutputFile);

                byte[] buffer = new byte[MEGABYTE];
                int bufferLength = 0;
                while ((bufferLength = inputStream.read(buffer)) > 0) {
                    fileOutputStream.write(buffer, 0, bufferLength);
                }
                fileOutputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return false;
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return false;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            hideProgressDialog();
            if (result) {
                mPdfViewer.fromFile(mOutputFile).defaultPage(1).enableDoubletap(true).swipeHorizontal(false).enableSwipe(true).load();
            } else {
                Toast.makeText(getApplicationContext(), "Unable to download the file", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mOutputFile != null)
            mOutputFile.delete();

        if (mOutputDir != null)
            mOutputDir.delete();
    }
}
