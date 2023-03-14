package com.example.se2_ea;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.*;
import java.net.*;

public class MainActivity extends AppCompatActivity {
    private EditText etUserInput;
    private TextView tvReturnData;
    private Button btnSend;
    private String serverName = "time.nist.gov";            //"se2-isys.aau.at";
    private int serverPort = 13;               //53212;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUserInput = findViewById(R.id.etUserInput);
        tvReturnData = findViewById(R.id.tvReturnData);
        btnSend = findViewById(R.id.btnSend);

    }

    public void onClickSendToServer(View view) {
            String userInput = etUserInput.toString();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket socket = new Socket(serverName,serverPort);




                    BufferedReader inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    inFromServer.readLine();
                    String serverOutput = inFromServer.readLine().substring(6,23);

                    socket.close();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvReturnData.setText(serverOutput);
                        }
                    });


                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

    }
}


