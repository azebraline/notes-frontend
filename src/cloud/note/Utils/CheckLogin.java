package cloud.note.Utils;


import java.io.IOException;

public class CheckLogin {
    public static int isLogin(String iuser, String ipass) throws IOException {
        return Integer.valueOf(Client.sentStr("login;" + iuser + ";" + ipass));
    }
}
