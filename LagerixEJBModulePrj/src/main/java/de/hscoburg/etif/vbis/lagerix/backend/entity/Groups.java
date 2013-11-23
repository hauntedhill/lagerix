/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.hscoburg.etif.vbis.lagerix.backend.entity;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author zuch1000
 */
@Entity(name="USERS_GROUPS")
public class Groups {
    @Id
    @GeneratedValue
    private Integer id;
    
    @Column(name = "groupname")
    @Enumerated(EnumType.STRING)
    private Group groups;
    
    
    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "email")
    private User user;

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the group
     */
    public Group getGroups() {
        return groups;
    }

    /**
     * @param group the group to set
     */
    public void setGroups(Group groups) {
        this.groups = groups;
    }

    /**
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }
}
