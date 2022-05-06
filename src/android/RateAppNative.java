package  com.jdsoftwarellc.cordova.rateappnative;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
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

        ReviewManager manager = ReviewManagerFactory.create(this);
        Task<ReviewInfo> request = manager.requestReviewFlow();
        request.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // We can get the ReviewInfo object
                ReviewInfo reviewInfo = task.getResult();
                callbackContext.success(reviewInfo);
            } else {
                // There was some problem, log or handle the error code.
                @ReviewErrorCode
                int reviewErrorCode = ((TaskException) task.getException()).getErrorCode();
                callbackContext.error("Error: "+reviewErrorCode);
            }
        });
    }
}
