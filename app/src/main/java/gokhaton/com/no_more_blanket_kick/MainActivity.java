package gokhaton.com.no_more_blanket_kick;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import gokhaton.com.no_more_blanket_kick.constant.Prefs;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(checkHomeLocationRegistered()){

        }

    }


    public boolean checkHomeLocationRegistered()
    {
        SharedPreferences pref = getSharedPreferences(Prefs.PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        String location = pref.getString("location",null);

        if(location == null){
            return false;
        }
        return true;
    }
    public boolean showLocationAlert(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this.context);

        // 제목셋팅
        alertDialogBuilder.setTitle("프로그램 종료");

        // AlertDialog 셋팅
        alertDialogBuilder
                .setMessage("프로그램을 종료할 것입니까?")
                .setCancelable(false)
                .setPositiveButton("종료",
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialog, int id) {
                                // 프로그램을 종료한다
                                AlertDialogActivity.this.finish();
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

        // 다이얼로그 생성
        AlertDialog alertDialog = alertDialogBuilder.create();

        // 다이얼로그 보여주기
        alertDialog.show();
    }
}
