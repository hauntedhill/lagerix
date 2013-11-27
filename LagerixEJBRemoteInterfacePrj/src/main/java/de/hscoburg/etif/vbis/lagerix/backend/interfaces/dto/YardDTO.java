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
public class YardDTO  extends BaseDTO{
	private int id;
	private int storageID;			//ID of the corresponding storage

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
     * @return the storageID
     */
    public int getStorageID() {
        return storageID;
    }

    /**
     * @param storageID the storageID to set
     */
    public void setStorageID(int storageID) {
        this.storageID = storageID;
    }
}
