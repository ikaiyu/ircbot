package pl.quider.standalone.irc;

import pl.quider.standalone.irc.dbsession.ADatabaseSession;
import pl.quider.standalone.irc.dbsession.MySqlDatabaseSession;

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
        bot.connect("open.ircnet.net");

        // Join the #pircbot channel.
        bot.joinChannel("#pircbot");

    }
}
