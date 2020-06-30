package iktato;

import iktato.domain.Mail;
import iktato.view.MailOverview;
import iktato.view.NewEditDialog;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;

public class Main extends Application {

    private Stage mainStage;

    @Override
    public void start(Stage stage) throws Exception {
        this.mainStage = stage;

        stage.setTitle("IKTATÓ");
        //stage.setResizable(false);
        stage.centerOnScreen();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/MailOverview.fxml"));
        AnchorPane ap = (AnchorPane) loader.load();
        MailOverview controller = loader.getController();
        controller.setMain(this);

        Scene scene = new Scene(ap);
        stage.setScene(scene);
        stage.show();
    }

    public boolean showNewEditDialog(Mail mail) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/NewEditDialog.fxml"));
        AnchorPane ap = (AnchorPane) loader.load();

        Stage dialogStage = new Stage();
        dialogStage.setTitle(mail.getNumber());
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(this.mainStage);
        Scene scene = new Scene(ap);
        dialogStage.setScene(scene);

        NewEditDialog controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setMail(mail);

        dialogStage.showAndWait();

        return controller.isOkClicked();
    }

    public Stage getStage() {
        return mainStage;
    }


}
