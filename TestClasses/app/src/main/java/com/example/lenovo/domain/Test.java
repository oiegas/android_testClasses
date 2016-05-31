package com.example.lenovo.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.Set;

/**
 * Created by AlexandruOi on 4/26/2016.
 */
public class Test implements Parcelable{

    private int testId;
    private String name;
    private long startDate;
    public Set<Question> questions;
    private Clas classForTest;
    private Set<Grade> grades;
    private boolean available;
    private boolean opened;
    private User userCreator;
    private Set<StudentAnswer> studentAnswers;

    public Test() {

    }
    protected Test(Parcel in) {
        testId = in.readInt();
        name = in.readString();
        startDate = in.readLong();
        available = in.readByte() != 0;
        opened = in.readByte() != 0;
        userCreator = in.readParcelable(User.class.getClassLoader());
    }

    public static User create() {return new User();}

    public static final Creator<Test> CREATOR = new Creator<Test>() {
        @Override
        public Test createFromParcel(Parcel in) {
            return new Test(in);
        }

        @Override
        public Test[] newArray(int size) {
            return new Test[size];
        }
    };

    public int getTestId() {
        return testId;
    }

    public void setTestId(int testId) {
        this.testId = testId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return new Date(startDate);
    }
    public long getStartDateLong() {
        return startDate;
    }
    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }
    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public boolean isOpened() {
        return opened;
    }

    public void setOpened(boolean opened) {
        this.opened = opened;
    }

    public Clas getClassForTest() {
        return classForTest;
    }

    public void setClassForTest(Clas classForTest) {
        this.classForTest = classForTest;
    }

    public Set<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(Set<Question> questions) {
        this.questions = questions;
    }

    public Set<Grade> getGrades() {
        return grades;
    }

    public void setGrades(Set<Grade> grades) {
        this.grades = grades;
    }

    public User getUserCreator() {
        return userCreator;
    }

    public void setUserCreator(User userCreator) {
        this.userCreator = userCreator;
    }

    public Set<StudentAnswer> getStudentAnswers() {
        return studentAnswers;
    }

    public void setStudentAnswers(Set<StudentAnswer> studentAnswers) {
        this.studentAnswers = studentAnswers;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(testId);
        dest.writeString(name);
        dest.writeLong(startDate);
        dest.writeByte((byte) (available ? 1 : 0));
        dest.writeByte((byte) (opened ? 1 : 0));
        dest.writeParcelable(userCreator, flags);
    }
}
