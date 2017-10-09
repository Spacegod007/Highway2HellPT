package logic.administration;

import java.util.List;

public class Administration {
    private User user;

    public Administration() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void joinLobby(){
        throw new UnsupportedOperationException();
    }
    public void hostLobby(){
        throw new UnsupportedOperationException();
    }



}
