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


/**
 *
 * @author Daniil Gentili
 */
public class ProductCollection {
    private final Product product;
    private Integer count;
    public ProductCollection(Product product, Integer count) {
        this.product = product;
        this.count = count;
    }
    public Integer getCount() {
        return count;
    }
    public Integer increaseCount() {
        return count++;
    }
    public Integer decreaseCount() {
        return count--;
    }
    public Product getProduct() {
        return product;
    }
    public Integer getID() {
        return product.getID();
    }
    public String getDescription() {
        return product.getDescription();
    }
    public Double getPrice() {
        return product.getPrice();
    }

    public String toString() {
        return String.format("%d;%s;%f;%d", product.getID(), product.getDescription(), product.getPrice(), count);
    }
}
