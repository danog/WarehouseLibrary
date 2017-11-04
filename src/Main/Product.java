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
public class Product {
    private Double price;
    private String description = "";
    private Integer id;
    
    public Product(Integer id, String descrizione, Double prezzo) {
        this.id = id;
        this.description = descrizione;
        this.price = prezzo;
    }
    
    public Integer getID() {
        return id;
    }
    public String getDescription() {
        return description;
    }
    public Double getPrice() {
        return price;
    }
    
    public String toString() {
        return String.format("%d;%s;%f;", id, description, price);
    }
}
