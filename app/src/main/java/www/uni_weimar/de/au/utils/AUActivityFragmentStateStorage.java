package www.uni_weimar.de.au.utils;

import android.support.v4.app.Fragment;

import java.util.Stack;

import www.uni_weimar.de.au.view.fragments.AUCafeteriaMenuProgramFragment;

/**
 * Created by user on 30.08.17.
 */

public class AUActivityFragmentStateStorage {

    private static Stack<Fragment> fragmentStack = new Stack<>();

    public static void store(Fragment fragment) {
        fragmentStack.push(fragment);
    }

    public static Fragment active(){
        return fragmentStack.isEmpty()? null: fragmentStack.pop();
    }
}
