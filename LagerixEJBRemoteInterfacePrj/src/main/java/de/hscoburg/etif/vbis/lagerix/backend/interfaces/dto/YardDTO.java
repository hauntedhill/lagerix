package de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto;

import de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.base.BaseDTO;

/**
 * DTO for the data of an yard
 *
 * @author zuch1000
 */
public class YardDTO extends BaseDTO {

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

    @Override
    public String toString() {
        return "YardDTO{" + "id=" + id + ", storageID=" + storageID + '}';
    }
}
