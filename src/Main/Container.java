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

import java.util.Hashtable;

/**
 *
 * @author Daniil Gentili
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
        rebuild(response);
    }
    public Container() {
        rebuild();
    }
    
    public void rebuild() {
        this.products.clear();
    }
    public void rebuild(String response) {
        this.products.clear();
        if (response.isEmpty()) {
            return;
        }
        String[] split;
        for (String line: response.split("\n")) {
            if (line.isEmpty()) {
                continue;
            }
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
    public Boolean hasProduct(ProductCollection collection) {
        return products.containsKey(collection.getID());
    }
    public Boolean hasProduct(Product product) {
        return products.containsKey(product.getID());
    }
    public Boolean hasProduct(Integer id) {
        return products.containsKey(id);
    }
    public Product getProduct(ProductCollection collection) {
        return products.get(collection.getID()).getProduct();
    }
    public Product getProduct(Product product) {
        return products.get(product.getID()).getProduct();
    }
    public Product getProduct(Integer id) {
        return products.get(id).getProduct();
    }
    public Integer getProductCount(ProductCollection collection) {
        return products.get(collection.getID()).getCount();
    }
    public Integer getProductCount(Product product) {
        return products.get(product.getID()).getCount();
    }
    public Integer getProductCount(Integer id) {
        return products.get(id).getCount();
    }
    public Integer getProductCount() {
        return products.size();
    }
    public ProductCollection getNthProductCollection(Integer id) {
        return (ProductCollection) products.values().toArray()[id];
    }
    public ProductCollection getProductCollection(ProductCollection collection) {
        return products.get(collection.getID());
    }
    public ProductCollection getProductCollection(Product product) {
        return products.get(product.getID());
    }
    public ProductCollection getProductCollection(Integer id) {
        return products.get(id);
    }
    public Double getPriceTotal() {
        Double res = 0d;
        for (ProductCollection p: products.values()) {
            res += p.getTotalPrice();
        }
        return res;
    }
}
