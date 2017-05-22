package com.example.android.miwok;

/**
 * Created by ismile on 5/19/2017.
 */

public class Word {

    private String mDefaultTranslation;
    private String mMiwokTranslation;
    private int mImageResourceId;
    private int mAudioFileId;
    private boolean mImageAttach;

    public Word(String defaultTranslation, String miwokTranslation) {
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mImageAttach=false;
    }

    public Word(String defaultTranslation, String miwokTranslation, int maudiofileId, int mimagersourceId) {
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mImageResourceId = mimagersourceId;
        mImageAttach=true;
        mAudioFileId=maudiofileId;

    }

    public Word(String defaultTranslation, String miwokTranslation,  int maudiofileId) {
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mImageAttach=false;
        mAudioFileId=maudiofileId;
    }



    public String getDefaultTranslation() {
        return mDefaultTranslation;
    }

    public String getMiwokTranslation() {
        return mMiwokTranslation;
    }

    public int getImageResourceId() {
        return mImageResourceId;
    }

    public boolean getImageAttach() {
        return mImageAttach;
    }
    public int getmAudioResourceId() {
        return mAudioFileId;
    }


}
