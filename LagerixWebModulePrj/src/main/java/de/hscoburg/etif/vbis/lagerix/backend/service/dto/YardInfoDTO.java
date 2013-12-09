/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hscoburg.etif.vbis.lagerix.backend.service.dto;

/**
 *
 * @author Felix
 */
public class YardInfoDTO {
    
    private Integer yardId;
    private String yardStatus;

    /**
     * @return the yardId
     */
    public Integer getYardId() {
        return yardId;
    }

    /**
     * @param yardId the yardId to set
     */
    public void setYardId(Integer yardId) {
        this.yardId = yardId;
    }

    /**
     * @return the yardStatus
     */
    public String getYardStatus() {
        return yardStatus;
    }

    /**
     * @param yardStatus the yardStatus to set
     */
    public void setYardStatus(String yardStatus) {
        this.yardStatus = yardStatus;
    }
    
}