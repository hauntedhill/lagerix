package de.hscoburg.etif.vbis.lagerix.backend.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * Entity for an ArticleType
 *
 * @author zuch1000
 */
@Entity
public class ArticleType implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    private String description;

    private Integer minimumStock;

    @OneToMany(targetEntity = Article.class, mappedBy = "articleType")
    private List<Article> articles;

    @ManyToOne(targetEntity = Storage.class)
    private Storage storage;

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
     * @return the articles
     */
    public List<Article> getArticles() {
        return articles;
    }

    /**
     * @param articles the articles to set
     */
    public void setArticles(List<Article> articles) {
        this.articles = articles;
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
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the minimumStock
     */
    public Integer getMinimumStock() {
        return minimumStock;
    }

    /**
     * @param minimumStock the minimumStock to set
     */
    public void setMinimumStock(Integer minimumStock) {
        this.minimumStock = minimumStock;
    }

    public void addArticle(Article a) {
        if (articles == null) {
            articles = new ArrayList<Article>();
        }
        a.setArticleType(this);
        articles.add(a);
    }
}
