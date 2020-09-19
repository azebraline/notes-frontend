package cloud.note.Utils;


import java.io.IOException;
import java.util.Objects;

import static cloud.note.config.Constants.DELIMITER;

public class CheckLogin {
    public static int isLogin(String username, String password) throws IOException {
        return Integer.parseInt(Objects.requireNonNull(Client.sentStr("login" + DELIMITER + username + DELIMITER + password)));
    }
}
