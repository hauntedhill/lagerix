/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto;

import de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.base.BaseDTO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Haunted
 */
public class GroupDTO extends BaseDTO{
    
    private String group;
    
    private List<Integer> storageId;

    public GroupDTO() {
        storageId = new ArrayList<Integer>();
    }

    
    
    
    
    /**
     * @return the lagerId
     */
    public List<Integer> getStorageId() {
        return storageId;
    }

    /**
     * @param lagerId the lagerId to set
     */
    public void setStorageId(List<Integer> lagerId) {
        this.storageId = lagerId;
    }

    /**
     * @return the group
     */
    public String getGroup() {
        return group;
    }

    /**
     * @param group the group to set
     */
    public void setGroup(String group) {
        this.group = group;
    }
}
