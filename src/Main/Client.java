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

import Payloads.RequestPayload;
import Payloads.ResponsePayload;
import Payloads.ServerException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 *
 * @author Daniil Gentili
 */
public class Client {
    private final BufferedWriter out;
    private final BufferedReader in;
    
    private final Warehouse warehouse;
    private final Cart cart;
    private Double allTimeTotal = 0d;
    
    public Client(String host, Integer port) throws IOException {
        
        Socket socket = new Socket(host, port);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));    
        
        RequestPayload request = new RequestPayload("GET", "/");
        request.shouldKeepAlive(true);
        request.write(out);
        warehouse = new Warehouse(new ResponsePayload(in).getPayload());
        cart = new Cart();
    }
    
    public Warehouse getWarehouse() {
        return warehouse;
    }
    public Cart getCart() {
        return cart;
    }

    public void addToCart(Integer id) throws ClientException {
        Product product = warehouse.getProduct(id);
        warehouse.removeProduct(product);
        cart.addProduct(product);
    }
    public void removeFromCart(Integer id) throws ClientException {
        Product product = warehouse.getProduct(id);
        cart.removeProduct(product);
        warehouse.addProduct(product);
    }
    
    public void checkout() throws IOException, ServerException {
        RequestPayload request = new RequestPayload("POST", "/", cart.getPayload());
        request.shouldKeepAlive(true);
        request.write(out);

        ResponsePayload response = new ResponsePayload(in);

        this.warehouse.rebuild(response.getPayload());
        this.cart.rebuild();
        
        if (response.getResponseCode() != 200) {
            throw new ServerException(response);
        }
        this.allTimeTotal += this.cart.getPriceTotal();
    }
    public void close() throws IOException {
        RequestPayload request = new RequestPayload("POST", "/analytics", allTimeTotal.toString());
        request.shouldKeepAlive(false); // Just to close the connection
        request.write(out);        
        this.in.close();
        this.out.close();
    }
}
