package qwew.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static IConnector connector;

    //Port to bind sender to
    static int port;

    //Destination address
    static String destIP;
    static int destPort;

    public static void main(String[] args) throws IOException {

        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

        
        port = 8080;
        

        try{
            destIP = args[0];
            destPort = Integer.parseInt(args[1]);
        }
        catch(ArrayIndexOutOfBoundsException e){
            
            System.out.println("Enter destination IP:");
            destIP = stdin.readLine();

            System.out.println("Enter port on destination host:");
            destPort = Integer.parseInt(stdin.readLine());
        }

        //Sender setup
        connector = new UDPConnector(port);
        System.out.println("\n--CONNECTOR IS SET UP--\n");

        //Listener setup
        ListenerThread listener = new ListenerThread(port+1);
        listener.start();

        System.out.print("\nYou: ");
        String msg = stdin.readLine();
        connector.send(msg, destIP, destPort);
    }
}