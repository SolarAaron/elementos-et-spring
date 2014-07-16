/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package slr.epoo.web.cnt;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import slr.epoo.web.lib.SessionDAO;
import slr.epoo.web.mdl.Usuario;

/**
 *
 * @author Aaron
 */
@Controller
@RequestMapping("/usuarios")
public class PersonaCntr {
    private static final Logger logger = Logger.getLogger(PersonaCntr.class.getName());

    @RequestMapping(method=RequestMethod.GET, value="/todos", headers = {"Accept=text/html"})
    public @ResponseBody String resp(){
        return "probando metodo todos";
    }
    
    public static ArrayList<Usuario> getUsers(){
        ArrayList<Usuario> res;
        try(SessionDAO sd = new SessionDAO()){
            Session ss = SessionDAO.getSession();
            Query qq;
            Criteria cc;
            ss.getTransaction().begin();
            cc = ss.createCriteria(Usuario.class);
            res =  (ArrayList<Usuario>) cc.list();
        } catch(Exception e){
            res = new ArrayList<>();
            logger.log(Level.WARNING, "Excepcion en getUsers");
        }
        
        return res;
    }

    @RequestMapping(method = RequestMethod.GET, value="/usuario", headers = {"Accept=Application/JSON"})
    public @ResponseBody String userJSON() throws IOException{
        String res;
        JsonFactory fc = new JsonFactory(null);
        ObjectMapper ob = new ObjectMapper(fc);
        try(ByteArrayOutputStream out = new ByteArrayOutputStream()){
            JsonGenerator jg = ob.getJsonFactory().createJsonGenerator(out);
            ArrayList<Usuario> a = PersonaCntr.getUsers();
            jg.writeObject(Collections.singletonMap("object", a));
            res = out.toString();
        }
        logger.log(Level.INFO, res);
        return res;
    }

}
