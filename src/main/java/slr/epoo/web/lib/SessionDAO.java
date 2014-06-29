/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package slr.epoo.web.lib;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.classic.Session;


/**
 *
 * @author Aaron
 */
public class SessionDAO {
    private static final Logger logger = Logger.getLogger(SessionDAO.class.getName());
    private static ThreadLocal tt = new ThreadLocal();
    private final static SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();

    public SessionDAO() {
    }
    
    public static Session getSession(){
        Session ss = (Session) tt.get();
        if(ss == null){
            ss = sessionFactory.openSession();
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
}
