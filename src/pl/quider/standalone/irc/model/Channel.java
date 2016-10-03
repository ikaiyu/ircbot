package pl.quider.standalone.irc.model;

import javax.persistence.*;

/**
 * Created by Adrian on 02.10.2016.
 */
@Entity
@Table(name="bot_channel")
public class Channel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name="channel")
    private String channelName;
    @Column(name="wordCount")
    private int wordCount;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public int getWordCount() {
        return wordCount;
    }

    public void setWordCount(int wordCount) {
        this.wordCount = wordCount;
    }
}
