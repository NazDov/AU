package www.uni_weimar.de.au.view.components;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
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

public class AUSpinner extends Dialog {

    ImageView mSpinnerImage;

    public AUSpinner(@NonNull Context context, int imageId){
        super(context, R.style.TransparentDialog);
        WindowManager.LayoutParams attr = getWindow().getAttributes();
        attr.gravity = Gravity.CENTER_HORIZONTAL;
        getWindow().setAttributes(attr);
        setTitle(null);
        setCancelable(false);
        setOnCancelListener(null);
        mSpinnerImage = new ImageView(context);
        mSpinnerImage.setImageResource(imageId);
        LinearLayout view = new LinearLayout(context);
        view.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.addView(mSpinnerImage, params);
        setContentView(view, params);

    }

    @Override
    public void show() {
        super.show();
        RotateAnimation anim = new RotateAnimation(0.0f, 360.0f);
        anim.setDuration(3000);
        anim.setInterpolator(new LinearInterpolator());
        anim.setRepeatMode(Animation.INFINITE);
        mSpinnerImage.setAnimation(anim);
        mSpinnerImage.startAnimation(anim);
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }
}
