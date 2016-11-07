package com.ryosm.core.com.ryosm.utils;

import android.app.Activity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class AnimatorClass {

	public static void startAnimation(Activity activity, int layoutId,
									  int animationId, final boolean isVisible) {
		final LinearLayout myLayout = (LinearLayout) activity
				.findViewById(layoutId);

		Animation animation = AnimationUtils.loadAnimation(activity,
				animationId);

		animation.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				if (isVisible)
					myLayout.setVisibility(View.VISIBLE);
				else
					myLayout.setVisibility(View.GONE);
			}
		});

		myLayout.clearAnimation();
		myLayout.startAnimation(animation);

	}
	
	public static void startButtonAnimation(final int translateX, final int translateY, final ImageButton imageButtonInitial,
			final ImageButton imageButtonFinal) {

        TranslateAnimation translate = new TranslateAnimation(0, translateX, 0,
                translateY);
        translate.setDuration(500);
        translate.setAnimationListener(new Animation.AnimationListener() {
			

            public void onAnimationEnd(Animation animation) {
               imageButtonFinal.setVisibility(View.VISIBLE);
               imageButtonInitial.setVisibility(View.GONE);
            }

            public void onAnimationRepeat(Animation animation) {

            }

            public void onAnimationStart(Animation animation) {
            	imageButtonFinal.setVisibility(View.GONE);
                imageButtonInitial.setVisibility(View.VISIBLE);

            }

        });
        imageButtonInitial.startAnimation(translate);
    }

}
