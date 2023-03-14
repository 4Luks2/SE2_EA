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

    private String test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUserInput = findViewById(R.id.etUserInput);
        tvReturnData = findViewById(R.id.tvReturnData);
        btnSend = findViewById(R.id.btnSend);

    }

    public void onClickSendToServer(View view) {

        System.out.println("hi");

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //int userInput = Integer.parseInt(etUserInput.getText().toString());
                    String userInput = etUserInput.getText().toString();
                    String serverOutput;
                    test = userInput;

                    BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

                    Socket socket = new Socket(serverName,serverPort);

                   DataOutputStream outToServer = new DataOutputStream(socket.getOutputStream());

                    BufferedReader inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    outToServer.writeBytes(userInput + '\n');
                    serverOutput = inFromServer.readLine();

                    socket.close();
                    System.out.println(userInput);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println(userInput);

                            tvReturnData.setText(serverOutput);

                        }
                    });

                    System.out.println(test);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

    }
}


