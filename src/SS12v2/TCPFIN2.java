package SS12v2;

import java.io.IOException;

public class TCPFIN2 extends Base {
    enum State{INITIATE, FINWAIT1, FINWAIT2, CLOSING, TIMEWAIT, CLOSED};

    State currentState = State.INITIATE;



    @Override
    protected void close() throws IOException {
        if(currentState == State.INITIATE){
            send(FIN);
            currentState = State.FINWAIT1;
        }
    }

    @Override
    protected void receive(int flag) throws IOException {
        if(currentState == State.FINWAIT1){
            if(flag == ACK){
                currentState = State.FINWAIT2;
            }
            else if(flag == FIN){
                send(ACK);
                currentState = State.CLOSING;
            }
        }
        else if(currentState == State.FINWAIT2){
            if(flag == FIN){
                send(ACK);
                startTimer();
                currentState = State.TIMEWAIT;
            }
        }
        else if(currentState == State.CLOSING){
            if(flag == ACK){
                startTimer();
                currentState = State.TIMEWAIT;
            }
        }
    }

    @Override
    public void timeout() throws WrongStateException {
        if(currentState == State.TIMEWAIT){
            currentState = State.CLOSED;
        }
    }
}
