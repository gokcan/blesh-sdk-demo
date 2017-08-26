package blesh;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.blesh.demo.bleshapp.TransactionActivity;
import com.blesh.sdk.util.BleshConstant;

/**
 * @author Skylifee7 on 23/06/2017.
 */

public class BleshTemplateResultReceiver extends BroadcastReceiver {

        private static final String TAG = "BleshTemplate";
        public static final String EXTRA_MESSAGE = "TRANSACTION_MESSAGE";
        Context mContext;

        @Override
        public void onReceive(Context context, Intent intent) {

            mContext = context;

            if (intent.getAction().equals(BleshConstant.BLESH_TEMPLATE_RESULT_ACTION)) {

                String actionType = intent.getStringExtra(BleshConstant.BLESH_ACTION_TYPE);
                String actionValue = intent.getStringExtra(BleshConstant.BLESH_ACTION_VALUE);

                if (actionType != null && actionValue != null) {

                    switch (actionType) {
                        case "MENU": sendMessage(actionValue);
                        /*
                        You may want to increase the case possibilities here, like below:
                        case: "ADMOB"
                        case: "VIRTUAL_AVM"
                        case: "SMART_CAR_KEY"
                         */
                        default: sendMessage(actionValue);
                    }
                }
            }
    }

    private void sendMessage(String actionValue) {
        Intent intent = new Intent(mContext.getApplicationContext(),TransactionActivity.class);
        intent.putExtra(EXTRA_MESSAGE, actionValue);
        mContext.getApplicationContext().startActivity(intent);
    }
}
