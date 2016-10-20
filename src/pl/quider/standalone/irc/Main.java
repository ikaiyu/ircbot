package pl.quider.standalone.irc;

import pl.quider.standalone.irc.exceptions.NotConfiguredException;
import pl.quider.standalone.irc.protocol.NickAlreadyInUseException;
import pl.quider.standalone.irc.dbsession.ADatabaseSession;
import pl.quider.standalone.irc.dbsession.MySqlDatabaseSession;

import java.io.File;
import java.io.IOException;

/**
 * Created by Adrian on 27.09.2016.
 */
public class Main {

    public static void main(String[] args) throws Exception {
        //before we run database connection and other parts
        //first read parameters:
        Configuration configuration = Configuration.getInstance();
        try {
            if (args.length > 0) {
                //we have parameter which supposed to be a configuration file
                File file = new File(args[0]);
                configuration.loadConfig(file.getAbsolutePath());

            } else {
                configuration.loadConfig("configuration.properties");
            }
        } catch (NotConfiguredException e) {
            //end application because it is not configured.

            return;
        }

        ADatabaseSession session = MySqlDatabaseSession.create();
        MyBot bot = new MyBot(session);
        bot.setVerbose(true);
        bot.changeNick(configuration.getValue(ConfigurationKeysContants.NICK));
        try {
            bot.connect(configuration.getValue(ConfigurationKeysContants.SERVER1));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NickAlreadyInUseException e) {
            bot.changeNick(configuration.getValue(ConfigurationKeysContants.ALT_NICK));

        }
        //join service channel
        bot.joinChannel(configuration.getValue(ConfigurationKeysContants.JOIN_CHANNEL));

    }
}
