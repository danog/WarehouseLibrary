/* 
 * Copyright (C) 2017 Daniil Gentili
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package Main;

import java.awt.Component;
import java.awt.Dimension;
import java.util.Hashtable;
import javax.swing.BorderFactory;
import java.awt.Cursor;
import javax.swing.JButton;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

/**
 *
 * @author root
 */
public abstract class Container 
{ 
    /*
        Properties
    */
    protected Hashtable<Integer, ProductCollection> products = new Hashtable<>(); // ID prodotto, collezione prodotti
    /*
        Constructors
    */
    public Container(String response) {
        String[] split;
        for (String line: response.split("\n")) {
            split = line.split(";");
            products.put(
                    Integer.parseInt(split[0]),
                    new ProductCollection(
                        new Product(
                                Integer.parseInt(split[0]), 
                                split[1], 
                                Double.parseDouble(split[2])
                        ), 
                        Integer.parseInt(split[3])
                    )
            );
        }
    }
    public Container() {
        
    }
    
    
    /**
     * Get the string payload of the container
     * @return The string payload
     */
    public String getPayload() {
        String res = "";
        for (ProductCollection prodotti: products.values()) {
            res += prodotti;
            res += "\n";
        }
        return res;
    }
    
    /**
     * Add product
     * @param product
     */
    public void addProduct(Product product) {
        if (!products.containsKey(product.getID())) {
            products.put(product.getID(), new ProductCollection(product, 0));
        }
        this.getProductCollection(product).increaseCount();
    }

    /**
     * Remove product
     * @param product
     * @throws ClientException
     */
    abstract public void removeProduct(Product product) throws ClientException;
 
   /*
        Product getters/setters/checkers
     */
    public Boolean hasProduct(Product product) {
        return products.containsKey(product.getID());
    }
    public Boolean hasProduct(Integer id) {
        return products.containsKey(id);
    }
    public Product getProduct(Integer id) {
        return products.get(id).getProduct();
    }
    public Product getProduct(Product product) {
        return products.get(product.getID()).getProduct();
    }
    public Integer getProductCount(Integer id) {
        return products.get(id).getCount();
    }
    public Integer getProductCount(Product product) {
        return products.get(product.getID()).getCount();
    }
    public Integer getProductCount() {
        return products.size();
    }
    public ProductCollection getNthProductCollection(Integer id) {
        return (ProductCollection) products.values().toArray()[id];
    }
    public ProductCollection getProductCollection(Integer id) {
        return products.get(id);
    }
    public ProductCollection getProductCollection(Product product) {
        return products.get(product.getID());
    }

}
