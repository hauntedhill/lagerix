/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto;

import de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.base.BaseDTO;
import java.util.List;

/**
 *
 * @author zuch1000
 */
public class Storage  extends BaseDTO{
	private int id;
	private String name;
	   private List<StorageLocation> storageLocations;

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
     * @return the storageLocations
     */
    public List<StorageLocation> getStorageLocations() {
        return storageLocations;
    }

    /**
     * @param storageLocations the storageLocations to set
     */
    public void setStorageLocations(List<StorageLocation> storageLocations) {
        this.storageLocations = storageLocations;
    }
}
