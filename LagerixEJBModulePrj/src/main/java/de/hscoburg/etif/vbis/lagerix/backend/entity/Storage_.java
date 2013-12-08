package de.hscoburg.etif.vbis.lagerix.backend.entity;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * Metamodel for the storage entitie
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
