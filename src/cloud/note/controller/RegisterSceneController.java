package cloud.note.controller;

import cloud.note.ClientApplication;
import cloud.note.Utils.Client;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

import static cloud.note.config.Constants.DELIMITER;

public class RegisterSceneController {

    @FXML
    private TextField registerUserName;
    @FXML
    private TextField registerPassword;
    @FXML
    private PasswordField registerPasswordRepeat;


    private final Stage stage = ClientApplication.getPrimaryStage();
    private Scene scene;

    @FXML
    public void resetButtonPress() {
        registerUserName.setText("");
        registerPassword.setText("");
        registerPasswordRepeat.setText("");
        registerUserName.requestFocus();
    }

    @FXML
    public void cancelButtonPress() throws IOException {
        scene = new Scene(FXMLLoader.load(getClass().getResource("../layout/LoginScene.fxml")));
        stage.setScene(scene);
    }

    @FXML
    public void registerButtonPress() throws IOException {
        registerVerify();
    }

    public void registerVerify() throws IOException {

        if (registerPassword.getText().equals(registerPasswordRepeat.getText())) {

            String username = registerUserName.getText();
            String password = registerPassword.getText();
            String res = Client.sentStr("reg"+DELIMITER+username+DELIMITER+password);
            System.out.println(res);
            if ("true".equals(res)) {
                Alert alert = new Alert(AlertType.CONFIRMATION, "是否要跳转登陆界面？");
                alert.setTitle("注册成功");
                alert.setHeaderText("注册成功");
                alert.initOwner(stage);

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    scene = new Scene(FXMLLoader.load(getClass().getResource("../layout/LoginScene.fxml")));
                    stage.setScene(scene);
                }
            } else {
                Alert alert3 = new Alert(AlertType.ERROR, "账号或密码有误，请重新注册");
                alert3.setTitle("注册失败");
                alert3.setHeaderText("注册失败");
                alert3.initOwner(stage);
                alert3.show();
            }
        } else {
            Alert alert3 = new Alert(AlertType.ERROR, "两次密码输入不一致");
            alert3.setTitle("注册失败");
            alert3.setHeaderText("注册失败");
            alert3.initOwner(stage);
            alert3.show();
        }
    }
}
