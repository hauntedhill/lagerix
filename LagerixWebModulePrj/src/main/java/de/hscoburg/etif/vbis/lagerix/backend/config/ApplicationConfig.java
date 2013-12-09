/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.hscoburg.etif.vbis.lagerix.backend.config;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author zuch1000
 */
@javax.ws.rs.ApplicationPath("services")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<Class<?>>();
        addRestResourceClasses(resources);
        System.out.println("execute getClass");
        System.out.println(resources.toString());
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(de.hscoburg.etif.vbis.lagerix.backend.service.AndroidBookingService.class);
        resources.add(de.hscoburg.etif.vbis.lagerix.backend.service.AndroidSearchService.class);
        resources.add(de.hscoburg.etif.vbis.lagerix.backend.service.UserManagementService.class);
        resources.add(de.hscoburg.etif.vbis.lagerix.backend.service.WebAppService.class);
    }
    
}
