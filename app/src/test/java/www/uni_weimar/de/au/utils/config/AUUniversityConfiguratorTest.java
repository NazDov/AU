package www.uni_weimar.de.au.utils.config;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import www.uni_weimar.de.au.AUTestApplicationConfig;
import www.uni_weimar.de.au.application.AUApplicationConfiguration;

import static junit.framework.Assert.assertEquals;

/**
 * Created by user on 23.10.17.
 */
@RunWith(RobolectricTestRunner.class)
@Config(application = AUTestApplicationConfig.class)
public class AUUniversityConfiguratorTest {

    AUUniversityConfigurator universityConfigurator;

    @Before
    public void init(){
        universityConfigurator = new AUUniversityConfigurator();
    }


    @Test
    public void testReturnCorrectConfigPackage(){
        String expConfigPackageName = "www.uni_weimar.de.au.utils.config";
        String actualConfigPackageName = universityConfigurator.getBaseConfigPackageName();
        assertEquals(expConfigPackageName, actualConfigPackageName);
    }

    @Test
    public void testGetCorrectUniversityConfigClassName(){
        String expUniversityClassName = "www.uni_weimar.de.au.utils.config.WEIMARConfiguration";
        String universityClassName = universityConfigurator.getUniversityConfigClassName("WEIMAR");
        assertEquals(expUniversityClassName, universityClassName);
    }

    @Test
    public void testBuildConfiguration(){
        IConfiguration configuration = universityConfigurator.buildConfiguration("WEIMAR");
        assertEquals("WEIMARConfiguration", configuration.toString());
    }

}
