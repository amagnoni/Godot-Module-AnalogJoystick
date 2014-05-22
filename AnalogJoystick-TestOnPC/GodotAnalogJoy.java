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
	

	private FrameLayout layout;
	
	private DualJoystickView joystick;
	
	private FrameLayout.LayoutParams joyParams;
	
	private int leftpan;
	private int lefttilt;

	
	public void InitializeUIThread() {

			
		joystick = new DualJoystickView((Godot)activity);
		
		layout = ((Godot)activity).layout;
		
		joyParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, 300,Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM);

		layout.addView(joystick, joyParams);
		
//		layout.invalidate();
		
//		joystick.setVisibility(View.VISIBLE); 
		
		
	        
	     joystick.setOnJostickMovedListener(_listenerLeft, _listenerRight);
		
		
    
		initialized = true;

		Log.d("godot", "AdMob: Initialized");
	}	
	
	   private JoystickMovedListener _listenerLeft = new JoystickMovedListener() {

			@Override
			public void OnMoved(int pan, int tilt) {
				leftpan = pan;
				lefttilt = tilt;
				Log.d(Integer.toString(pan), "com.android.godot.panpanpan");

			}

			@Override
			public void OnReleased() {
			}
			
			public void OnReturnedToCenter() {
			};
		}; 

	    private JoystickMovedListener _listenerRight = new JoystickMovedListener() {

			@Override
			public void OnMoved(int pan, int tilt) {
			}

			@Override
			public void OnReleased() {
			}
			
			public void OnReturnedToCenter() {
			};
		}; 

	

	
	public void Initialize() {

		activity.runOnUiThread(new Runnable() {
			public void run() {
				InitializeUIThread();
			}
		});
	}
	
	public int GetLeftPan() {
		Log.d(Integer.toString(leftpan), "com.android.godot.leftpan");
		return leftpan;
		
	}	
	
	public void ShowJoyUIThread() {
		layout = ((Godot)activity).layout;
		
		joyParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, 300,Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM);

		layout.addView(joystick, joyParams);
		
		layout.invalidate();
		
		joystick.setVisibility(View.VISIBLE);    
		
	}
	
	public void ShowJoy() {
		activity.runOnUiThread(new Runnable() {
			public void run() {
				ShowJoyUIThread();
			}
		});
	}
	
	
	
	public void HideJoy() {
		layout.removeView(joystick);		

	}


	static public Godot.SingletonBase initialize(Activity p_activity) {

		return new GodotAnalogJoy(p_activity);
	}

	public GodotAnalogJoy(Activity p_activity) {

		registerClass("AnalogJoy", new String[] {"GetLeftPan", "ShowJoy", "HideJoy"});
		
		activity=p_activity;
		initialized = false;
		

		activity.runOnUiThread(new Runnable() {
			public void run() {
				InitializeUIThread();
			}
		});	
	}	
}
