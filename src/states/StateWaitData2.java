package states;

import serveur.ServeurThread;

/**
 * Created by calvi on 24/04/2017.
 */
public class StateWaitData2 implements Etat{

    ServeurThread thread;

    public StateWaitData2(ServeurThread thread){
        this.thread = thread;
    }

    @Override
    public void ehlo() {

    }

    @Override
    public void mailFrom(String user) {

    }

    @Override
    public void rcptTo(String user) {

    }

    @Override
    public void reset() {

    }

    @Override
    public void data(String[] data) {

    }

    @Override
    public void quit() {

    }
}
