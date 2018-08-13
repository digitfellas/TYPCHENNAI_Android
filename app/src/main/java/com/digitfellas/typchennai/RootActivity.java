package com.digitfellas.typchennai;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.digitfellas.typchennai.common.customDialog.CustomProgressDialog;
import com.digitfellas.typchennai.common.customtextview.LatoBoldTextview;
import com.digitfellas.typchennai.utils.Logger;
import com.digitfellas.typchennai.utils.PermissionListener;
import com.digitfellas.typchennai.utils.PermissionUtil;

import java.util.Observable;
import java.util.Observer;


public class RootActivity extends AppCompatActivity implements Observer {

    public static final String BATCH_ID = "batchID";
    public static final String COURSE_ID = "courseId";

    protected static final String TAG = RootActivity.class.getSimpleName();

    /**
     * Our primary toolbar that is going to be made
     * the ActionBar.
     */
    protected Toolbar mToolbar;

    /**
     * The actual ActionBar instance.
     */
    private ActionBar mActionBar;

    /**
     * The layout holding the ToolBar (ActionBar) and TabLayout if any.
     * This layout allows us to control scroll behavior or the child elements
     * and its motion more easily and gracefully.
     */
    protected AppBarLayout mAppBarLayout;

    /**
     * The layout that is going to hold the actual content of the activity,
     * fragment, tab etc.
     */
    private FrameLayout mFlContentLayout;

    /**
     * Instance of Alert dialog to ensure dismiss of the dialog can be
     * handled separately.
     */
    protected AlertDialog mAlertDialog;

    /**
     * Dialogs are primarily UI components and hence convenience to declare
     * in RootActivity since any screen in Android is going to be under an
     * activity.
     */
    protected CustomProgressDialog mProgressDialog;

    /**
     * Parent drawer layout that holds the content and navigation view.
     */
    protected DrawerLayout mDrawerLayout;

    /**
     * Floating Action button on each screen. To be used if necessary.
     * By default, VISIBILITY of this item is set to GONE since not all
     * screens would actually need this functionality.
     */
    protected FloatingActionButton mFloatingActionButton;

    /**
     * Handler to this activity thread.
     */
    protected final Handler mHandler = new Handler();
    protected NavigationView mNavigationView;
    protected View mNavigationHeaderView;
    protected ImageView mActionBack, mRightIcon;
    protected LatoBoldTextview mToolBarTitle;
    protected LinearLayout mLlStatus;
    protected ImageView mIvDrawerProfile;
    protected TextView mTvstatus;
    protected LinearLayout mLlTripdateLayout;
    protected TextView mTvTripdate;
    //protected LinearLayout llTripEarnings;
    protected ImageView mIvPlusMenu, mIvLogo;
    protected AlertDialog alertDialog;

    protected LatoBoldTextview mTvUserName;

    protected ExpandableListView mNavigationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_layout);

        // Initialize the toolbar layout and assign it as the Action Bar for
        // all screens.
        mToolbar = (Toolbar) findViewById(R.id.ga_ActionBar);
        setSupportActionBar(mToolbar);
        mToolBarTitle = (LatoBoldTextview) mToolbar.findViewById(R.id.toolbar_title);
        mActionBack = (ImageView) mToolbar.findViewById(R.id.action_back);

        /*mIvPlusMenu = (ImageView) mToolbar.findViewById(R.id.iv_plus_menu);
        mLlStatus = (LinearLayout) mToolbar.findViewById(R.id.ll_status);
        mIvStatus = (ImageView) mToolbar.findViewById(R.id.iv_status);
        mTvstatus = (TextView) mToolbar.findViewById(R.id.tv_status);
        mLlTripdateLayout = (LinearLayout) mToolbar.findViewById(R.id.ll_trip_detail);
        mTvTripdate = (TextView) mToolbar.findViewById(R.id.tv_trip_date);*/
        mActionBar = getSupportActionBar();

        // Initialize all the parent level views. They wil be modified or added
        // by child activities and fragments based on their need.
        mDrawerLayout = (DrawerLayout) findViewById(R.id.base_DrawerLayout);
        mFlContentLayout = (FrameLayout) findViewById(R.id.base_FlContent);
        mAppBarLayout = (AppBarLayout) findViewById(R.id.base_AppBar);
        mNavigationView = (NavigationView) findViewById(R.id.base_NavigationView);
//        mNavigationHeaderView = mNavigationView.getHeaderView(0);
//        mTvUserName = (LatoBoldTextview) mNavigationHeaderView.findViewById(R.id.tv_nav_name);
//        mIvDrawerProfile = (ImageView) mNavigationHeaderView.findViewById(R.id.iv_nav_profile_picture);


        //mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        // Call the ActionBar customizer. We do the basic setup. Child activities
        // can override this function to get better control over the action bar.
        // The action bar and toolbar instances should not be misused anywhere else.
        // Hence made private and setup here. Child activities can save a local instance
        // of action bar from here on. But they cannot manipulate before this level.
        customizeActionBar(mActionBar, mToolbar);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * Method to customize the action bar based on each activity's needs.
     */
    protected void customizeActionBar(ActionBar actionBar, Toolbar toolbar) {
        // actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setTitle("");
    }


    public final void setActionBarTitle(String title) {
        mActionBar.setTitle(title);
    }

    // ----------------------------------------------
    // - LAYOUT STRUCTURE CHANGES
    // ----------------------------------------------

    /**
     * Sets the content view for this activity. Since we are using navigation
     * bar, content view cannot be used as before.
     *
     * @param view View to be set for the fragment or layout that is to be shown
     *             as the main content layout.
     */
    protected final void setMainContentView(View view) {
        try {
            if (mFlContentLayout != null) {
                mFlContentLayout.addView(view);
            }
        } catch (Exception e) {
            Logger.d(e.getLocalizedMessage());
            Logger.d(e.getMessage());
        }
    }

    public void buttonEnabled(TextView view, boolean selection) {
        if (selection) {
            view.setEnabled(true);
            view.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            view.setTextColor(getResources().getColor(R.color.colorPrimary));
        } else {
            view.setEnabled(false);
            view.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            view.setTextColor(getResources().getColor(R.color.colorPrimary));
        }
    }

    public TextWatcher performTextChange(final EditText mEdittext) {

        TextWatcher watch = new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                mEdittext.setHintTextColor(getResources().getColor(R.color.colorPrimary));
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence s, int a, int b, int c) {
                // TODO Auto-generated method stub


            }
        };
        return watch;

    }

    /**
     * Sets the content view for this activity. Since we are using navigation
     * bar, content view cannot be used as before.
     *
     * @param layout Layout to be set for the fragment or layout that is to be shown
     *               as the main content layout.
     */
    protected final void setMainContentView(int layout) {
        try {
            if (mFlContentLayout != null) {
                Logger.d(TAG, "setMainContentView - setting the Layout with ID: " + layout);
                mFlContentLayout.addView(getLayoutInflater().inflate(layout, null));
            }
        } catch (Exception e) {
            Logger.d(e.getLocalizedMessage());
            Logger.d(e.getMessage());
        }
    }

    /**
     * Clears all the child views associated with the content view.
     */
    protected final void cleanMainContentView() {
        mFlContentLayout.removeAllViews();
    }

    /**
     * Look for a child with the given id in the Content layout.
     *
     * @param id Id of the view to search for in Content layout
     * @return View associated with the given ID
     */
    protected final View findViewByIdInContent(int id) {
        return mFlContentLayout.findViewById(id);
    }

    // ---------------------------------------------
    // - TAB BAR RELATED
    // ---------------------------------------------

    protected final void addTabLayout(int layout) {
        View view = getLayoutInflater().inflate(layout, null);
        mAppBarLayout.addView(view);
    }

    protected final void addTabLayout(View view) {
        mAppBarLayout.addView(view);
    }

    // ----------------------------------------------
    // - PROGRESS DIALOG FUNCTIONS
    // ----------------------------------------------

    protected void showNonCancelableProgressDialog(int msgResourceId) {
        showNonCancelableProgressDialog(getString(msgResourceId));
    }

    protected void showNonCancelableProgressDialog(String message) {
        initDialog();
        mProgressDialog.showNonCancelable(message);
    }

    public void showProgressDialog(int msgResourceId) {
        showProgressDialog(getString(msgResourceId));
    }

    public void showProgressDialog(String message) {
        initDialog();
        mProgressDialog.setCancelable(false);
        mProgressDialog.show(message);
    }

    private void initDialog() {
        // If a progress dialog instance is not available, create one.
        if (mProgressDialog == null) {
            mProgressDialog = new CustomProgressDialog(this);
        }
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
            mProgressDialog.dismiss();
        }
    }

    // -------------------------------------------
    // - ALERT DIALOG FUNCTIONS
    // -------------------------------------------

    public void showErrorDialog(String message) {
        showErrorDialog(null, message);
    }

    public void showErrorDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);

        if (title != null && title.length() > 0) {
            // We can show an alert dialog with an empty title. Hence this check.
            builder.setTitle(title);
        }

        // Generic error dialogs are used for information only. User only clicks on OK to dismiss it.
        builder.setNegativeButton(getString(R.string.okay), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        mAlertDialog = builder.create();
        mAlertDialog.setCancelable(false);
        mAlertDialog.show();
    }

    /* public void showFinishableDialog(String message, final Context mContext) {
         AlertDialog mAlert;
         AlertDialog.Builder builder = new AlertDialog.Builder(this);
         builder.setMessage(message);
         builder.setNegativeButton(getString(R.string.okay), new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialog, int which) {
                 dialog.dismiss();
                 Intent intent = new Intent(mContext.getApplicationContext(), HospitalTicketAdmissionActivity.class);
                 intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                 intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                 mContext.startActivity(intent);
                 finish();
             }
         });

         mAlert = builder.create();
         mAlert.setCancelable(false);
         mAlert.show();

     }*/
    public void showClosingDialog(String message) {
        AlertDialog mAlert;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setNegativeButton(getString(R.string.okay), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });

        mAlert = builder.create();
        mAlert.setCancelable(false);
        mAlert.show();

    }

    public View getActionbarView(int signaturepad_actionbar) {

        LayoutInflater inflator = LayoutInflater.from(this);
        View v = inflator.inflate(signaturepad_actionbar, null);
        return v;

    }

    /*public void showRemoveDialog(String message, String positiveButtonText, String negativeButtonText, View.OnClickListener listener) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        final View dialogView = LayoutInflater.from(this).inflate(R.layout.custom_dialog, null);
        dialogBuilder.setView(dialogView);
        final TextView tvMessage = (TextView) dialogView.findViewById(R.id.tv_delete_message);
        final TextView tvDeleteYes = (TextView) dialogView.findViewById(R.id.tv_delete_yes);
        final TextView tvDeleteNo = (TextView) dialogView.findViewById(R.id.tv_delete_no);

        tvMessage.setText(message);
        tvDeleteYes.setText(positiveButtonText);
        tvDeleteNo.setText(negativeButtonText);

        tvDeleteNo.setOnClickListener(listener);
        tvDeleteYes.setOnClickListener(listener);

        alertDialog = dialogBuilder.create();
        alertDialog.show();
    }*/

    /**
     * Used to hide both Error dialog and Long press dialog
     */
    public void hideAlertDialog() {
        if (mAlertDialog != null && mAlertDialog.isShowing()) {
            mAlertDialog.dismiss();
        }
    }

    /**
     * @param email
     * @return
     */
    public boolean IsInValidEmail(String email) {
        return !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    /**
     * Check null for Edit text
     *
     * @param editText
     * @return
     */
    public boolean IsNull(EditText editText) {
        String strValue = editText.getText().toString();
        if (strValue != null && strValue.trim().length() > 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * @param editText
     * @return
     */
    public String GetString(EditText editText) {
        return editText.getText().toString();
    }

    // -------------------------------------------
    // - UTILITY FUNCTIONS
    // -------------------------------------------

    /**
     * Hides the virtual Keyboard if its showing currently
     */
    public void hideSoftKeyBoard() {
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputManager.isAcceptingText()) {
            if (getCurrentFocus() != null) {
                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        }
    }

    public void showSoftKeyboard(View view) {
        if (view.requestFocus()) {
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    /* Handling Permission Check */
    private PermissionListener mPermissionListener;

    public boolean checkPermission(PermissionUtil permission, PermissionListener listener) {
        /*Check requested permission is already granted or not*/
        boolean permissionStatus = ContextCompat.checkSelfPermission(this, permission.getPermission()) == PackageManager.PERMISSION_GRANTED;
        if (permissionStatus) {
            return true;
        } else {
            mPermissionListener = listener;
            ActivityCompat.requestPermissions(this, new String[]{permission.getPermission()}, permission.ordinal());
            return false;
        }
    }


    public void showAppPermissionDialog(){
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.CAMERA}, 100);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        boolean isPermissionGranted = grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED;
        switch (requestCode) {
            case 0:
                mPermissionListener.onPermissionResult(PermissionUtil.RECORD_AUDIO, isPermissionGranted);
                return;
            case 1:
                mPermissionListener.onPermissionResult(PermissionUtil.CAMERA, isPermissionGranted);
                return;
            case 2:
                mPermissionListener.onPermissionResult(PermissionUtil.CAM_EXTERNAL_STORAGE, isPermissionGranted);
                break;
            case 3:
                mPermissionListener.onPermissionResult(PermissionUtil.EXTERNAL_STORAGE, isPermissionGranted);
                break;
            case 4:
                mPermissionListener.onPermissionResult(PermissionUtil.READ_CONTACT, isPermissionGranted);
                break;
            case 5:
                mPermissionListener.onPermissionResult(PermissionUtil.ACCESS_FINE_LOCATION, isPermissionGranted);
                break;
            case 6:
                mPermissionListener.onPermissionResult(PermissionUtil.CALL_PHONE, isPermissionGranted);
                break;
        }
    }

    @Override
    public void update(Observable observable, Object data) {

    }

    public static InputFilter EMOJI_FILTER = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            for (int index = start; index < end; index++) {
                int type = Character.getType(source.charAt(index));
                if (type == Character.SURROGATE) {
                    return "";
                }
            }
            return null;
        }
    };

}
