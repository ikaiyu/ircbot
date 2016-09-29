package pl.quider.standalone.irc.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Adrian on 29.09.2016.
 */
@Entity
@Table(name = "bot_table_history")
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
}
