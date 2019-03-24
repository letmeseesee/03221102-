package server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server.business.ChargeServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class AcceptServer extends Thread
{
    final static Logger logger = LoggerFactory.getLogger(ChargeServer.class);

    ServerSocket server = null;
    Socket sk = null;

    public AcceptServer()
    {
        try
        {
            server = new ServerSocket(8888,5);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
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
