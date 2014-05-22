package com.android.godot;

import android.app.Activity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

import com.android.godot.joystick.DualJoystickView;
import com.android.godot.joystick.JoystickMovedListener;


public class GodotAnalogJoy extends Godot.SingletonBase {

	
	private Activity activity;
	
	boolean initialized;
	

	
	public void InitializeUIThread() {

		((Godot)activity).showjoy = true;
		((Godot)activity).ShowHideJoy();

		
    
		initialized = true;

		Log.d("godot", "AdMob: Initialized");
	}	
	

	

	
	public void Initialize() {
//		((Godot)activity).showjoy = true;

		activity.runOnUiThread(new Runnable() {
			public void run() {
				InitializeUIThread();
			}
		});
	}
	
	public int GetLeftPan() {
		int lpan = ((Godot)activity).leftpan;
//		Log.d(Integer.toString(lpan), "com.android.godot.leftpan");
		return lpan;
		
	}	
	public int GetRightPan() {
		int rpan = ((Godot)activity).rightpan;
//		Log.d(Integer.toString(rpan), "com.android.godot.leftpan");
		return rpan;
		
	}	
	
	public int GetLeftTilt() {
		int ltilt = ((Godot)activity).lefttilt;
//		Log.d(Integer.toString(lpan), "com.android.godot.leftpan");
		return ltilt;
		
	}	
	
	public int GetRightTilt() {
		int rtilt = ((Godot)activity).righttilt;
//		Log.d(Integer.toString(lpan), "com.android.godot.leftpan");
		return rtilt;
		
	}	
	
	public boolean LeftReleased() {
		boolean lrel = ((Godot)activity).leftreleased;
//		Log.d(Integer.toString(lpan), "com.android.godot.leftpan");
		return lrel;		
	}
	
	public boolean LeftReturned() {
		boolean lret = ((Godot)activity).leftreturned;
//		Log.d(Integer.toString(lpan), "com.android.godot.leftpan");
		return lret;		
	}
	
	public boolean RightReleased() {
		boolean rrel = ((Godot)activity).rightreleased;
//		Log.d(Integer.toString(lpan), "com.android.godot.leftpan");
		return rrel;		
	}
	
	public boolean RightReturned() {
		boolean rret = ((Godot)activity).rightreturned;
//		Log.d(Integer.toString(lpan), "com.android.godot.leftpan");
		return rret;		
	}
	
	public void ShowHideJoyUIThread() {
		((Godot)activity).ShowHideJoy();
		
	}
	
	public void ShowJoy() {
//		((Godot)activity).joy();

//		((Godot)activity).joystick.setVisibility(View.VISIBLE); 
		((Godot)activity).showjoy = true;
//		Godot aaaa = new Godot();
//		aaaa.ShowHideJoy();
//		((Godot)activity).joy();
		activity.runOnUiThread(new Runnable() {
			public void run() {
				ShowHideJoyUIThread();
//				ShowJoyUIThread();
			}
		});
	}
	
	
	public void HideJoy() {
		((Godot)activity).showjoy = false;
		
		activity.runOnUiThread(new Runnable() {
			public void run() {
				ShowHideJoyUIThread();
//				HideJoyUIThread();
			}
		});
		}
	



	static public Godot.SingletonBase initialize(Activity p_activity) {

		return new GodotAnalogJoy(p_activity);
	}

	public GodotAnalogJoy(Activity p_activity) {

		registerClass("AnalogJoy", new String[] {"ShowJoy", "HideJoy", "GetLeftPan", "GetRightPan", "GetLeftTilt", "GetRightTilt",
												 "LeftReleased", "LeftReturned", "RightReleased", "RightReturned"});
		
		activity=p_activity;
		initialized = false;
		

		activity.runOnUiThread(new Runnable() {
			public void run() {
				InitializeUIThread();
			}
		});	
	}	
}
