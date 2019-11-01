package team6072.robo2019.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import team6072.robo2019.subsystems.DriveSys;

public class SwerveDriveCmd extends Command {

    private Joystick mStick;
    private DriveSys mDriveSys;
    private double mPreviousTargetAngle = 0;

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
        try{
            y = y / magnitude;
        }catch (Exception err){
            
        }
        if (x < 0) {
            targetAngle = Math.acos(y);
            mPreviousTargetAngle = targetAngle;
        } else {
            targetAngle = -Math.acos(y);
            mPreviousTargetAngle = targetAngle;
        }
        /**
         * This all assumes that the joystick's output is how I assume it is. Double check that later
         * may need this line 
         * y = -y
         */
        mDriveSys.executeSwerveDrive(mPreviousTargetAngle, magnitude);
    }

    public boolean isFinished() {
        return false;
    }

}