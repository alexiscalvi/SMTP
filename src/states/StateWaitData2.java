package states;

import serveur.ServeurThread;

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
    public void data(String[] data) {

        if(data[data.length - 1] == "<CR><LF>.<CR><LF>") {


            StringBuilder stb = new StringBuilder();
            String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
            stb.append("Date: ");
            stb.append(date);
            stb.append("/n From: ");
            stb.append(thread.getBufferSender().get(0));
            stb.append("/n To: ");
            boolean firstDest = true;
            for (String dest : thread.getBufferDest()) {
                if (firstDest) {
                    stb.append(dest);
                    firstDest = false;
                } else {
                    stb.append(", " + dest);
                }
            }
            stb.append("/n");
            int i = 1;
            if (data[0] == "Subject") {
                stb.append("Subject: ");

                while (data[i] != "/n") {
                    stb.append(data[i] + " ");
                    i++;
                }
                stb.append("/n");

            }
            for(int j=i; j<data.length -1; j++){
                stb.append(data[i]+ " ");
            }

            System.out.println(stb);
        }
        else{
            thread.send("554 Votre message doit terminer par <CR><LF>.<CR><LF>");
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
