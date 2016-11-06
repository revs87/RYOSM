package com.ryosm.core.com.ryosm.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by revs on 04/03/2016.
 */
public class CoreBaseIntent extends Intent implements Parcelable {

    public CoreBaseIntent() {
        super();
        putExtraMandatoryProtection();
    }

    public CoreBaseIntent(Activity activity, Class<?> baseClass) {
        super(activity, baseClass);
        putExtraMandatoryProtection();
    }

    public CoreBaseIntent(Context context, Class<?> baseClass) {
        super(context, baseClass);
        putExtraMandatoryProtection();
    }

    protected CoreBaseIntent(Parcel in) {
        super(String.valueOf(in));
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CoreBaseIntent> CREATOR = new Creator<CoreBaseIntent>() {
        @Override
        public CoreBaseIntent createFromParcel(Parcel in) {
            return new CoreBaseIntent(in);
        }

        @Override
        public CoreBaseIntent[] newArray(int size) {
            return new CoreBaseIntent[size];
        }
    };

    private void putExtraMandatoryProtection() {
        if (CoreBaseActivity.isInBackground) {
            this.putExtra(CoreBaseActivity.SHOW_PROTECTION, true);
        }
    }

}
