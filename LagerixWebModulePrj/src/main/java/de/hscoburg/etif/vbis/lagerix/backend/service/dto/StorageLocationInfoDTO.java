/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hscoburg.etif.vbis.lagerix.backend.service.dto;

import de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.base.BaseDTO;

/**
 *
 * @author Felix Lisczyk
 */
public class StorageLocationInfoDTO extends BaseDTO {
    
    private Integer yardId;
    private String yardStatus;
    
    public StorageLocationInfoDTO(Integer yardId, String yardStatus) {
        this.yardId = yardId;
        this.yardStatus = yardStatus;
    }
    
    public Integer getYardId() {
        return yardId;
    }
}
