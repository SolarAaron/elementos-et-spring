/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package slr.epoo.web.cnt;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Aaron
 */
@Controller
@RequestMapping("/usuarios")
public class PersonaCntr {
    @RequestMapping(method=RequestMethod.GET, value="/todos", headers = {"Accept=text/html"})
    public @ResponseBody String resp(){
        return "probando metodo todos";
    }
    
    @RequestMapping(method = RequestMethod.GET, value="/usuario", headers = {"Accept=Application/JSON"})
    public @ResponseBody String userJSON() throws IOException{
        String res;
        ObjectMapper mp = new ObjectMapper();
        try(ByteArrayOutputStream out = new ByteArrayOutputStream()){
            JsonGenerator jg = mp.getJsonFactory().createJsonGenerator(out, JsonEncoding.UTF8);
            jg.writeObject(new slr.epoo.web.mdl.Usuario());
            res = out.toString();
        }
        return res;
    }
    
}
