package gokhaton.com.no_more_blanket_kick;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static android.provider.Settings.ACTION_MANAGE_OVERLAY_PERMISSION;

public class AppBlocker extends Service {


    private View mView;
    private WindowManager mManager;
    @Override
    public void onCreate() {

        super.onCreate();
        Log.d("status","OnCreate");
        if(checkSelfPermission(this, ACTION_MANAGE_OVERLAY_PERMISSION)== PackageManager.PERMISSION_GRANTED)
            onStartOverlay();
        //앱이 전화 앱일 경우 -> onStartCallOverlay
        else{
            Toast.makeText(this, "권한이 없습니다", Toast.LENGTH_LONG).show();
        }

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
                WindowManager.LayoutParams.WRAP_CONTENT,  //높이

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
    }

    public AppBlocker() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");

    }

}
