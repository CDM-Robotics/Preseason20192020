package team6072.robo2019.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import team6072.robo2019.subsystems.DriveSys;

public class SwerveDriveCmd extends Command {

    private Joystick mStick;
    private DriveSys mDriveSys;

    public SwerveDriveCmd(Joystick stick) {
        requires(DriveSys.getInstance());
        mDriveSys = DriveSys.getInstance();
        mStick = stick;
    }

    public void initialize() {
        mDriveSys.initSwerveDrive();
    }

    public void execute() {
        // compute angle from joysticks
        double y = mStick.getY();
        double x = mStick.getX();
        double magnitude = Math.sqrt((y * y) + (x * x));
        double targetAngle;
        if (x < 0) {
            targetAngle = Math.acos(y);
        } else {
            targetAngle = -Math.acos(y);
        }
        /**
         * This all assumes that the joystick's output is how I assume it is. Double check that later
         * may need this line 
         * y = -y
         */
        mDriveSys.executeSwerveDrive(targetAngle, magnitude);
    }

    public boolean isFinished() {
        return false;
    }

}