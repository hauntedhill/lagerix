/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.hscoburg.etif.vbis.lagerix.backend.entity;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 *
 * @author zuch1000
 */
@StaticMetamodel(ArticleType.class)
public class ArticleType_ {
     public static volatile SingularAttribute<ArticleType, Integer> id;
    public static volatile SingularAttribute<ArticleType, String> name;
    public static volatile SingularAttribute<ArticleType, String> description;
    public static volatile SingularAttribute<ArticleType, Integer> minimumStock;
    public static volatile ListAttribute<ArticleType, Movement> movements;
    public static volatile ListAttribute<ArticleType, Article> articles;
    
    
  
    

    
    
}
