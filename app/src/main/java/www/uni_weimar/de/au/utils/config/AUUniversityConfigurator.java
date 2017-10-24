package www.uni_weimar.de.au.utils.config;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Created by user on 23.10.17.
 */

class AUUniversityConfigurator {

    private static final String CONFIGURATION = "Configuration";
    private static String basePackage;

    AUUniversityConfigurator() {
        basePackage = getBaseConfigPackageName();
    }

    String getBaseConfigPackageName() {
        return this.getClass().getPackage().getName();
    }

    void initConfiguration(Context context, String universityName) {
        buildConfiguration(universityName).configure(context);
    }

    IConfiguration buildConfiguration(String universityName) {
        IConfiguration configuration;
        try {
            Class classToLoad = Class.forName(getUniversityConfigClassName(universityName));
            configuration = (IConfiguration) classToLoad.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e.getCause());
        }
        return configuration;
    }


    @NonNull
    String getUniversityConfigClassName(String universityName) {
        return basePackage + "." + universityName + CONFIGURATION;
    }
}
