package WS1314;


import java.util.Random;

public abstract class Behavior {
    final int TAU = 100; // in Milliseconds
    final String ACK = "K";

    int m = 0; // Anzahl Fehlerversuche
    Random rnd = new Random();

    public class NotReadyException extends Exception {
        // ...
    }

    void startTimer(int ms) {
        // startet den Timer
        // ...
    }

    void stopTimer() {
        // stoppt den Timer
        // ...
    }

    void toAbove(String data) {
        // ...
    }

    void transmit(String data) {
        // ...
    }

    abstract void fromAbove(String data) throws NotReadyException;

    abstract void receive(String data);

    abstract void timeout();
}

