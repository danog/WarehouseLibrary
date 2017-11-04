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
 * @author root
 */
public class Warehouse extends Container {
    public Warehouse(String input) {
        super(input);
    }
    public Warehouse() {
        
    }
    @Override
    public void removeProduct(Product prodotto) throws ClientException {
        if (this.getProductCollection(prodotto).getCount() < 1) {
            throw new ClientException("Le scorte del prodotto che stai cercando di comprare sono esaurite.");
        }
        this.getProductCollection(prodotto).decreaseCount();
    }
}
