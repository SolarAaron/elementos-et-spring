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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import slr.epoo.web.mdl.Usuario;

/**
 *
 * @author Aaron
 */
@Controller
@RequestMapping("/")
public class UsuarioCntr {
    private static final Logger logger = Logger.getLogger(UsuarioCntr.class.getName());

    public static ArrayList<Usuario> getUsers(){
        ArrayList<Usuario> res = new ArrayList<>();
        try{
            res = new UsuarioDaoV2().list();
        } catch(Exception x){
            logger.log(Level.WARNING, "Algo anda mal: {0}", x.getMessage());
        }
        return res;
    }

    public static String putUser(Usuario u){
        String status = "ok";
        try{
            UsuarioDaoV2 disp = new UsuarioDaoV2();
            ArrayList<Usuario> ulist = disp.list();
            u.setId(ulist.get(ulist.size() - 1).getId() + 1);
            disp.save(u);
        } catch(Exception ee){
            logger.log(Level.WARNING, "Algo anda mal: {0}", ee.getMessage());
            status = "algo anda mal: " + ee.getMessage();
        }
        return status;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/usuario/{nombre}/{salario}", headers = {"Accept=Application/JSON"})
    public @ResponseBody String insertUser(@PathVariable String nombre, @PathVariable Float salario) throws IOException{
        String res;
        JsonFactory fc = new JsonFactory(null);
        ObjectMapper ob = new ObjectMapper(fc);
        try(ByteArrayOutputStream out = new ByteArrayOutputStream()){
            JsonGenerator jg = ob.getJsonFactory().createJsonGenerator(out);
            jg.writeObject(Collections.singletonMap("object", UsuarioCntr.putUser(new Usuario(0, nombre, salario))));
            res = out.toString();
        }
        logger.log(Level.INFO, res);
        return res;
    }

    @RequestMapping(method = RequestMethod.GET, value="/usuarios", headers = {"Accept=Application/JSON"})
    public @ResponseBody String listUsers() throws IOException{
        String res;
        JsonFactory fc = new JsonFactory(null);
        ObjectMapper ob = new ObjectMapper(fc);
        try(ByteArrayOutputStream out = new ByteArrayOutputStream()){
            JsonGenerator jg = ob.getJsonFactory().createJsonGenerator(out);
            jg.writeObject(Collections.singletonMap("object", UsuarioCntr.getUsers()));
            res = out.toString();
        }
        logger.log(Level.INFO, res);
        return res;
    }

}
