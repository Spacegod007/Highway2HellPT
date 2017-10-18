/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package RMItest;

import javafx.collections.ObservableList;
import logic.administration.Lobby;
import logic.administration.User;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Scanner;


public class RMIClient {

    // Set binding name for lobby administration
    private static final String bindingName = "LobbyAdmin";

    // References to registry and lobby administration
    private Registry registry = null;
    private ILobbyAdmin lobbyAdmin = null;

    // Constructor
    public RMIClient(String ipAddress, int portNumber) {

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
                System.out.println("Client: Cannot bind student administration");
                System.out.println("Client: RemoteException: " + ex.getMessage());
                lobbyAdmin = null;
            } catch (NotBoundException ex) {
                System.out.println("Client: Cannot bind student administration");
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

    public void addLobby(Lobby lobby){
        try{
            lobbyAdmin.addLobby(lobby);
        }
        catch(RemoteException ex){
            System.out.println("Client: RemoteException: " + ex.getMessage());
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

    public boolean joinLobby(Lobby lobby, User user)
    {
        try{
            return lobbyAdmin.joinLobby(lobby, user);
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
    };
    private void testlobbyAdministration() {
        // Get number of students
        try {
            System.out.println("Client: Number of lobbies: " + lobbyAdmin.getNumberOfLobbies());
        } catch (RemoteException ex) {
            System.out.println("Client: Cannot get number of lobbies");
            System.out.println("Client: RemoteException: " + ex.getMessage());
        }
        try {
            System.out.println("Client: Addlobby: " + lobbyAdmin.addLobby(new Lobby("test")));
        } catch (RemoteException ex) {
            System.out.println("Client: Cannot add lobby");
            System.out.println("Client: RemoteException: " + ex.getMessage());
        }
        try {
            System.out.println("Client: Number of lobbies: " + lobbyAdmin.getNumberOfLobbies());
        } catch (RemoteException ex) {
            System.out.println("Client: Cannot get number of lobbies");
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
        RMIClient client = new RMIClient(ipAddress, portNumber);
    }
}
