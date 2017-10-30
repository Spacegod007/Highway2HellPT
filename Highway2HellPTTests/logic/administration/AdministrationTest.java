package logic.administration;

import RMItest.RMIClient;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by maxhe on 23-10-2017.
 */
public class AdministrationTest {

    Administration administration = new Administration(new RMIClient("192.168.44.1",1100));

    @Test
    public void getUserTest(){
        User user = new User("Max");
        administration.setUser(user);
        assertEquals(user,administration.getUser());
    }

    @Test
    public void getUserNullTest(){
        administration.setUser(null);
        assertEquals(null,administration.getUser());
    }

    @Test
    public void getUserNotEqualTest(){
        User user = new User("Max");
        administration.setUser(new User("Jan"));
        assertNotSame(user,administration.getUser());
    }

    @Test
    public void setUserTest(){
        User user = new User("Max");
        administration.setUser(user);
        assertEquals(user,administration.getUser());
    }

    @Test
    public void setUserNullTest(){
        User user = null;
        administration.setUser(user);
        assertEquals(null,user);
    }

    @Test
    public void setUserNotEqualTest(){
        User user = new User("Max");
        administration.setUser(new User("Jan"));
        assertNotSame(user,administration.getUser());
    }

    @Test
    public void hostLobbyTest(){
        assertEquals(true,administration.hostLobby("lobby"));
    }

    @Test
    public void joinLobbyTest(){
        administration.hostLobby("lobby");
        administration.hostLobby("lobby1");
        administration.refresh();
        Lobby lobby = new Lobby("lobby",1);
        assertEquals(true,administration.joinLobby(lobby));
    }
}
