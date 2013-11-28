/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.hscoburg.etif.vbis.lagerix.backend.entity;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import javax.xml.crypto.Data;

/**
 *
 * @author zuch1000
 */
@StaticMetamodel( Movement.class )
public class Movement_ {
   public static volatile SingularAttribute<Movement, Integer> id;
    public static volatile SingularAttribute<Movement, Movements> movement;
    public static volatile SingularAttribute<Movement, Data> time;
    public static volatile SingularAttribute<Movement, Article> article;
   
}
