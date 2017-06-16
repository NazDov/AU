package www.uni_weimar.de.au;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import www.uni_weimar.de.au.models.AUMainMenuTab;
import www.uni_weimar.de.au.orm.AUMainMenuTabORM;
import www.uni_weimar.de.au.parsers.impl.AUMainMenuTabParser;
import www.uni_weimar.de.au.service.impl.AUMainMenuContentRequestService;

/**
 * Created by ndovhuy on 14.06.2017.
 */

public class TestMainMenuContentProviderService {

    @Injectable
    private AUMainMenuTabParser auMainMenuTabParser;

    @Injectable
    private AUMainMenuTabORM auMainMenuTabORM;

    private List<AUMainMenuTab> actualList;

    @Tested
    private AUMainMenuContentRequestService AUMainMenuContentProviderService = new AUMainMenuContentRequestService() {
        @Override
        public Observable<List<AUMainMenuTab>> requestContent() {
            return Observable.create(e -> {
                try {
                    List<AUMainMenuTab> auMainMenuTabList = auMainMenuTabParser.parseAllAU();
                    e.onNext(auMainMenuTabList);
                    e.onComplete();
                } catch (Exception throwable) {
                    e.onError(throwable);
                }
            });
        }
    };


    @Test
    public void testProvideContent() {

        new Expectations() {
            {
                auMainMenuTabParser.parseAllAU();
                returns(testList());
            }
        };

        AUMainMenuContentProviderService
                .requestContent()
                .subscribe(auMainMenuTabs -> {
                    actualList = auMainMenuTabs;
                });


        assertEquals(0, actualList.size());


    }

    private List<AUMainMenuTab> testList() {
        List<AUMainMenuTab> auMainMenuTabList = new ArrayList<>();
        AUMainMenuTab auMainMenuTab = new AUMainMenuTab();
        auMainMenuTab.setTitle("News");
        return auMainMenuTabList;
    }


}
