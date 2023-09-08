package SS13;

import java.io.*;
import java.net.Socket;
import java.util.Objects;

public class RKMailClient {

    private static boolean checkPOP3 (String s){
        return s.contains("+OK");
    }

    private static int getEmailsCount() throws IOException {
        int emailCount = 0;
        Socket sock = new Socket("mail.rk.org", 110);

        BufferedReader rx = new BufferedReader(new InputStreamReader(sock.getInputStream()));
        BufferedWriter tx = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));

        if(checkPOP3(rx.readLine())){
            tx.write("user rk\r\n");
            if(checkPOP3(rx.readLine())) {
                tx.write("pass rK!23\r\n");
                if (checkPOP3(rx.readLine())) {
                    tx.write("list\r\n");
                    int i = 1;
                    String s = rx.readLine();
                    while (!s.equals(".\r\n")) {
                        emailCount++;
                        s = rx.readLine();
                    }
                    tx.write("quit\r\n");
                }
            }
        }

        rx.close();
        tx.close();
        sock.close();
        return emailCount;


    }

    private static boolean checkSMTP(String s){
        return (s.contains("220") || s.contains("250") || s.contains("354") || s.contains("221"));
    }

    private static void sendEmail(int emailCount) throws IOException {
        Socket sock = new Socket("cipmail.cs.fau.de", 25);
        BufferedReader rx = new BufferedReader(new InputStreamReader(sock.getInputStream()));
        BufferedWriter tx = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));

        if(checkSMTP(rx.readLine())){
            tx.write("HELO rk.org\r\n");
            if(checkSMTP(rx.readLine())){
                tx.write("MAIL FROM <status@rk.org>\r\n");
                if(checkSMTP(rx.readLine())){
                    tx.write("RCPT TO: <mailCheck@cs.fau.de>\r\n");
                    if(checkSMTP(rx.readLine())){
                        tx.write("DATA\r\n");
                        if(checkSMTP(rx.readLine())){
                            tx.write("RK hat " + emailCount + " E-Mails.\r\n");
                            tx.write(".\r\n");
                            if(checkSMTP(rx.readLine())){
                                tx.write("QUIT\r\n");
                            }
                        }

                    }
                }
            }
        }
        tx.close();
        rx.close();
        sock.close();

    }



    public static void main(String[] args) throws IOException {

        sendEmail(getEmailsCount());


    }
    public static void exit(){
        System.out.println("Fehler aufgetreten");
        System.exit(-1);
    }


    
}
