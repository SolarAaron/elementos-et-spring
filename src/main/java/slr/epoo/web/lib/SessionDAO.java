/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package slr.epoo.web.lib;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import slr.epoo.web.HibernateBS;


/**
 *
 * @author Aaron
 */
public class SessionDAO implements AutoCloseable{
    private static final Logger logger = Logger.getLogger(SessionDAO.class.getName());
    private static ThreadLocal tt = new ThreadLocal();

    public SessionDAO() {
        logger.log(Level.INFO, "Abierta conexion");
    }
    
    public static Session getSession(){
        Session ss = (Session) tt.get();
        if(ss == null){
            ss = HibernateBS.getSessionFactory().openSession();
            tt.set(ss);
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
        getSession().close();
        tt.set(null);
        logger.log(Level.INFO, "Cerrada conexion");
    }
}
