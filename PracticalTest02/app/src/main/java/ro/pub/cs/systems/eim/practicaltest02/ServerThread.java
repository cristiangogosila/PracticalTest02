package ro.pub.cs.systems.eim.practicaltest02;

import android.util.Log;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

/**
 * Created by student on 23.05.2017.
 */
public class ServerThread extends Thread{

    private int port;
    private ServerSocket serverSocket;
    private HashMap<String , StockInformation> data;


    public HashMap<String, StockInformation> getData() {
        return data;
    }

    public synchronized void setData(String info, StockInformation stockInfo) {
        data.put(info, stockInfo);
    }

    public ServerThread( int port)
    {
        this.port = port;
        data = new HashMap<>();

        try {
            serverSocket = new ServerSocket(this.port);
        } catch (IOException ioException) {
            Log.e("SERVER", "An exception has occurred: " + ioException.getMessage());
        }
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Log.i("SERVER", "[SERVER] Waiting for a connection...");
                Socket socket = serverSocket.accept();
                Log.i("SERVER", "[SERVER] A connection request was received from " + socket.getInetAddress() + ":" + socket.getLocalPort());
                CommunicationThread communicationThread = new CommunicationThread(this, socket);
                communicationThread.start();
            }
        } catch (IOException ioException) {
            Log.e("SERVER", "An exception has occurred: " + ioException.getMessage());
        }
    }

    public Object getServerSocket() {
        return serverSocket;
    }

    public void stopThread() {
        if (serverSocket != null) {
            interrupt();
            try {
                if (serverSocket != null) {
                    serverSocket.close();
                }
            } catch (IOException ioException) {
                Log.e("SERVER", "An exception has occurred: " + ioException.getMessage());
            }
        }
    }
}
