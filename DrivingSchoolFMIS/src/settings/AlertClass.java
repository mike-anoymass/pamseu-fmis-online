package settings;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;

public class AlertClass {

    public static void makeAlert(String title, String txt) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        switch (title) {
            case "error":
                alert = new Alert(Alert.AlertType.ERROR);
                break;

            case "information":
                alert = new Alert(Alert.AlertType.INFORMATION);
                break;
        }

        alert.initModality(Modality.APPLICATION_MODAL);
        
        if(StageManager.ParentStage.isShowing()){
            alert.initOwner(StageManager.ParentStage);
        }     
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(txt);
        alert.showAndWait();

    }

    public static boolean makePromptAlert(String title, String txt) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(txt);
        Optional<ButtonType> action = alert.showAndWait();

        if (action.get() == ButtonType.OK) {

            return true;
        }
        return false;
    }

}
