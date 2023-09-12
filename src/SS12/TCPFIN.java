package SS12;

import java.io.IOException;

public class TCPFIN extends Base{

    STATE state = STATE.INIT;
    public enum STATE {FINWAIT1, FINWAIT2, CLOSING, TIMEWAIT, CLOSED, INIT };


    @Override
    void close() throws IOException {
        if(state == STATE.INIT){
            send(FIN);
            state = STATE.FINWAIT1;
        }
    }

    @Override
    void receive(int flag) throws IOException {
        // increase ACKs maybe ?
        if(state == STATE.FINWAIT1){
            if(flag == ACK){
                state = STATE.CLOSING;
            }
            else if(flag == FIN){
                send(ACK);
                state = STATE.FINWAIT2;

            }
        }
        else if(state == STATE.FINWAIT2){
            if(flag == FIN) {
                send(ACK);
                startTimer();
                state = STATE.TIMEWAIT;
            }
        }
        else if(state == STATE.CLOSING){
            if(flag == ACK){
                startTimer();
                state = STATE.TIMEWAIT;
            }
        }

    }

    @Override
    void timeout() throws WrongStateException {
        if(state == STATE.TIMEWAIT){
            state = STATE.CLOSED;
        }
    }
}
