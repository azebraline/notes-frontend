package cloud.note.controller;

import cloud.note.dao.ArticleDao;
import cloud.note.dao.CategoryDao;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

import static cloud.note.config.Constants.DELIMITER;
import static cloud.note.config.Constants.END;

public class EditController implements Initializable {

    @FXML
    ChoiceBox<String> readCategoryBox = new ChoiceBox<>();
    @FXML
    TextField artTitleField;
    @FXML
    HTMLEditor editorField;
    @FXML
    Button artSubmitButton;
    @FXML
    Button artCancelButton;
    @FXML
    TextField newCategoryField;
    @FXML
    Button catSubmitButton;
    @FXML
    Button catCancelButton;
    @FXML
    Button catResetButton;
    @FXML
    Button artResetButton;
    @FXML
    ChoiceBox<String> readCategoryUpdateBox = new ChoiceBox<>();
    @FXML
    TextField artTitleUpdateField = new TextField();
    @FXML
    HTMLEditor editorUpdateField = new HTMLEditor();
    @FXML
    Button artSubmitUpdateButton;
    @FXML
    Button artCancelUpdateButton;
    @FXML
    Button artResetUpdateButton;
    @FXML
    TextField newCategoryUpdateField;
    @FXML
    Button catSubmitUpdateButton;
    @FXML
    Button catCancelUpdateButton;
    @FXML
    Button catResetUpdateButton;
    @FXML
    ChoiceBox<String> readCategoryReadBox = new ChoiceBox<>();
    @FXML
    TextField artTitleReadField = new TextField();
    @FXML
    HTMLEditor editorReadField = new HTMLEditor();
    @FXML
    Button artCloseReadButton;
    private Stage stage = null;
    private final Map<Integer, String> map = new LinkedHashMap<>();
    private int count = 0;
    private Set<Integer> keySet;
    private int j = 1;
    private int k = 1;
    private int l = 1;
    private String art;
    private String cat;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (MainSceneController.getSelectedRowArt != null) {
            // 修改文章
            readCategoryUpdateBox.setValue(MainSceneController.getSelectedRowArt.getCatName());
            artTitleUpdateField.setText(MainSceneController.getSelectedRowArt.getArtName());
            editorUpdateField.setHtmlText(MainSceneController.getSelectedRowArt.getArtContent());
            try {
                updateChoiceBoxReadCat();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 读取文章

            readCategoryReadBox.setValue(MainSceneController.getSelectedRowArt.getCatName());
            artTitleReadField.setText(MainSceneController.getSelectedRowArt.getArtName());
            editorReadField.setHtmlText(MainSceneController.getSelectedRowArt.getArtContent());
            try {
                readChoiceBoxReadCat();
            } catch (IOException e) {
                e.printStackTrace();
            }
            readCategoryReadBox.setDisable(true);
            artTitleReadField.setDisable(true);
            editorReadField.setDisable(true);

        }

    }

    /**
     * --------文章新建页面------------
     */
    @FXML
    public void readCategoryBoxClick() throws IOException {
        choiceBoxReadCat();
    }

    @FXML
    public void artSubmitButtonClick() {
        try {
            art = "";
            art += new LoginController().getUserId() + DELIMITER + getCatId(readCategoryBox.getValue().trim()) + DELIMITER + artTitleField.getText().trim() + DELIMITER + editorField.getHtmlText().trim() + DELIMITER + new SimpleDateFormat("yyyy-MM-dd").format(new Date());

            if (!readCategoryBox.getValue().isEmpty() && artTitleField.getText().length() != 0) {
                String res = ArticleDao.insert(art);
                if ("false".equals(res)) {
                    Alert alert = new Alert(AlertType.ERROR, "创建失败");
                    alert.setTitle("创建失败");
                    alert.setHeaderText("创建失败");
                    alert.show();
                }
                stage = (Stage) artSubmitButton.getScene().getWindow();
                stage.close();
                new MainSceneController().loadArtData();
            } else {
                Alert alert = new Alert(AlertType.ERROR, "类名或题目不能为空");
                alert.setTitle("创建失败");
                alert.setHeaderText("创建失败");
                alert.show();
            }
        } catch (NullPointerException ne) {
            Alert alert = new Alert(AlertType.ERROR, "类名或题目不能为空");
            alert.setTitle("创建失败");
            alert.setHeaderText("创建失败");
            alert.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void artCancelButtonClick() {
        Stage stage = (Stage) artCancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void artResetButtonClick() {
        editorField.setHtmlText("");
        editorField.requestFocus();
    }

    /**
     * -----------------------文章修改界面--------------------------
     */
    @FXML
    public void readCategoryBoxUpdateClick() throws IOException {
        choiceBoxReadCat();
    }

    @FXML
    public void artSubmitButtonUpdateClick() {
        try {

            art = "";
            art += new LoginController().getUserId() + DELIMITER + getCatId(readCategoryUpdateBox.getValue()) + DELIMITER + artTitleUpdateField.getText() + DELIMITER + editorUpdateField.getHtmlText() + DELIMITER + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + DELIMITER + MainSceneController.getSelectedRowArt.getArtId();
            if (!readCategoryUpdateBox.getValue().isEmpty() && artTitleUpdateField.getText().length() != 0) {
                if (ArticleDao.update(art).equals("true")) {
                    stage = (Stage) artSubmitUpdateButton.getScene().getWindow();
                    stage.close();
                    new MainSceneController().loadArtData();
                } else {
                    Alert alert = new Alert(AlertType.ERROR, "修改失败");
                    alert.setTitle("修改失败");
                    alert.setHeaderText("修改失败");
                    alert.show();
                }

            } else {
                Alert alert = new Alert(AlertType.ERROR, "类名或题目不能为空");
                alert.setTitle("修改失败");
                alert.setHeaderText("修改失败");
                alert.show();
            }
        } catch (NullPointerException ne) {
            Alert alert = new Alert(AlertType.ERROR, "类名或题目不能为空");
            alert.setTitle("修改失败");
            alert.setHeaderText("修改失败");
            alert.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void artCancelButtonUpdateClick() {
        Stage stage = (Stage) artCancelUpdateButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void artResetButtonUpdateClick() {
        artTitleUpdateField.setText("");
        editorUpdateField.setHtmlText("");
        editorUpdateField.requestFocus();
    }

    /**
     * -------------------文章查看页面---------------------------
     */
    @FXML
    public void artCloseButtonReadClick() {
        Stage stage = (Stage) artCloseReadButton.getScene().getWindow();
        stage.close();
    }

    /**
     * -------------------类别新建页面---------------------------
     */
    @FXML
    public void catSubmitButtonClick() throws IOException {
        cat = "";
        cat += new LoginController().getUserId() + DELIMITER + newCategoryField.getText() + DELIMITER + new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        if (newCategoryField.getText().length() != 0) {
            String res = CategoryDao.insert(cat);
            if ("false".equals(res)) {
                Alert alert = new Alert(AlertType.ERROR, "创建失败");
                alert.setTitle("创建失败");
                alert.setHeaderText("创建失败");
                alert.show();
            }
            stage = (Stage) catSubmitButton.getScene().getWindow();
            stage.close();
            new MainSceneController().loadCatData();
        } else {
            Alert alert = new Alert(AlertType.ERROR, "类名不能为空");
            alert.setTitle("创建失败");
            alert.setHeaderText("创建失败");
            alert.show();
        }
    }

    @FXML
    public void catCancelButtonClick() {
        Stage stage = (Stage) catCancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void catResetButtonClick() {
        newCategoryField.setText("");
        newCategoryField.requestFocus();
    }

    /**
     * ----------------类别修改界面---------------------------------------
     */
    @FXML
    public void catSubmitUpdateButtonClick() throws IOException {
        MainSceneController.getSelectedRowCat.setCatCreateTime(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        MainSceneController.getSelectedRowCat.setCatName(newCategoryUpdateField.getText());
        if (newCategoryUpdateField.getText().length() != 0) {
            cat = "";
            cat += MainSceneController.getSelectedRowCat.getCatName() + DELIMITER + MainSceneController.getSelectedRowCat.getCatCreateTime() + DELIMITER + MainSceneController.getSelectedRowCat.getCatId();
            String res = CategoryDao.update(cat);
            if ("false".equals(res)) {
                Alert alert = new Alert(AlertType.ERROR, "修改失败");
                alert.setTitle("修改失败");
                alert.setHeaderText("修改失败");
                alert.show();
            }
            stage = (Stage) catSubmitUpdateButton.getScene().getWindow();
            stage.close();
            new MainSceneController().loadCatData();
        } else {
            Alert alert = new Alert(AlertType.ERROR, "类名不能为空");
            alert.setTitle("修改失败");
            alert.setHeaderText("修改失败");
            alert.show();
        }
    }

    @FXML
    public void catCancelUpdateButtonClick() {
        Stage stage = (Stage) catCancelUpdateButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void catResetUpdateButtonClick() {
        newCategoryUpdateField.setText("");
        newCategoryUpdateField.requestFocus();
    }

    /**
     * -----------------------工具---------------------------------
     */
    // 新建文章初始化
    private void choiceBoxReadCat() throws IOException {
        initArticleEditScene();
        keySet = map.keySet();
        for (Integer i : keySet) {
            String name = map.get(i);
            System.out.println(name);
            if (j < count) {
                readCategoryBox.getItems().add(name);
                j++;
            }
        }
    }

    // 修改文章初始化
    private void updateChoiceBoxReadCat() throws IOException {
        initArticleEditScene();
        keySet = map.keySet();
        for (Integer i : keySet) {
            String name = map.get(i);
            if (k < count) {
                readCategoryUpdateBox.getItems().add(name);
                k++;
            }
        }
    }

    // 查看文章初始化
    private void readChoiceBoxReadCat() throws IOException {
        initArticleEditScene();
        keySet = map.keySet();
        for (Integer i : keySet) {
            String name = map.get(i);
            if (l < count) {
                readCategoryReadBox.getItems().add(name);
                l++;
            }
        }
    }

    // 文章编辑界面初始化时获取类别数据
    private void initArticleEditScene() throws IOException {
        count = 1;
        String cate = CategoryDao.select(Integer.toString(new LoginController().getUserId()));
        for (String category : cate.split(END)) {
            int id = Integer.parseInt(category.split(DELIMITER)[0]);
            int userId = Integer.parseInt(category.split(DELIMITER)[1]);
            String name = category.split(DELIMITER)[3];
            if (userId == new LoginController().getUserId()) {
                map.put(id, name);
                count++;
            }
        }
    }

    /**
     * ---------------根据键找值----------------
     */
    private Integer getCatId(String catName) {
        Set<Map.Entry<Integer, String>> set = map.entrySet();
        for (Map.Entry<Integer, String> me : set) {
            Integer id = me.getKey();
            String name = me.getValue();
            if (name.equals(catName)) {
                return id;
            }
        }
        return null;
    }

}
