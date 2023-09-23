package SS14;

public class SendingHost extends CommonHost {



    int seq = 0;

    @Override
    boolean fromAbove(Pkt pkt) {
        return false;
    }

    @Override
    void receive(Pkt pkt) {

    }

    @Override
    void timeout() {

    }
}
