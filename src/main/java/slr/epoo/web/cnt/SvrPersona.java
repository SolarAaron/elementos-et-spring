/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package slr.epoo.web.cnt;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import slr.lib.Author;
import slr.lib.IServletExtension;
import slr.lib.RequestType;

@Author(name="Aaron", version="0.0.1")
public class SvrPersona implements IServletExtension{

    @Override
    public void procesar(HttpServletRequest request, HttpServletResponse response, RequestType req, PrintWriter out) throws ServletException, IOException {
        
    }
    
}
