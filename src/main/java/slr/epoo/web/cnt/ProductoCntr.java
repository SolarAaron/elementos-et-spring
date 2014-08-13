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
import slr.epoo.web.mdl.Producto;
import slr.epoo.web.srv.ProductoDaoV2;

/**
 *
 * @author Aaron Torres <solaraaron@gmail.com>
 */
@Controller
@RequestMapping(value="/productos")
public class ProductoCntr extends ControllerBase<Producto, String, ProductoDaoV2> {
    private static final Logger logger = Logger.getLogger(ProductoCntr.class.getName());

    public ProductoCntr() {
        super(ProductoDaoV2.class);
    }


    @Override
    protected String put(Producto u) {
        String status = "ok";
        try{
            ProductoDaoV2 disp = new ProductoDaoV2();
            ArrayList<Producto> ulist = disp.list(true);
            if(!ulist.isEmpty()){
                boolean xists = false;
                for(Producto usr: ulist){
                    xists |= (usr.getCodP().equals(u.getCodP()));
                }
                if(!xists){
                    disp.save(u);
                } else {
                    disp.update(u);
                }
            } else {
                disp.save(u);
            }
        } catch(Exception ee){
            logger.log(Level.WARNING, "Algo anda mal: {0}", ee.getMessage());
            status = "algo anda mal: " + ee.getMessage();
        }
        return status;
    }

    @RequestMapping(value="", method= RequestMethod.GET, headers={"Accept=Application/JSON"})
    public @ResponseBody String listProductos() throws IOException{
        return jsonWrite(get());
    }

    @RequestMapping(value="/{cod}", method= RequestMethod.GET, headers={"Accept=Application/JSON"})
    public @ResponseBody String searchProducto(@PathVariable String cod) throws IOException{
        return jsonWrite(search(cod));
    }

    @RequestMapping(value="/{codigo}/{precio}", method={RequestMethod.POST, RequestMethod.PUT}, headers={"Accept=Application/JSON"})
    public @ResponseBody String insertProducto(@PathVariable String codigo, @RequestParam String desc, @PathVariable BigDecimal precio) throws IOException{
        return jsonWrite(put(new Producto(codigo, desc, precio)));
    }

    @RequestMapping(value="/{cod}", method= RequestMethod.DELETE, headers={"Accept=Application/JSON"})
    public @ResponseBody String deleteProducto(@PathVariable String cod) throws IOException{
        return jsonWrite(delete(cod));
    }
}
