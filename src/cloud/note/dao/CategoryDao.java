package cloud.note.dao;

import cloud.note.Utils.Client;

import java.io.IOException;

import static cloud.note.config.Constants.DELIMITER;

public class CategoryDao {
    public static String select(String userId) throws IOException {
        return Client.sentStr("category"+DELIMITER + userId);
    }

    public static String getMap(String userId) throws IOException {
        return Client.sentStr("getMap"+DELIMITER + userId);
    }

    public static String delete(String id) throws IOException {
        return Client.sentStr("CatDelete"+DELIMITER + id);
    }

    public static String insert(String cat) throws IOException {
        return Client.sentStr("CatInsert" +DELIMITER + cat);
    }

    public static String update(String cat) throws IOException {
        return Client.sentStr("CatUpdate" + DELIMITER + cat);

    }
}
