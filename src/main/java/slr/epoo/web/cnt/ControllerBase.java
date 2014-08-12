package slr.epoo.web.cnt;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;
import slr.epoo.web.lib.DaoV2;

/**
 *
 * @author Aaron Torres <solaraaron@gmail.com>
 * @param <T> Entity type
 * @param <K> Key type for entity
 * @param <D> Data access object type
 */
public abstract class ControllerBase<T, K extends Serializable, D extends DaoV2<T, K>> {
    private static final Logger logger = Logger.getLogger(ControllerBase.class.getName());
    
    private final Class<D> daoType;

    public ControllerBase(Class<D> daoType) {
        this.daoType = daoType;
    }

    protected String jsonWrite(Object obj) throws IOException{
        String res;
        JsonFactory fc = new JsonFactory(null);
        ObjectMapper ob = new ObjectMapper(fc);
        try(ByteArrayOutputStream out = new ByteArrayOutputStream()){
            JsonGenerator jg = ob.getJsonFactory().createJsonGenerator(out);
            jg.writeObject(Collections.singletonMap("object", obj));
            res = out.toString();
        }
        logger.log(Level.INFO, res);
        return res;
    }

    protected ArrayList<T> get(){
        try{
            return daoType.newInstance().list(true);
        } catch(Exception e){
            logger.log(Level.WARNING, "Algo anda mal: {0}", e.getMessage());
            return new ArrayList<>();
        }
    }
    protected abstract String put(T u);
    protected  T search(K id){
        T res = null;
        try{
            res = daoType.newInstance().search(id, true);
        } catch(Exception e){
            logger.log(Level.WARNING, "Algo anda mal...");
        }
        return res;
    }
    protected String delete(K key){
        String status = "ok";
        try{
            daoType.newInstance().delete(key);
        } catch(Exception ee){
            logger.log(Level.WARNING, "Algo anda mal: {0}{1}", new Object[]{ee.getMessage(), ee.getMessage()});
            status = "algo anda mal: " + ee.getMessage();
        }
        return status;
    }
}
