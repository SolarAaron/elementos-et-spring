package slr.epoo.web.cnt;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;

/**
 *
 * @author Aaron Torres <solaraaron@gmail.com>
 */
public abstract class ControllerBase<T, K> {
    private static final Logger logger = Logger.getLogger(ControllerBase.class.getName());

    public String jsonWrite(Object obj) throws IOException{
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

    protected abstract ArrayList<T> get();
    protected abstract String put(T u);
    protected abstract T search(K key);
    protected abstract String delete(K key);
}
