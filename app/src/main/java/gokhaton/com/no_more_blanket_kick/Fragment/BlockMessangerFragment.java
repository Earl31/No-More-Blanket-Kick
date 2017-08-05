package gokhaton.com.no_more_blanket_kick.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import gokhaton.com.no_more_blanket_kick.AppBlocker;
import gokhaton.com.no_more_blanket_kick.R;

/**
 * Created by Earl-PC on 2017. 8. 5..
 */

public class BlockMessangerFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.block_messanger_fragment,
                container, false);

        Button startService = (Button)rootView.findViewById(R.id.startService);
        startService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),AppBlocker.class);
                getContext().startService(intent);
            }
        });


        return rootView;
    }
}
