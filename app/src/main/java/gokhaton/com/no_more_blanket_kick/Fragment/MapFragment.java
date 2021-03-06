package gokhaton.com.no_more_blanket_kick.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import gokhaton.com.no_more_blanket_kick.LocationRegisterActivity;
import gokhaton.com.no_more_blanket_kick.R;
import gokhaton.com.no_more_blanket_kick.constant.Prefs;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Earl-PC on 2017. 8. 5..
 */

public class MapFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.map_fragment,
                container, false);


        if(!checkHomeLocationRegistered()){
            showLocationAlert();
        }
        return rootView;
    }


    public boolean checkHomeLocationRegistered()
    {
        SharedPreferences pref = getContext().getSharedPreferences(Prefs.PREF_NAME, MODE_PRIVATE);
        String location = pref.getString("location",null);

        return location != null;

    }

    public boolean showLocationAlert(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                getActivity());

        // 제목셋팅
        alertDialogBuilder.setTitle("위치 정보 등록");

        // AlertDialog 셋팅
        alertDialogBuilder
                .setMessage("위치 정보가 등록되어 있지 않습니다.\n앱을 이용하기 위해서 먼저 위치를 등록해주세요")
                .setCancelable(false)
                .setPositiveButton("등록",
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialog, int id) {
                                Intent intent = new Intent(getActivity(), LocationRegisterActivity.class);
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
