package pl.quider.standalone.irc.verbs;

import org.hibernate.Session;
import org.hibernate.Transaction;
import pl.quider.standalone.irc.MyBot;
import pl.quider.standalone.irc.services.UserService;

/**
 * Created by Adrian on 01.10.2016.
 */
public class Op extends Verb {
    public Op(MyBot myBot) {
        super(myBot);
    }

    @Override
    public void execute(String parameter) {
        Session session = bot.getSession();
        Transaction transaction = session.getTransaction();
        if(!transaction.isActive()){
            transaction = session.beginTransaction();
        }
        String[] split = parameter.split(" ");
        UserService userService = new UserService(split[1], split[2], split[3], session);
        userService.getUser().setOp(true);
        transaction.commit();
    }
}
