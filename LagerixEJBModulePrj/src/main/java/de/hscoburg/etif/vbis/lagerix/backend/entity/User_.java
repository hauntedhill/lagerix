package de.hscoburg.etif.vbis.lagerix.backend.entity;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import javax.xml.crypto.Data;

/**
 * Metamodel for the user entity.
 *
 * @author zuch1000
 */
@StaticMetamodel(User.class)
public class User_ {

    public static volatile SingularAttribute<User, String> email;
    public static volatile SingularAttribute<User, String> firstName;
    public static volatile SingularAttribute<User, String> lastName;
    public static volatile SingularAttribute<User, String> password;
    public static volatile SingularAttribute<User, Data> registeredOn;

    public static volatile ListAttribute<User, Groups> groups;

}
