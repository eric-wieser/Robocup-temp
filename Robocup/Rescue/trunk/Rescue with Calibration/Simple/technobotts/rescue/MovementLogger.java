package technobotts.rescue;

import lejos.nxt.LCD;
import lejos.nxt.Sound;
import technobotts.rescue.RescueRobot;

public class MovementLogger extends Thread
{
	private final float	tolerance	= 180;
	private final int	time	  = 2000;

	private RescueRobot	_robot;

	public MovementLogger(RescueRobot robot)
	{
		super();
		_robot = robot;
	}

	@Override
	public void run()
	{
		int left = _robot.motors.getLeftTacho(), right = _robot.motors.getRightTacho();
		int lastLeft, lastRight;
		while(true)
		{
			lastLeft = left;
			lastRight = right;
			left = _robot.motors.getLeftTacho();
			right = _robot.motors.getRightTacho();

			try
			{
				sleep(time);
			}
			catch(InterruptedException e)
			{}

			if(Math.abs(right - lastRight) <= tolerance && Math.abs(left - lastLeft) <= tolerance)
			{
				synchronized(_robot.motors)
				{
					Sound.beepSequenceUp();
					LCD.drawString("Lack of progress", 0, 0);
					_robot.pilot.travel(15);
					LCD.clear();
				}
			}

			yield();
		}
	}
}