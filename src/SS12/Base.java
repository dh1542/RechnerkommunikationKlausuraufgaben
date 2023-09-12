package SS12;

import java.io.IOException;

public abstract class Base{

    final int FIN = 0;

    final int ACK = 0;


    class WrongStateException extends Exception{}

    void send(int flag) {
        // send flag to communication parntner
    }

    void startTimer(){
        // start timer
    }

    abstract void close() throws IOException;

    abstract void receive(int flag) throws IOException;

    abstract void timeout() throws WrongStateException;

}
