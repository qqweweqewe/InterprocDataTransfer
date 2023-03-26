package qwew.net;

import java.net.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class UDPConnector implements IConnector{

    private DatagramSocket dgSocket;

    //Regular constructor with bind port
    public UDPConnector(int port) {
        try {
            dgSocket = new DatagramSocket(port);
        }catch (SocketException e){
            System.out.println("Something's wrong with socket:");
            System.out.println(e.getMessage());
        }
    }

    //Listens for an incoming package, 1 KB limit for now
    @Override
    public String get() throws IOException {
        String msg; //Empty String to extract data to
        DatagramPacket pack = new DatagramPacket(new byte[1024], 1024); //Packet to "catch" data in
        //Receiving package
        dgSocket.receive(pack);
        msg = new String(pack.getData());
        return msg;
    }

    //A method to send a message. Parameters: msg - the message itself, toIP - destination host, toPort - port on dest. host
    @Override
    public void send(String msg, String toIP, int toPort) {
        try {
            byte[] buffer = msg.getBytes(StandardCharsets.UTF_8); //Converting String to bytes array
            try{
                InetAddress destIP = InetAddress.getByName(toIP); //Converting from String to InetAddress

                DatagramPacket pack = new DatagramPacket(buffer, buffer.length, destIP, toPort); //Preparing a package

                dgSocket.send(pack); //Sending it
                dgSocket.close(); //And closing the socket
            }catch(UnknownHostException e){
                System.out.println("Something's wrong with the host: ");
                System.out.println(e.getMessage());
            }

            System.out.println("\n--MESSAGE SENT--"); //This message means success
        } catch (IOException e) {
            System.out.println("An error has occurred: "); //And this does not :(
            System.out.println(e.getMessage());
        }
    }
}