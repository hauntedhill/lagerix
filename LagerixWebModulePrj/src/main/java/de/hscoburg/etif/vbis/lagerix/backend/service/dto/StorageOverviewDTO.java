/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hscoburg.etif.vbis.lagerix.backend.service.dto;

import java.util.List;

/**
 *
 * @author Felix
 */
public class StorageOverviewDTO {
    
    private List<YardInfoDTO> storageInfo;
    
    /**
     * @return the storageInfo
     */
    public List<YardInfoDTO> getStorageInfo() {
        return storageInfo;
    }

    /**
     * @param storageInfo the storageInfo to set
     */
    public void setStorageInfo(List<YardInfoDTO> storageInfo) {
        this.storageInfo = storageInfo;
    }
}