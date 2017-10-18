package logic.administration;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import jdk.internal.util.xml.impl.Input;

public class User implements Observable {
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public User(String username) {
        this.username = username;
    }

    @Override
    public void addListener(InvalidationListener listener) {

    }

    @Override
    public void removeListener(InvalidationListener listener) {

    }

    @Override
    public String toString() {
        return "User: " + username;
    }
}
