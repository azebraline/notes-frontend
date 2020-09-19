package cloud.note.controller;

import cloud.note.ClientApplication;
import cloud.note.bean.ArticleProperty;
import cloud.note.bean.CategoryProperty;
import cloud.note.dao.ArticleDao;
import cloud.note.dao.CategoryDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

import static cloud.note.config.Constants.DELIMITER;
import static cloud.note.config.Constants.END;

public class MainSceneController {

    /**
     * -----菜单栏--------
     */
    @FXML
    final static Label welcomeLabel = new Label();
    @FXML
    final static TableView<ArticleProperty> articleTable = new TableView<>();
    @FXML
    final static TableView<CategoryProperty> categoryTable = new TableView<>();
    private static final Label artLabel = new Label();
    private static final Label label = new Label();
    public static ArticleProperty getSelectedRowArt;
    // 相关类别变量
    public static CategoryProperty getSelectedRowCat;
    private static final ObservableList<CategoryProperty> catData = FXCollections.observableArrayList();
    @FXML
    final TableColumn<ArticleProperty, String> artId = new TableColumn<>("Id");
    @FXML
    final TableColumn<ArticleProperty, String> artIndex = new TableColumn<>("序号");
    @FXML
    final TableColumn<ArticleProperty, String> artName = new TableColumn<>("标题");
    @FXML
    final TableColumn<ArticleProperty, String> catNameOfArt = new TableColumn<>("类名");
    @FXML
    final TableColumn<ArticleProperty, String> artTime = new TableColumn<>("创建时间");
    @FXML
    final TableColumn<CategoryProperty, String> catId = new TableColumn<>("Id");
    @FXML
    final TableColumn<CategoryProperty, String> catIndex = new TableColumn<>("序号");
    @FXML
    final TableColumn<CategoryProperty, String> catName = new TableColumn<>("类名");
    @FXML
    final TableColumn<CategoryProperty, String> catCreateTime = new TableColumn<>("创建时间");
    private final VBox avBox = new VBox();
    private final HBox ahBox = new HBox();
    private final VBox vbox = new VBox();
    private final HBox hbox = new HBox();
    @FXML
    VBox mainScene;
    @FXML
    MenuItem aboutItem;
    @FXML
    MenuBar menuBar;
    @FXML
    MenuItem closeMenuItem1;
    @FXML
    MenuItem explainItem;
    /**
     * -----左边栏 文章和类别管理--------
     */
    @FXML
    Button articleManagement;
    @FXML
    Button categoryManagement;
    /**
     * -----右侧显示界面--------
     */
    @FXML
    BorderPane root;
    @FXML
    AnchorPane infoArea;
    @FXML
    BorderPane articleArea;
    @FXML
    AnchorPane bottomArea;
    @FXML
    AnchorPane sideArea;
    @FXML
    TitledPane naviArea;
    @FXML
    AnchorPane categoryBlock;
    @FXML
    AnchorPane articleBlock;
    /**
     * -----文章管理界面--------
     */
    @FXML
    Button newArticleButton;
    @FXML
    Button updateArticleButton;
    @FXML
    Button deleteArticleButton;
    ObservableList<ArticleProperty> artData = FXCollections.observableArrayList();
    /**
     * -----类别管理界面--------
     */
    @FXML
    Button newCategoryButton;
    @FXML
    Button updateCategoryButton;
    @FXML
    Button deleteCategoryButton;
    private int readOnce = 1;
    private int readOrNot = 1;
    /**
     * -----全局变量--------
     */
    private Stage stage = ClientApplication.getPrimaryStage();
    private Scene scene;


    public MainSceneController() {
        getSelectedRowCat = null;
        getSelectedRowArt = null;
        categoryTable.setOnMouseClicked(event -> getSelectedRowCat = categoryTable.getSelectionModel().getSelectedItem());
        articleTable.setOnMouseClicked(new EventHandler<>() {
            @Override
            public void handle(MouseEvent event) {
                getSelectedRowArt = articleTable.getSelectionModel().getSelectedItem();
                if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() >= 2) {
                    stage = new Stage();
                    try {
                        scene = new Scene(FXMLLoader.load(getClass().getResource("../layout/ArticleReadScene.fxml")));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    stage.setTitle("查看文章");
                    stage.setScene(scene);
                    stage.getIcons()
                            .add(new Image(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream("resources/logo.png"))));
                    stage.show();
                }
            }
        });
    }

    // 菜单
    @FXML
    public void aboutItemClick() {
        Alert alert = new Alert(AlertType.INFORMATION, "    这是一个基于JavaFX+Socket的云笔记");
        alert.setTitle("关于");
        alert.setGraphic(
                new ImageView(new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("resources/note.png")))));
        alert.setHeaderText("     Author:" + "沈阳理工大学课程设计 " + "\n\r" + "     Version: 1.0");
        alert.initOwner(stage);
        alert.show();
    }

    @FXML
    public void closeMenuItemClick() {
        System.exit(0);
    }

    @FXML
    public void explainItemClick() {
        Alert alert = new Alert(AlertType.INFORMATION, "作者：沈阳理工大学课程设计");
        alert.setTitle("说明");
        alert.setGraphic(
                new ImageView(new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("resources/about.png")))));
        alert.setHeaderText("  该笔记本实现了类别和文章的增删改查功能，其中文章的查看通过双击表中条\r\n目实现，文章和类别的增加、修改、删除操作通过点击面板上对应按钮实现 。");
        alert.initOwner(stage);
        alert.show();
    }

    // 左边栏，点击文章按钮
    @FXML
    public void articleManagementClick() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        // 设置路径基准

        loader.setLocation(ClientApplication.class.getResource("layout/ArticleScene.fxml"));
        try {
            articleBlock = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        articleArea.setCenter(articleBlock);

        newArticleButton = new Button("新建");
        newArticleButton.setPrefWidth(70);
        newArticleButton.setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent event) {
                stage = new Stage();
                try {
                    scene = new Scene(FXMLLoader.load(getClass().getResource("../layout/ArticleEditScene.fxml")));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                stage.setTitle("新建文章");
                stage.setScene(scene);
                stage.getIcons()
                        .add(new Image(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream("resources/logo.png"))));
                stage.show();
            }
        });

        updateArticleButton = new Button("修改");
        updateArticleButton.setPrefWidth(70);
        updateArticleButton.setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent event) {
                if (getSelectedRowArt != null) {
                    stage = new Stage();
                    try {
                        scene = new Scene(FXMLLoader.load(getClass().getResource("../layout/ArticleUpdateScene.fxml")));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    stage.setTitle("修改文章");
                    stage.setScene(scene);
                    stage.getIcons()
                            .add(new Image(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream("resources/logo.png"))));
                    stage.show();
                }
            }
        });

        deleteArticleButton = new Button("删除");
        deleteArticleButton.setPrefWidth(70);
        deleteArticleButton.setOnAction(event1 -> {
            if (getSelectedRowArt != null) {
                String res;
                try {
                    res = ArticleDao.delete(Integer.toString(getSelectedRowArt.getArtId()));
                    if ("false".equals(res)) {
                        Alert alert = new Alert(AlertType.ERROR, "删除失败");
                        alert.setTitle("删除失败");
                        alert.setHeaderText("删除失败");
                        alert.show();
                    }
                    loadArtData();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        artId.setCellValueFactory(new PropertyValueFactory<>("artId"));
        artIndex.setCellValueFactory(new PropertyValueFactory<>("artIndex"));
        artName.setCellValueFactory(new PropertyValueFactory<>("artName"));
        catNameOfArt.setCellValueFactory(new PropertyValueFactory<>("catName"));
        artTime.setCellValueFactory(new PropertyValueFactory<>("artCreateTime"));

        artId.setVisible(false);

        artId.setSortable(false);
        artIndex.setSortable(false);
        artName.setSortable(false);
        catNameOfArt.setSortable(false);
        artTime.setSortable(false);

        artId.setMaxWidth(100.0);
        artIndex.setMaxWidth(100.0);
        artName.setMaxWidth(150.0);
        catNameOfArt.setMaxWidth(150.0);
        artTime.setMaxWidth(150.0);

        artId.setMinWidth(100.0);
        artIndex.setMinWidth(100.0);
        artName.setMinWidth(150);
        catNameOfArt.setMinWidth(150.0);
        artTime.setMinWidth(150.0);
        loadArtData();

        if (readOnce == 1) {
            articleTable.getColumns().addAll(artId, artIndex, artName, catNameOfArt, artTime);
            // 调整控件间隔
            ahBox.setSpacing(5.0);
            ahBox.getChildren().addAll(newArticleButton, updateArticleButton, deleteArticleButton);
            avBox.setSpacing(5.0);
            avBox.getChildren().addAll(ahBox, articleTable, artLabel);
            avBox.setPadding(new Insets(50, 0, 0, 16));
            ++readOnce;
        }
        artLabel.setText("共" + artData.size() + "条记录");
        articleBlock.getChildren().addAll(avBox);

    }

    // 点击分类按钮
    @FXML
    public void categoryManagementClick() throws IOException {
        catData.clear();
        // 点击按钮，出现界面
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ClientApplication.class.getResource("layout/CategoryScene.fxml"));
        try {
            categoryBlock = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        articleArea.setCenter(categoryBlock);

        newCategoryButton = new Button("新建");
        newCategoryButton.setPrefWidth(70);
        newCategoryButton.setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent event) {

                stage = new Stage();
                try {
                    scene = new Scene(FXMLLoader.load(getClass().getResource("../layout/CategoryEditScene.fxml")));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                stage.setTitle("新建类别");
                stage.getIcons()
                        .add(new Image(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream("resources/logo.png"))));
                stage.setScene(scene);
                stage.show();

            }
        });

        updateCategoryButton = new Button("修改");
        updateCategoryButton.setPrefWidth(70);
        updateCategoryButton.setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent event) {
                if (getSelectedRowCat != null) {
                    stage = new Stage();
                    try {
                        scene = new Scene(FXMLLoader.load(getClass().getResource("../layout/CategoryUpdateScene.fxml")));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    stage.setTitle("修改类别");
                    stage.getIcons()
                            .add(new Image(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream("resources/logo.png"))));
                    stage.setScene(scene);
                    stage.show();
                }
            }
        });

        deleteCategoryButton = new Button("删除");
        deleteCategoryButton.setPrefWidth(70);
        deleteCategoryButton.setOnAction(event1 -> {
            if (getSelectedRowCat != null) {
                try {
                    if (!delDateExistInArtOrNot()) {
                        String res = CategoryDao.delete(String.valueOf(getSelectedRowCat.getCatId()));
                        if ("false".equals(res)) {
                            Alert alert = new Alert(AlertType.ERROR);
                            alert.setTitle("删除失败");
                            alert.setContentText("删除失败");
                            alert.initOwner(stage);
                            alert.show();
                        }
                        loadCatData();
                    } else {
                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("删除失败");
                        alert.setContentText("文章包含该类名，请先修改或删除文章");
                        alert.initOwner(stage);
                        alert.show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        catId.setCellValueFactory(new PropertyValueFactory<>("catId"));
        catIndex.setCellValueFactory(new PropertyValueFactory<>("catIndex"));
        catName.setCellValueFactory(new PropertyValueFactory<>("catName"));
        catCreateTime.setCellValueFactory(new PropertyValueFactory<>("catCreateTime"));

        catId.setVisible(false);

        catId.setSortable(false);
        catIndex.setSortable(false);
        catName.setSortable(false);
        catCreateTime.setSortable(false);

        catId.setMaxWidth(100.0);
        catIndex.setMaxWidth(300.0);
        catName.setMaxWidth(225.0);
        catCreateTime.setMaxWidth(225.0);

        catId.setMinWidth(100.0);
        catIndex.setMinWidth(100.0);
        catName.setMinWidth(225.0);
        catCreateTime.setMinWidth(225.0);
        loadCatData();
        System.out.println(readOrNot);

        if (readOrNot == 1) {
            categoryTable.getColumns().addAll(catId, catIndex, catName, catCreateTime);
            // 调整控件间隔
            hbox.setSpacing(5.0);
            hbox.getChildren().addAll(newCategoryButton, updateCategoryButton, deleteCategoryButton);
            vbox.setSpacing(5.0);
            vbox.getChildren().addAll(hbox, categoryTable, label);
            vbox.setPadding(new Insets(50, 0, 0, 16));
            ++readOrNot;
        }

        label.setText("共" + catData.size() + "条记录");
        categoryBlock.getChildren().addAll(vbox);
    }

    /**
     * -----------------工具类-------------------------
     */
    // 类别表载入数据
    public void loadCatData() throws IOException {
        catData.clear();
        // 读取数据库中数据
        String rs = CategoryDao.select(Integer.toString(new LoginController().getUserId()));
        for (String category : rs.split(END)) {
            if("".equals(category))
                break;
            String id = category.split(DELIMITER)[0];
            String userId = category.split(DELIMITER)[1];
            String index = category.split(DELIMITER)[2];
            String name = category.split(DELIMITER)[3];
            String time = category.split(DELIMITER)[4];
            CategoryProperty cp = new CategoryProperty(id, userId, index, name, time);
            catData.add(cp);
        }
        System.out.println(catData.size());
        label.setText("共" + catData.size() + "条记录");
        categoryTable.setItems(catData);
    }

    public void loadArtData() throws IOException {
        artData.clear();
        // 读取数据库中数据
        String rs = ArticleDao.select(String.valueOf(new LoginController().getUserId()));
        System.out.println(rs);
        for (String article : rs.split(END)){
            if("".equals(article))
                break;
            String artId = article.split(DELIMITER)[0];
            String userId = article.split(DELIMITER)[1];
            String catId = article.split(DELIMITER)[2];
            String index = article.split(DELIMITER)[3];
            String artName = article.split(DELIMITER)[4];
            String catName = article.split(DELIMITER)[5];
            String content = article.split(DELIMITER)[6];
            String time = article.split(DELIMITER)[7];
            // 相关文章变量
            ArticleProperty ap = new ArticleProperty(artId, userId, catId, index, artName, catName, content, time);
            artData.add(ap);
        }
        artLabel.setText("共" + artData.size() + "条记录");
        articleTable.setItems(artData);
    }

    private boolean delDateExistInArtOrNot() throws IOException {
        boolean flag = false;
        String rs = ArticleDao.select(String.valueOf(new LoginController().getUserId()));
        for (String cate : rs.split(END)) {
            int catId = Integer.parseInt(cate.split(DELIMITER)[2]);
            if (getSelectedRowCat.getCatId() == catId) {
                flag = true;
            }
        }
        return flag;
    }
}
