package www.uni_weimar.de.au.view.components;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

import static android.provider.MediaStore.Images.Thumbnails.IMAGE_ID;

/**
 * Created by user on 28.08.17.
 */

public class AUSpinnerFragment extends DialogFragment {

    private int imageID;
    private Context context;

    public static AUSpinnerFragment newInstance(@NonNull Context context, int imageID) {
        Bundle args = new Bundle();
        AUSpinnerFragment fragment = new AUSpinnerFragment();
        fragment.imageID = imageID;
        fragment.context = context;
        fragment.setArguments(args);
        args.putInt(IMAGE_ID, imageID);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AUSpinner(context, imageID);
    }

}
