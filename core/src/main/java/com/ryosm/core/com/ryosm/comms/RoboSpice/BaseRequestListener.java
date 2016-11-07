
package com.ryosm.core.com.ryosm.comms.RoboSpice;

import android.content.res.Resources;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.octo.android.robospice.exception.RequestCancelledException;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.SpiceRequest;
import com.octo.android.robospice.request.listener.PendingRequestListener;
import com.octo.android.robospice.request.listener.RequestProgress;
import com.octo.android.robospice.request.listener.RequestProgressListener;
import com.octo.android.robospice.request.listener.RequestStatus;
import com.ryosm.core.com.ryosm.comms.api.Response;
import com.ryosm.core.com.ryosm.utils.L;
import com.ryosm.core.com.ryosm.base.CoreBaseActivity;
import com.ryosm.core.com.ryosm.base.CoreBaseDialogFragment;
import com.ryosm.core.com.ryosm.base.CoreBaseFragment;
import com.ryosm.core.com.ryosm.base.CoreLauncherActivity;


    public abstract class BaseRequestListener<RESP> implements
        PendingRequestListener<RESP>,
        RequestProgressListener {

    private static final String TAG = BaseRequestListener.class.getSimpleName();
    private static final String ERROR_TAG = "errorTag";
    private CoreBaseActivity act;
    protected CoreBaseFragment frag;
    protected CoreBaseDialogFragment dfrag;
    protected View rootView;
    private boolean hasRequestToRetry = false;
    protected int status = IDLE;
    private String errorMessage;
    private SpiceRequest<RESP> request;
    private boolean updateUIonSuccess = true;
    private boolean goBackOnCloseDialog = false;
    private boolean goShowKeyboardCloseDialog = false;
    protected boolean hasMessageToShow = false;


    public static final int IDLE = 0;
    public static final int ONGOING = 1;
    public static final int ERROR = 2;

    private boolean isVisibleMultiChoice = false;
    private boolean isShowingWarningMessages = false;
    private boolean isShowingErrorMessages = true;

    public BaseRequestListener() {
        super();
    }

    public BaseRequestListener(CoreBaseActivity act, View rootView) {
        super();
        this.act = act;
        this.rootView = rootView;

    }

    public BaseRequestListener(CoreBaseActivity act, View rootView,
                               SpiceRequest<RESP> request) {
        super();
        this.act = act;
        this.rootView = rootView;

    }


    /**
     * Default success call back, instead on onRequestSuccess
     *
     * @param response
     */
    public abstract void onSuccess(RESP response);

    @Override
    public void onRequestSuccess(final RESP response) {


        try {
            if (response instanceof Response) {
                Response responseObj = (Response) response;
            }

            // Message Ok
            status = IDLE;
            updateUI(rootView);
        } catch (ClassCastException e) {
            L.wtf(TAG, "RESP Generic class not instance of Response", e);
            return;
        } catch (NullPointerException e) {
            status = IDLE;
            updateUI(rootView);
            L.w(TAG, "ResponseObj message incomplete", e);
        }

    }

    @Override
    public void onRequestFailure(SpiceException spiceException) {

        if (spiceException instanceof RequestCancelledException) {
            status = IDLE;
            updateUI(rootView);
            return;
        }
        status = ERROR;
        // Send exception to ErrorHandler

        /*
         * Block error message to be shown
         * */
        if (!isShowingErrorMessages) {
            updateUI(rootView);
            return;
        }

        act = CoreLauncherActivity.getCurrentActivity();


    }

    private Resources getResources() {
        return CoreLauncherActivity.getCurrentActivity().getResources();
    }

    private FragmentManager getSupportFragmentManager() {
        return CoreLauncherActivity.getCurrentActivity().getSupportFragmentManager();
    }

    @Override
    public void onRequestProgressUpdate(RequestProgress progress) {

        // This section controls the default Loading spinning circle
        if (progress.getStatus() == RequestStatus.PENDING
                || progress.getStatus() == RequestStatus.LOADING_FROM_NETWORK) {
            L.v(TAG, "onRequestProgressUpdate PENDING");
            status = ONGOING;
            updateUI(rootView);

        } /*
           * else if (progress.getStatus() == RequestStatus.COMPLETE) {
           * L.v(TAG, "onRequestProgressUpdate COMPLETE"); status = IDLE;
           * updateUI(rootView); }
           */

    }

    @Override
    public void onRequestNotFound() {
    }


//    private boolean hasLoadingView() {
//        if (rootView != null) {
//            View loadingView = rootView.findViewById(R.id.loading_view);
//            return loadingView != null;
//        }
//        return false;
//    }
//
//    private View getLoadingView() {
//        if (rootView != null) {
//            return rootView.findViewById(R.id.loading_view);
//        }
//        return null;
//    }

    public int updateUI(View rootView) {

        //TODO
        return status;
    }


}
