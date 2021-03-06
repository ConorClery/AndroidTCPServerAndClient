package com.example.root.OOPsMusic;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;

/**
 * Created by root on 25/01/18.
 */

public class JoinSession extends AppCompatActivity {

    Button goBackHome;
    TCPClient client;
    TextView view;
    TextView serverResponseView;
    EditText ipAddress;
    EditText portNumber;
    EditText transmission;
    Handler updateServerResponse;
    Button sendTransMission;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_a_server_screen);
        instantiateThreads();
    }

    protected void instantiateThreads() {
        view = (TextView) findViewById(R.id.ConnectedToView);
        view.setText("Dont crash plox");

        serverResponseView = (TextView) findViewById(R.id.viewFive);
        serverResponseView.setText("Nothing back yet");

        client = new TCPClient();

        updateServerResponse = new Handler();
        updateServerResponse.post(updateServerResponseView);

        goBackHome = (Button) findViewById(R.id.button4);
        goBackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    Runnable updateServerResponseView = new Runnable() {

        @Override
        public void run() {
            String toUpdate = client.getMostRecentResponse();
            serverResponseView.setText(toUpdate);
            updateServerResponse.postDelayed(this, 1000);
        }
    };


    public void sendIpAndPortData(View button) {
        ipAddress = (EditText) findViewById(R.id.IPInputField);
        String ip = ipAddress.getText().toString();

        portNumber = (EditText) findViewById(R.id.PortInputField);
        String port = (portNumber.getText().toString());
        int portNum = Integer.parseInt(portNumber.getText().toString());
        String txt = "CLICKED, ip: " + ip + ", port: " + port;
        view.setText(txt);

        client.setIPandPortNum(ip, 1978);
        client.start();

    }

    public void sendTransmission(View button) {
        if (client.isConnected()) {
            transmission = (EditText) findViewById(R.id.TransmissionInputField);
            String toSend = transmission.getText().toString();
            try {
                client.sendTrans(toSend);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
