package de.hscoburg.etif.vbis.lagerix.backend.entity;

import de.hscoburg.etif.vbis.lagerix.backend.entity.base.Movements;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import javax.xml.crypto.Data;

/**
 * Metamodel for the movement entity
 *
 * @author zuch1000
 */
@StaticMetamodel(Movement.class)
public class Movement_ {

    public static volatile SingularAttribute<Movement, Integer> id;
    public static volatile SingularAttribute<Movement, Movements> movement;
    public static volatile SingularAttribute<Movement, Data> time;
    public static volatile SingularAttribute<Movement, Article> article;

}
