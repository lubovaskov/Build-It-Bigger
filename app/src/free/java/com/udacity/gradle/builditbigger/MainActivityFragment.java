package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import com.udacity.gradle.jokepresenter.JokeActivity;

public class MainActivityFragment extends Fragment implements JokeAsyncTask.IJokePresenter {

    private Context mContext;
    private Unbinder unbinder;

    @BindView(R.id.button_tell_joke)
    Button buttonJoke;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.adView)
    AdView mAdView;

    private InterstitialAd interstitialAd;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View fragmentView = inflater.inflate(R.layout.fragment_main, container, false);

        //initialize butterknife bindings
        unbinder = ButterKnife.bind(this, fragmentView);

        progressBar.setVisibility(View.GONE);

        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);

        return fragmentView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //release butterknife bindings
        unbinder.unbind();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //save a reference to the current activity
        mContext = context;

        //create interstitial ad
        interstitialAd = new InterstitialAd(mContext);
        interstitialAd.setAdUnitId(getResources().getString(R.string.inter_ad_unit_id));
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
                getJoke();
            }
        });
        requestNewInterstitial();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //clear the reference to the current activity
        mContext = null;
    }

    @OnClick(R.id.button_tell_joke)
    void onButtonJokeClick() {
        //show interstitial ad or joke if ad can not be loaded
        if (interstitialAd.isLoaded()) {
            interstitialAd.show();
        } else {
            getJoke();
        }
    }

    private void getJoke() {
        //show loading indicator
        progressBar.setVisibility(View.VISIBLE);
        //start joke loading task
        new JokeAsyncTask(this).execute();
    }

    public void displayJoke(String jokeText) {
        //hide loading indicator
        progressBar.setVisibility(View.GONE);
        //show joke activity
        Intent intent = new Intent(mContext, JokeActivity.class);
        intent.putExtra(JokeActivity.JOKE_TEXT_EXTRA_NAME, jokeText);
        startActivity(intent);
    }

    private void requestNewInterstitial() {
        //load new interstitial ad
        final AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        interstitialAd.loadAd(adRequest);
    }
}
