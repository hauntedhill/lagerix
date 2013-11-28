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
 * @author Haunted
 */
public class StorageDTO extends BaseDTO{
 	private int id;
	private String name;
	   private List<YardDTO> yards;   

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
     * @return the yards
     */
    public List<YardDTO> getYards() {
        return yards;
    }

    /**
     * @param yards the yards to set
     */
    public void setYards(List<YardDTO> yards) {
        this.yards = yards;
    }
}
