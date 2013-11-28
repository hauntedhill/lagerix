/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto;

import de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.base.BaseDTO;

/**
 *
 * @author zuch1000
 */
public class ArticleTypeDTO extends BaseDTO{
	private int id;
	private String name;
	private String description;
	private int minimumStock;
	private int storageID;			//where the article type is stored in

    @Override
    public String toString() {
        return "ArticleTypeDTO{" + "id=" + id + ", name=" + name + ", description=" + description + ", minimumStock=" + minimumStock + ", storageID=" + storageID + '}';
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
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
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the minimumStock
     */
    public int getMinimumStock() {
        return minimumStock;
    }

    /**
     * @param minimumStock the minimumStock to set
     */
    public void setMinimumStock(int minimumStock) {
        this.minimumStock = minimumStock;
    }

    /**
     * @return the storageID
     */
    public int getStorageID() {
        return storageID;
    }

    /**
     * @param storageID the storageID to set
     */
    public void setStorageID(int yardID) {
        this.storageID = yardID;
    }
}
