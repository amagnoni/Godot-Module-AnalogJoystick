Analog Joysticks
================

This is a modified work of:
https://code.google.com/p/mobile-anarchy-widgets/

for use with Godot Engine

How to use
----------
Recompile the Java Android project (https://github.com/okamstudio/godot/wiki/compiling_android)

Configuring your game
---------------------

To enable the module on Android, add the path to the module to the "modules" property on the [android] section of your engine.cfg file. It should look like this:

	[android]
	modules="com/android/godot/GodotAnalogJoy"

Note this is a comma separated list inside a string, you can have other modules there.

The singleton "AnalogJoy" will be available on gdscript.

API Reference
-------------

The following methods are available:

        void ShowJoy();
        void HideJoy();
        int GetLeftPan();
        int GetLeftTilt();
        int GetRightPan();
        int GetRightTilt();
        bool LeftReleased();
        bool LeftReturned();
        bool RightReleased();
        bool RightReturned();


Issues
------

The joystick is initialized on godot opening but doesnt show (only after a resume).
So we need to hide from a GDScript!

After we only need to show or hide when needed!



