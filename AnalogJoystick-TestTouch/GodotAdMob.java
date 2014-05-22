/*
 * Activity:
 * 	<activity android:name="com.google.ads.AdActivity"
 *   	android:configChanges="keyboard|keyboardHidden|orientation|screenLayout|uiMode|screenSize|smallestScreenSize">
 *  </activity>
 *  
 *  Target API level at least 13
 */

package com.android.godot;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.google.ads.Ad;
import com.google.ads.AdListener;
import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;
import com.google.ads.InterstitialAd;
import com.google.ads.AdRequest.ErrorCode;

public class GodotAdMob extends Godot.SingletonBase implements AdListener {

	InterstitialAd interstitial;
	AdView adView;
	
	private Activity activity;
	
	boolean initialized;
	
	boolean adReceived;
	boolean screenDismissed;
	boolean failedToReceiveAd;
	boolean applicationLeaved;
	boolean presentScreen;
	
	int layoutRule1;
	int layoutRule2;
	
	@Override
	public void onReceiveAd(Ad ad) {
	  if (ad == interstitial) {
		Log.d("godot", "AdMob: onReceiveAd");
	    interstitial.show();
	    adReceived = true;
	  }
	}

	@Override
	public void onDismissScreen(Ad arg0) {
		Log.d("godot", "AdMob: onDismissScreen");	
		screenDismissed = true;
	}

	@Override
	public void onFailedToReceiveAd(Ad arg0, ErrorCode arg1) {
		Log.w("godot", "AdMob: onFailedToReceiveAd-> " + arg1.toString());
		failedToReceiveAd = true;
	}

	@Override
	public void onLeaveApplication(Ad arg0) {
		Log.d("godot", "AdMob: onLeaveApplication");
		applicationLeaved = true;
	}

	@Override
	public void onPresentScreen(Ad arg0) {
		Log.d("godot", "AdMob: onPresentScreen");
		presentScreen = true;
	} 	
	
	public void InitializeUIThread(String p_key) {
		
		// Create the interstitial
		interstitial = new InterstitialAd(activity, p_key);
		
		// Create banner
        	adView = new AdView(activity, AdSize.BANNER, p_key);
//		adView = new AdView(activity, AdSize.SMART_BANNER, p_key);
	
		FrameLayout layout = ((Godot)activity).layout;
		FrameLayout.LayoutParams adViewLayoutParams =
                new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,
                                             FrameLayout.LayoutParams.WRAP_CONTENT,
                                             Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM);

		layout.addView(adView, layoutParams);
		layout.invalidate();
	
		adView.setVisibility(View.VISIBLE);        
		
		initialized = true;
		
	    	adReceived = false;
	    	screenDismissed = false;
	    	failedToReceiveAd = false;
	    	applicationLeaved = false;
	    	presentScreen = false;        

		Log.d("godot", "AdMob: Initialized");
	}	
	
	public void ShowInterstitialUIThread()
	{
		if (initialized)
		{
			// Create ad request
			AdRequest adRequest = new AdRequest();

			// Begin loading your interstitial
			interstitial.loadAd(adRequest);

			// Set Ad Listener to use the callbacks below
			interstitial.setAdListener((AdListener) this);
			
		    	adReceived = false;
		    	screenDismissed = false;
		    	failedToReceiveAd = false;
		    	applicationLeaved = false;
		    	presentScreen = false; 	        
			
			Log.d("godot", "AdMob: Show Interstitial");
		}
		else
		{
			Log.e("godot", "AdMob: Calling \"ShowInterstitial()\" but AdMob not initilized");
		}
	} 	

	public void ShowBannerUIThread()
	{
		if (initialized)
		{
		    AdRequest request = new AdRequest();
		    adView.loadAd(request);
		    adView.setVisibility(View.VISIBLE);
	        
	            Log.d("godot", "AdMob: Show Banner");
		}
		else
		{
			Log.e("godot", "AdMob: Calling \"ShowBanner()\" but AdMob not initilized");
		}
	}
	
	public void HideBannerUIThread()
	{
		if (initialized)
		{
		    adView.setVisibility(View.GONE);
    		    Log.d("godot", "AdMob: Hide Banner");
		}	
	}
	
	public void SetBannerBottomLeft()
	{
		if (!initialized) return;

		if (layoutRule1 != RelativeLayout.ALIGN_PARENT_BOTTOM || 
			layoutRule2 != RelativeLayout.ALIGN_PARENT_LEFT)
		{
			activity.runOnUiThread(new Runnable() {
				public void run() {
					layoutRule1 = RelativeLayout.ALIGN_PARENT_BOTTOM; 
					layoutRule2 = RelativeLayout.ALIGN_PARENT_LEFT;
					
					Log.d("godot", "AdMob: Moving Banner to bottom left");
					
					if (adView != null)
					{
						SetBannerView();
					}			
				}
			});
		}
	}
	
	public void SetBannerTopLeft()
	{
		if (!initialized) return;

		if (layoutRule1 != RelativeLayout.ALIGN_PARENT_TOP || 
			layoutRule2 != RelativeLayout.ALIGN_PARENT_LEFT)
		{
			activity.runOnUiThread(new Runnable() {
				public void run() {
					layoutRule1 = RelativeLayout.ALIGN_PARENT_TOP; 
					layoutRule2 = RelativeLayout.ALIGN_PARENT_LEFT;
					
					Log.d("godot", "AdMob: Moving Banner to top left");
					
					if (adView != null)
					{
						SetBannerView();
					}
				}
			});
		}
	}
	
	void SetBannerView()
	{
		FrameLayout layout = ((Godot)activity).layout;
		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
		
		layoutParams.addRule(layoutRule1);
		layoutParams.addRule(layoutRule2);
		
		layout.removeView(adView);
		layout.addView(adView, layoutParams);		
	}
	
	public void Initialize(final String p_key) {

		activity.runOnUiThread(new Runnable() {
			public void run() {
				InitializeUIThread(p_key);
			}
		});
	}
	
	public void ShowInterstitial() {
		activity.runOnUiThread(new Runnable() {
			public void run() {
				ShowInterstitialUIThread();
			}
		});
	}	
	
	public void ShowBanner() {
		activity.runOnUiThread(new Runnable() {
			public void run() {
				ShowBannerUIThread();
			}
		});
	}
	
	public void HideBanner() {
		activity.runOnUiThread(new Runnable() {
			public void run() {
				HideBannerUIThread();
			}
		});
	}	
	
	public boolean HasReceiveAd() {
		boolean ret = adReceived;
		adReceived = false;
		return ret;
	}

	public boolean HasDismissScreen() {
		boolean ret = screenDismissed;
		screenDismissed = false;
		return ret;		
	}

	public boolean HasFailedToReceive() {
		boolean ret = failedToReceiveAd;
		failedToReceiveAd = false;
		return ret;			
	}

	public boolean HasLeaveApplication() {
		boolean ret = applicationLeaved;
		applicationLeaved = false;
		return ret;
	}

	public boolean HasPresentScreen() {
		boolean ret = presentScreen;
		presentScreen = false;
		return ret;
	} 	

	static public Godot.SingletonBase initialize(Activity p_activity) {

		return new GodotAdMob(p_activity);
	}

	public GodotAdMob(Activity p_activity) {

		registerClass("AdMob", new String[] {"ShowBanner", "HideBanner",
				"SetBannerBottomLeft", "SetBannerTopLeft", "ShowInterstitial", "HasReceiveAd",
				"HasDismissScreen", "HasFailedToReceive","HasLeaveApplication","HasPresentScreen"});
		
		activity=p_activity;
		initialized = false;
		
		layoutRule1 = RelativeLayout.ALIGN_PARENT_BOTTOM;
		layoutRule2 = RelativeLayout.ALIGN_PARENT_LEFT;	

		activity.runOnUiThread(new Runnable() {
			public void run() {
				String key = GodotLib.getGlobal("admob/api_key");
				InitializeUIThread(key);
			}
		});	
	}	
}
