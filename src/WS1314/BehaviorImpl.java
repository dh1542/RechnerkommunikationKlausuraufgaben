package WS1314;

import java.util.Objects;

public class BehaviorImpl extends Behavior{
    public enum STATE{WAITDATA, WAITACK, BACKOFF, WAITFRAME};
    STATE currentState = STATE.WAITDATA;
    String data = null;


    @Override
    void fromAbove(String data) throws NotReadyException {
        if(currentState == STATE.WAITDATA){
            transmit(data);
            startTimer(TAU);
            this.data = data;
            currentState = STATE.WAITACK;
        }
        else throw new NotReadyException();
    }

    @Override
    void receive(String data) {
        if(currentState == STATE.WAITACK && data.equals(ACK)){
            stopTimer();
            m = 0;
            currentState = STATE.WAITDATA;
        }
        else if(currentState == STATE.WAITFRAME){
            if(data.equals("K")){
                stopTimer();
                m = 0;
            }
            else{
                transmit(ACK);
                toAbove(data);
            }

        }
    }

    @Override
    void timeout() {
        if(currentState == STATE.WAITACK){
            m++;
            currentState = STATE.BACKOFF;
            startTimer(rnd.nextInt((int) (Math.pow(2, m) - 1) *  TAU));
            transmit(data);
            currentState = STATE.WAITACK;
        }

    }
}
