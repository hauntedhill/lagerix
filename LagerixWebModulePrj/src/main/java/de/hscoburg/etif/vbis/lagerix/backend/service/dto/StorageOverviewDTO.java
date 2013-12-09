/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hscoburg.etif.vbis.lagerix.backend.service.dto;

import de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.base.BaseDTO;
import java.util.List;

/**
 *
 * @author Felix Lisczyk
 */
public class StorageOverviewDTO extends BaseDTO {
    
    private List<StorageLocationInfoDTO> storageInfo;
    
    public StorageOverviewDTO(List<StorageLocationInfoDTO> storageInfo) {
        this.storageInfo = storageInfo;
    }
}
