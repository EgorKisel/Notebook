package com.geekbrains.notebook;

import android.os.Parcel;
import android.os.Parcelable;

public class Notebook implements Parcelable {

    private int index;

    public Notebook(){};

    public Notebook(int index) {
        this.index = index;
    }

    protected Notebook(Parcel in) {
        index = in.readInt();
    }

    public static final Creator<Notebook> CREATOR = new Creator<Notebook>() {
        @Override
        public Notebook createFromParcel(Parcel in) {
            return new Notebook(in);
        }

        @Override
        public Notebook[] newArray(int size) {
            return new Notebook[size];
        }
    };

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(index);
    }
}
