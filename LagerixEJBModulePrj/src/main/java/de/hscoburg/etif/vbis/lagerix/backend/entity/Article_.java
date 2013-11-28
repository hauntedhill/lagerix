/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.hscoburg.etif.vbis.lagerix.backend.entity;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import javax.xml.crypto.Data;

/**
 *
 * @author zuch1000
 */
@StaticMetamodel( Article.class )
public class Article_ {
    public static volatile SingularAttribute<Article, Integer> id;
    public static volatile SingularAttribute<Article, String> name;
    public static volatile SingularAttribute<Article, ArticleType> articleType;
    public static volatile SingularAttribute<Article, Yard> yard;
    public static volatile ListAttribute<Article, Movement> movements;
}
