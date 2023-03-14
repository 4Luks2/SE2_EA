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
    private String serverName = "se2-isys.aau.at";            //"se2-isys.aau.at"; "time.nist.gov";
    private int serverPort = 53212;               //53212; 13;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUserInput = findViewById(R.id.etUserInput);
        tvReturnData = findViewById(R.id.tvReturnData);
        btnSend = findViewById(R.id.btnSend);

    }

    public void onClickSendToServer(View view) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String userInput = etUserInput.toString();
                    String serverOutput;

                    BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

                    Socket socket = new Socket(serverName,serverPort);

                   DataOutputStream outToServer = new DataOutputStream(socket.getOutputStream());

                    BufferedReader inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    //inFromServer.readLine();
                    outToServer.writeBytes(userInput + '\n');
                    serverOutput = inFromServer.readLine();
                    //String serverOutput = inFromServer.readLine().substring(6,23);

                    socket.close();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvReturnData.setText(serverOutput);
                            System.out.println(userInput);
                        }
                    });


                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

    }
}


