package de.hscoburg.etif.vbis.lagerix.backend.entity;

import de.hscoburg.etif.vbis.lagerix.backend.entity.base.SecureEntity;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 * Entity for an article
 *
 * @author zuch1000
 */
@Entity
public class Article extends SecureEntity {

    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    @ManyToOne(targetEntity = ArticleType.class)
    private ArticleType articleType;

    @OneToOne(targetEntity = Yard.class)
    private Yard yard;

    @OneToMany(targetEntity = Movement.class, mappedBy = "article")
    private List<Movement> movements;

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
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the articleType
     */
    public ArticleType getArticleType() {
        return articleType;
    }

    /**
     * @param articleType the articleType to set
     */
    public void setArticleType(ArticleType articleType) {
        this.articleType = articleType;
    }

    /**
     * @return the yard
     */
    public Yard getYard() {
        return yard;
    }

    /**
     * @param yard the yard to set
     */
    public void setYard(Yard yard) {
        this.yard = yard;
    }

    /**
     * @return the movements
     */
    public List<Movement> getMovements() {
        return movements;
    }

    /**
     * @param movements the movements to set
     */
    public void setMovements(List<Movement> movements) {
        this.movements = movements;
    }

    /**
     * Return the storage assosiated with the article
     *
     * @return The storage entitiy
     */
    @Override
    public Storage getStorageForObject() {
        return this.getArticleType().getStorage();
    }

}
