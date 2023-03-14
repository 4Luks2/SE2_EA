package com.example.se2_ea;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.*;
import java.lang.reflect.Array;
import java.net.*;
import java.util.ArrayList;

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
                    String userInput = etUserInput.getText().toString();
                    String serverOutput;

                    //BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

                    Socket socket = new Socket(serverName, serverPort);

                    DataOutputStream outToServer = new DataOutputStream(socket.getOutputStream());

                    BufferedReader inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    outToServer.writeBytes(userInput + '\n');
                    serverOutput = inFromServer.readLine();

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


    public void onClickFilterPrime(View view) {
        String userInput = etUserInput.getText().toString();

        char[] numbers = userInput.toCharArray();
        ArrayList<Integer> primeNumbers = new ArrayList<>(); //= Integer.parseInt(String.valueOf(charArray));

        for (char c : numbers) {
            if(isPrime(c)){
                primeNumbers.add((int) c);
            }
        }

        tvReturnData.setText(toInt(primeNumbers));
    }

    private boolean isPrime(int x) {
        if (x <= 1) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(x); i++) {
            if (x % i == 0) {
                return false;
            }
        }
        return true;
    }

    int toInt(ArrayList<Integer> list) {
        int retVal = 0;
        for (Integer i : list) { // assuming list is of type List<Integer>
            retVal = 10*retVal + i;
        }
        return retVal;
    }
}
