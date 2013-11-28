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
import de.hscoburg.etif.vbis.lagerix.backend.entity.Storage;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Entity;
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
public class ArticleTypeDAO extends BaseDAO<ArticleType>{
    
    
    public List<Movement> getMovementsForArticleTypeId(int articleTypeId)
    {
        
        CriteriaBuilder cb = em.getCriteriaBuilder();
        
        CriteriaQuery<Movement> movementCriteria = cb.createQuery( Movement.class );
        Root<Movement> movementRoot = movementCriteria.from( Movement.class );
        // Person.address is an embedded attribute
        Join<Movement,Article> articelRoot = movementRoot.join( Movement_.article );
        // Address.country is a ManyToOne
        Join<Article,ArticleType> articleTypeRoot = articelRoot.join( Article_.articleType );
        
        
        
        movementCriteria.select(movementRoot).where(cb.equal(articleTypeRoot.get(ArticleType_.id), articleTypeId));
        TypedQuery<Movement> q = em.createQuery(movementCriteria);
        
        return q.getResultList();
    }
    
    
}
