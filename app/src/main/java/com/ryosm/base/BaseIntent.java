package com.ryosm.base;

import android.os.Parcel;
import android.os.Parcelable;

import com.ryosm.core.com.ryosm.base.CoreBaseIntent;

/**
 * Created by revs on 04/03/2016.
 */
public class BaseIntent extends CoreBaseIntent implements Parcelable {

    public BaseIntent(Parcel in) {
        super(in);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BaseIntent> CREATOR = new Creator<BaseIntent>() {
        @Override
        public BaseIntent createFromParcel(Parcel in) {
            return new BaseIntent(in);
        }

        @Override
        public BaseIntent[] newArray(int size) {
            return new BaseIntent[size];
        }
    };
}
