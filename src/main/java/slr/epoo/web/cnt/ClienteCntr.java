package slr.epoo.web.cnt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import slr.epoo.web.mdl.Cliente;
import slr.epoo.web.mdl.ClientePK;
import slr.epoo.web.srv.ClienteDaoV2;

@Controller
@RequestMapping("/clientes")
public class ClienteCntr extends ControllerBase<Cliente, ClientePK, ClienteDaoV2> {
    private static final Logger logger = Logger.getLogger(ClienteCntr.class.getName());

    public ClienteCntr() {
        super(ClienteDaoV2.class);
    }

    @Override
    protected String put(Cliente c) {
        String status = "ok";
        try{
            ClienteDaoV2 disp = new ClienteDaoV2();
            ArrayList<Cliente> clist = disp.list(true);
            if(!clist.isEmpty()){
                for(Cliente cli: clist){
                    if(cli.getClientePK().getNomUsuario().equals(c.getClientePK().getNomUsuario())){
                        c.setClientePK(cli.getClientePK());
                    }
                }
                if(c.getClientePK().getIdC() == null){
                    c.setClientePK(new ClientePK(clist.get(clist.size() - 1).getClientePK().getIdC() + 1, c.getClientePK().getNomUsuario()));
                    disp.save(c);
                } else {
                    disp.update(c);
                }
            } else {
                c.setClientePK(new ClientePK(1, c.getClientePK().getNomUsuario()));
                disp.save(c);
            }
        } catch(Exception ee){
            logger.log(Level.WARNING, "Algo anda mal: {0}", ee.getMessage());
            status = "algo anda mal: " + ee.getMessage();
        }
        return status;
    }

    @RequestMapping(method = RequestMethod.GET, value = "", headers={"Accept=Application/JSON"})
    public @ResponseBody String listClientes() throws IOException{
        return jsonWrite(get());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}/{cli}", headers={"Accept=Application/JSON"})
    public @ResponseBody String searchCliente(@PathVariable Integer id, @PathVariable String cli) throws IOException{
        return jsonWrite(search(new ClientePK(id, cli)));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}/{cli}", headers={"Accept=Application/JSON"})
    public @ResponseBody String deleteCliente(@PathVariable Integer id, @PathVariable String cli) throws IOException{
        return jsonWrite(delete(new ClientePK(id, cli)));
    }

    @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT}, value = "/{cli}", headers = {"Accept=Application/JSON"})
    public  @ResponseBody String insertCliente(@PathVariable String cli, @RequestParam(value="nombre") String nombre, @RequestParam(value="password") String password) throws IOException{
        return jsonWrite(put(new Cliente(new ClientePK(null, cli), nombre, password)));
    }
}
