package de.hscoburg.etif.vbis.lagerix.backend.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entity for an movement
 *
 * @author zuch1000
 */
@Entity
public class Movement implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;

    @Enumerated(EnumType.STRING)
    private Movements movement;

    @Temporal(TemporalType.TIMESTAMP)
    private Date time;

    @ManyToOne(targetEntity = Article.class)
    private Article article;

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the movement
     */
    public Movements getMovement() {
        return movement;
    }

    /**
     * @param movement the movement to set
     */
    public void setMovement(Movements movement) {
        this.movement = movement;
    }

    /**
     * @return the time
     */
    public Date getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(Date time) {
        this.time = time;
    }

    /**
     * @return the article
     */
    public Article getArticle() {
        return article;
    }

    /**
     * @param article the article to set
     */
    public void setArticle(Article article) {
        this.article = article;
    }
}
