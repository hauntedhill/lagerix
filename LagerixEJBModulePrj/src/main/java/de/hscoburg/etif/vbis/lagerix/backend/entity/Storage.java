/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.hscoburg.etif.vbis.lagerix.backend.entity;

import java.util.List;
import javax.annotation.Generated;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author zuch1000
 */
@Entity
public class Storage {
    @Id
    @GeneratedValue
    private Integer id;
    
    private String name;
    
    
    @OneToMany(targetEntity = Yard.class,mappedBy = "storage")
    private List<Yard> yards;
    
    @OneToMany(targetEntity = ArticleType.class,mappedBy = "storage")
    private List<ArticleType> articleTypes;
    
  @ManyToMany(targetEntity = Groups.class)
    private List<Groups> group;

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
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the yards
     */
    public List<Yard> getYards() {
        return yards;
    }

    /**
     * @param yards the yards to set
     */
    public void setYards(List<Yard> yards) {
        this.yards = yards;
    }

    /**
     * @return the articleTypes
     */
    public List<ArticleType> getArticleTypes() {
        return articleTypes;
    }

    /**
     * @param articleTypes the articleTypes to set
     */
    public void setArticleTypes(List<ArticleType> articleTypes) {
        this.articleTypes = articleTypes;
    }

    /**
     * @return the group
     */
    public List<Groups> getGroup() {
        return group;
    }

    /**
     * @param group the group to set
     */
    public void setGroup(List<Groups> group) {
        this.group = group;
    }

   
    
}
