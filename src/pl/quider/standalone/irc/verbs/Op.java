package pl.quider.standalone.irc.verbs;

import org.hibernate.Session;
import org.hibernate.Transaction;
import pl.quider.standalone.irc.MyBot;
import pl.quider.standalone.irc.model.Message;
import pl.quider.standalone.irc.services.UserService;

/**
 * Created by Adrian on 01.10.2016.
 */
public class Op extends Verb {
    private boolean setOpBysomeOne = false;

    public Op(MyBot myBot, Message msg) {
        super(myBot, msg);
    }

    public Op(MyBot myBot) {
        super(myBot, null);
        this.setOpBysomeOne = true;
    }

    @Override
    public void execute(String parameter) {
        if(!this.setOpBysomeOne) {
            if (msg.getUser().isOp()) {
                bot.op(msg.getChannel(), msg.getUser().getNickName());
            }
        } else {
            Session session = bot.getSession();
            String[] split = parameter.split(" ");
            UserService userService = new UserService(split[1], split[2], split[3], session);
            userService.opUser();
        }
    }
}
