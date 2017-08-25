package www.uni_weimar.de.au.service.impl;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import www.uni_weimar.de.au.models.AUFacultyHeader;
import www.uni_weimar.de.au.orm.AUBaseORM;
import www.uni_weimar.de.au.orm.AUFacultyORM;
import www.uni_weimar.de.au.parsers.exception.AUParseException;
import www.uni_weimar.de.au.parsers.impl.AUFacultyParser;
import www.uni_weimar.de.au.parsers.inter.AUParser;
import www.uni_weimar.de.au.service.inter.AUAbstractContentRequestService;
import www.uni_weimar.de.au.service.inter.AUContentChangeListener;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by ndovhuy on 10.07.2017.
 */

public class AUFacultyContentRequestService extends AUAbstractContentRequestService<AUFacultyHeader> {

    private AUFacultyContentRequestService(Realm realm, String url) {
        super(new AUFacultyORM(realm), AUFacultyParser.of(url));
    }

    private AUFacultyContentRequestService(AUBaseORM<AUFacultyHeader> auBaseORm, AUParser<AUFacultyHeader> auParser) {
        super(auBaseORm, auParser);
    }

    public static AUFacultyContentRequestService of(Realm realm, String url) {
        checkNotNull(realm);
        checkNotNull(url);
        return new AUFacultyContentRequestService(realm, url);
    }

    public static AUFacultyContentRequestService of(AUBaseORM<AUFacultyHeader> auBaseORm, AUParser<AUFacultyHeader> auParser) {
        checkNotNull(auBaseORm);
        checkNotNull(auParser);
        return new AUFacultyContentRequestService(auBaseORm, auParser);
    }


    public Observable<List<AUFacultyHeader>> requestContent(final String url,
                                                            final int escapeHeaderTag,
                                                            final AUFacultyHeader topLevelHeader) {
        return Observable.create((ObservableOnSubscribe<List<AUFacultyHeader>>) e -> {
            try {
                List<AUFacultyHeader> value = ((AUFacultyParser) auParser).parseAU(url, escapeHeaderTag, topLevelHeader);
                e.onNext(value);
                e.onComplete();
            } catch (AUParseException a) {
                e.onError(a);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(this::writeToCache);
    }


    public AUFacultyHeader update(AUFacultyHeader headerToUpdate) {
        checkNotNull(headerToUpdate);
        headerToUpdate = ((AUFacultyORM) auBaseORM).update(headerToUpdate);
        return headerToUpdate;
    }

    public List<AUFacultyHeader> readFromCache(String key, String val) {
        return auBaseORM.findAllBy(key, val);
    }

    @Override
    public List<AUFacultyHeader> readFromCache(List<AUFacultyHeader> objects) {
        return ((AUFacultyORM) auBaseORM).findAllBy("headerLevel", 1);
    }
}
