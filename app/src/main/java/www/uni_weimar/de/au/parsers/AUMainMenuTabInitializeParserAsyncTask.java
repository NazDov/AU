package www.uni_weimar.de.au.parsers;

import android.os.AsyncTask;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.RealmObject;
import www.uni_weimar.de.au.models.AUMainMenuTab;

/**
 * Created by nazar on 12.06.17.
 */

public abstract class AUMainMenuTabInitializeParserAsyncTask
        extends AsyncTask<AUParser<AUMainMenuTab>, Void, List<AUMainMenuTab>> implements OnAUParsingFinalized {

    protected Realm realm;

    public AUMainMenuTabInitializeParserAsyncTask() {
        realm = Realm.getDefaultInstance();

    }

    @Override
    protected List<AUMainMenuTab> doInBackground(AUParser<AUMainMenuTab>... parsers) {
        return parsers[0].parseAllAU();
    }

    @Override
    protected void onPostExecute(final List<AUMainMenuTab> auMainMenuTabList) {
        super.onPostExecute(auMainMenuTabList);
        onAUParsingFinalized(auMainMenuTabList);
    }


    public void onAUParsingFinalized(final List<AUMainMenuTab> auMainMenuTabList) {
         realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                //realm.copyToRealm(auMainMenuTabList);
            }
        });
    }
}
