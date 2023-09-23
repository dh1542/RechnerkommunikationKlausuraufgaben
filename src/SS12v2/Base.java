package SS12v2;

import java.io.IOException;

public abstract class Base {

    final int FIN = 0;

    final int ACK = 0;


    class WrongStateException extends Exception{}

    void send(int flag) {
        // send flag to communication parntner
    }

    void startTimer(){
        // start timer
    }

    protected abstract void close() throws IOException;

    protected abstract void receive(int flag) throws IOException;

    public abstract void timeout() throws WrongStateException;

}
