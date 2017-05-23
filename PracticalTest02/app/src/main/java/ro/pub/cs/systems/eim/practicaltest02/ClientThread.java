package ro.pub.cs.systems.eim.practicaltest02;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by student on 23.05.2017.
 */
public class ClientThread extends Thread{

    String clientAddress;
    int clientPort;
    String informationType;
    String request;

    public ClientThread(String clientAddress, int clientPort,  String informationType , String request) {
        this.clientAddress = clientAddress;
        this.clientPort = clientPort;
        this.informationType = informationType;
        this.request = request;
    }

    @Override
    public void run() {
        try {
            Socket socket = new Socket(clientAddress, clientPort);
            if (socket == null) {
                Log.e("CLIENT", "[CLIENT THREAD] Could not create socket!");
                return;
            }
            BufferedReader bufferedReader = Utilities.getReader(socket);
            PrintWriter printWriter    = Utilities.getWriter(socket);

            if (bufferedReader != null && printWriter != null) {
                printWriter.println(informationType);
                printWriter.flush();
                String stockInformation;
                while ((stockInformation = bufferedReader.readLine()) != null) {
                    final String finalizedStockInformation = stockInformation;
                    /*
                    outputView.post(new Runnable() {
                        @Override
                        public void run() {
                            outputView.append(finalizedStockInformation + "\n");
                        }
                    });
                    */
                    Log.i(request , informationType);
                }
            } else {
                Log.e("CLIENT", "[CLIENT THREAD] BufferedReader / PrintWriter are null!");
            }
            socket.close();
        } catch (IOException ioException) {
            Log.e("CLIENT", "[CLIENT THREAD] An exception has occurred: " + ioException.getMessage());
        }
    }
}