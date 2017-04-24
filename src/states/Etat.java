package states;

/**
 * Created by calvi on 24/04/2017.
 */
public interface Etat {

    void ehlo();

    void mailFrom(String user);

    void rcptTo(String user);

    void reset();

    void data(String[] data);

    void quit();
}
