package slr.epoo.web.lib;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.HibernateException;
import org.hibernate.classic.Session;
import slr.epoo.web.HibernateBS;


/**
 *
 * @author Aaron
 */
public class SessionUtil implements AutoCloseable{
    private static final Logger logger = Logger.getLogger(SessionUtil.class.getName());
    private static ThreadLocal<Session> tt = new ThreadLocal<>();

    Session ss;
    public SessionUtil() {
        ss = getSessionI();
        logger.log(Level.INFO, "Abierta conexion");
    }

    public static Session getSessionI(){
        Session rss = tt.get();
        if(rss == null){
            rss = HibernateBS.getSessionFactory().openSession();
            tt.set(rss);
        }
        return rss;
    }

    public Session getSession(){
        if(ss == null){
            ss = getSessionI();
        }
        return ss;
    }

    protected void begin(){
        getSession().beginTransaction();
    }

    protected void commit(){
        getSession().getTransaction().commit();
    }

    protected void rollback(){
        try{
            getSession().getTransaction().rollback();
            getSession().close();
        } catch(HibernateException e){
            getSession().close();
            tt.set(null);
            logger.log(Level.WARNING, e.getMessage());
        }
    }

    @Override
    public void close() throws Exception {
        ss.close();
        tt.set(null);
        logger.log(Level.INFO, "Cerrada conexion");
    }
}
