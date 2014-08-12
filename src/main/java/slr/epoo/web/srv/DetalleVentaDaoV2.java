
package slr.epoo.web.srv;

import slr.epoo.web.lib.DaoV2Impl;
import slr.epoo.web.mdl.DetalleVenta;
import slr.epoo.web.mdl.DetalleVentaPK;

/**
 *
 * @author Aaron
 */
public class DetalleVentaDaoV2 extends DaoV2Impl<DetalleVenta, DetalleVentaPK>{

    public DetalleVentaDaoV2() {
        super(DetalleVenta.class);
    }

}
