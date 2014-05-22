package com.android.godot;

import android.app.Activity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

import com.android.godot.joystick.DualJoystickView;
import com.android.godot.joystick.JoystickMovedListener;


public class GodotAnalogJoy1 extends Godot.SingletonBase {

	
	private Activity activity;
	
	boolean initialized;
	
	Godot aaaa;

	
	public void InitializeUIThread() {

		aaaa = new Godot();

    
		initialized = true;

		Log.d("godot", "AdMob: Initialized");
	}	
	

	

	
	public void Initialize() {

		activity.runOnUiThread(new Runnable() {
			public void run() {
				InitializeUIThread();
			}
		});
	}
	
	public int GetLeftPan() {
		int lpan = ((Godot)activity).leftpan;
		Log.d(Integer.toString(lpan), "com.android.godot.leftpan");
		return lpan;
		
	}	
	
	public void ShowJoy() {
//		((Godot)activity).joy();

//		((Godot)activity).joystick.setVisibility(View.VISIBLE); 
		aaaa.showjoy = true;
//		Godot aaaa = new Godot();
		aaaa.ShowHideJoy();
//		((Godot)activity).joy();
	}
	
	public void HideJoy() {
		((Godot)activity).showjoy = false;
//		Godot aaaa = new Godot();
		aaaa.ShowHideJoy();
//		((Godot)activity).joy();

		}


	static public Godot.SingletonBase initialize(Activity p_activity) {

		return new GodotAnalogJoy1(p_activity);
	}

	public GodotAnalogJoy1(Activity p_activity) {

		registerClass("AnalogJoy", new String[] {"GetLeftPan", "HideJoy", "ShowJoy"});
		
		activity=p_activity;
		initialized = false;
		

		activity.runOnUiThread(new Runnable() {
			public void run() {
				InitializeUIThread();
			}
		});	
	}	
}
