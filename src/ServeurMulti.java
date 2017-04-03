import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
/**
 * Created by calvi on 03/04/2017.
 */
public class ServeurMulti {

    private ServeurSocket ss;

    public void launch(int port) throws IOException {

        this.ss = new ServeurSocket(port);
         while(true){
             Socket clientSocket = this.ss.accept();
             System.out.println("new client " + clientSocket.getInetAddress());
             ServeurThread thread = new ServeurThread(clientSocket);
         }

    }

}

