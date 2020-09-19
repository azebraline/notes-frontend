package cloud.note.controller;

import cloud.note.dao.ArticleDao;
import cloud.note.dao.CategoryDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.*;

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
    private Map<Integer, String> map = new LinkedHashMap<>();
    private ResultSet rs = null;
    private int count = 0;
    private Set<Integer> keySet = null;
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
    public void readCategoryBoxClick(MouseEvent event) throws IOException {
        choiceBoxReadCat();
    }

    @FXML
    public void artSubmitButtonClick(ActionEvent event) {
        try {
            art = "";
            art += new LoginController().getUserId() + ";" + getCatId(readCategoryBox.getValue()) + ";" + artTitleField.getText() + ";" + editorField.getHtmlText() + ";" + new SimpleDateFormat("yyyy-MM-dd").format(new Date());

            if (readCategoryBox.getValue() != "" && artTitleField.getText().length() != 0) {
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
    public void artCancelButtonClick(ActionEvent event) {
        Stage stage = (Stage) artCancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void artResetButtonClick(ActionEvent event) {
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
    public void artSubmitButtonUpdateClick(ActionEvent event) {
        try {

            art = "";
            art += new LoginController().getUserId() + ";" + getCatId(readCategoryUpdateBox.getValue()) + ";" + artTitleUpdateField.getText() + ";" + editorUpdateField.getHtmlText() + ";" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + ";" + MainSceneController.getSelectedRowArt.getArtId();
            if (readCategoryUpdateBox.getValue() != "" && artTitleUpdateField.getText().length() != 0) {
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
    public void artCancelButtonUpdateClick(ActionEvent event) {
        Stage stage = (Stage) artCancelUpdateButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void artResetButtonUpdateClick(ActionEvent event) {
        artTitleUpdateField.setText("");
        editorUpdateField.setHtmlText("");
        editorUpdateField.requestFocus();
    }

    /**
     * -------------------文章查看页面---------------------------
     */
    @FXML
    public void artCloseButtonReadClick(ActionEvent event) {
        Stage stage = (Stage) artCloseReadButton.getScene().getWindow();
        stage.close();
    }

    /**
     * -------------------类别新建页面---------------------------
     */
    @FXML
    public void catSubmitButtonClick(ActionEvent event) throws IOException {
        cat = "";
        cat += new LoginController().getUserId() + ";" + newCategoryField.getText() + ";" + new SimpleDateFormat("yyyy-MM-dd").format(new Date());
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
    public void catCancelButtonClick(ActionEvent event) {
        Stage stage = (Stage) catCancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void catResetButtonClick(ActionEvent event) {
        newCategoryField.setText("");
        newCategoryField.requestFocus();
    }

    /**
     * ----------------类别修改界面---------------------------------------
     */
    @FXML
    public void catSubmitUpdateButtonClick(ActionEvent event) throws IOException {
        MainSceneController.getSelectedRowCat.setCatCreateTime(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        MainSceneController.getSelectedRowCat.setCatName(newCategoryUpdateField.getText());
        if (newCategoryUpdateField.getText().length() != 0) {
            cat = "";
            cat += MainSceneController.getSelectedRowCat.getCatName() + ";" + MainSceneController.getSelectedRowCat.getCatCreateTime() + ";" + MainSceneController.getSelectedRowCat.getCatId();
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
    public void catCancelUpdateButtonClick(ActionEvent event) {
        Stage stage = (Stage) catCancelUpdateButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void catResetUpdateButtonClick(ActionEvent event) {
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
        for (String category : cate.split("10010")) {
            int id = Integer.valueOf(category.split(";")[0]);
            int userId = Integer.valueOf(category.split(";")[1]);
            String name = category.split(";")[3];
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
