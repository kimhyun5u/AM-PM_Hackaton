package org.sosos.liferoad;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;

public class MainActivity extends AppCompatActivity {
    private TextView tvstate;
    private Button btn;
    private BluetoothSPP bt;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvstate = findViewById(R.id.tv_state);
        btn = findViewById(R.id.button);
        bt = new BluetoothSPP(this);

        if (!bt.isBluetoothAvailable()) { //블루투스 사용 불가
            Toast.makeText(getApplicationContext()
                    , "Bluetooth is not available"
                    , Toast.LENGTH_SHORT).show();
        }
        bt.setOnDataReceivedListener(new BluetoothSPP.OnDataReceivedListener() { //데이터 수신
            public void onDataReceived(byte[] data, String message) {
                if(message == "정상")
                    tvstate.setText("정상입니다!!");
                else {
                    tvstate.setText("화재가 일어났습니다!!");
                    Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:"+"119")); //화재시 자동으로 119에 전화 할 수 있게 연결
                    startActivity(intent);
                }
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),
                        "새로고침 되었습니다."
                ,Toast.LENGTH_SHORT).show();

                bt.setOnDataReceivedListener(new BluetoothSPP.OnDataReceivedListener() { //데이터 수신
                    public void onDataReceived(byte[] data, String message) {
                        if(message == "정상")
                            tvstate.setText("정상입니다!!");
                        else
                            tvstate.setText("화재가 일어났습니다!!");
                            Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:"+"119"));
                            startActivity(intent);
                    }
                });
            }
        });
    }
}
