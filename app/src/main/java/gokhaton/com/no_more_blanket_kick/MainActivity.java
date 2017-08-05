package gokhaton.com.no_more_blanket_kick;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import gokhaton.com.no_more_blanket_kick.Fragment.BlockMessangerFragment;
import gokhaton.com.no_more_blanket_kick.Fragment.BlockedFragment;
import gokhaton.com.no_more_blanket_kick.Fragment.EmergencyCallFragment;
import gokhaton.com.no_more_blanket_kick.Fragment.MapFragment;
import gokhaton.com.no_more_blanket_kick.Fragment.TransportFragment;
import gokhaton.com.no_more_blanket_kick.constant.Prefs;

public class MainActivity extends AppCompatActivity {

    private TabLayout main_tab;
    private FrameLayout container;

    private Fragment fragment1;
    private Fragment fragment2;
    private Fragment fragment3;
    private Fragment fragment4;
    private Fragment fragment5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        main_tab = (TabLayout) findViewById(R.id.main_tab);
        container = (FrameLayout) findViewById(R.id.main_container);

        fragment1 = new EmergencyCallFragment();
        fragment2 = new BlockedFragment();
        fragment3 = new MapFragment();
        fragment4 = new TransportFragment();
        fragment5 = new BlockMessangerFragment();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_container, fragment1).commit();

        main_tab.addTab(main_tab.newTab().setText("비상 연락"));
        main_tab.addTab(main_tab.newTab().setText("차단"));
        main_tab.addTab(main_tab.newTab().setText("위치 등록"));
        main_tab.addTab(main_tab.newTab().setText("막차 계산"));
        main_tab.addTab(main_tab.newTab().setText("앱 차단"));

        main_tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                int position = tab.getPosition();

                Fragment selected;

                switch (position){
                    case 0:
                        selected = fragment1;
                        break;
                    case 1:
                        selected = fragment2;
                        break;
                    case 2:
                        selected = fragment3;
                        break;
                    case 3:
                        selected = fragment4;
                        break;
                    default:
                        selected = fragment5;
                        break;
                }

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_container, selected).commit();

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        if(!checkHomeLocationRegistered()){
            showLocationAlert();
        }
    }


    public boolean checkHomeLocationRegistered()
    {
        SharedPreferences pref = getSharedPreferences(Prefs.PREF_NAME, MODE_PRIVATE);
//        SharedPreferences.Editor editor = pref.edit();
        String location = pref.getString("location",null);

        return location != null;

    }

    public boolean showLocationAlert(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                MainActivity.this);

        // 제목셋팅
        alertDialogBuilder.setTitle("위치 정보 등록");

        // AlertDialog 셋팅
        alertDialogBuilder
                .setMessage("위치 정보가 등록되어 있지 않습니다.\n 앱을 이용하기 위해서 먼저 위치를 등록해주세요")
                .setCancelable(false)
                .setPositiveButton("등록",
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialog, int id) {
                                Intent intent = new Intent(MainActivity.this, LocationRegisterActivity.class);
                                startActivity(intent);
                            }
                        })
                .setNegativeButton("취소",
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialog, int id) {
                                // 다이얼로그를 취소한다
                                dialog.cancel();
                            }
                        });

        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.show();

        return true;
    }
}
