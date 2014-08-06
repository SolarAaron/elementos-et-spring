package slr.epoo.web.cnt;

import slr.epoo.web.lib.DaoV2Impl;
import slr.epoo.web.mdl.Empleado;

/**
 *
 * @author Aaron Torres <solaraaron@gmail.com>
 */
public class EmpleadoDaoV2 extends DaoV2Impl<Empleado, Integer>{

    public EmpleadoDaoV2() {
        super(Empleado.class);
    }

}
