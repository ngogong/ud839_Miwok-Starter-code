package com.example.android.miwok;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ColorsFragment extends Fragment {

    private MediaPlayer mMediaPlayer;
    private AudioManager mAm;
    private AudioManager.OnAudioFocusChangeListener mAfChangeListener;


    public ColorsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.word_list, container, false);

        /** TODO: Insert all the code from the NumberActivity’s onCreate() method after the setContentView method call */

        //@ir-notes fragment does not have getSystemService, activity have this function code
        //mAm = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        mAm = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
        mAfChangeListener = new AudioManager.OnAudioFocusChangeListener() {
            public void onAudioFocusChange(int focusChange) {
                if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                    // Permanent loss of audio focus
                    // Pause playback immediately
                    releaseMediaPlayer();

                }
                else if (   (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT)
                        ||(focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) ) {
                    // Pause playback
                    // Lower the volume, keep playing
                    if (mMediaPlayer != null){
                        mMediaPlayer.pause();
                        mMediaPlayer.seekTo(0);
                    }

                } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                    // Your app has been granted audio focus again
                    // Raise volume to normal, restart playback if necessary
                    mMediaPlayer.start();
                }
            }
        };


        final ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word("red", "weṭeṭṭi", R.raw.color_red, R.drawable.color_red));
        words.add(new Word("mustard yellow", "chiwiiṭә", R.raw.color_mustard_yellow, R.drawable.color_mustard_yellow));
        words.add(new Word("dusty yellow", "ṭopiisә", R.raw.color_dusty_yellow, R.drawable.color_dusty_yellow));
        words.add(new Word("green", "chokokki", R.raw.color_green, R.drawable.color_green));
        words.add(new Word("brown", "ṭakaakki", R.raw.color_brown, R.drawable.color_brown));
        words.add(new Word("gray", "ṭopoppi", R.raw.color_gray, R.drawable.color_gray));
        words.add(new Word("black", "kululli", R.raw.color_black, R.drawable.color_black));
        words.add(new Word("white", "kelelli", R.raw.color_white, R.drawable.color_white));

        // Create an {@link WordAdapter}, whose data source is a list of {@link Word}s. The
        // adapter knows how to create list items for each item in the list.
        WordAdapter adapter = new WordAdapter(getActivity(), words, R.color.category_colors);

        // Find the {@link ListView} object in the view hierarchy of the {@link Activity}.
        // There should be a {@link ListView} with the view ID called list, which is declared in the
        // word_list.xml layout file.
        ListView listView = (ListView) rootView.findViewById(R.id.list);
        //listView.setBackgroundResource(R.color.category_colors);

        // Make the {@link ListView} use the {@link WordAdapter} we created above, so that the
        // {@link ListView} will display list items for each {@link Word} in the list.
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if ( (mAm != null) && (mAm.requestAudioFocus(mAfChangeListener,
                        // Use the music stream.
                        AudioManager.STREAM_MUSIC,
                        // Request permanent focus.
                        AudioManager.AUDIOFOCUS_GAIN )
                        == AudioManager.AUDIOFOCUS_GAIN) ) {
                    mMediaPlayer = MediaPlayer.create(view.getContext(), words.get(i).getmAudioResourceId());
                    mMediaPlayer.start();

                    mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            releaseMediaPlayer();
                            Toast.makeText(getActivity(), "i am done", Toast.LENGTH_LONG).show();
                        }
                    });


                }
            }});

        return rootView;

    }


    @Override
    public void onStop() {
        super.onStop();
        // When the activity is stopped, release the media player resources because we won't
        // be playing any more sounds.
        releaseMediaPlayer();
    }

    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;
        }
        if ((mAm != null) && (mAfChangeListener != null) ){
            mAm.abandonAudioFocus(mAfChangeListener);
        }
    }






}
