package states;

import serveur.ServeurThread;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    public void data(String[] data) throws IOException {



        if(data[data.length - 1].equals("<CR><LF>.<CR><LF>")) {

            String date = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SS").format(new Date());
            thread.addData("Date: "+ date);

            String from = "From: ";
            from += thread.getBufferSender().get(0);
            thread.addData(from);

            String to = "To: ";

            boolean firstDest = true;
            for (String dest : thread.getBufferDest()) {
                if (firstDest) {
                    to += dest;
                    firstDest = false;
                } else {
                    to += ", " + dest;
                }
            }

            thread.addData(to);

            int i = 1;
            String subject = "";
            if (data[0].equals("Subject:")) {
                while (!data[i].equals("\\n")) {
                    subject += data[i] + " ";
                    i++;
                }
                thread.addData("Subject: "+ subject);
            }
            else{
                subject += data[0];
            }

            String content = "";
            for(int j=i+1; j<data.length-1; j++){
                content += data[j]+ " ";
            }

            thread.addData(content);

            for(String dest: thread.getBufferDest()){
                String path = "users/" + dest + "/" + subject;

                /*
                Gestion du cas ou le subject existe deja
                 */
                File monFichier = new File(path);
                if(monFichier.exists()){
                    path += "(1)";
                }
                System.out.println("path: "+ path);
                try {
                    BufferedWriter writer = new BufferedWriter(new FileWriter(new File(path)));
                    for(String ligne : thread.getBufferData()){
                        writer.write(ligne);
                        writer.newLine();
                    }
                    writer.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            thread.send("250 OK send");
            thread.changeState(new StateCommunication(thread));


        }
        else{
            thread.send("554 Votre message doit terminer par <CR><LF>.<CR><LF>");
            thread.clearBuffers();
            thread.changeState(new StateCommunication(thread));
        }

    }

    @Override
    public void quit() {
    }

    @Override
    public String getLabel() {
        return "WaitData2";
    }
}
