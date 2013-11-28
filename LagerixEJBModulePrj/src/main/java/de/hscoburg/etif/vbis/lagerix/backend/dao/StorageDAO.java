/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.hscoburg.etif.vbis.lagerix.backend.dao;

import de.hscoburg.etif.vbis.lagerix.backend.entity.Storage;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Entity;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author zuch1000
 */
@Stateless
public class StorageDAO extends BaseDAO<Storage>{
    
    
    public List<Storage> getAllStorages()
    {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Storage> cq = cb.createQuery(Storage.class);
        Root<Storage> pet = cq.from(Storage.class);
        cq.select(pet);
        TypedQuery<Storage> q = em.createQuery(cq);
        
        return q.getResultList();
    }
            
    
    
}
