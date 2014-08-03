package slr.epoo.web.cnt;

import slr.epoo.web.lib.DaoV2Impl;
import slr.epoo.web.mdl.Usuario;

/**
 *
 * @author Aaron Torres <solaraaron@gmail.com>
 */
public class UsuarioDaoV2 extends DaoV2Impl<Usuario, Integer>{

    public UsuarioDaoV2() {
        super(Usuario.class);
    }

}
