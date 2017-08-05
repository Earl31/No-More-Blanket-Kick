package gokhaton.com.no_more_blanket_kick;

import android.app.ActivityManager;
import android.app.Service;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class AppBlocker extends Service {


    private View mAppBlockerView;
    boolean mAppBlockerViewChk = false;
    private View mCallAppBlockerView;
    boolean mCallAppBlockerViewChk = false;
    private WindowManager mManager;
    @Override
    public void onCreate() {

        super.onCreate();
        Toast.makeText(this,"서비스 시작",Toast.LENGTH_LONG).show();





    }

    public void resetOverlay(){
        if(mAppBlockerViewChk) {
            mManager.removeView(mAppBlockerView);
            mAppBlockerViewChk=false;
        }
        if(mCallAppBlockerViewChk) {
            mManager.removeView(mCallAppBlockerView);
            mCallAppBlockerViewChk=false;
        }
    }
    public void onStartOverlay(){
        if(!mAppBlockerViewChk) {

            Log.d("status", "onStartOverlay");

            LayoutInflater mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mAppBlockerView = mInflater.inflate(R.layout.appblocker, null);

            WindowManager.LayoutParams mParams = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.MATCH_PARENT, //너비
                    WindowManager.LayoutParams.WRAP_CONTENT,  //높이
                    200, 800, //상대좌표 못함?
                    WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,  //종

                    WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, //flags

                    PixelFormat.TRANSLUCENT); //format

            mManager = (WindowManager) getSystemService(WINDOW_SERVICE);
            mManager.addView(mAppBlockerView, mParams);
            mAppBlockerView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });
        }
        mAppBlockerViewChk = true;
    } //메신저 앱 overlay창을 띄워 줌

    public void onStartCallOverlay(){
        if(!mCallAppBlockerViewChk) {
            Log.d("status", "onStartCallOverlay");

            LayoutInflater mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            mCallAppBlockerView = mInflater.inflate(R.layout.callappblocker, null);

            WindowManager.LayoutParams mParams = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.MATCH_PARENT, //너비
                    WindowManager.LayoutParams.MATCH_PARENT,  //높이

                    WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,  //종

                    WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, //flags

                    PixelFormat.TRANSLUCENT); //format

            mManager = (WindowManager) getSystemService(WINDOW_SERVICE);
            mManager.addView(mCallAppBlockerView, mParams);
            mCallAppBlockerView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });
        }
        mCallAppBlockerViewChk=true;
    } //전화 관련 앱 전체 화면 오버레이 창을 띄워 줌


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Timer timer;
        TimerTask timertask = new TimerTask(){
            @Override
            public void run() {
                Log.d("d:", "쓰레드 실행 중");
                String packageName = getTopAppName(getApplication());


                if (packageName.equals("COM.KAKAO.TALK".toLowerCase())) {
                    Handler mHandler = new Handler(Looper.getMainLooper());
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            onStartOverlay();

                        }
                    }, 0);
                }
                else if (packageName.equals("COM.SAMSUNG.ANDROID.CONTACTS".toLowerCase())) {
                    Handler mHandler = new Handler(Looper.getMainLooper());
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            onStartCallOverlay();

                        }
                    }, 0);
                }
                else{

                    Handler mHandler = new Handler(Looper.getMainLooper());
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            resetOverlay();

                        }
                    }, 0);
                }

            }
        };
        timer = new Timer();
        timer.schedule(timertask, 1000, 1000);


        return super.onStartCommand(intent, flags, startId);
    }

    public static String getTopAppName(Context context) {
        ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        String strName = "";
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                strName = getLollipopFGAppPackageName(context);
            } else {
                strName = mActivityManager.getRunningTasks(1).get(0).topActivity.getClassName();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strName;
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private static String getLollipopFGAppPackageName(Context ctx) {

        try {
            UsageStatsManager usageStatsManager = (UsageStatsManager) ctx.getSystemService("usagestats");
            long milliSecs = 60 * 1000;
            Date date = new Date();
            List<UsageStats> queryUsageStats = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, date.getTime() - milliSecs, date.getTime());
            if (queryUsageStats.size() > 0) {
                Log.i("LPU", "queryUsageStats size: " + queryUsageStats.size());
            }
            long recentTime = 0;
            String recentPkg = "";
            for (int i = 0; i < queryUsageStats.size(); i++) {
                UsageStats stats = queryUsageStats.get(i);
                if (i == 0 && !"org.pervacio.pvadiag".equals(stats.getPackageName())) {
                    Log.i("LPU", "PackageName: " + stats.getPackageName() + " " + stats.getLastTimeStamp());
                }
                if (stats.getLastTimeStamp() > recentTime) {
                    recentTime = stats.getLastTimeStamp();
                    recentPkg = stats.getPackageName();
                }
            }
            return recentPkg;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mManager.removeView(mCallAppBlockerView);
        mManager.removeView(mAppBlockerView);

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
