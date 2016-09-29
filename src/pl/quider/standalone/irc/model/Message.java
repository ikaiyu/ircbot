package pl.quider.standalone.irc.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Adrian on 29.09.2016.
 */
@Entity
@Table(name = "bot_messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private Date date;
    @Column
    private String message;
    @Column
    private  String channel;
    @ManyToOne()
    @JoinColumn(name="user_id")
    private User user;

    public Message(String channel, String sender, User user, String message) {
        this.channel = channel;
        this.message = message;
        this.user = user;
        this.date = new Date();
    }

    public Message(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
