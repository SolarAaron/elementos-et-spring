
package slr.epoo.web.cnt;

import slr.epoo.web.lib.DaoV2Impl;
import slr.epoo.web.mdl.Cliente;

/**
 *
 * @author Aaron
 */
public class ClienteDaoV2 extends DaoV2Impl<Cliente, Integer>{

    public ClienteDaoV2() {
        super(Cliente.class);
    }
    
}
