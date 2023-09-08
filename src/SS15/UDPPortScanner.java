package SS15;

import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;

public class UDPPortScanner {
    public static boolean[] busy = new boolean[1000];

    // number of used ports
    public static int m;

    // number of scan requests
    public static int n;

    public static void scan(){
        n = 0;
        for(int i = 0; i <= 1000; i++){
            if(!scanPort(i + 2000)){
                busy[i] = true;
            }
            else{
                busy[i] = false;
                n++;
            }
        }
    }

    public static void sendEmail() throws IOException {
        DataOutputStream out = connectTo("cipmail.cs.fau.de", 25);
        out.writeBytes("HELO" + "cipmail.cs.fau.de" + "\r\n");
        out.writeBytes("MAIL FROM" + "status@rk.org" + "\r\n");
        out.writeBytes("RCPT TO:" + "mailCheck@cs.fau.de" + "\r\n");
        out.writeBytes("DATA" + "\r\n");
        out.writeBytes("Subject: Belegte UDP Ports" + "\r\n");
        out.writeBytes("\r\n");
        out.writeBytes("Scan " + m + ": " + n + "Ports sind besetzt" + "\r\n");
        for(int i = 0; i < busy.length; i++){
            if(busy[i]){
                out.writeBytes("Port " + (i + 2000) + " besetzt" + "\r\n");
            }
        }
        out.writeBytes("." + "\r\n");
        out.writeBytes("QUIT" + "\r\n");
        closeConnection(out);
    }

    public static void main(String[] args) throws IOException {
        m = 0;
        DatagramSocket serverSocket = new DatagramSocket(1500);


        byte[] receiveData = new byte[1024];
        while(true){
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            serverSocket.receive(receivePacket);

            String command = receivePacket.toString();
            if(command.equals("scan") && m < 10){
                m++;
                scan();
                sendEmail();
            }
            else if(command.equals("exit") || m >= 10){
                System.exit(42);
            }

        }


    }




    // ----------- Helper methods ------------- //

    private static boolean scanPort(int port){
        return true;
    }

    private static DataOutputStream connectTo(String hostname, int port){
        // Socket clientSocket = new Socket("cipmail.cs.fau.de", 25);

    }

    private static void closeConnection(DataOutputStream out){

    }
}


