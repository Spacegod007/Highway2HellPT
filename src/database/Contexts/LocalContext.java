package database.Contexts;

import database.IContext;

import java.util.Properties;

public class LocalContext implements IContext {
    @Override
    public boolean testConnection(Properties props) {
        return false;
    }

    @Override
    public boolean closeConnection() {
        return false;
    }
}
