package states;

import serveur.ServeurThread;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

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
        thread.nonAutorise();
    }

    @Override
    public void mailFrom(String user) {
        thread.nonAutorise();
    }

    @Override
    public void rcptTo(String user) {
        Path path = FileSystems.getDefault().getPath("users/"+ user);
        if (Files.isDirectory(path)) {
            System.out.println("User connu");
            if(thread.getBufferDest().size() < thread.getSizeDestMax()){

                System.out.println("Buffer destinataire pas plein");
                thread.send("250 OK recipient");
                thread.addDest(user);

            }
            else{
                System.out.println("BufferDest plein");
                thread.send("452 buffer destinataires plein");

            }
            thread.changeState(new StateWaitData(thread));
        }
        else{
            System.out.println("User inconnu");
            thread.send("550 no such user");

        }
    }

    @Override
    public void reset() {
        thread.clearBuffers();
        thread.send("250 OK");
    }

    @Override
    public void data(String[] data) {
        thread.send("354 End data with <CR><LF>.<CR><LF>");
        thread.changeState(new StateWaitData2(thread));
    }

    @Override
    public void quit() {
        thread.send("+OK");
        thread.closeClientSocket();
    }

    @Override
    public String getLabel() {
        return "WaitData";
    }
}
