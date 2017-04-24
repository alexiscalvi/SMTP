package serveur;

import serveur.ServeurMulti;

import java.io.IOException;

/**
 * Created by calvi on 03/04/2017.
 */
public class ServeurSMTP {

    public static void main(String[] args) throws IOException {

        //instanciation du serveur
        ServeurMulti serveurMulti = new ServeurMulti();
        serveurMulti.launch(25, "ServeurLucasFlorianAlexis", 4);


    }
}
