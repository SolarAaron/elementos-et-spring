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
import slr.epoo.web.mdl.DetalleVenta;
import slr.epoo.web.mdl.DetalleVentaPK;
import slr.epoo.web.mdl.Empleado;
import slr.epoo.web.mdl.Venta;
import slr.epoo.web.srv.DetalleVentaDaoV2;
import slr.epoo.web.srv.VentaDaoV2;

/**
 *
 * @author Aaron Torres <solaraaron@gmail.com>
 */
@Controller
@RequestMapping(value="/ventas")
public class VentaCntr extends ControllerBase<Venta, Integer, VentaDaoV2> {
    private static final Logger logger = Logger.getLogger(VentaCntr.class.getName());

    public VentaCntr() {
        super(VentaDaoV2.class);
    }

    public class DetalleCntr extends ControllerBase<DetalleVenta, DetalleVentaPK, DetalleVentaDaoV2> {

        public DetalleCntr() {
            super(DetalleVentaDaoV2.class);
        }

        @Override
        protected String put(DetalleVenta u) {
            String status = "ok";
            try{
                DetalleVentaDaoV2 disp = new DetalleVentaDaoV2();
                ArrayList<DetalleVenta> vlist = disp.list(true);
                boolean xist = false;

                u.setPrecioActual(new ProductoCntr().search(u.getDetalleVentaPK().getCodP()).getPrecio());
                if(!vlist.isEmpty()){
                    for(DetalleVenta v: vlist){
                        if(v.getDetalleVentaPK().equals(u.getDetalleVentaPK())){
                            xist |= true;
                        }
                    }
                    if(xist){
                        disp.update(u);
                    } else {
                        disp.save(u);
                    }
                } else {
                    disp.save(u);
                }
            }catch(Exception ee){
                logger.log(Level.WARNING, "Algo anda mal: {0}", ee.getMessage());
                status = "algo anda mal: " + ee.getMessage();
            }
            return status;
        }
    }

    @Override
    protected String put(Venta u) {
        String status = "ok";
        try{
            VentaDaoV2 disp = new VentaDaoV2();
            ArrayList<Venta> vlist = disp.list(true);

            if(!vlist.isEmpty()){
                for(Venta v: vlist){
                    if(v.getFecha().equals(u.getFecha())){
                        u.setIdV(v.getIdV());
                    }
                }
                if(u.getIdV() == null){
                    u.setIdV(vlist.get(vlist.size() - 1).getIdV() + 1);
                    disp.update(u);
                } else {
                    disp.save(u);
                }
            } else {
                u.setIdV(1);
                disp.save(u);
            }
        }catch(Exception ee){
            logger.log(Level.WARNING, "Algo anda mal: {0}", ee.getMessage());
            status = "algo anda mal: " + ee.getMessage();
        }
        return status;
    }

    @RequestMapping(value="", method= RequestMethod.GET, headers={"Accept=Application/JSON"})
    public @ResponseBody String listVentas() throws IOException{
        return jsonWrite(get());
    }

    @RequestMapping(value="/{id}", method = RequestMethod.GET, headers={"Accept=Application/JSON"})
    public @ResponseBody String searchVenta(@PathVariable Integer id) throws IOException{
        return jsonWrite(search(id));
    }

    @RequestMapping(value="", method={RequestMethod.POST, RequestMethod.PUT}, headers={"Accept=Application/JSON"})
    public @ResponseBody String insertVenta(@RequestParam Integer ide, @RequestParam Integer idc, @RequestParam String nc) throws IOException{
        return jsonWrite(put(new Venta(null, new Empleado(ide), new Cliente(new ClientePK(idc, nc)))));
    }

    @RequestMapping(value="/{id}", method = RequestMethod.DELETE, headers={"Accept=Application/JSON"})
    public @ResponseBody String deleteVenta(@PathVariable Integer id) throws IOException{
        return jsonWrite(delete(id));
    }

    @RequestMapping(value="/{id}/{cod}", method = RequestMethod.GET, headers={"Accept=Application/JSON"})
    public @ResponseBody String detalleVenta(@PathVariable Integer id, @PathVariable String cod) throws IOException{
        return jsonWrite(new DetalleCntr().search(new DetalleVentaPK(id, cod)));
    }

    @RequestMapping(value="/{id}/{cod}", method = RequestMethod.DELETE, headers={"Accept=Application/JSON"})
    public @ResponseBody String removeDetalleVenta(@PathVariable Integer id, @PathVariable String cod) throws IOException{
        return jsonWrite(new DetalleCntr().delete(new DetalleVentaPK(id, cod)));
    }

    @RequestMapping(value="/{id}/{cod}/{cant}", method = {RequestMethod.POST, RequestMethod.PUT}, headers={"Accept=Application/JSON"})
    public @ResponseBody String insertDetalleVenta(@PathVariable Integer id, @PathVariable String cod, @PathVariable Integer cant) throws IOException{
        return jsonWrite(new DetalleCntr().put(new DetalleVenta(new DetalleVentaPK(id, cod), cant)));
    }
}
