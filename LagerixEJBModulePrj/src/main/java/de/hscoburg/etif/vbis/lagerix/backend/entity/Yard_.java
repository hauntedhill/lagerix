package de.hscoburg.etif.vbis.lagerix.backend.entity;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * Metamodel for the yard entitie.
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
