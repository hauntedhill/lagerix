/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.hscoburg.etif.vbis.lagerix.backend.dao;

import de.hscoburg.etif.vbis.lagerix.backend.entity.Article;
import de.hscoburg.etif.vbis.lagerix.backend.entity.ArticleType;
import de.hscoburg.etif.vbis.lagerix.backend.entity.ArticleType_;
import de.hscoburg.etif.vbis.lagerix.backend.entity.Article_;
import de.hscoburg.etif.vbis.lagerix.backend.entity.Movement;
import de.hscoburg.etif.vbis.lagerix.backend.entity.Movement_;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

/**
 *
 * @author zuch1000
 */
@Stateless
public class ArticleDAO extends BaseDAO<Article> {
    
    
    public List<Article> getArticleUnderStock()
    {
        
        
        CriteriaBuilder cb = em.getCriteriaBuilder();
        
        CriteriaQuery<Article> articleCriteria = cb.createQuery( Article.class );
        Root<Article> articleRoot = articleCriteria.from( Article.class );
        // Person.address is an embedded attribute
        Join<Article,ArticleType> articelRoot = articleRoot.join( Article_.articleType );
        // Address.country is a ManyToOne
        
        
        
        
        //articleCriteria.select(articleRoot).where(cb.lessThan(articleTypeRoot.get(ArticleType_.id), articleTypeId));
        TypedQuery<Article> q = em.createQuery(articleCriteria);
        
        return q.getResultList();
        
       
    }
    
    
}
