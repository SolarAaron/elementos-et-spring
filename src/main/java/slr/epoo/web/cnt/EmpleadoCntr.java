package slr.epoo.web.cnt;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import slr.epoo.web.mdl.Empleado;
import slr.epoo.web.srv.EmpleadoDaoV2;

/**
 *
 * @author Aaron
 */
@Controller
@RequestMapping("/empleados")
public class EmpleadoCntr extends ControllerBase<Empleado, Integer>{
    private static final Logger logger = Logger.getLogger(EmpleadoCntr.class.getName());

    @Override
    protected ArrayList<Empleado> get(){
        ArrayList<Empleado> res = new ArrayList<>();
        try{
            res = new EmpleadoDaoV2().list(true);
        } catch(Exception x){
            logger.log(Level.WARNING, "Algo anda mal: {0}", x.getMessage());
        }
        return res;
    }

    @Override
    protected String put(Empleado u){
        String status = "ok";
        try{
            EmpleadoDaoV2 disp = new EmpleadoDaoV2();
            ArrayList<Empleado> ulist = disp.list(true);
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

    @Override
    public Empleado search(Integer id){
        Empleado res = null;
        try{
            res = new EmpleadoDaoV2().search(id, true);
        } catch(Exception e){
            logger.log(Level.WARNING, "Algo anda mal...");
        }
        return res;
    }

    @Override
    public String delete(Integer id){
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

    @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT}, value = "/{nombre}/{salario}", headers = {"Accept=Application/JSON"})
    public @ResponseBody String insertUser(@PathVariable String nombre, @PathVariable BigDecimal salario, @RequestParam(value="password") String password) throws IOException{
        return jsonWrite(put(new Empleado(null, nombre, salario, password)));
    }

    @RequestMapping(method= RequestMethod.GET, value="/{id}", headers={"Accept=Application/JSON"})
    public @ResponseBody String getUser(@PathVariable Integer id) throws Exception{
        return jsonWrite(search(id));
    }

    @RequestMapping(method= RequestMethod.DELETE, value="/{id}", headers={"Accept=Application/JSON"})
    public @ResponseBody String removeUser(@PathVariable Integer id) throws Exception{
        return jsonWrite(delete(id));
    }

    @RequestMapping(method = RequestMethod.GET, value="", headers = {"Accept=Application/JSON"})
    public @ResponseBody String listUsers() throws IOException{
        return jsonWrite(get());
    }

}
