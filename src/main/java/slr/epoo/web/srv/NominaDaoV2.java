package slr.epoo.web.srv;

import slr.epoo.web.lib.DaoV2Impl;
import slr.epoo.web.mdl.Nomina;

/**
 *
 * @author Aaron Torres <solaraaron@gmail.com>
 */
public class NominaDaoV2 extends DaoV2Impl<Nomina, Integer> {

    public NominaDaoV2() {
        super(Nomina.class);
    }

}
