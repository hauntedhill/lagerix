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
import de.hscoburg.etif.vbis.lagerix.backend.entity.Yard;
import de.hscoburg.etif.vbis.lagerix.backend.entity.Yard_;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

/**
 *
 * @author Haunted
 */
@Stateless
public class YardDAO extends BaseDAO<Yard>{
    
    
    public List<Yard> getYardsForArticleType(int articleTypeId)
    {
        
        CriteriaBuilder cb = em.getCriteriaBuilder();
        
        CriteriaQuery<Yard> movementCriteria = cb.createQuery( Yard.class );
        Root<Yard> movementRoot = movementCriteria.from( Yard.class );
        // Person.address is an embedded attribute
        Join<Yard,Article> articelRoot = movementRoot.join( Yard_.article );
        // Address.country is a ManyToOne
        Join<Article,ArticleType> articleTypeRoot = articelRoot.join( Article_.articleType );
        
        
        
        movementCriteria.select(movementRoot).where(cb.equal(articleTypeRoot.get(ArticleType_.id), articleTypeId));
        TypedQuery<Yard> q = em.createQuery(movementCriteria);
        
        return q.getResultList();
        
        
    }
    
}
