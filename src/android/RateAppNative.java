package  com.jdsoftwarellc.cordova.rateappnative;

import android.app.Activity;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.apache.cordova.LOG;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.play.core.review.ReviewException;
import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.android.play.core.review.model.ReviewErrorCode;
import com.google.android.play.core.tasks.Task;

/**
 * This class echoes a string called from JavaScript.
 */
public class RateAppNative extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("rate")) {
            this.rate(callbackContext);
            return true;
        }
        return false;
    }

    private void rate(CallbackContext callbackContext) {
         Activity activity = this.cordova.getActivity();
         ReviewManager manager = ReviewManagerFactory.create(activity);
         Task<ReviewInfo> request = manager.requestReviewFlow();
         request.addOnCompleteListener(task -> {
             if (task.isSuccessful()) {
                 // We can get the ReviewInfo object
                 ReviewInfo reviewInfo = task.getResult();
                 Task<Void> flow = manager.launchReviewFlow(activity, reviewInfo);
                 flow.addOnCompleteListener(launchTask -> {
                     if (task.isSuccessful()) {
                         LOG.d("RateAppNatice", "launch review success");
                         callbackContext.success();
                     } else {
                         Exception error = task.getException();
                         LOG.d("RateAppNatice", "Failed to launch review", error);
                         callbackContext.error("Failed to launch review - " + error.getMessage());
                     }
                 });
             } else {
                 Exception error = task.getException();
                 LOG.d("AppRate", "Failed to launch review", error);
                 callbackContext.error("Failed to launch review flow - " + error.getMessage());
             }
         });
    }
}
