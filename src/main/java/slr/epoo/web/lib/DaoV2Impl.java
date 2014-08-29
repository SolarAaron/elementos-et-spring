
package slr.epoo.web.lib;

import java.io.Serializable;
import java.util.ArrayList;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Restrictions;
import slr.lib.Utility;

/**
 *
 * @author Aaron Torres <solaraaron@gmail.com>
 */
public class DaoV2Impl<Entity, Key extends Serializable> implements DaoV2<Entity, Key>{
    private Session ss;
    private Class<Entity> cc;

    public DaoV2Impl(Class<Entity> cc){
        this.cc = cc;
        ss = null;
    }

    @Override
    public ArrayList<Entity> filter(Entity filter, boolean unproxy) throws Exception{
        ArrayList<Entity> ret = new ArrayList<>();
        try(SessionUtil disp = new SessionUtil()){
            if(ss == null){
                ss = disp.getSession();
            }
            ss.beginTransaction();
            if(unproxy){
                ArrayList<Entity> tmplist = (ArrayList<Entity>)ss.createCriteria(cc).add(Example.create(filter)).list();
                for(Entity x: tmplist){
                    ret.add(Utility.unproxy(x));
                }
            } else {
                ret = (ArrayList<Entity>)ss.createCriteria(cc).list();
            }
            ss.getTransaction().commit();
        } finally {
            ss = null;
        }
        return ret;
    }

    @Override
    public ArrayList<Entity> restrict(String restriction, Object field, boolean unproxy) throws Exception{
        ArrayList<Entity> ret = new ArrayList<>();
        try(SessionUtil disp = new SessionUtil()){
            if(ss == null){
                ss = disp.getSession();
            }
            ss.beginTransaction();
            if(unproxy){
                ArrayList<Entity> tmplist = (ArrayList<Entity>)ss.createCriteria(cc).add(Restrictions.eq(restriction, field)).list();
                for(Entity x: tmplist){
                    ret.add(Utility.unproxy(x));
                }
            } else {
                ret = (ArrayList<Entity>)ss.createCriteria(cc).list();
            }
            ss.getTransaction().commit();
        } finally {
            ss = null;
        }
        return ret;
    }

    @Override
    public void save(Entity ee) throws Exception{
        try(SessionUtil disp = new SessionUtil()){
            if(ss == null){
                ss = disp.getSession();
            }
            ss.beginTransaction();
            ss.save(ee);
            ss.getTransaction().commit();
        } finally {
            ss = null;
        }
    }

    @Override
    public void update(Entity ee) throws Exception{
        try(SessionUtil disp = new SessionUtil()){
            if(ss == null){
                ss = disp.getSession();
            }
            ss.beginTransaction();
            ss.update(ee);
            ss.getTransaction().commit();
        } finally {
            ss = null;
        }
    }

    @Override
    public Entity search(Key kk, boolean unproxy) throws Exception{
        Entity ret = null;
        try(SessionUtil disp = new SessionUtil()){
            if(ss == null){
                ss = disp.getSession();
            }
            ss.beginTransaction();
            if(unproxy){
                ret = Utility.unproxy((Entity)ss.load(cc, kk));
            } else {
                ret = (Entity)ss.load(cc, kk);
            }
            ss.getTransaction().commit();
        } finally {
            ss = null;
        }
        return ret;
    }

    @Override
    public void delete(Key kk) throws Exception{
        delete(search(kk, false));
    }

    @Override
    public void delete(Entity ee) throws Exception{
        try(SessionUtil disp = new SessionUtil()){
            if(ss == null){
                ss = disp.getSession();
            }
            ss.beginTransaction();
            ss.delete(ee);
            ss.getTransaction().commit();
        } finally {
            ss = null;
        }
    }

    @Override
    public ArrayList<Entity> list(boolean unproxy) throws Exception{
        ArrayList<Entity> ret = new ArrayList<>();
        try(SessionUtil disp = new SessionUtil()){
            if(ss == null){
                ss = disp.getSession();
            }
            ss.beginTransaction();
            if(unproxy){
                ArrayList<Entity> tmplist = (ArrayList<Entity>)ss.createCriteria(cc).list();
                for(Entity x: tmplist){
                    ret.add(Utility.unproxy(x));
                }
            } else {
                ret = (ArrayList<Entity>)ss.createCriteria(cc).list();
            }
            ss.getTransaction().commit();
        } finally {
            ss = null;
        }
        return ret;
    }

}
