package iktato.view;

import iktato.domain.Mail;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;

public class NewEditDialog {

    private Stage dialogStage;
    private Mail mail;
    private boolean isOkClicked = false;

    @FXML
    private TextField txNumber;
    @FXML
    private TextField txFrom;
    @FXML
    private TextField txWhat;
    @FXML
    private TextField txName;
    @FXML
    private TextField txID;
    @FXML
    private Button btOK;
    @FXML
    private Button btCancel;
    @FXML
    private DatePicker datePicker;

    @FXML
    private void handleOk() {
        LocalDate localDate = datePicker.getValue();

        mail.setNumber(txNumber.getText());
        mail.setFrom(txFrom.getText());
        mail.setWhat(txWhat.getText());
        mail.setName(txName.getText());
        mail.setID(txID.getText());
        mail.setDate(localDate);

        isOkClicked = true;
        dialogStage.close();
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setMail(Mail mail) {
        this.mail = mail;

        txNumber.setText(mail.getNumber());
        txFrom.setText(mail.getFrom());
        txWhat.setText(mail.getWhat());
        txName.setText(mail.getName());
        txID.setText(mail.getID());
        datePicker.setValue(mail.getDate());
    }

    public boolean isOkClicked() {
        return isOkClicked;
    }
}
