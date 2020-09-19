package cloud.note.controller;

import cloud.note.ClientApplication;
import cloud.note.Utils.CheckLogin;
import cloud.note.bean.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

/*
 * 每个FXML只有一个控制器，用于响应页面的各种事件
 */

public class LoginController {

    private static final User user = new User();
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField userNameField;
    @FXML
    private Button exitButton;
    ;
    private Stage stage = ClientApplication.getPrimaryStage();
    private Scene scene;

    public LoginController() {
    }

    @FXML
    public void loginButtonClick() throws IOException {
        if (CheckLogin.isLogin(userNameField.getText(), passwordField.getText()) != -1) {
            System.out.println(getClass().getResource(""));
            scene = new Scene(FXMLLoader.load(getClass().getResource("../layout/MainScene.fxml")));
            stage.setScene(scene);
            stage.show();
            user.setUserId(CheckLogin.isLogin(userNameField.getText(), passwordField.getText()));
            System.out.println(user.getUserId());
        } else {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("登录失败");
            alert.setHeaderText("登录失败");
            alert.setContentText("账号或密码错误");
            alert.initOwner(stage);
            alert.show();
        }
    }

    @FXML
    public void registerButtonClick() throws IOException {
        scene = new Scene(FXMLLoader.load(getClass().getResource("../layout/RegisterScene.fxml")));
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void resetButtonClick() {
        userNameField.setText("");
        passwordField.setText("");
        userNameField.requestFocus();
    }

    @FXML
    public void exitButtonClick() {
        stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    public int getUserId() {
        return user.getUserId();
    }
}
