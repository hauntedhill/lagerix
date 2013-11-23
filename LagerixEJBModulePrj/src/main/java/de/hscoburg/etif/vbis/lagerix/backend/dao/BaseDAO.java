/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.hscoburg.etif.vbis.lagerix.backend.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author zuch1000
 */
public class BaseDAO<T> {
    
    @PersistenceContext
    protected EntityManager em;
    
    public void save(T obj)
    {
        em.persist(obj);
    }
    
    public T findById(Class<T> obj,Integer id)
    {
        return em.find(obj, id);
    }
    
    public void remove(T obj)
    {
        em.remove(obj);
    }
    
    public void merge(T obj)
    {
        em.merge(obj);
    }
    
}
