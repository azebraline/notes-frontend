package cloud.note;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ClientApplication extends Application {
    private static Stage primaryStage;
    private AnchorPane loginPane;

    /**
     * 返回主舞台 main stage
     *
     * @return
     */
    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            ClientApplication.primaryStage = primaryStage;
            System.out.println(getClass().getResource("layout/LoginScene.fxml"));
            System.out.println(getClass().getClassLoader().getResourceAsStream("resources/logo.png"));
            loginPane = FXMLLoader.load(getClass().getResource("layout/LoginScene.fxml"));
            Scene scene = new Scene(loginPane);
            primaryStage.setTitle("云笔记课程设计");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.getIcons()
                    .add(new Image(this.getClass().getClassLoader().getResourceAsStream("resources/logo.png")));
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
