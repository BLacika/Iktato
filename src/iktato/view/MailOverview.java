package iktato.view;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import iktato.Main;
import iktato.domain.Mail;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.util.Callback;
import org.hildan.fxgson.FxGson;

import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class MailOverview {

    private Main main;
    private ObservableList<Mail> mails = FXCollections.observableArrayList();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
    @FXML
    private TextField txSearch;
    @FXML
    private TableView<Mail> mailTable;
    @FXML
    private TableColumn<Mail, String> colNumber;
    @FXML
    private TableColumn<Mail, LocalDate> colDate;
    @FXML
    private TableColumn<Mail, String> colFrom;
    @FXML
    private TableColumn<Mail, String> colWhat;
    @FXML
    private TableColumn<Mail, String> colName;
    @FXML
    private TableColumn<Mail, String> colID;
    @FXML
    private Button btNew;
    @FXML
    private Button btEdit;
    @FXML
    private Button btDelete;

    public MailOverview() {
        loadMailsFromFile(new File("data.json"));
    }

    @FXML
    private void initialize() {
        mailTable.setFixedCellSize(Region.USE_COMPUTED_SIZE);

        colNumber.setCellValueFactory(data -> data.getValue().numberProperty());

        colDate.setCellValueFactory(data -> data.getValue().dateProperty());
        colDate.setCellFactory(column -> {
            return new TableCell<Mail, LocalDate>() {
                @Override
                protected void updateItem(LocalDate item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        setText(null);
                    } else {
                        setText(formatter.format(item));
                    }
                }
            };
        });

        colFrom.setCellValueFactory(data -> data.getValue().fromProperty());
        colFrom.setCellFactory(new Callback<TableColumn<Mail, String>, TableCell<Mail, String>>() {
            @Override
            public TableCell<Mail, String> call(TableColumn<Mail, String> param) {
                TableCell<Mail, String> cell = new TableCell<>();
                Text text = new Text();
                cell.setGraphic(text);
                cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
                text.wrappingWidthProperty().bind(cell.widthProperty());
                text.textProperty().bind(cell.itemProperty());
                return cell;
            }
        });

        colWhat.setCellValueFactory(data -> data.getValue().whatProperty());
        colWhat.setCellFactory(new Callback<TableColumn<Mail, String>, TableCell<Mail, String>>() {
            @Override
            public TableCell<Mail, String> call(TableColumn<Mail, String> param) {
                TableCell<Mail, String> cell = new TableCell<>();
                Text text = new Text();
                cell.setGraphic(text);
                cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
                text.wrappingWidthProperty().bind(cell.widthProperty());
                text.textProperty().bind(cell.itemProperty());
                return cell;
            }
        });

        colName.setCellValueFactory(data -> data.getValue().nameProperty());
        colName.setCellFactory(new Callback<TableColumn<Mail, String>, TableCell<Mail, String>>() {
            @Override
            public TableCell<Mail, String> call(TableColumn<Mail, String> param) {
                TableCell<Mail, String> cell = new TableCell<>();
                Text text = new Text();
                cell.setGraphic(text);
                cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
                text.wrappingWidthProperty().bind(cell.widthProperty());
                text.textProperty().bind(cell.itemProperty());
                return cell;
            }
        });

        colID.setCellValueFactory(data -> data.getValue().IDProperty());

        FilteredList<Mail> filteredData = new FilteredList<>(mails, p -> true);
        txSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(mail -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (mail.getNumber().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (mail.getFrom().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (mail.getWhat().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (mail.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (mail.getID().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });

        SortedList<Mail> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(mailTable.comparatorProperty());
        mailTable.setItems(sortedData);
        mailTable.getSortOrder().add(colNumber);

        this.mails.addListener((ListChangeListener<? super Mail>) e -> {
            saveMailsToFile(new File("data.json"));
        });
    }

    @FXML
    private void handleNewButton() throws IOException {
        Mail mail = new Mail();
        boolean isOkClicked = main.showNewEditDialog(mail);
        if (isOkClicked) {
            this.mails.add(mail);
        }
    }

    @FXML
    private void handleEditButton() throws IOException {
        Mail selected = mailTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            boolean okClicked = main.showNewEditDialog(selected);
            if (okClicked) {
                return;
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(main.getStage());
            alert.setTitle("Nincs kiválasztva");
            alert.setHeaderText("Nincs levél kiválasztva");
            alert.setContentText("Kérlek válassz ki egy sort!");

            alert.showAndWait();
        }
    }

    @FXML
    private void handleDeleteButton() throws IOException {
        int selectedIndex = mailTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initOwner(main.getStage());
            alert.setTitle("Sor törlése");
            alert.setHeaderText("Sor törlésének megerősítése!");
            alert.setContentText("Biztos, hogy törlöd ezt a sort?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                mailTable.getItems().remove(selectedIndex);
            } else {
                alert.close();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(main.getStage());
            alert.setTitle("Nincs kiválasztva");
            alert.setHeaderText("Nincs sor kiválasztva");
            alert.setContentText("Kérlek válassz ki egy sort!");

            alert.showAndWait();
        }
    }

    public void loadMailsFromFile(File file) {
        Gson gson = FxGson.coreBuilder().setPrettyPrinting().disableHtmlEscaping().create();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            this.mails = gson.fromJson(reader, new TypeToken<ObservableList<Mail>>() {}.getType());
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveMailsToFile(File file) {
        Gson gson = FxGson.coreBuilder().setPrettyPrinting().disableHtmlEscaping().create();
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            gson.toJson(this.mails, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setMain(Main main) {
        this.main = main;
    }
}
