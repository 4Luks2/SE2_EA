package com.example.se2_ea;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.*;
import java.net.*;

public class MainActivity extends AppCompatActivity {
    private TextView tvReturnSentence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void onClickSendToServer (View view){


    }
   // private class TCPClient {//implements Runnable{

        public void main(String[] argv) throws Exception{

            String sentence;
            String returnSentence;

            Socket clientSocket = new Socket("se2-isys.aau.at",53212);

            BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            //inFromServer.readLine(); //maybe there is an empty line
            sentence = inFromUser.readLine();
            outToServer.writeBytes(sentence + '\n');

            returnSentence = inFromServer.readLine();
            System.out.println("From Server:" + returnSentence);

            clientSocket.close();

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tvReturnSentence.setText(returnSentence);
                }
            });
        }
    }

