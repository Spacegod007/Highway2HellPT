package database.Repositories;

import database.*;

import java.util.Properties;

public class Repository {
    private IContext context;

    public Repository(IContext context){
        this.context = context;
    }

    public boolean testConnection(){
        Properties props = new Properties();
        props.setProperty("jdbc.url","jdbc:sqlserver://mssql.fhict.local");
        props.setProperty("jdbc.username","dbi358092");
        props.setProperty("jdbc.password","Proftaak4");
        return context.testConnection(props);
    }

    public boolean closeConnection(){
        return context.closeConnection();
    }
}
