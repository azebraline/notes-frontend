package cloud.note.dao;

import cloud.note.Utils.Client;

import java.io.IOException;

public class CategoryDao {
    public static String select(String userId) throws IOException {
        return Client.sentStr("category;" + userId);
    }

    public static String getmap(String userId) throws IOException {
        return Client.sentStr("getmap;" + userId);
    }

    public static String delete(String id) throws IOException {
        System.out.println("###删除###:" + id);
        return Client.sentStr("CatDelete;" + id);
    }

    public static String insert(String cat) throws IOException {
        return Client.sentStr("CatInsert;" + cat);
    }

    public static String update(String cat) throws IOException {
        return Client.sentStr("CatUpdate;" + cat);

    }
}
