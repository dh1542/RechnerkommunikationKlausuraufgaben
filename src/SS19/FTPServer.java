package SS19;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class FTPServer {
    private static boolean checkLogin(String username, String password){
        return true;
    }

    private static void rcvFile(InputStream in, String filename){
        // do sth, always works
    }

    private static void handleDialog(Socket sock) throws IOException {
        BufferedReader rx = new BufferedReader(new InputStreamReader(sock.getInputStream()));
        BufferedWriter tx = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));


        String username = "";
        String password = "";
        int port = 0;

        tx.write("220" + sock.getLocalAddress() +  "FTP Server ready .\r\n");
        tx.flush();


        while(!rx.readLine().equals("QUIT")){
            String[] command = rx.readLine().split(" ");
            if(command[0].equals("USER")){
                username = command[1];
                tx.write("331 User okay, need password .\r\n");
                tx.flush();
            }
            else if(command[0].equals("PASS")){
                password = command[1];
                if(checkLogin(username, password)){
                    tx.write("230 Login successful .\r\n");
                    tx.flush();
                }
            }
            else if(command[0].equals("PORT")){
                port = Integer.parseInt(command[1]);
                tx.write("200 PORT command successful .\r\n");
                tx.flush();
            }
            else if(command[0].equals("STOR")){
                // new socket + accepting connection
                Socket acceptingSocket = new Socket(sock.getInetAddress(), port, null, 20);
                // call receive File
                rcvFile(acceptingSocket.getInputStream(), command[1]);

                tx.write("226 Transfer completed . \r\n");
                tx.flush();

                acceptingSocket.close();
            }
        }
        tx.write("221 Goodbye .\r\n");



        rx.close();
        tx.close();

    }

    public static void main(String[] args) throws IOException {
        ServerSocket welcomeSocket = new ServerSocket(21);

        while(true){
            Socket connectionSocket = welcomeSocket.accept();

            handleDialog(connectionSocket);

            connectionSocket.close();
        }

    }
}
