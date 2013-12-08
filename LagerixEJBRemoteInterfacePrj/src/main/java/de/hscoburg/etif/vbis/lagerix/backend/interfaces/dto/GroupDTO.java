package de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto;

import de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.base.BaseDTO;
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.base.GroupType;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO for the group data.
 *
 * @author zuch1000
 */
public class GroupDTO extends BaseDTO {

    private GroupType group;

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
    public GroupType getGroup() {
        return group;
    }

    /**
     * @param group the group to set
     */
    public void setGroup(GroupType group) {
        this.group = group;
    }
}
