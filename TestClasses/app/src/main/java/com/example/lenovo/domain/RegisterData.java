package com.example.lenovo.domain;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.lenovo.testclasses.DateFormats;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class RegisterData implements Parcelable {

    @SerializedName("Email")
    private String email;
    @SerializedName("Password")
    private String password;
    @SerializedName("ConfirmPassword")
    private String confirmPassword;
    @SerializedName("FirstName")
    private String firstName;
    @SerializedName("LastName")
    private String lastName;
    @SerializedName("Street")
    private String street;
    @SerializedName("CityId")
    private long cityId;
    @SerializedName("Mobile")
    private String mobile;
    //We keep the birthdate as a date to (but not serialized it, hence transient)
    private transient Date birthdayDate;
    //We just serialize the string representation
    @SerializedName("Birthday")
    private String birthday;
    @SerializedName("Country")
    private int countryId = 10;
    @SerializedName("Nationality")
    private int nationality;
    @SerializedName("ZipCodeId")
    private long zipCodeId;
    @SerializedName("IdentityNumber")
    private String identityNumber;

    private RegisterData() {
    }

    public static RegisterData create() {
        return new RegisterData();
    }

    public RegisterData email(String email) {
        this.email = email;
        return this;
    }

    public RegisterData password(String password) {
        this.password = password;
        return this;
    }

    public RegisterData confirmPassword(String password) {
        this.confirmPassword = password;
        return this;
    }

    public RegisterData firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public RegisterData lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public RegisterData street(String street) {
        this.street = street;
        return this;
    }

    public RegisterData cityId(long cityId) {
        this.cityId = cityId;
        return this;
    }

    public RegisterData mobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    public RegisterData birthday(Date date) {
        this.birthdayDate = date;
        this.birthday = DateFormats.justDate().format(birthdayDate);
        return this;
    }

    public RegisterData nationality(int nationality) {
        this.nationality = nationality;
        return this;
    }

    public RegisterData zipCodeId(long zipCodeId) {
        this.zipCodeId = zipCodeId;
        return this;
    }

    public RegisterData identityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
        return this;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.email);
        dest.writeString(this.password);
        dest.writeString(this.confirmPassword);
        dest.writeString(this.firstName);
        dest.writeString(this.lastName);
        dest.writeString(this.street);
        dest.writeLong(this.cityId);
        dest.writeString(this.mobile);
        dest.writeLong(birthdayDate != null ? birthdayDate.getTime() : -1);
        dest.writeInt(this.countryId);
        dest.writeInt(this.nationality);
        dest.writeLong(this.zipCodeId);
        dest.writeString(this.identityNumber);
    }

    protected RegisterData(Parcel in) {
        this.email = in.readString();
        this.password = in.readString();
        this.confirmPassword = in.readString();
        this.firstName = in.readString();
        this.lastName = in.readString();
        this.street = in.readString();
        this.cityId = in.readLong();
        this.mobile = in.readString();
        long tmpBirthday = in.readLong();
        this.birthdayDate = tmpBirthday == -1 ? null : new Date(tmpBirthday);
        this.birthday = DateFormats.justDate().format(birthdayDate);
        this.countryId = in.readInt();
        this.nationality = in.readInt();
        this.zipCodeId = in.readLong();
        this.identityNumber = in.readString();
    }

    public static final Creator<RegisterData> CREATOR = new Creator<RegisterData>() {
        public RegisterData createFromParcel(Parcel source) {
            return new RegisterData(source);
        }

        public RegisterData[] newArray(int size) {
            return new RegisterData[size];
        }
    };
}
