package states;

import serveur.ServeurThread;

/**
 * Created by calvi on 24/04/2017.
 */
public class StateCommunication implements Etat{

    ServeurThread thread;

    public StateCommunication(ServeurThread thread){
        this.thread = thread;
    }



    @Override
    public void ehlo() {
        thread.nonAutorise();
    }

    @Override
    public void mailFrom(String user) {

        thread.clearBuffers();
        thread.addSender(user);
        thread.send("250 sender OK");
        thread.changeState(new StateWaitRCPT(thread));

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

    @Override
    public String getLabel() {
        return "Communication";
    }
}
