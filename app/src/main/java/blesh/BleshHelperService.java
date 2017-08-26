package blesh;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.blesh.sdk.util.BleshConstant;
import com.blesh.sdk.util.BleshIntent;

/**
 * @author Skylifee7 on 23/06/2017.
 */

public class BleshHelperService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        isHostAppRunning(true);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        try {
            startService(new BleshIntent.Builder(Config.DEVELOPER_UNAME, Config.DEVELOPER_KEY, Config.INTEGRATION_ID)
                    .notificationColor("#670H5B")
                    .getIntent(this));
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        isHostAppRunning(false);
        stopSelf();
    }

    private void isHostAppRunning(boolean status) {
        Intent intent = new Intent(BleshConstant.BLESH_HOST_APP);
        intent.putExtra(BleshConstant.BLESH_HOST_APP_RUNNING_STATUS, status);
        sendBroadcast(intent);
    }
}

