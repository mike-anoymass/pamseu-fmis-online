/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package settings;

import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author ANOYMASS
 */
public class StageManager {
    public static Stage historyStage = new Stage();
    public static Stage ParentStage = new Stage();
    public static Stage announcementStage = new Stage();
    public static Stage receiptStage = new Stage();
    public static Stage browsingStage = new Stage();
    public static Stage studentFormAddStage= new Stage();
    public static Stage studentFormEditStage= new Stage();
    public static Stage studentTestStage= new Stage();
    public static Stage receiptDetails = new Stage();
    public static Stage studentDetails = new Stage();
    public static Stage staffAddStage = new Stage();
    public static Stage staffEditStage = new Stage();
    public static Stage staffDetailsStage = new Stage();
    public static Stage staffPaymentStage = new Stage();
    public static Stage staffHistoryStage = new Stage();
    public static Stage loadingStage1 = new Stage();
    public static Stage loadingStage = new Stage();
    public static Stage loginStage = new Stage();

    public static void setStages(){
        browse(announcementStage, receiptStage, historyStage);

        browse(browsingStage, studentFormAddStage, studentFormEditStage);

        browse(studentTestStage, receiptDetails , studentDetails);

        browse(staffAddStage, staffEditStage , staffDetailsStage);

        browse(staffPaymentStage, staffHistoryStage , new Stage());
        
        browse(loadingStage, loadingStage1, loginStage, new Stage());
          
    }


    private static void browse(Stage browsingStage, Stage studentFormAddStage, Stage studentFormEditStage) {
        browsingStage.initModality(Modality.WINDOW_MODAL);
        browsingStage.setResizable(false);
        browsingStage.initOwner(ParentStage);

        studentFormAddStage.initModality(Modality.WINDOW_MODAL);
        studentFormAddStage.setResizable(false);
        studentFormAddStage.initOwner(ParentStage);

        studentFormEditStage.initModality(Modality.WINDOW_MODAL);
        studentFormEditStage.setResizable(false);
        studentFormEditStage.initOwner(ParentStage);
        
        
    }

    private static void browse(Stage loadingStage, Stage loadingStage1, Stage loginStage, Stage ll) {
        loadingStage.initModality(Modality.WINDOW_MODAL);
        loadingStage.setResizable(false);
        loadingStage.initOwner(ParentStage);
        loadingStage.initStyle(StageStyle.TRANSPARENT);

        loadingStage1.initModality(Modality.WINDOW_MODAL);
        loadingStage1.setResizable(false);
        loadingStage1.initStyle(StageStyle.TRANSPARENT);
        loadingStage1.initOwner(loginStage);
        
        loginStage.initModality(Modality.WINDOW_MODAL);
        loginStage.setResizable(false);
        loginStage.initStyle(StageStyle.UNDECORATED);
        
    }


}
