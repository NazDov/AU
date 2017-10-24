package www.uni_weimar.de.au.utils.config;

import android.content.Context;

/**
 * a base interface representing configuration object.
 * the name of the class that derives from this object
 * should be conformed in the following way:
 * name property value taken from university.properties
 * and the word "Configuration"
 */
abstract class IConfiguration {

    public abstract String getConfigFileURL();

    IConfiguration(){

    }

    void configure(Context context) {
        configDefaultLinks(context);
        configDefaultParsers(context);
        configDefaultFragments(context);
    }

    abstract void configDefaultFragments(Context context);

    abstract void configDefaultParsers(Context context);

    abstract void configDefaultLinks(Context context);
}
