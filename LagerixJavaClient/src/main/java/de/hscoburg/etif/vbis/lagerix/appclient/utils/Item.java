/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.hscoburg.etif.vbis.lagerix.appclient.utils;

/**
 *
 * @author mti578
 */
 public class Item
 {
        private Object obj;
        private String description;

        public Item(Object obj, String description)
        {
            this.obj = obj;
            this.description = description;
        }

        public Object getObj()
        {
            return obj;
        }

        public String getDescription()
        {
            return description;
        }

        public String toString()
        {
            return description;
        }
}
