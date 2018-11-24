package com.smartass.missionsanta.android;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.facebook.CallbackManager;
import com.facebook.share.widget.ShareDialog;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.smartass.missionsanta.AdsController;
import com.smartass.missionsanta.SantaGame;

public class AndroidLauncher extends AndroidApplication implements AdsController { //extends AndroidApplication  {
    CallbackManager callbackManager;
    ShareDialog shareDialog;

    private static final String BANNER_AD_UNIT_ID = "ca-app-pub-5519384153835422/2811367393";

    AdView bannerAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.useImmersiveMode = true;

        //GameFragment fragment = new GameFragment();

        //initialize(new SantaGame(), config);
        View gameView = initializeForView(new SantaGame(this), config);
        setupAds();

        // Define the layout
        RelativeLayout layout = new RelativeLayout(this);
        layout.addView(gameView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        layout.addView(bannerAd, params);

        setContentView(layout);


//        FacebookSdk.sdkInitialize(getApplicationContext());
//
//        callbackManager = CallbackManager.Factory.create();
//        shareDialog = new ShareDialog(this);
//        // this part is optional
//        shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
//            @Override
//            public void onSuccess(Sharer.Result result) {
//
//            }
//
//            @Override
//            public void onCancel() {
//
//            }
//
//            @Override
//            public void onError(FacebookException error) {
//
//            }
//        });
//
//        if (ShareDialog.canShow(ShareLinkContent.class)) {
//            ShareLinkContent linkContent = new ShareLinkContent.Builder()
//                    .setContentTitle("Hello Facebook")
//                    .setContentDescription(
//                            "The 'Hello Facebook' sample  showcases simple Facebook integration")
//                    .setContentUrl(Uri.parse("http://developers.facebook.com/android"))
//                    .build();
//
//            shareDialog.show(linkContent);
//        }


    }

    public void setupAds() {
        bannerAd = new AdView(this);
        bannerAd.setVisibility(View.INVISIBLE);
        bannerAd.setBackgroundColor(0xff000000); // black
        bannerAd.setAdUnitId(BANNER_AD_UNIT_ID);
        bannerAd.setAdSize(AdSize.SMART_BANNER);
    }

    @Override
    public void showBannerAd() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                bannerAd.setVisibility(View.VISIBLE);
                AdRequest.Builder builder = new AdRequest.Builder();
                AdRequest ad = builder.build();
                bannerAd.loadAd(ad);
            }
        });

    }

    @Override
    public void hideBannerAd() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                bannerAd.setVisibility(View.INVISIBLE);
            }
        });

    }


//    @Override
//    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        callbackManager.onActivityResult(requestCode, resultCode, data);
//    }


}
