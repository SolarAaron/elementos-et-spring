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
import slr.epoo.web.mdl.Empleado;

/**
 *
 * @author Aaron
 */
@Controller
@RequestMapping("/")
public class EmpleadoCntr {
    private static final Logger logger = Logger.getLogger(EmpleadoCntr.class.getName());

    public static ArrayList<Empleado> getUsers(){
        ArrayList<Empleado> res = new ArrayList<>();
        try{
            res = new EmpleadoDaoV2().list();
        } catch(Exception x){
            logger.log(Level.WARNING, "Algo anda mal: {0}", x.getMessage());
        }
        return res;
    }

    public static String putUser(Empleado u){
        String status = "ok";
        try{
            EmpleadoDaoV2 disp = new EmpleadoDaoV2();
            ArrayList<Empleado> ulist = disp.list();
            if(!ulist.isEmpty()){
                for(Empleado usr: ulist){
                    if(usr.getNombre().equals(u.getNombre())){
                        u.setIdE(usr.getIdE());
                    }
                }
                if(u.getIdE() == null){
                    u.setIdE(ulist.get(ulist.size() - 1).getIdE() + 1);
                    disp.save(u);
                } else {
                    disp.update(u);
                }
            } else {
                u.setIdE(1);
                disp.save(u);
            }
        } catch(Exception ee){
            logger.log(Level.WARNING, "Algo anda mal: {0}{1}", new Object[]{ee.getMessage(), ee.getMessage()});
            status = "algo anda mal: " + ee.getMessage();
        }
        return status;
    }

    public static Empleado searchUser(Integer id){
        Empleado res = null;
        try{
            res = new EmpleadoDaoV2().search(id, true);
        } catch(Exception e){
            logger.log(Level.WARNING, "Algo anda mal...");
        }
        return res;
    }

    public static String deleteUser(Integer id){
        String status = "ok";
        try{
            EmpleadoDaoV2 disp = new EmpleadoDaoV2();
            disp.delete(id);
        } catch(Exception ee){
            logger.log(Level.WARNING, "Algo anda mal: {0}{1}", new Object[]{ee.getMessage(), ee.getMessage()});
            status = "algo anda mal: " + ee.getMessage();
        }
        return status;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/empleado/{nombre}/{salario}", headers = {"Accept=Application/JSON"})
    public @ResponseBody String insertUser(@PathVariable String nombre, @PathVariable Float salario) throws IOException{
        String res;
        JsonFactory fc = new JsonFactory(null);
        ObjectMapper ob = new ObjectMapper(fc);
        try(ByteArrayOutputStream out = new ByteArrayOutputStream()){
            JsonGenerator jg = ob.getJsonFactory().createJsonGenerator(out);
            jg.writeObject(Collections.singletonMap("object", EmpleadoCntr.putUser(new Empleado(null, nombre, salario))));
            res = out.toString();
        }
        logger.log(Level.INFO, res);
        return res;
    }

    @RequestMapping(method= RequestMethod.GET, value="/empleado/{id}", headers={"Accept=Application/JSON"})
    public @ResponseBody String getUser(@PathVariable Integer id) throws Exception{
        String res;
        JsonFactory fc = new JsonFactory(null);
        ObjectMapper ob = new ObjectMapper(fc);
        try(ByteArrayOutputStream out = new ByteArrayOutputStream()){
            JsonGenerator jg = ob.getJsonFactory().createJsonGenerator(out);
            jg.writeObject(Collections.singletonMap("object", EmpleadoCntr.searchUser(id)));
            res = out.toString();
        }
        logger.log(Level.INFO, res);
        return res;
    }

    @RequestMapping(method= RequestMethod.DELETE, value="/empleado/{id}", headers={"Accept=Application/JSON"})
    public @ResponseBody String removeUser(@PathVariable Integer id) throws Exception{
        String res;
        JsonFactory fc = new JsonFactory(null);
        ObjectMapper ob = new ObjectMapper(fc);
        try(ByteArrayOutputStream out = new ByteArrayOutputStream()){
            JsonGenerator jg = ob.getJsonFactory().createJsonGenerator(out);
            jg.writeObject(Collections.singletonMap("object", EmpleadoCntr.deleteUser(id)));
            res = out.toString();
        }
        logger.log(Level.INFO, res);
        return res;
    }

    @RequestMapping(method = RequestMethod.GET, value="/empleados", headers = {"Accept=Application/JSON"})
    public @ResponseBody String listUsers() throws IOException{
        String res;
        JsonFactory fc = new JsonFactory(null);
        ObjectMapper ob = new ObjectMapper(fc);
        try(ByteArrayOutputStream out = new ByteArrayOutputStream()){
            JsonGenerator jg = ob.getJsonFactory().createJsonGenerator(out);
            jg.writeObject(Collections.singletonMap("object", EmpleadoCntr.getUsers()));
            res = out.toString();
        }
        logger.log(Level.INFO, res);
        return res;
    }

}
