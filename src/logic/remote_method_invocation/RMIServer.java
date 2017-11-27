
package logic.remote_method_invocation;

import logic.fontyspublisher.RemotePublisher;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Timer;
import java.util.TimerTask;

public class RMIServer {

    // Set port number
    private static final int portNumber = 1100;

    // Set binding name for student administration
    private static final String bindingName = "LobbyAdmin";
    private static final String bindingNamePublisher = "publisher";

    // References to registry and student administration
    private Registry registry = null;
    private LobbyAdmin lobbyAdmin = null;
    private RemotePublisher publisher = null;

    // Constructor
    public RMIServer() {


        // Print port number for registry
        System.out.println("Server: Port number " + portNumber);

        // Create student administration
        try {
            publisher = new RemotePublisher();
            lobbyAdmin = new LobbyAdmin(publisher);
            System.out.println("Server: Lobby administration created");
        } catch (RemoteException ex) {
            System.out.println("Server: Cannot create lobby administration");
            System.out.println("Server: RemoteException: " + ex.getMessage());
            lobbyAdmin = null;
        }

        //Start the timer to clean the list of lobbies
        updateLobbies();

        // Create registry at port number
        try {
            registry = LocateRegistry.createRegistry(portNumber);
            System.out.println("Server: Registry created on port number " + portNumber);
        } catch (RemoteException ex) {
            System.out.println("Server: Cannot create registry");
            System.out.println("Server: RemoteException: " + ex.getMessage());
            registry = null;
        }

        try{
            registry.rebind(bindingNamePublisher, publisher);
        } catch(RemoteException ex){
            System.out.println("Server: Cannot bind publisher");
            System.out.println("Server: RemoteException: " + ex.getMessage());
        }
        // Bind student administration using registry
        try {
            registry.rebind(bindingName, lobbyAdmin);
        } catch (RemoteException ex) {
            System.out.println("Server: Cannot bind lobby administration");
            System.out.println("Server: RemoteException: " + ex.getMessage());
        }
    }

    /**
     * Start the timer to clean the list of lobbies
     */
    private void updateLobbies()
    {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask()
        {
            @Override
            public void run()
            {
                lobbyAdmin.cleanLobbies();
            }
        }, 0, 1000);
    }

    /** Print IP addresses and network interfaces
     *
     */
    private static void printIPAddresses() {
        try {
            InetAddress localhost = InetAddress.getLocalHost();
            System.out.println("Server: IP Address: " + localhost.getHostAddress());
            // Just in case this host has multiple IP addresses....
            InetAddress[] allMyIps = InetAddress.getAllByName(localhost.getCanonicalHostName());
            if (allMyIps != null && allMyIps.length > 1) {
                System.out.println("Server: Full list of IP addresses:");
                for (InetAddress allMyIp : allMyIps) {
                    System.out.println("    " + allMyIp);
                }
            }
        } catch (UnknownHostException ex) {
            System.out.println("Server: Cannot get IP address of local host");
            System.out.println("Server: UnknownHostException: " + ex.getMessage());
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        // Welcome message
        System.out.println("SERVER USING REGISTRY");

        // Print IP addresses and network interfaces
        printIPAddresses();

        // Create server
        RMIServer server = new RMIServer();
    }
}
