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
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Entity;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

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
    public List<ArticleType> getArticleTypesBy(String articleTypeName, String articleTypeDescription, String articleTypeMinimumStock)
    {
        
        CriteriaBuilder cb = em.getCriteriaBuilder();
        
        CriteriaQuery<ArticleType> movementCriteria = cb.createQuery( ArticleType.class );
        Root<ArticleType> movementRoot = movementCriteria.from( ArticleType.class );
       
        List<Predicate> wheres = new ArrayList<Predicate>();
        
        if(articleTypeName!=null && !"".equals(articleTypeName.trim()))
        {
            wheres.add(cb.like(movementRoot.get(ArticleType_.name), "%"+articleTypeName+"%"));
        }
        if(articleTypeDescription!=null && !"".equals(articleTypeDescription.trim()))
        {
            wheres.add(cb.like(movementRoot.get(ArticleType_.description), "%"+articleTypeDescription+"%"));
        }
        if(articleTypeMinimumStock!=null && !"".equals(articleTypeMinimumStock.trim()))
        {
            wheres.add(cb.equal(movementRoot.get(ArticleType_.minimumStock), new Integer(articleTypeMinimumStock)));
        }
        
        
        
        movementCriteria.select(movementRoot).where(wheres.toArray(new Predicate[0]));
        
        
        
        
        
        TypedQuery<ArticleType> q = em.createQuery(movementCriteria);
        
        return q.getResultList();
    }
   
    
    public List<ArticleType> getAllArticleTypes()
    {
       CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<ArticleType> cq = cb.createQuery(ArticleType.class);
        Root<ArticleType> pet = cq.from(ArticleType.class);
        cq.select(pet);
        TypedQuery<ArticleType> q = em.createQuery(cq);
        
        return q.getResultList(); 
    }
    
 public long getArticleTypeStock(ArticleType articleType)
    {
        
        
        
        
        
        CriteriaBuilder cb = em.getCriteriaBuilder();
        
        CriteriaQuery<Long> articleCriteria = cb.createQuery( Long.class );
        Root<ArticleType> articleRoot = articleCriteria.from( ArticleType.class );
        
        Join<ArticleType,Article> articelRoot = articleRoot.join( ArticleType_.articles );
       
        articleCriteria.select(cb.count(articleRoot)).where(cb.equal(articleRoot.get(ArticleType_.id),articleType.getId())).groupBy(articleRoot.get(ArticleType_.id));

        TypedQuery<Long> q = em.createQuery(articleCriteria);
        
        
        
        
        return q.getResultList().size()==1?q.getResultList().get(0).longValue():0;
        
       
    }
    
}
