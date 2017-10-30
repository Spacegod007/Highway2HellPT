package bootstrapper;

import logic.remote_method_invocation.RMIClient;
import logic.administration.Administration;
import sample.Main;

import java.util.Properties;

/**
 * Bootstrap class to initiate the entire application
 */
public class Program
{
    public static void main(String[] args)
    {
        //TODO: initiating the entire application from this point

        Properties properties = RMIClient.getConnectionProperties();
        System.out.println("properties made");

        RMIClient rmiClient = new RMIClient(properties);
        System.out.println("rmi client created");

        Administration administration = new Administration(rmiClient);
        System.out.println("administration created");

        Main.launchView(args, administration);
    }
}

