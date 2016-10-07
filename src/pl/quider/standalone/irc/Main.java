package pl.quider.standalone.irc;

import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.NickAlreadyInUseException;
import pl.quider.standalone.irc.dbsession.ADatabaseSession;
import pl.quider.standalone.irc.dbsession.MySqlDatabaseSession;

import java.io.IOException;

/**
 * Created by Adrian on 27.09.2016.
 */
public class Main {

    public static void main(String[] args) throws Exception {

        //do dispatching depends on what db is connected
        ADatabaseSession session = MySqlDatabaseSession.create();

        // Now start our bot up.
        MyBot bot = new MyBot(session);

        // Enable debugging output.
        bot.setVerbose(true);

        // Connect to the IRC server.
        bot.changeNick("Adirael");
        try {
            bot.connect("open.ircnet.net");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NickAlreadyInUseException e) {
            bot.changeNick("_Adirael");

        }
        // Join the #pircbot channel.
        bot.joinChannel("#pircbot");

    }
}
