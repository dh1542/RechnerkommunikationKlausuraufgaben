package WS1213;

import java.io.IOException;




public abstract class BaseALOHA {



    final int TAU = 10; // Slotzeit

/* Exception für einen falschen Systemzustand */


    void send(Packet pkt) throws IOException {
        // ...
    }

    void startTimer(int s) {
        // ...
    }

    /* Stoppt den Timer */
    void stopTimer() {
        // ...
    }

    /* Liefert eine Zufallszahl im Bereich [b, e) zurück */
    int rnd(int b, int e) {
        return 0;
    }

    /**
     * Folgendes muss in Saloha implementiert werden
     */

    /* Wird aufgerufen wenn Daten verschickt werden sollen */
    abstract void fromAbove(Packet pkt);

    /* Wird nach Ablauf des Timers aufgerufen */
    abstract void timeout();

    /* Wird ZYKLISCH zu JEDEM Slotbeginn aufgerufen */
    abstract void newSlot() throws IOException;

    /* Wird aufgerufen wenn ein ACK empfangen wird */
    abstract void rcvACK();
}
