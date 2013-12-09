/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hscoburg.etif.vbis.lagerix.backend.entity.base;

import de.hscoburg.etif.vbis.lagerix.backend.entity.Storage;
import java.io.Serializable;

/**
 * Abstract class for all Entities with security ceck over storage
 *
 * @author zuch1000
 */
public abstract class SecureEntity implements Serializable {

    /**
     * Returns the storage associated with this entity.
     *
     * @return The storage for this entity
     */
    public abstract Storage getStorageForObject();

}
