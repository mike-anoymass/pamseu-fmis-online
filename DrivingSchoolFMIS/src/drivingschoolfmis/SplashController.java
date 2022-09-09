/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drivingschoolfmis;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import settings.StageManager;

/**
 *
 * @author way4ward
 */
public class SplashController implements Initializable {

    @FXML
    private AnchorPane splashAnchorPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        new loading().start();
    }

    class loading extends Thread {

        public void run() {
            try {
                Thread.sleep(2000);
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        FadeTransition fadeout = new FadeTransition(Duration.seconds(4), splashAnchorPane);
                        fadeout.setFromValue(1);
                        fadeout.setToValue(0);
                        fadeout.setCycleCount(1);
                        fadeout.play();

                        fadeout.setOnFinished(e -> {
                            Parent root = null;
                            try {
                                root = FXMLLoader.load(getClass().getResource("/login/LoginDocument.fxml"));
                            } catch (IOException ex) {
                                Logger.getLogger(SplashController.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            Scene scene = new Scene(root);

                            StageManager.loginStage.setScene(scene);
                            StageManager.loginStage.getIcons().add(new Image("img/driving_16.png"));
                            StageManager.loginStage.show();
                            splashAnchorPane.getScene().getWindow().hide();
                        });
                    }
                });
            } catch (InterruptedException ex) {
                Logger.getLogger(SplashController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
