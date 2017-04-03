import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by calvi on 03/04/2017.
 */
public class ServeurThread extends Thread{

    private State state;
    private PrintStream out;
    private BufferedReader in;
    private Socket clientSocket;
    private String serveurName;

    public ServeurThread(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        InputStreamReader inputStream = new InputStreamReader(clientSocket.getInputStream());
        this.out = new PrintStream(clientSocket.getOutputStream());
        this.in = new BufferedReader(inputStream);
        this.send("220 "+ serveurName + " STMP READY");
        // on lance le serveur
        this.state = new StateConnexion(this);
        System.out.println("State CONNEXION");
        this.start(clientSocket);
    }


    public void start(Socket clientSocket){

    }

    //Cette méthode est celle qui envoie tous les messages.
    public void send(String string) {
        try {
            byte[] message = (string + "\n").getBytes();
            this.out.write(message);
            this.out.flush();
            System.out.println("out: " + string);
        } catch (IOException ex) {
            Logger.getLogger(ServeurThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //Si le client appelle une méthode non existente, celle-ci sera appellée par défaut.
    private void error(String[] stringList) {
        this.send("-ERR méthode non existente");
    }

    //Ferme le socket.
    public void closeClientSocket() {
        try {
            clientSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(ServeurThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
