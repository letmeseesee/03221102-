package server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server.business.ChargeServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread
{
    final static Logger logger = LoggerFactory.getLogger(ChargeServer.class);

    ServerSocket server = null;
    Socket sk = null;
    BufferedReader rdr = null;
    PrintWriter wtr = null;

    public Server()
    {
        try
        {
            server = new ServerSocket(1987);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void run()
    {

        while (true)
        {
            System.out.println("接收请求中...");
            try
            {
                sk = server.accept();
                HttpServer th = new HttpServer(sk);
                th.start();
                sleep(1000);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

}
