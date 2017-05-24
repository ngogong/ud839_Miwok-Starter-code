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
public class NumbersFragment extends Fragment {

    private MediaPlayer mMediaPlayer;
    private AudioManager mAm;
    private AudioManager.OnAudioFocusChangeListener mAfChangeListener;


    public NumbersFragment() {
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

        // Create a list of words
        words.add(new Word("one", "lutti", R.raw.number_one, R.drawable.number_one));
        words.add(new Word("two", "otiiko", R.raw.number_two, R.drawable.number_two));
        words.add(new Word("three", "tolookosu", R.raw.number_three, R.drawable.number_three));
        words.add(new Word("four", "oyyisa", R.raw.number_four, R.drawable.number_four));
        words.add(new Word("five", "massokka", R.raw.number_five, R.drawable.number_five));
        words.add(new Word("six", "temmokka", R.raw.number_six, R.drawable.number_six));
        words.add(new Word("seven", "kenekaku", R.raw.number_seven, R.drawable.number_seven));
        words.add(new Word("eight", "kawinta", R.raw.number_eight, R.drawable.number_eight));
        words.add(new Word("nine", "wo’e", R.raw.number_nine, R.drawable.number_nine));
        words.add(new Word("ten", "na’aacha", R.raw.number_ten, R.drawable.number_ten));

        // Create an {@link WordAdapter}, whose data source is a list of {@link Word}s. The
        // adapter knows how to create list items for each item in the list.

        //@ir-notes "this" is the context, however, fragment does not equal context
        //WordAdapter adapter = new WordAdapter(this, words, R.color.category_numbers);
        WordAdapter adapter = new WordAdapter(getActivity(), words, R.color.category_numbers);

        // Find the {@link ListView} object in the view hierarchy of the {@link Activity}.
        // There should be a {@link ListView} with the view ID called list, which is declared in the
        // word_list.xml file.

        // @ir-notes fragment does not have findViewById, use the inflated viewgroup(the rootView)
        //ListView listView = (ListView) findViewById(R.id.list);
        ListView listView = (ListView) rootView.findViewById(R.id.list);


        //listView.setBackgroundResource(R.color.category_numbers);
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

                            //@ir-notes get the correct context
                            //Toast.makeText(NumbersActivity.this, "i am done", Toast.LENGTH_LONG).show();
                            Toast.makeText(getActivity(), "i am done", Toast.LENGTH_LONG).show();
                        }
                    });

                }
                //@ir-notes get the correct context
                //Toast.makeText(NumbersActivity.this, "hello i=" + i + ", l=" +l, Toast.LENGTH_SHORT).show();
                Toast.makeText(getActivity(), "hello i=" + i + ", l=" +l, Toast.LENGTH_SHORT).show();



            }
        });







        return rootView;





        //TextView textView = new TextView(getActivity());
        //textView.setText(R.string.hello_blank_fragment);
        //return textView;
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
