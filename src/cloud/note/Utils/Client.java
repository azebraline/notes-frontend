package cloud.note.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class Client {
    public static String sentStr(String str) throws IOException {
        //客户端请求与本机在20006端口建立TCP连接
        Socket client = new Socket("127.0.0.1", 23333);
        client.setSoTimeout(2000);
        //获取Socket的输出流，用来发送数据到服务端
        PrintStream out = new PrintStream(client.getOutputStream());
        //获取Socket的输入流，用来接收从服务端发送过来的数据
        BufferedReader buf = new BufferedReader(new InputStreamReader(client.getInputStream()));
        //发送数据到服务端
        out.println(str);
        try {
            //从服务器端接收数据有个时间限制（系统自设，也可以自己设置），超过了这个时间，便会抛出该异常
            return buf.readLine();
        } catch (SocketTimeoutException e) {
            System.out.println("Time out, No response");
            return null;
        } finally {
            if (client != null) {
                //如果构造函数建立起了连接，则关闭套接字，如果没有建立起连接，自然不用关闭
                client.close(); //只关闭socket，其关联的输入输出流也会被关闭
            }
        }

    }


}
