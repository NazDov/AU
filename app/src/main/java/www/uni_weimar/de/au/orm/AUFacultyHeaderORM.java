package www.uni_weimar.de.au.orm;

import java.util.List;

import io.realm.Realm;
import www.uni_weimar.de.au.models.AUFacultyHeader;

/**
 * Created by ndovhuy on 10.07.2017.
 */
public class AUFacultyHeaderORM implements AUBaseORM<AUFacultyHeader>{

    private Realm realm;

    public AUFacultyHeaderORM(Realm realm) {
        this.realm = realm;
    }

    @Override
    public AUFacultyHeader add(AUFacultyHeader item) {
        return null;
    }

    @Override
    public List<AUFacultyHeader> addAll(List<AUFacultyHeader> items) {
        return null;
    }

    @Override
    public boolean deleteAll(Class<AUFacultyHeader> auFacultyHeaderClass) {
        return false;
    }

    @Override
    public void delete(String key, String name) {

    }

    @Override
    public List<AUFacultyHeader> findAll() {
        return null;
    }

    @Override
    public AUFacultyHeader findBy(String key, String name) {
        return null;
    }

    @Override
    public List<AUFacultyHeader> findAllBy(String key, String name) {
        return null;
    }
}
