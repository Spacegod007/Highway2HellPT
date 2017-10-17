package database;

import java.util.Properties;

public interface IContext{
    boolean testConnection(Properties props);
    boolean closeConnection();
}
