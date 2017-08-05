package gokhaton.com.no_more_blanket_kick;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.maps.model.DirectionsLeg;

/**
 * Created by josh on 2017. 8. 6..
 */

public class DestinationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination);
        Intent intent = getIntent();
        DirectionsLeg legs = (DirectionsLeg) intent.getSerializableExtra("transport");

        TextView textView = (TextView) findViewById(R.id.transportSummaryView);
        textView.setText(legs.toString());

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
