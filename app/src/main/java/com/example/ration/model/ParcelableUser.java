package com.example.ration.model;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ParcelableUser implements Parcelable {
    private final String name;
    private final int weight;
    private final int height;
    private final int age;

    protected ParcelableUser(Parcel in) {
        name = in.readString();
        weight = in.readInt();
        height = in.readInt();
        age = in.readInt();
    }

    public static final Creator<ParcelableUser> CREATOR = new Creator<>() {
        @Override
        public ParcelableUser createFromParcel(Parcel in) {
            return new ParcelableUser(in);
        }

        @Override
        public ParcelableUser[] newArray(int size) {
            return new ParcelableUser[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(weight);
        dest.writeInt(height);
        dest.writeInt(age);
    }
}
