
package slr.epoo.web.srv;

import slr.epoo.web.lib.DaoV2Impl;
import slr.epoo.web.mdl.Cliente;
import slr.epoo.web.mdl.ClientePK;

/**
 *
 * @author Aaron
 */
public class ClienteDaoV2 extends DaoV2Impl<Cliente, ClientePK>{

    public ClienteDaoV2() {
        super(Cliente.class);
    }

}
