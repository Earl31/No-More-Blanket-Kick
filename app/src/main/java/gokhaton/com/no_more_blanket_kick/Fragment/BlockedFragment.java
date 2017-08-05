package gokhaton.com.no_more_blanket_kick.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import gokhaton.com.no_more_blanket_kick.R;

/**
 * Created by Earl-PC on 2017. 8. 5..
 */

public class BlockedFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.blocked_fragment,
                container, false);

        return rootView;
    }
}
