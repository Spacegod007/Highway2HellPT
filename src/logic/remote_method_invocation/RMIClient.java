/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.remote_method_invocation;

import logic.administration.Lobby;
import logic.administration.User;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;


public class RMIClient {

    // Set binding name for lobby administration
    private static final String bindingName = "LobbyAdmin";
    private User user;
    public User getUser()
    {
        return user;
    }

    // References to registry and lobby administration
    private Registry registry = null;
    private ILobbyAdmin lobbyAdmin = null;

    public RMIClient(Properties properties)
    {
        String ip = properties.getProperty("ipAddress");
        int port = Integer.parseInt(properties.getProperty("port"));
        callClient(ip, port);
    }

    // Constructor
    public RMIClient(String ipAddress, int portNumber)
    {
        callClient(ipAddress, portNumber);
    }

    private void callClient(String ipAddress, int portNumber)
    {
        // Print IP address and port number for registry
        System.out.println("Client: IP Address: " + ipAddress);
        System.out.println("Client: Port number " + portNumber);

        // Locate registry at IP address and port number
        try {
            registry = LocateRegistry.getRegistry(ipAddress, portNumber);
        } catch (RemoteException ex) {
            System.out.println("Client: Cannot locate registry");
            System.out.println("Client: RemoteException: " + ex.getMessage());
            registry = null;
        }

        // Print result locating registry
        if (registry != null) {
            System.out.println("Client: Registry located");
        } else {
            System.out.println("Client: Cannot locate registry");
            System.out.println("Client: Registry is null pointer");
        }

        // Print contents of registry
        if (registry != null) {
            printContentsRegistry();
        }

        // Bind student administration using registry
        if (registry != null) {
            try {
                lobbyAdmin = (ILobbyAdmin) registry.lookup(bindingName);
            } catch (RemoteException ex) {
                System.out.println("Client: Cannot bind lobby administration");
                System.out.println("Client: RemoteException: " + ex.getMessage());
                lobbyAdmin = null;
            } catch (NotBoundException ex) {
                System.out.println("Client: Cannot bind lobby administration");
                System.out.println("Client: NotBoundException: " + ex.getMessage());
                lobbyAdmin = null;
            }
        }

        // Print result binding student administration
        if (lobbyAdmin != null) {
            System.out.println("Client: Student administration bound");
        } else {
            System.out.println("Client: Student administration is null pointer");
        }

        // Test RMI connection
        if (lobbyAdmin != null) {
            testConnection();
            try
            {
                user = lobbyAdmin.addUser("David");
            }
            catch(RemoteException ex)
            {
                System.out.println("Client: can't bind user to client");
            }
            //testlobbyAdministration();
        }
    }

    // Print contents of registry
    private void printContentsRegistry() {
        try {
            String[] listOfNames = registry.list();
            System.out.println("Client: list of names bound in registry:");
            if (listOfNames.length != 0) {
                for (String s : registry.list()) {
                    System.out.println(s);
                }
            } else {
                System.out.println("Client: list of names bound in registry is empty");
            }
        } catch (RemoteException ex) {
            System.out.println("Client: Cannot show list of names bound in registry");
            System.out.println("Client: RemoteException: " + ex.getMessage());
        }
    }

    public Lobby addLobby(String name){
        try{
            return lobbyAdmin.addLobby(name);
        }
        catch(RemoteException ex){
            System.out.println("Client: RemoteException: " + ex.getMessage());
            return null;
        }
    }

    public List<Lobby> getLobbies(){
        try{
            return lobbyAdmin.getLobbies();
        }
        catch (RemoteException ex){
            System.out.println("Client: RemoteException: " + ex.getMessage());
            return null;
        }
    }

    public int getNextID()
    {
        int i = LobbyAdmin.getNextID();
        System.out.println(i);
        return i;
    }

    public boolean joinLobby(Lobby lobby)
    {
        try{
            return lobbyAdmin.joinLobby(lobby, user);
        }
        catch(RemoteException ex){
            System.out.println("Client: RemoteException: " + ex.getMessage());
            return false;
        }
    }

    public boolean leaveLobby(Lobby lobby)
    {
        try{
            if(lobbyAdmin.leaveLobby(lobby, user)){
                System.out.println("Success(RMI)");
                return true;
            }
            return false;
        }
        catch(RemoteException ex){
            System.out.println("Client: RemoteException: " + ex.getMessage());
            return false;
        }
    }

    public Lobby getActiveLobby()
    {
        try{
            return lobbyAdmin.getActiveLobby(user);
        }
        catch(RemoteException ex){
            System.out.println("Client: RemoteException: " + ex.getMessage());
            return null;
        }
    }

    public void setActiveLobby(Lobby lobby)
    {
        try{
            lobbyAdmin.setActiveLobby(user, lobby);
        }
        catch(RemoteException ex){
            System.out.println("Client: RemoteException: " + ex.getMessage());
        }
    }

    public boolean kickPlayer(int l, int index)
    {
        try{
            return lobbyAdmin.kickPlayer(l, index);
        }
        catch(RemoteException ex){
            System.out.println("Client: RemoteException: " + ex.getMessage());
            return false;
        }
    }

    // Test RMI connection
    private void testConnection()
    {
        try
        {
            lobbyAdmin.getNumberOfLobbies();
            System.out.println("Client: Connected ");
        }
        catch (RemoteException ex)
        {
            System.out.println("Client: Cannot connect");
            System.out.println("Client: RemoteException: " + ex.getMessage());
    }
    }


    // Main method
    public static void main(String[] args) {

        // Welcome message
        System.out.println("CLIENT USING REGISTRY");

        // Get ip address of server
        Scanner input = new Scanner(System.in);
        System.out.print("Client: Enter IP address of server: ");
        String ipAddress = input.nextLine();

        // Get port number
        System.out.print("Client: Enter port number: ");
        int portNumber = input.nextInt();

        // Create client
        new RMIClient(ipAddress, portNumber);
    }

    public static Properties getConnectionProperties()
    {
        Properties properties = new Properties();

        File file = new File("properties/lobbyAdmin.properties");
        try (InputStream inputStream = new FileInputStream(file))
        {
            properties.load(inputStream);
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        return properties;
    }
}
