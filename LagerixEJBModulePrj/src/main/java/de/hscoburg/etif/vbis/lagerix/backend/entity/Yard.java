package de.hscoburg.etif.vbis.lagerix.backend.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 * Entity for an yard
 *
 * @author zuch1000
 */
@Entity
public class Yard implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;

    private Integer positionX;

    private Integer positionY;

    private Integer positionZ;

    @ManyToOne(targetEntity = Storage.class)
    private Storage storage;

    @OneToOne(targetEntity = Article.class, mappedBy = "yard")
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
     * @return the positionX
     */
    public Integer getPositionX() {
        return positionX;
    }

    /**
     * @param positionX the positionX to set
     */
    public void setPositionX(Integer positionX) {
        this.positionX = positionX;
    }

    /**
     * @return the positionY
     */
    public Integer getPositionY() {
        return positionY;
    }

    /**
     * @param positionY the positionY to set
     */
    public void setPositionY(Integer positionY) {
        this.positionY = positionY;
    }

    /**
     * @return the positionZ
     */
    public Integer getPositionZ() {
        return positionZ;
    }

    /**
     * @param positionZ the positionZ to set
     */
    public void setPositionZ(Integer positionZ) {
        this.positionZ = positionZ;
    }

    /**
     * @return the storage
     */
    public Storage getStorage() {
        return storage;
    }

    /**
     * @param storage the storage to set
     */
    public void setStorage(Storage storage) {
        this.storage = storage;
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
