package WS1213;

import SS12v2.Base;

import java.io.IOException;

public class Saloha extends BaseALOHA {
    enum State{WAITDATA, WAITSLOTSTART, TRANS, WAITACK, BACKOFF}
    State currentState = State.WAITDATA;
    int m = 0;
    Packet p = null;

    Exception WrongStateException() {
        return null;
    }

    @Override
    void fromAbove(Packet pkt) {
        if(currentState == State.WAITDATA){
            currentState = State.WAITSLOTSTART;
            p = pkt;
        }
        else{
            // new Exception
        };
    }

    @Override
    void timeout() {
        if(currentState == State.WAITACK){
            m++;
            currentState = State.BACKOFF;
            startTimer(rnd(0,(int) Math.pow(2, m - 1)) * TAU);
        }
        else if(currentState == State.BACKOFF){
            currentState = State.WAITSLOTSTART;
        }
    }

    @Override
    void newSlot() throws IOException {
        if(currentState == State.WAITSLOTSTART){
            currentState = State.TRANS;
            send(p);
            startTimer(TAU);
        }
    }

    @Override
    void rcvACK() {
        if(currentState == State.WAITACK){
            stopTimer();
            m = 0;
            currentState = State.WAITDATA;
        }
    }



}
