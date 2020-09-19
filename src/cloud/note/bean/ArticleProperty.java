package cloud.note.bean;

import javafx.beans.property.SimpleStringProperty;

public class ArticleProperty {
    private final SimpleStringProperty artId;
    private final SimpleStringProperty userId;
    private final SimpleStringProperty catId;
    private final SimpleStringProperty catName;
    private final SimpleStringProperty artIndex;
    private final SimpleStringProperty artName;
    private final SimpleStringProperty artContent;
    private final SimpleStringProperty artCreateTime;

    public ArticleProperty(String artId, String userId, String catId, String artIndex, String artName, String catName,
                           String artContent, String artCreateTime) {
        this.artId = new SimpleStringProperty(artId);
        this.userId = new SimpleStringProperty(userId);
        this.catId = new SimpleStringProperty(catId);
        this.artIndex = new SimpleStringProperty(artIndex);
        this.artName = new SimpleStringProperty(artName);
        this.catName = new SimpleStringProperty(catName);
        this.artContent = new SimpleStringProperty(artContent);
        this.artCreateTime = new SimpleStringProperty(artCreateTime);
    }

    // getter
    public int getArtId() {
        return Integer.parseInt(artId.get());
    }

    public int getUserId() {
        return Integer.parseInt(userId.get());
    }

    public int getCatId() {
        return Integer.parseInt(catId.get());
    }

    // setter
    public void setCatId(String id) {
        this.artIndex.set(id);
    }

    public String getCatName() {
        return catName.get();
    }

    public String getArtIndex() {
        return artIndex.get();
    }

    public void setArtIndex(String index) {
        this.artIndex.set(index);
    }

    public String getArtName() {
        return artName.get();
    }

    public void setArtName(String name) {
        this.artIndex.set(name);
    }

    public String getArtContent() {
        return artContent.get();
    }

    public void setArtContent(String content) {
        this.artIndex.set(content);
    }

    public String getArtCreateTime() {
        return artCreateTime.get();
    }

    public void setArtTime(String time) {
        this.artIndex.set(time);
    }

}
