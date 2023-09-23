package SS14;

public class SendingHost extends CommonHost {
    int nextSQN = 1;
    int base = 1;

    Pkt[] packets = new Pkt[1000];


    private Pkt genPkt(int nextSQN, Pkt packet){
        Pkt out = new Pkt();
        out.seqnum = nextSQN;
        out.acknum = packet.acknum;
        out.payload = packet.payload;
        out.checksum = packet.checksum;
        return out;
    }



    @Override
    boolean fromAbove(Pkt pkt) {
        if(nextSQN >= base + W){
            return false;
        }
        else{
            packets[nextSQN] = genPkt(nextSQN, pkt);
            transmit(packets[nextSQN]);
            if(base == nextSQN){
                startTimer();
            }
            nextSQN++;
        }
        return true;
    }

    @Override
    void receive(Pkt pkt) {
        if(!bitError(pkt)){
            if(pkt.seqnum == base - 1){
                for(int i = base; i < nextSQN; i++){
                    transmit(packets[i]);
                }
            }
            else if(base <= pkt.seqnum && pkt.seqnum < nextSQN){
                base = pkt.acknum + 1;
                if(nextSQN == base){
                    stopTimer();
                }
                else{
                    startTimer();
                }
            }
        }
    }

    @Override
    void timeout() {
        for(int i = base; i < nextSQN; i++){
            transmit(packets[i]);
        }
        startTimer();
    }
}
