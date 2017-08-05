package gokhaton.com.no_more_blanket_kick;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout home_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        home_btn = (RelativeLayout) findViewById(R.id.home_change_btn);
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
}
