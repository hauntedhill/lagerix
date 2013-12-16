package de.hscoburg.etif.vbis.lagerix.appclient.utils;

/**
 * Helper class to store an object and a specific toString string
 * @author mti578
 */
public class Item {

    private final Object obj;
    private final String description;

    /**
     * Creates a new Item
     * @param obj the Object
     * @param description the toString string
     */
    public Item(Object obj, String description) {
        this.obj = obj;
        this.description = description;
    }

    /**
     *
     * @return the item's object
     */
    public Object getObj() {
        return obj;
    }

    /**
     *
     * @return the same string as toString
     */
    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return description;
    }
}
