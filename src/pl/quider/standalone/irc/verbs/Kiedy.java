package pl.quider.standalone.irc.verbs;

import org.hibernate.procedure.ParameterMisuseException;
import pl.quider.standalone.irc.MyBot;
import pl.quider.standalone.irc.model.Message;
import pl.quider.standalone.irc.services.UserService;

import java.util.Date;

/**
 * Created by Adrian on 14.10.2016.
 */
public class Kiedy extends Verb {
    public Kiedy(MyBot mybot, Message msg) {
        super(mybot, msg);
    }

    @Override
    public void execute(String parameter) throws Exception {
        final String[] split = parameter.split(MyBot.VERB_PARAM_DELIMITER);
        if(split.length >1){
            throw new ParameterMisuseException("Tylko jeden nick na raz");
        }

        final UserService userService = new UserService(split[0], null, null, bot.getSession());
        final Date seen = userService.seen(split[0]);
        this.sendMessage(seen, split[0]);
    }

    private void sendMessage(Date seen, String nickname) {
        StringBuilder sb = new StringBuilder();
        sb.append("Ostatni raz ").append(nickname).append(" pisał(a) ").append(seen);
    }
}
