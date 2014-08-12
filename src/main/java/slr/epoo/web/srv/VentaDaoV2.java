
package slr.epoo.web.srv;

import slr.epoo.web.lib.DaoV2Impl;
import slr.epoo.web.mdl.Venta;

/**
 *
 * @author Aaron
 */
public class VentaDaoV2 extends DaoV2Impl<Venta, Integer>{

    public VentaDaoV2() {
        super(Venta.class);
    }

}
