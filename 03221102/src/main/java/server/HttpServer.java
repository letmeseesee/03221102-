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
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author 阿尔卑斯狗 2019-3-22 socketServer
 */
public class HttpServer extends Thread {

    final static Logger logger = LoggerFactory.getLogger(ChargeServer.class);

    Socket socket = null;

    public HttpServer(Socket sk) {
        this.socket = sk;
    }

    /**
     * 接收消息
     */
    public void run() {
        try {
            BufferedReader bd = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            /**
             * 接受HTTP请求
             */
            String requestHeader;
            int contentLength = 0;
            while ((requestHeader = bd.readLine()) != null && !requestHeader.isEmpty()) {
                logger.info(requestHeader);
                if (requestHeader.startsWith("GET")) {
                    int begin = requestHeader.indexOf("/?") + 2;
                    int end = requestHeader.indexOf("HTTP/");
                    String condition = requestHeader.substring(begin, end);
                    logger.info("GET参数是：" + condition);
                }
                if (requestHeader.startsWith("Content-Length")) {
                    int begin = requestHeader.indexOf("Content-Lengh:") + "Content-Length:".length();
                    String postParamterLength = requestHeader.substring(begin).trim();
                    contentLength = Integer.parseInt(postParamterLength);
                    logger.info("POST参数长度是：" + Integer.parseInt(postParamterLength));
                }
            }
            StringBuffer sb = new StringBuffer();
            if (contentLength > 0) {
                for (int i = 0; i < contentLength; i++) {
                    sb.append((char) bd.read());
                }
                logger.info("POST参数是：" + sb.toString());
            }
            //
            //发送回执
            PrintWriter pw = new PrintWriter(socket.getOutputStream());

            pw.println("HTTP/1.1 200 OK");
            pw.println("Content-type:text/html");
            pw.println();
            pw.println("<h1>访问成功！</h1>");

            pw.flush();
            socket.close();
        }catch (Exception e){
            logger.info("访问失败");
        }
    }
}