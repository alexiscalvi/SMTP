package states;

import serveur.ServeurThread;

/**
 * Created by calvi on 24/04/2017.
 */
public class StateWaitData implements Etat{

    ServeurThread thread;

    public StateWaitData(ServeurThread thread){
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
