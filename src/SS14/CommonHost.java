package SS14;

public abstract class CommonHost {
    static final int W = 8;

    void startTimer(){
        // start timer
    }

    void stopTimer(){
        // stop timer
    }

    void transmit(Pkt pkt){}
    int checkSum(Pkt pkt){return 0;}
    boolean bitError(Pkt pkt){return false;}
    abstract boolean fromAbove(Pkt pkt);

    abstract void receive(Pkt pkt);

    abstract void timeout();

}
