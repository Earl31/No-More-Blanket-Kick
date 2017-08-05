package gokhaton.com.no_more_blanket_kick;

import android.content.Intent;
import android.location.LocationManager;
import android.preference.Preference;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout home_btn;
    private Switch aSwitch;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);

        home_btn = (RelativeLayout) findViewById(R.id.home_change_btn);
        aSwitch = (Switch) findViewById(R.id.gps_settings_switch);

        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            aSwitch.setChecked(true);
        }else{
            aSwitch.setChecked(false);
        }

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivityForResult(intent, 1);
                }
            }
        });
        home_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch(v.getId()){
            case R.id.home_change_btn:
                intent = new Intent(SettingsActivity.this, LocationRegisterActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1){

            if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                aSwitch.setChecked(true);
            }else{
                aSwitch.setChecked(false);
            }

        }
    }
}
