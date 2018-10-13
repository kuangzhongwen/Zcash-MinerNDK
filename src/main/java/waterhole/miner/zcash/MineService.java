package waterhole.miner.zcash;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import android.util.Log;

/**
 * 挖矿后台服务.
 *
 * @author kzw on 2018/03/14.
 */
public final class MineService extends Service {

    // kernel文件名
    private static final String KERNEL_FILENAME = "zcash.kernel";

    static {
        try {
            System.loadLibrary("zcash-miner");
        } catch (Exception e) {
            Log.e("MineService", e.getMessage());
        }
    }

    public native void startJNIMine(String packName, StateObserver callback);

    private native void stopJNIMine();

    public native void writeJob(String job);

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        KernelCopy.copy(getApplicationContext(), KERNEL_FILENAME);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        stopJNIMine();
    }

    public static void startService(Context context) {
        if (context != null) {
            Intent intent = new Intent(context, MineService.class);
            context.startService(intent);
        }
    }

    public static void stopService(Context context) {
        if (context != null) {
            Intent intent = new Intent(context, MineService.class);
            context.stopService(intent);
        }
    }
}
