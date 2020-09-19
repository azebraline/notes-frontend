package cloud.note.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class Client {
    public static String sentStr(String str) throws IOException {
        Socket client = new Socket("127.0.0.1", 23333);
        client.setSoTimeout(2000);
        PrintStream out = new PrintStream(client.getOutputStream());
        BufferedReader buf = new BufferedReader(new InputStreamReader(client.getInputStream()));
        out.println(str);
        try {
            return buf.readLine();
        } catch (SocketTimeoutException e) {
            System.out.println("Time out, No response");
            return null;
        } finally {
            client.close();
        }
    }
}
