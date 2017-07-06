package www.uni_weimar.de.au;

import android.support.annotation.NonNull;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.rule.PowerMockRule;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import java.util.ArrayList;
import java.util.List;
import io.realm.Realm;
import www.uni_weimar.de.au.models.AUNewsFeed;
import www.uni_weimar.de.au.orm.AUNewsFeedORM;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.*;

/**
 * Created by ndovhuy on 05.07.2017.
 */
@RunWith(RobolectricTestRunner.class)
@Config(application = AUTestApplicationConfig.class, constants = BuildConfig.class, sdk = 19)
@PowerMockIgnore({"org.mockito.*", "org.robolectric.*", "android.*"})
@SuppressStaticInitializationFor("io.realm.internal.Util")
@PrepareForTest(Realm.class)
public class AUNewsFeedORMTest {

    private Realm realm;
    private AUNewsFeedORM auNewsFeedORM;
    @Rule
    public PowerMockRule powerMockRule = new PowerMockRule();

    @Before
    public void setup() {
        mockStatic(Realm.class);
        Realm realm = PowerMockito.mock(Realm.class);
        when(Realm.getDefaultInstance()).thenReturn(realm);
        this.realm = realm;
        auNewsFeedORM = new AUNewsFeedORM(realm);
    }

    @Test
    public void testAddAUNewsItem() {
        //Given
        AUNewsFeed originalItem = createAuNewsFeedItemStub();
        when(realm.copyToRealm(originalItem)).thenReturn(originalItem);
        //when
        AUNewsFeed savedItem = auNewsFeedORM.add(originalItem);
        //Then
        assertThat(originalItem, is(savedItem));
        verify(realm).copyToRealm(originalItem);
    }

    @Test
    public void testAddAllAUNewsFeedItems() {
        //Given
        List<AUNewsFeed> expAuNewsFeedList = createAuNewsFeedItemsStub();
        when(realm.copyToRealm(expAuNewsFeedList)).thenReturn(expAuNewsFeedList);
        //when
        List<AUNewsFeed> actualAUNewsFeedList = auNewsFeedORM.addAll(expAuNewsFeedList);
        //then
        assertThat(expAuNewsFeedList, is(actualAUNewsFeedList));
        verify(realm).copyToRealm(expAuNewsFeedList);
    }

    @Test
    public void testDeleteAllAuNewsFeedItems() {
        boolean deleted = auNewsFeedORM.deleteAll(AUNewsFeed.class);
        assertTrue(deleted);
    }

    @Test
    public void testFindAllAUNewsFeedItems() {

    }


    private List<AUNewsFeed> createAuNewsFeedItemsStub() {
        List<AUNewsFeed> auNewsFeeds = new ArrayList<>();
        AUNewsFeed auNewsFeedItemOne = new AUNewsFeed();
        auNewsFeedItemOne.setTitle("Title 1");
        auNewsFeedItemOne.setDesciption("Description 1");
        auNewsFeedItemOne.setAuthor("Author 1");
        auNewsFeeds.add(auNewsFeedItemOne);
        AUNewsFeed auNewsFeedItemTwo = new AUNewsFeed();
        auNewsFeedItemTwo.setTitle("Title 2");
        auNewsFeedItemTwo.setDesciption("Description 2");
        auNewsFeedItemTwo.setAuthor("Author 2");
        auNewsFeeds.add(auNewsFeedItemTwo);
        AUNewsFeed auNewsFeedItemThree = new AUNewsFeed();
        auNewsFeedItemThree.setTitle("Title 3");
        auNewsFeedItemThree.setDesciption("Description 3");
        auNewsFeedItemThree.setAuthor("Author 3");
        auNewsFeeds.add(auNewsFeedItemTwo);
        return auNewsFeeds;
    }

    @NonNull
    private AUNewsFeed createAuNewsFeedItemStub() {
        AUNewsFeed auNewsFeed = new AUNewsFeed();
        auNewsFeed.setAuthor("John Doe");
        auNewsFeed.setDesciption("custom description...");
        auNewsFeed.setTitle("custom title");
        return auNewsFeed;
    }

}
