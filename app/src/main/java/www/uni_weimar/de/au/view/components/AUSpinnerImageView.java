package www.uni_weimar.de.au.view.components;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

/**
 * Created by user on 29.08.17.
 */

public class AUSpinnerImageView extends ImageView{

    public AUSpinnerImageView(Context context) {
        super(context);
        RotateAnimation anim = new RotateAnimation(0.0f, 360.0f);
        anim.setDuration(3000);
        anim.setInterpolator(new LinearInterpolator());
        anim.setRepeatMode(Animation.INFINITE);
        setAnimation(anim);
        startAnimation(anim);
    }

    public void stop(){
        this.setVisibility(GONE);
    }

    public void show() {
        this.setVisibility(VISIBLE);
    }
}
