package www.uni_weimar.de.au.view.components;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import www.uni_weimar.de.au.R;

/**
 * Created by user on 28.08.17.
 */

public class AUSpinner {

    private ImageView mSpinnerImage;

    public AUSpinner(ImageView spinnerView) {
        this.mSpinnerImage = spinnerView;
    }

    public void stopSpinner() {
        new Handler().postDelayed(()->{
            mSpinnerImage.setVisibility(View.GONE);
        },10);
    }

    public void startSpinner() {
        mSpinnerImage.setVisibility(View.VISIBLE);
        RotateAnimation anim = new RotateAnimation(0.0f, 360.0f , Animation.RELATIVE_TO_SELF, .5f, Animation.RELATIVE_TO_SELF, .5f);
        anim.setDuration(500);
        anim.setInterpolator(new LinearInterpolator());
        anim.setRepeatMode(Animation.INFINITE);
        mSpinnerImage.setAnimation(anim);
        mSpinnerImage.startAnimation(anim);
    }
}
