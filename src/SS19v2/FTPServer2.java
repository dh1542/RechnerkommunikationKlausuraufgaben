package SS19v2;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class FTPServer2 {
    private static boolean checkLogin(String username, String password){
        return true;
    }

    private static void rcvFile(InputStream in, String filename){
        // do sth, always works
    }

    public static void handleDialog(Socket sock) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));

        writer.write("220 ftp.rk.org FTP Server ready .\r\n");
        writer.flush();

        String username = "";
        String password = "";

        int port = 0;

        String received = reader.readLine();
        if(received.startsWith("USER")){
            username = received.split(" ")[1];
            writer.write("331 User okay, need password .\r\n");
            writer.flush();
            received = reader.readLine();
            if(received.startsWith("PASS")){
                password = received.split(" ")[1];
                if(checkLogin(username, password)){
                    writer.write("230 Login successful .\r\n");
                    writer.flush();
                    received = reader.readLine();
                    while(!received.startsWith("QUIT")){
                        if(received.startsWith("PORT")) {
                            port = Integer.parseInt(received.split(" ")[1]);
                            writer.write("200 PORT command successful .\r\n");
                            writer.flush();
                        }
                        if(received.startsWith("STOR")) {
                            String filename = received.split(" ")[1];
                            Socket socket = new Socket(sock.getInetAddress(), port, null, 20);
                            rcvFile(socket.getInputStream(), filename);
                            socket.close();
                            writer.write("226 Transfer completed .\r\n");
                            writer.flush();

                        }
                        received = reader.readLine();
                    }

                    writer.write("221 Goodbye .\r\n");
                    writer.flush();

                }
            }

        }
        reader.close();
        writer.close();
    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(21);

        while(true){
            Socket acceptingSocket = serverSocket.accept();
            handleDialog(acceptingSocket);
            acceptingSocket.close();
        }
    }
}
