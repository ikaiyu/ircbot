package pl.quider.standalone.irc.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Adrian on 27.09.2016.
 */
@Entity
@Table(name = "bot_user")
public class User implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name="login")
    private String nick;
    @Column(name = "isOp")
    private boolean isOp;
    @Column(name="mask")
    private String mask;
    @Column
    private String login;
    @Column(name="lastSeen")
    private Date lastSeen;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String login) {
        this.nick = login;
    }

    public boolean isOp() {
        return isOp;
    }

    public void setOp(boolean op) {
        isOp = op;
    }

    public String getMask() {
        return mask;
    }

    public void setMask(String mask) {
        this.mask = mask;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Date getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(Date lastSeen) {
        this.lastSeen = lastSeen;
    }
}
