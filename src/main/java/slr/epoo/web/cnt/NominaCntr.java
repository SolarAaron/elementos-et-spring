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
import org.springframework.web.bind.annotation.ResponseBody;
import slr.epoo.web.mdl.Empleado;
import slr.epoo.web.mdl.Nomina;
import slr.epoo.web.srv.EmpleadoDaoV2;
import slr.epoo.web.srv.NominaDaoV2;

/**
 *
 * @author Aaron Torres <solaraaron@gmail.com>
 */
@Controller
@RequestMapping("/nominas")
public class NominaCntr extends ControllerBase<Nomina, Integer>{
    private static final Logger logger = Logger.getLogger(NominaCntr.class.getName());

    @Override
    public ArrayList<Nomina> get(){
        ArrayList<Nomina> res = new ArrayList<>();
        try{
            res = new NominaDaoV2().list(true);
        } catch(Exception e){
            logger.log(Level.WARNING, "Excepcion en getNomina: {0}", e.getMessage());
        }
        return res;
    }

    @Override
    public String put(Nomina u){
        String status = "ok";
        try{
            NominaDaoV2 ndisp = new NominaDaoV2();
            Empleado usr = u.getIdE();

            if(usr != null){
                if(usr.getNomina() != null){
                    u.setIdN(usr.getNomina().getIdN());
                    if(usr.getNomina().getSaldo() != null){
                        u.setSaldo(usr.getNomina().getSaldo().add(usr.getSalario()));
                    } else {
                        u.setSaldo(usr.getSalario());
                    }
                    ndisp.update(u);
                } else {
                    ArrayList<Nomina> nlist = ndisp.list(true);
                    if(!nlist.isEmpty()){
                        u.setIdN(nlist.get(nlist.size() - 1).getIdN() + 1);
                    } else {
                        u.setIdN(1);
                    }
                    u.setSaldo(usr.getSalario());
                    ndisp.save(u);
                }
            }
        } catch(Exception ee){
            ee.printStackTrace(System.err);
            logger.log(Level.WARNING, "Algo anda mal: {0}", ee.getMessage());
            status = "algo anda mal: " + ee.getMessage();
        }
        return status;
    }

    public Nomina search(Integer id){
        Nomina res = null;
        try{
            res = new NominaDaoV2().search(id, true);
        } catch(Exception e){
            logger.log(Level.WARNING, "Algo anda mal...");
        }
        return null;
    }

    @Override
    public String delete(Integer id){
        String status = "ok";
        try{
            NominaDaoV2 disp = new NominaDaoV2();
            disp.delete(id);
        } catch(Exception ee){
            logger.log(Level.WARNING, "Algo anda mal: {0}{1}", new Object[]{ee.getMessage(), ee.getMessage()});
            status = "algo anda mal: " + ee.getMessage();
        }
        return status;
    }

    @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT}, value = "/{identidad}", headers = {"Accept=Application/JSON"})
    public @ResponseBody String insertNomina(@PathVariable Integer identidad) throws Exception{
        return jsonWrite(put(new Nomina(0, BigDecimal.ZERO, new EmpleadoDaoV2().search(identidad, true))));
    }

    @RequestMapping(method = RequestMethod.GET, value="/{id}", headers={"Accept=Application/JSON"})
    public @ResponseBody String getNomina(@PathVariable Integer id) throws IOException{
        return jsonWrite(search(id));
    }

    @RequestMapping(method = RequestMethod.DELETE, value="/{id}", headers={"Accept=Application/JSON"})
    public @ResponseBody String removeNomina(@PathVariable Integer id) throws IOException{
        return jsonWrite(delete(id));
    }

    @RequestMapping(method = RequestMethod.GET, value="", headers = {"Accept=Application/JSON"})
    public @ResponseBody String listNominas() throws IOException{
        return jsonWrite(get());
    }
}
