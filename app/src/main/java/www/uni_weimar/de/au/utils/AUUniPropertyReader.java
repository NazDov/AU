package www.uni_weimar.de.au.utils;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by nazar on 22.08.17.
 */

public class AUUniPropertyReader {

    private static final String FILE_NAME = "university.properties";
    private static final java.lang.String NAME = "name";

    public AUUniPropertyReader(Context context){
        InputStream is = null;
        try {
            is =context.getAssets().open(FILE_NAME);
            Properties properties = new Properties();
            properties.load(is);
            String uni = properties.getProperty(NAME);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(is != null) try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
