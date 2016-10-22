package pl.quider.standalone.irc.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Adrian on 15.10.2016.
 */
@Entity
@Table(name = "bot_rss_entries")
public class RssEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private Date added;
    @Column
    private String title;
    @Column
    private String uri;
    @Column
    private  String description;
    @ManyToOne()
    @JoinColumn(name="rss_id")
    private Rss rss;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getAdded() {
        return added;
    }

    public void setAdded(Date added) {
        this.added = added;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Rss getRss() {
        return rss;
    }

    public void setRss(Rss rss) {
        this.rss = rss;
    }
}
