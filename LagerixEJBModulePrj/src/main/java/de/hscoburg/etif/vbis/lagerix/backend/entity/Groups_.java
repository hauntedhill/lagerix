package de.hscoburg.etif.vbis.lagerix.backend.entity;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * Metamodel for the entity Groups.
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
