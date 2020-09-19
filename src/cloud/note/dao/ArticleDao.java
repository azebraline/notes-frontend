package cloud.note.dao;

import cloud.note.Utils.Client;

import java.io.IOException;

public class ArticleDao {
    public static String delete(String id) throws IOException {
        return Client.sentStr("ArticleDelete;" + id);
    }

    public static String select(String userId) throws IOException {

        return Client.sentStr("ArticleSelect;" + userId);
    }

    public static String insert(String art) throws IOException {
        return Client.sentStr("ArticleInsert;" + art);
    }

    public static String update(String art) throws IOException {
        return Client.sentStr("ArticleUpdate;" + art);
    }
}
