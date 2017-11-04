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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 *
 * @author root
 */
public class Client implements ActionListener {
    private BufferedWriter out;
    private BufferedReader in;
    
    private Warehouse warehouse;
    private Cart cart;
    
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
    
    public void commit() throws IOException, ServerException {

        RequestPayload request = new RequestPayload("POST", "/", cart.getPayload());
        request.shouldKeepAlive(true);
        request.write(out);

        ResponsePayload response = new ResponsePayload(in);

        if (response.getResponseCode() != 200) {
            throw new ServerException(response);
        }
        warehouse = new Warehouse(response.getPayload());
        cart = new Cart();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(e.paramString());
/*        if (e.getText().equals("Buy")) {
            try {
                this.addToCart(Integer.parseInt(e.getActionCommand()));
            } catch (ClientException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
        }
        */
    }
}
