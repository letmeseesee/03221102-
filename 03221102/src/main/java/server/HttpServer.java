package server;

import facade.vo.request.Request;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

import static com.alibaba.fastjson.JSON.parseObject;

/**
 * @author 阿尔卑斯狗 2019-3-22 socketServer
 */
public class HttpServer extends Thread {
    Socket socket = null;

    public HttpServer(Socket sk) {
        this.socket = sk;
    }

    /**
     * 接收消息
     */
    @Override
    public void run() {
        try {
            // 读取客户端数据
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String clientInputStr = input.readLine();
            // 处理客户端数据
            System.out.println("客户端发过来的内容:" + clientInputStr);
            //json转化为对象
            Request request = parseObject(clientInputStr, Request.class);
            // 向客户端回复信息
            PrintStream out = new PrintStream(socket.getOutputStream());
            //System.out.print("请输入:\t");
            // 发送键盘输入的一行
            //String s = new BufferedReader(new InputStreamReader(System.in)).readLine();
            DispatchServer dispatchServer = new DispatchServer();
            String result = dispatchServer.handle(request);
            System.out.println("服务端返回的内容:" + result);
            out.println(result);

            out.close();
            input.close();

        }catch (Exception e){
            System.out.println("访问失败");
        }
    }
}