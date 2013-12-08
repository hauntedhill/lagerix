package de.hscoburg.etif.vbis.lagerix.backend.entity;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * Metamodel for the entity article.
 *
 * @author zuch1000
 */
@StaticMetamodel(Article.class)
public class Article_ {

    public static volatile SingularAttribute<Article, Integer> id;
    public static volatile SingularAttribute<Article, String> name;
    public static volatile SingularAttribute<Article, ArticleType> articleType;
    public static volatile SingularAttribute<Article, Yard> yard;
    public static volatile ListAttribute<Article, Movement> movements;
}
