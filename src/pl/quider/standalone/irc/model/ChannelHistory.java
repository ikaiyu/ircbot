package pl.quider.standalone.irc.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Adrian on 29.09.2016.
 */
@Entity
@Table(name = "bot_channel_history")
public class ChannelHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private Date date;
    @Column
    private String topic;
    @Column
    private  String channel;

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

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }
}
