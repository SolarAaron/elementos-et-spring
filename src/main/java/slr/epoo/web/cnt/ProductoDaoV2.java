
package slr.epoo.web.cnt;

import slr.epoo.web.lib.DaoV2Impl;
import slr.epoo.web.mdl.Producto;

/**
 *
 * @author Aaron
 */
public class ProductoDaoV2 extends DaoV2Impl<Producto, String>{

    public ProductoDaoV2() {
        super(Producto.class);
    }
    
}
