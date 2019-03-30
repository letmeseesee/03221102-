package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class AcceptServer extends Thread
{

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
