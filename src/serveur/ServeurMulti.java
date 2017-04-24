package serveur;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
/**
 * Created by calvi on 03/04/2017.
 */
public class ServeurMulti {

    private ServerSocket ss;


    public void launch(int port, String serverName, int sizeDestMax) throws IOException {

        this.ss = new ServerSocket(port);
         while(true){
             Socket clientSocket = this.ss.accept();
             System.out.println("new client " + clientSocket.getInetAddress());
             if(canBeAccepted()){
                 ServeurThread thread = new ServeurThread(clientSocket, serverName, sizeDestMax);
             }

         }

    }

    //Methode qui dit si le nouveau client peut etre accepter (nombre de thread limités)
    public boolean canBeAccepted(){
        return true;
    }

}

