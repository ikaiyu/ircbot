package pl.quider.standalone.irc.verbs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.procedure.ParameterMisuseException;
import pl.quider.standalone.irc.MyBot;
import pl.quider.standalone.irc.model.Message;
import pl.quider.standalone.irc.services.UserService;

import java.util.Date;

/**
 * Created by Adrian on 14.10.2016.
 */
public class Kiedy extends Verb {
    private static final Logger LOG = LogManager.getLogger("Kiedy");

    public Kiedy(MyBot mybot, Message msg) {
        super(mybot, msg);
    }

    @Override
    public void execute(String parameter) throws Exception {
        LOG.info("Kiedy verb fired");
        final String[] split = parameter.split(MyBot.VERB_PARAM_DELIMITER);
        if(split.length >1 || split.length == 0){
            throw new ParameterMisuseException("Tylko jeden nick na raz");
        }
        LOG.debug("parameter: "+ split[0].toString());
        final UserService userService = new UserService(split[0], null, null, bot.getSession());
        Date seen = null;
        seen = userService.seen(split[0]);
        this.sendMessage(seen, split[0]);
    }

    private void sendMessage(Date seen, String nickname) {
        if(seen != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("Ostatni raz ").append(nickname).append(" pisał(a) ").append(seen);
            bot.sendMessage(msg.getChannel(), sb.toString());
        } else {
            bot.sendMessage(msg.getChannel(), "Nic nie mówi mi nick "+nickname);
        }
    }
}
