package gokhaton.com.no_more_blanket_kick;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

public class AppBlocker extends Service {


    private View mView;
    private WindowManager mManager;
    @Override
    public void onCreate() {

        super.onCreate();
        Log.d("status","OnCreate");
        onStartOverlay();

    }

    public void onStartOverlay(){
        Log.d("status", "onStartOverlay");

        LayoutInflater mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = mInflater.inflate(R.layout.appblocker, null);

        WindowManager.LayoutParams mParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT, //너비
                WindowManager.LayoutParams.WRAP_CONTENT,  //높이

                WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,  //종

                WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, //flags

                PixelFormat.TRANSLUCENT); //format

        mManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        mManager.addView(mView, mParams);
    } //메신저 앱 overlay창을 띄워 줌

    public void onStartCallOverlay(){
        Log.d("status", "onStartCallOverlay");

        LayoutInflater mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        mView = mInflater.inflate(R.layout.callappblocker, null);

        WindowManager.LayoutParams mParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT, //너비
                WindowManager.LayoutParams.MATCH_PARENT,  //높이

                WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,  //종

                WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, //flags

                PixelFormat.TRANSLUCENT); //format

        mManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        mManager.addView(mView, mParams);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("sedvice:", "시작");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mManager.removeView(mView);
        mManager = null;
    }

    public AppBlocker() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");

    }

}
