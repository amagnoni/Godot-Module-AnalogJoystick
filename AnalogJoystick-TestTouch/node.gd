
extends Node2D

var time = 0

var check = false


func _ready():
	set_fixed_process(true)
#	set_process_input(true)
	AnalogJoy.ShowJoy()
#	AnalogJoy.HideJoy()
#	AnalogJoy.ShowJoy()
#	lrel = AnalogJoy.LeftReleased()
	



func _fixed_process(delta):
#func _input(ev):
	time += delta
	get_node("Label").set_text("X = " + str(AnalogJoy.GetLeftPan()))
	get_node("Label 2").set_text("Y = " + str(AnalogJoy.GetLeftTilt()))
	get_node("Label 3").set_text("Released = " + str(AnalogJoy.LeftReleased()))
	get_node("Label 4").set_text("Returned = " + str(AnalogJoy.LeftReturned()))

#	get_node("Label").set_text("X = " + str(Input.get_joy_axis(1,0) * 10))
#	get_node("Label 2").set_text("Y = " + str(Input.get_joy_axis(1,1) * 10))


	get_node("Label 5").set_text("X = " + str(AnalogJoy.GetRightPan()))
	get_node("Label 6").set_text("Y = " + str(AnalogJoy.GetRightTilt()))
	get_node("Label 7").set_text("Released = " + str(AnalogJoy.RightReleased()))
	get_node("Label 8").set_text("Returned = " + str(AnalogJoy.RightReturned()))

#	get_node("Label 5").set_text("X = " + str(Input.get_joy_axis(1,3) * 10))
#	get_node("Label 6").set_text("Y = " + str(Input.get_joy_axis(1,4) * 10))



#	if AnalogJoy.GetLeft() <= -5:
#		AnalogJoy.HideJoy()
#		
	if time < 5 and !check:
		AnalogJoy.HideJoy()
		check = true
	elif time > 5 and check:
		AnalogJoy.ShowJoy()
		check = false




