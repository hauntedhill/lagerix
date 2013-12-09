package de.hscoburg.etif.vbis.lagerix.backend.entity;

import de.hscoburg.etif.vbis.lagerix.backend.entity.base.SecureEntity;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

/**
 * Entity for an Storage.
 *
 * @author zuch1000
 */
@Entity
public class Storage extends SecureEntity {

    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    @OneToMany(targetEntity = Yard.class, mappedBy = "storage")
    private List<Yard> yards;

    @OneToMany(targetEntity = ArticleType.class, mappedBy = "storage")
    private List<ArticleType> articleTypes;

    @ManyToMany(targetEntity = Groups.class)
    private List<Groups> group;

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
     * @return the yards
     */
    public List<Yard> getYards() {
        return yards;
    }

    /**
     * @param yards the yards to set
     */
    public void setYards(List<Yard> yards) {
        this.yards = yards;
    }

    /**
     * @return the articleTypes
     */
    public List<ArticleType> getArticleTypes() {
        return articleTypes;
    }

    /**
     * @param articleTypes the articleTypes to set
     */
    public void setArticleTypes(List<ArticleType> articleTypes) {
        this.articleTypes = articleTypes;
    }

    /**
     * @return the group
     */
    public List<Groups> getGroup() {
        return group;
    }

    /**
     * @param group the group to set
     */
    public void setGroup(List<Groups> group) {
        this.group = group;
    }

    public void addGroup(Groups g) {
        if (group == null) {
            group = new ArrayList<Groups>();
        }

        group.add(g);
    }

    public void addYard(Yard y) {
        if (yards == null) {
            yards = new ArrayList<Yard>();
        }
        y.setStorage(this);
        yards.add(y);
    }

    public void addArticleType(ArticleType y) {
        if (articleTypes == null) {
            articleTypes = new ArrayList<ArticleType>();
        }
        y.setStorage(this);
        articleTypes.add(y);
    }

    /**
     * Return the storage assosiated with the storage
     *
     * @return The storage object.
     */
    @Override
    public Storage getStorageForObject() {
        return this;
    }

}
