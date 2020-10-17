package com.kohei.gyouretukannyakuka;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class MainActivity extends AppCompatActivity {

    Matrix m;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //広告表示
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        TextView tv = findViewById(R.id.disp);
        m = new Matrix(4, 5, 10);	//デバッグ用自動行列生成
        m.print(tv);
//        tv.append("\n\n");
//        m.hakidashi(tv);
//        m.print(tv);

        Button btnConfirm = findViewById(R.id.btnSubmitSize);
        Button btnDebug = findViewById(R.id.btnDebug);
        Listener listener = new Listener();
        btnDebug.setOnClickListener(listener);
        btnConfirm.setOnClickListener(listener);

    }

    private class Listener implements View.OnClickListener{

        @Override
        public void onClick(View view){
            int id = view.getId();
            switch(id) {
                case R.id.btnSubmitSize:
                    EditText row = findViewById(R.id.rowSize);
                    EditText column = findViewById(R.id.columnSize);
                    //EditTextの文字を読み取り1~9
                    if (row.getText().toString().matches("[1-9]") && column.getText().toString().matches("[1-9]")){
                        //インテントをnewする
                        Intent intent = new Intent(MainActivity.this, InputMatrix.class);
                        //新しいアクテビティに行列サイズ情報を送る
                        intent.putExtra("row", Integer.parseInt(row.getText().toString()));
                        intent.putExtra("column", Integer.parseInt(column.getText().toString()));
                        //アクテビティ生成
                        startActivity(intent);
                    }else {
                        Toast.makeText(MainActivity.this, R.string.err, Toast.LENGTH_LONG).show();
                    }
                    break;

                case R.id.btnDebug:
                    Intent intent = new Intent(MainActivity.this, Result.class);
                    intent.putExtra("Matrix", m);
                    startActivity(intent);
                    break;
            }
        }
    }

}