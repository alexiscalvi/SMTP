package serveur;

import states.Etat;
import states.StateConnexion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 * Created by calvi on 03/04/2017.
 */
public class ServeurThread extends Thread{

    private Etat state;
    private PrintStream out;
    private BufferedReader in;
    private Socket clientSocket;
    private String serveurName;
    private List<String> bufferSender = new ArrayList<String>();
    private List<String> bufferDest = new ArrayList<String>();
    private List<String> bufferData = new ArrayList<String>();
    private int sizeDestMax;


    public ServeurThread(Socket clientSocket, String serverName, int sizeDestMax) throws IOException {
        this.clientSocket = clientSocket;
        this.sizeDestMax = sizeDestMax;
        InputStreamReader inputStream = new InputStreamReader(clientSocket.getInputStream());
        this.out = new PrintStream(clientSocket.getOutputStream());
        this.in = new BufferedReader(inputStream);
        this.send("220 "+ serveurName + " STMP READY");
        // on lance le serveur
        this.state = new StateConnexion(this, serverName);
        System.out.println("Stat CONNEXION");
        this.start(clientSocket);
    }


    public void start(Socket clientSocket) {
        while (!this.clientSocket.isClosed()) {
            try {
                String mess = this.in.readLine();

                //Log chaque message entrant
                System.out.println("in: " + mess);

                //Découpe le message entrant par mot et le range en tableau facile à utiliser
                String[] stringList = mess.split(" ");
                if (stringList.length == 0) {
                    stringList[0] = mess;
                }

                //appelle la méthode correspondant au 1er mot du message (APOP, STAT, ...)
                switch (stringList[0]) {
                    case "ehlo":
                        this.state.ehlo();
                        break;
                    case "MAILFROM":
                        if(!stringList[1].isEmpty()){
                            this.state.mailFrom(stringList[1]);
                        }
                        else{
                            this.missingArgument();
                        }
                        break;
                    case "RCPTTO":
                        if(!stringList[1].isEmpty()){
                            this.state.rcptTo(stringList[1]);
                        }
                        else{
                            this.missingArgument();
                        }
                        break;
                    case "RSET":
                        this.state.reset();
                        break;
                    case "DATA":
                        this.state.data(stringList);
                        break;
                    case "QUIT":
                        this.state.quit();
                        break;

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //Cette méthode permet à un état de changer l'état de cette classe.
    public void changeState(Etat state) {
        this.state = state;
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

    public void nonAutorise(){
        this.send("-ERR non autorisé");
    }
    public void nonAutorise(String message){
        this.send("-ERR non autorisé, "+ message);
    }
    public void missingArgument(){
        this.send("-ERR missing argument");
    }


    /*
    BUFFERS
     */
    public void clearBuffers(){
        bufferSender.clear();
        bufferData.clear();
        bufferDest.clear();
        System.out.println("Les buffers ont été vidé.");
    }
    public void addSender(String user){
        this.bufferSender.add(user);
        System.out.println("User ajouté au bufferSender");
    }
    public void addDest(String user){
        this.bufferDest.add(user);
        System.out.println("User ajouté au bufferDest");
    }
    public void addData(String user){
        this.bufferData.add(user);
        System.out.println("Données ajoutées au bufferData");
    }
    public List<String> getBufferSender(){
        return bufferSender;
    }
    public List<String> getBufferDest(){
        return bufferDest;
    }
    public List<String> getBufferData(){
        return bufferData;
    }
    public int getSizeDestMax(){
        return sizeDestMax;
    }

}
