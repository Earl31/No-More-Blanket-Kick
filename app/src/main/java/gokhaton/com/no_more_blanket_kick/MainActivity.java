package gokhaton.com.no_more_blanket_kick;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import gokhaton.com.no_more_blanket_kick.Fragment.BlockMessangerFragment;
import gokhaton.com.no_more_blanket_kick.Fragment.BlockedFragment;
import gokhaton.com.no_more_blanket_kick.Fragment.EmergencyCallFragment;
import gokhaton.com.no_more_blanket_kick.Fragment.MapFragment;
import gokhaton.com.no_more_blanket_kick.Fragment.TransportFragment;

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

    }
}
