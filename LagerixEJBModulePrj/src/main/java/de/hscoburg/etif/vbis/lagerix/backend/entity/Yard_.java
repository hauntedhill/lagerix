/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.hscoburg.etif.vbis.lagerix.backend.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 *
 * @author zuch1000
 */
@StaticMetamodel(Yard.class)
public class Yard_ {
    
    
         public static volatile SingularAttribute<Yard, Integer> id;
    public static volatile SingularAttribute<Yard, Integer> positionX;
    public static volatile SingularAttribute<Yard, Integer> positionY;
    public static volatile SingularAttribute<Yard, Integer> positionZ;
    public static volatile SingularAttribute<Yard, Storage> storage;
    public static volatile SingularAttribute<Yard, Article> article;

    
    
    
}
