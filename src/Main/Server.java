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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author Daniil Gentili
 */
public class Server {
    private final String dbPath;
    private final Warehouse warehouse;
    
    public Server(String dbPath) throws IOException {
        this.dbPath = dbPath;
        Scanner in = new Scanner(new FileReader(dbPath));
        String input = "";
        while (in.hasNextLine()) {
            input += in.nextLine()+"\n";
        }
        this.warehouse = new Warehouse(input);
    }
    
    public void checkout(String input) throws ClientException, IOException {
        checkout(new Cart(input));
    }
    synchronized public void checkout(Cart cart) throws ClientException, IOException {
        ProductCollection products;
        for (int x = 0; x < cart.getProductCount(); x++) {
            products = cart.getNthProductCollection(x);
            
            if (warehouse.getProductCollection(products).getCount() < products.getCount()) {
                throw new ClientException(String.format("Warehouse has only %d objects with ID %d", warehouse.getProductCollection(products).getCount(), products.getID()));
            }
        }
        for (int x = 0; x < cart.getProductCount(); x++) {
            products = cart.getNthProductCollection(x);
            warehouse.getProductCollection(products).decreaseCount(products.getCount());
        }
        FileWriter file = new FileWriter(dbPath);
        file.write(warehouse.getPayload(), 0, warehouse.getPayload().length());
        file.flush();
        file.close();
    }

    synchronized public Warehouse getWarehouse() {
        return warehouse;
    }
}
