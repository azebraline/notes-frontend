package cloud.note.dao;

import cloud.note.Utils.Client;

import java.io.IOException;

import static cloud.note.config.Constants.DELIMITER;

public class ArticleDao {
    public static String delete(String id) throws IOException {
        return Client.sentStr("ArticleDelete" + DELIMITER + id);
    }

    public static String select(String userId) throws IOException {

        return Client.sentStr("ArticleSelect" + DELIMITER + userId);
    }

    public static String insert(String art) throws IOException {
        return Client.sentStr("ArticleInsert" + DELIMITER + art);
    }

    public static String update(String art) throws IOException {
        return Client.sentStr("ArticleUpdate"+ DELIMITER + art);
    }
}
