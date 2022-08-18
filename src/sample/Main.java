package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.FileInputStream;

public class Main extends Application {

    public static Stage mainWindow;

    @Override
    public void start(Stage primaryStage) throws Exception {
        mainWindow = primaryStage;
        mainWindow.getIcons().add(new Image(new FileInputStream("@..\\..\\Images\\war_icon.png")));
        Parent root = FXMLLoader.load(getClass().getResource("MainScene.fxml"));
        mainWindow.setResizable(false);
        mainWindow.setTitle("Destroy or Defend");
        Scene scene = new Scene(root, 600, 430);
        mainWindow.setScene(scene);
        mainWindow.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}

/**
 File Defend:

 Sniper 947 947
 Sniper 940 940
 Sniper 920 930
 Sniper 910 800
 MirageTank 1150 930
 MirageTank 700 850
 Infantry 700 750
 Infantry 750 800
 Infantry 800 800
 Infantry 820 790
 Infantry 850 720
 Infantry 830 830
 NavySEAL 900 920
 NavySEAL 910 890

 PatriotMissileSystem 700 850
 MirageTank 1150 930
 Sniper 940 940

 **/


/**
 File Attack:

 Sniper 3 3
 Sniper 9 9
 Sniper 21 21
 Sniper 15 15
 Sniper 30 30
 MirageTank 650 650
 Infantry 120 120
 Infantry 9 3
 Infantry 15 30
 Infantry 100 100
 Infantry 80 80
 Infantry 90 90
 NavySEAL 150 150
 BlackEagle 700 700
 *
 *
 *
 *
 *
 Infantry 120 120
 BlackEagle 500 500
 GrizzlyTank 650 650
 GrizzlyTank 100 100
 GrizzlyTank 900 950
 NavySEAL 80 80
 NavySEAL 150 150
 **/

