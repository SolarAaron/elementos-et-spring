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
import slr.epoo.web.mdl.Nomina;
import slr.epoo.web.mdl.Usuario;

/**
 *
 * @author Aaron Torres <solaraaron@gmail.com>
 */
@Controller
@RequestMapping("/")
public class NominaCntr {
    private static final Logger logger = Logger.getLogger(NominaCntr.class.getName());

    public static ArrayList<Nomina> getNomina(){
        ArrayList<Nomina> res = new ArrayList<>();
        try{
            res = new NominaDaoV2().list();
        } catch(Exception e){
            logger.log(Level.WARNING, "Excepcion en getNomina: {0}", e.getMessage());
        }
        return res;
    }

    @RequestMapping(method = RequestMethod.GET, value="/nominas", headers = {"Accept=Application/JSON"})
    public @ResponseBody String listNominas() throws IOException{
        String res;
        JsonFactory fc = new JsonFactory(null);
        ObjectMapper ob = new ObjectMapper(fc);
        try(ByteArrayOutputStream out = new ByteArrayOutputStream()){
            JsonGenerator jg = ob.getJsonFactory().createJsonGenerator(out);
            jg.writeObject(Collections.singletonMap("object", NominaCntr.getNomina()));
            res = out.toString();
        }
        logger.log(Level.INFO, res);
        return res;
    }

    public static String putNomina(Nomina u){
        String status = "ok";
        try{
            UsuarioDaoV2 udisp = new UsuarioDaoV2();
            NominaDaoV2 ndisp = new NominaDaoV2();
            Usuario usr = u.getIdU();

            if(usr != null){
                if(usr.getNomina() != null){
                    u.setId(usr.getNomina().getId());
                    if(usr.getNomina().getSaldo() != null){
                        u.setSaldo(usr.getNomina().getSaldo() + usr.getSalario());
                    } else {
                        u.setSaldo(usr.getSalario());
                    }
                    ndisp.update(u);
                } else {
                    ArrayList<Nomina> nlist = ndisp.list();
                    u.setId(nlist.get(nlist.size() - 1).getId() + 1);
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

    @RequestMapping(method = RequestMethod.POST, value = "/nomina/{identidad}", headers = {"Accept=text/html"})
    public @ResponseBody String insertNomina(@PathVariable Integer identidad) throws IOException{
        String res;
        JsonFactory fc = new JsonFactory(null);
        ObjectMapper ob = new ObjectMapper(fc);
        try(ByteArrayOutputStream out = new ByteArrayOutputStream()){
            JsonGenerator jg = ob.getJsonFactory().createJsonGenerator(out);
            try {
                jg.writeObject(Collections.singletonMap("object", NominaCntr.putNomina(new Nomina(0, 0.0f, new UsuarioDaoV2().search(identidad, true)))));
            } catch (Exception ex) {
                logger.log(Level.SEVERE, null, ex);
                jg.writeObject(Collections.singletonMap("object", "Error"));
            }
            res = out.toString();
        }
        logger.log(Level.INFO, res);
        return res;
    }
}
