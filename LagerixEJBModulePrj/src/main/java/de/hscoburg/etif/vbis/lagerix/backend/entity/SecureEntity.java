/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hscoburg.etif.vbis.lagerix.backend.entity;

import java.io.Serializable;

/**
 *
 * @author Haunted
 */
public abstract class SecureEntity implements Serializable {

    public abstract Storage getStorageForObject();

}
