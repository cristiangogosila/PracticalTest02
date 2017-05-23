package ro.pub.cs.systems.eim.practicaltest02;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PracticalTest02MainActivity extends AppCompatActivity implements View.OnClickListener{


    EditText hour , port;
    Button set , reset , poll , start;
    ServerThread serverThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test02_main);

        hour = (EditText)findViewById(R.id.timp);
        port = (EditText)findViewById(R.id.port);
        set = (Button)findViewById(R.id.set_button);
        reset = (Button)findViewById(R.id.reset_button);
        poll = (Button)findViewById(R.id.poll_button);
        start = (Button)findViewById(R.id.start_button);

        set.setOnClickListener(this);
        reset.setOnClickListener(this);
        poll.setOnClickListener(this);
        start.setOnClickListener(this);

        serverThread = null;

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == set.getId())
        {
            String clientAddress = "localhost";
            String clientPort    = port.getText().toString();
            String request = "set";

            if (clientAddress == null || clientAddress.isEmpty() ||
                    clientPort == null || clientPort.isEmpty()) {
                Toast.makeText(
                        getApplicationContext(),
                        "Client connection parameters should be filled!",
                        Toast.LENGTH_SHORT
                ).show();
                return;
            }
            if (serverThread == null || !serverThread.isAlive()) {
                Log.e("ERROR", "[MAIN ACTIVITY] There is no server to connect to!");
                return;
            }

            String informationType = hour.getText().toString();

            if (informationType == null || informationType.isEmpty()) {
                Toast.makeText(
                        getApplicationContext(),
                        "Parameters from client (information type) should be filled!",
                        Toast.LENGTH_SHORT
                ).show();
                return;
            }

            ClientThread clientThread = new ClientThread(
                    clientAddress,
                    Integer.parseInt(clientPort),
                    informationType,
                    request);
            clientThread.start();
        }
        else if (v.getId() == reset.getId())
        {
            String clientAddress = "localhost";
            String clientPort    = port.getText().toString();
            String request = "reset";

            if (clientAddress == null || clientAddress.isEmpty() ||
                    clientPort == null || clientPort.isEmpty()) {
                Toast.makeText(
                        getApplicationContext(),
                        "Client connection parameters should be filled!",
                        Toast.LENGTH_SHORT
                ).show();
                return;
            }
            if (serverThread == null || !serverThread.isAlive()) {
                Log.e("ERROR", "[MAIN ACTIVITY] There is no server to connect to!");
                return;
            }

            String informationType = hour.getText().toString();

            if (informationType == null || informationType.isEmpty()) {
                Toast.makeText(
                        getApplicationContext(),
                        "Parameters from client (information type) should be filled!",
                        Toast.LENGTH_SHORT
                ).show();
                return;
            }

            ClientThread clientThread = new ClientThread(
                    clientAddress,
                    Integer.parseInt(clientPort),
                    informationType,
                    request);
            clientThread.start();
        }
        else if (v.getId() == poll.getId())
        {
            String clientAddress = "localhost";
            String clientPort    = port.getText().toString();
            String request = "poll";

            if (clientAddress == null || clientAddress.isEmpty() ||
                    clientPort == null || clientPort.isEmpty()) {
                Toast.makeText(
                        getApplicationContext(),
                        "Client connection parameters should be filled!",
                        Toast.LENGTH_SHORT
                ).show();
                return;
            }
            if (serverThread == null || !serverThread.isAlive()) {
                Log.e("ERROR", "[MAIN ACTIVITY] There is no server to connect to!");
                return;
            }

            String informationType = hour.getText().toString();

            if (informationType == null || informationType.isEmpty()) {
                Toast.makeText(
                        getApplicationContext(),
                        "Parameters from client (information type) should be filled!",
                        Toast.LENGTH_SHORT
                ).show();
                return;
            }

            ClientThread clientThread = new ClientThread(
                    clientAddress,
                    Integer.parseInt(clientPort),
                    informationType,
                    request);
            clientThread.start();
        }
        else if (v.getId() == start.getId())
        {
            if (serverThread == null) {
                String serverPort = port.getText().toString();
                if (serverPort == null || serverPort.isEmpty()) {
                    Toast.makeText(
                            getApplicationContext(),
                            "Server port should be filled!",
                            Toast.LENGTH_SHORT
                    ).show();
                    return;
                }
                serverThread = new ServerThread(Integer.parseInt(serverPort));
                if (serverThread.getServerSocket() != null) {
                    serverThread.start();
                } else {
                    Log.e("ERROR", "[MAIN ACTIVITY] Could not creat server thread!");
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        if (serverThread != null) {
            serverThread.stopThread();
        }
        super.onDestroy();
    }
}
