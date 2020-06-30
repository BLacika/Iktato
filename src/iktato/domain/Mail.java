package iktato.domain;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

public class Mail {

    private StringProperty number;
    private StringProperty from;
    private StringProperty what;
    private StringProperty name;
    private StringProperty ID;
    private ObjectProperty<LocalDate> date;

    public Mail(String number, String from, String what,
                String name, String ID, LocalDate date) {
        this.number = new SimpleStringProperty(number);
        this.from = new SimpleStringProperty(from);
        this.what = new SimpleStringProperty(what);
        this.name = new SimpleStringProperty(name);
        this.ID = new SimpleStringProperty(ID);
        this.date = new SimpleObjectProperty<>(date);
    }

    public Mail() {
        this("", "", "", "", "", null);
    }

    public String getNumber() {
        return number.get();
    }

    public StringProperty numberProperty() {
        return number;
    }

    public void setNumber(String number) {
        this.number.set(number);
    }

    public String getFrom() {
        return from.get();
    }

    public StringProperty fromProperty() {
        return from;
    }

    public void setFrom(String from) {
        this.from.set(from);
    }

    public String getWhat() {
        return what.get();
    }

    public StringProperty whatProperty() {
        return what;
    }

    public void setWhat(String what) {
        this.what.set(what);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getID() {
        return ID.get();
    }

    public StringProperty IDProperty() {
        return ID;
    }

    public void setID(String ID) {
        this.ID.set(ID);
    }

    public LocalDate getDate() {
        return date.get();
    }

    public ObjectProperty<LocalDate> dateProperty() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date.set(date);
    }

    @Override
    public String toString() {
        return "Mail{" +
                "number=" + number +
                ", from=" + from +
                ", what=" + what +
                ", name=" + name +
                ", ID=" + ID +
                ", date=" + date +
                '}';
    }
}
