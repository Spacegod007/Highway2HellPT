package database.Repositories;

import database.*;

public class Repository {
    private IContext context;

    public Repository(IContext context){
        this.context = context;
    }
}
