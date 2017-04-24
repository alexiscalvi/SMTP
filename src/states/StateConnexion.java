package states;

import serveur.ServeurThread;

/**
 * Created by calvi on 24/04/2017.
 */
public class StateConnexion implements Etat {

    String serverName;
    ServeurThread thread;

    public StateConnexion(ServeurThread thread, String serverName){
        this.thread = thread;
        this.serverName = serverName;
    }

    @Override
    public void ehlo() {

        thread.send("250 "+ serverName);

        //On envoie rien d'autre car on ne fait pas de fonctionnalitées supplémentaires.

        thread.changeState(new StateCommunication(thread));
        System.out.println("state: " + "COMMUNICATION");
    }

    @Override
    public void mailFrom(String user) {
        thread.nonAutorise();
    }

    @Override
    public void rcptTo(String user) {
        thread.nonAutorise();
    }

    @Override
    public void reset() {
        thread.nonAutorise();
    }

    @Override
    public void data(String[] data) {
        thread.nonAutorise();
    }

    @Override
    public void quit() {
        thread.send("+OK");
        thread.closeClientSocket();
    }
}
