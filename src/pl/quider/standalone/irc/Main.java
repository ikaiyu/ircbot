package pl.quider.standalone.irc;

import pl.quider.standalone.irc.protocol.IrcException;
import pl.quider.standalone.irc.protocol.NickAlreadyInUseException;
import pl.quider.standalone.irc.dbsession.ADatabaseSession;
import pl.quider.standalone.irc.dbsession.MySqlDatabaseSession;

import java.io.IOException;

/**
 * Created by Adrian on 27.09.2016.
 */
public class Main {

    public static void main(String[] args) throws Exception {

        ADatabaseSession session = MySqlDatabaseSession.create();
        MyBot bot = new MyBot(session);
        bot.setVerbose(true);
        bot.changeNick("Adirael");
        try {
            bot.connect("open.ircnet.net");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NickAlreadyInUseException e) {
            bot.changeNick("_Adirael");

        }
        bot.joinChannel("#pircbot");

    }
}
