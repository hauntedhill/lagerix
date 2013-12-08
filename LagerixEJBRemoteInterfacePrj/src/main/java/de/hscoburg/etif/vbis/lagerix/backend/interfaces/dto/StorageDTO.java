package de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto;

import de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.base.BaseDTO;
import java.util.List;

/**
 * DTA for the data of an storage
 *
 * @author zuch1000
 */
public class StorageDTO extends BaseDTO {

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

    @Override
    public String toString() {
        return "StorageDTO{" + "id=" + id + ", name=" + name + ", yards=" + yards + '}';
    }
}
