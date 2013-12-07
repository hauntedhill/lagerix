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
@StaticMetamodel(Storage.class)
public class Storage_ {

    public static volatile SingularAttribute<Storage, Integer> id;
    public static volatile SingularAttribute<Storage, String> name;

    public static volatile ListAttribute<Storage, Yard> yards;
    public static volatile ListAttribute<Storage, ArticleType> articleTypes;
    public static volatile ListAttribute<Storage, Groups> group;

}
