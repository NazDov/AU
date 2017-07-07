package www.uni_weimar.de.au;

import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.rule.PowerMockRule;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import java.util.List;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import www.uni_weimar.de.au.models.AUMainMenuTab;
import www.uni_weimar.de.au.orm.AUMainMenuTabORM;
import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.powermock.api.mockito.PowerMockito.*;

/**
 * Created by ndovhuy on 07.07.2017.
 */
@RunWith(RobolectricTestRunner.class)
@Config(application = AUTestApplicationConfig.class, constants = BuildConfig.class, sdk = 19, manifest = "../AndroidManifest.xml")
@PowerMockIgnore({"org.mockito.*", "org.robolectric.*", "android.*"})
@SuppressStaticInitializationFor("io.realm.internal.Util")
@PrepareForTest(Realm.class)
public class AUMainMenuTabORMTest {

    private Realm realm;
    private AUMainMenuTabORM auMainMenuTabOrm;
    @Rule
    public PowerMockRule powerMockRule = new PowerMockRule();

    @Before
    public void setup() {
        mockStatic(Realm.class);
        Realm mockRealm = mock(Realm.class);
        when(Realm.getDefaultInstance()).thenReturn(mockRealm);
        this.realm = mockRealm;
        auMainMenuTabOrm = new AUMainMenuTabORM(realm);
    }

    @Test
    public void testAddAUMAinMenuTab() {
        AUMainMenuTab expAUMainMenuTab = new AUMainMenuTab();
        expAUMainMenuTab.setTitle("Courses");
        when(realm.copyToRealm(expAUMainMenuTab)).thenReturn(expAUMainMenuTab);
        AUMainMenuTab actualAUMAinMenuTab = auMainMenuTabOrm.add(expAUMainMenuTab);
        verify(realm, times(1)).beginTransaction();
        verify(realm, times(1)).copyToRealm(expAUMainMenuTab);
        verify(realm, times(1)).commitTransaction();
        assertEquals(expAUMainMenuTab, actualAUMAinMenuTab);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddNullAuMainMenuTab() {
        //given
        AUMainMenuTab auMainMenuTab = null;
        when(realm.copyToRealm(auMainMenuTab)).thenThrow(IllegalArgumentException.class);
        //when
        auMainMenuTabOrm.add(auMainMenuTab);
        //then
        verify(realm, times(1)).copyToRealm(auMainMenuTab);
    }

    @Test
    public void testFindAllAuMainMenuTab() {
        //Given
        RealmQuery<AUMainMenuTab> auMainMenuTabQuery = mock(RealmQuery.class);
        RealmResults<AUMainMenuTab> expAuMAinMenuTabItems = mock(RealmResults.class);
        when(realm.where(AUMainMenuTab.class)).thenReturn(auMainMenuTabQuery);
        when(auMainMenuTabQuery.findAll()).thenReturn(expAuMAinMenuTabItems);
        //when
        List<AUMainMenuTab> actualAUMainMenuTabItems = auMainMenuTabOrm.findAll();
        //then
        verify(realm, times(1)).where(AUMainMenuTab.class);
        verify(auMainMenuTabQuery, times(1)).findAll();
        assertEquals(expAuMAinMenuTabItems, actualAUMainMenuTabItems);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testDeleteAllAuMainMenuTab() {
        //Given
        doThrow(new UnsupportedOperationException()).when(realm).delete(AUMainMenuTab.class);
        //when
        auMainMenuTabOrm.deleteAll(AUMainMenuTab.class);
        //then
        verify(realm).delete(AUMainMenuTab.class);
    }


}
