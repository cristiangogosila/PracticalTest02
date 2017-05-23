package ro.pub.cs.systems.eim.practicaltest02;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;

/**
 * Created by student on 23.05.2017.
 */
public class CommunicationThread extends Thread{
    ServerThread serverThread;
    Socket socket;
    long currentTime = 0;

    public CommunicationThread(Thread serverThread, Socket socket) {
        this.serverThread = (ServerThread)serverThread;
        this.socket = socket;
    }

    @Override
    public void run() {
        if (socket != null) {
            try {

                BufferedReader bufferedReader = Utilities.getReader(socket);
                PrintWriter printWriter    = Utilities.getWriter(socket);

                if (bufferedReader != null && printWriter != null) {
                    Log.i("COMMUNICATION", "[COMMUNICATION THREAD] Waiting for parameters from client (information type)!");
                    String informationType = bufferedReader.readLine();
                    StockInformation stockInfo = null;
                    Boolean fromCache = false;

                    HashMap<String, StockInformation> data = serverThread.getData();

                    if (informationType != null && !informationType.isEmpty()) {

                        if (data.containsKey(informationType)) {
                            Log.i("COMMUNICATION", "[COMMUNICATION THREAD] Found entry in cache");
                            fromCache = true;

                            stockInfo = data.get(informationType);
                            if (System.currentTimeMillis() - stockInfo.getTime() > 60000)
                                fromCache = false;
                        }

                        if (!fromCache) {
                            Log.i("COMMUNICATION", "[COMMUNICATION THREAD] Getting the information from the webservice...");


                            currentTime = System.currentTimeMillis();
                            Log.i("merge" , "am creat conexiunea");
                            if (currentTime != 0) {;
                                stockInfo = new StockInformation(System.currentTimeMillis(), "info");
                                serverThread.setData(informationType, stockInfo);

                            } else {
                                Log.e("COMMUNICATION", "[COMMUNICATION THREAD] Error getting the information from the webservice!");
                            }
                        }
                        if (stockInfo != null) {
                            printWriter.println(stockInfo.toString());
                            printWriter.flush();
                        } else {
                            Log.e("COMMUNICATION", "[COMMUNICATION THREAD] Time Forecast information is null!");
                        }
                    } else {
                        Log.e("COMMUNICATION", "[COMMUNICATION THREAD] Error receiving parameters from client !");
                    }
                } else {
                    Log.e("COMMUNICATION", "[COMMUNICATION THREAD] BufferedReader / PrintWriter are null!");
                }
                socket.close();
            } catch (IOException ioException) {
                Log.e("COMMUNICATION", "[COMMUNICATION THREAD] An exception has occurred: " + ioException.getMessage());
            }
        } else {
            Log.e("COMMUNICATION", "[COMMUNICATION THREAD] Socket is null!");
        }
    }
}
