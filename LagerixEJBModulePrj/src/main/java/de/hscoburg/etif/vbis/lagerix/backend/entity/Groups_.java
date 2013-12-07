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
@StaticMetamodel(Groups.class)
public class Groups_ {

    public static volatile SingularAttribute<Groups, Integer> id;
    public static volatile SingularAttribute<Groups, Group> groups;
    public static volatile SingularAttribute<Groups, User> user;

    public static volatile ListAttribute<Groups, Storage> storage;

}
