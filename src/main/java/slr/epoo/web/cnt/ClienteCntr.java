package slr.epoo.web.cnt;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import slr.epoo.web.mdl.Cliente;
import slr.epoo.web.mdl.ClientePK;
import slr.epoo.web.srv.ClienteDaoV2;

@Controller
@RequestMapping("/clientes")
public class ClienteCntr extends ControllerBase<Cliente, ClientePK> {
    private static final Logger logger = Logger.getLogger(ClienteCntr.class.getName());

    @Override
    protected ArrayList<Cliente> get() {
        try{
            return new ClienteDaoV2().list(true);
        } catch(Exception e){
            logger.log(Level.WARNING, "Algo anda mal: {0}", e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    protected String put(Cliente c) {
        String status = "ok";
        try{
            ClienteDaoV2 disp = new ClienteDaoV2();
            ArrayList<Cliente> clist = disp.list(true);
            for(Cliente cli: clist){

            }
        } catch(Exception e){
            logger.log(Level.WARNING, "Algo anda mal: {0}", e.getMessage());
        }
        return status;
    }

    @Override
    protected Cliente search(ClientePK key) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected String delete(ClientePK key) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
