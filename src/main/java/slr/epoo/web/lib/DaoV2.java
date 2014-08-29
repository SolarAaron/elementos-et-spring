
package slr.epoo.web.lib;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Aaron Torres <solaraaron@gmail.com>
 */
public interface DaoV2<Entity, Key extends Serializable>{
    public void save(Entity ee) throws Exception;

    public void update(Entity ee) throws Exception;

    public Entity search(Key kk, boolean unproxy) throws Exception;

    public ArrayList<Entity> filter(Entity filter, boolean unproxy) throws Exception;

    public ArrayList<Entity> restrict(String restriction, Object field, boolean unproxy) throws Exception;

    public void delete(Key kk) throws Exception; //Delete by key

    public void delete(Entity ee) throws Exception; // Delete by whole object

    public ArrayList<Entity> list(boolean unproxy) throws Exception;
}
