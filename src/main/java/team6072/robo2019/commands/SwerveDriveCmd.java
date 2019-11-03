package team6072.robo2019.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import team6072.robo2019.subsystems.DriveSys;
import team6072.robo2019.constants.logging.LoggerConstants;
import team6072.robo2019.logging.LogWrapper;
import team6072.robo2019.logging.LogWrapper.FileType;

public class SwerveDriveCmd extends Command {

    private Joystick mStick;
    private DriveSys mDriveSys;
    private double mPreviousTargetAngle = 0;
    private LogWrapper mLog;

    public SwerveDriveCmd(Joystick stick) {
        requires(DriveSys.getInstance());
        mDriveSys = DriveSys.getInstance();
        mStick = stick;
        mLog = new LogWrapper(FileType.COMMAND, "SwerveDriveCmd", LoggerConstants.SWERVE_DRIVE_CMD);
    }

    public void initialize() {
        mLog.debug("initializing");
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
            mLog.warning("Divide by zero error avoided. Magnitude = 0.");
        }
        if (x < 0) {
            targetAngle = Math.acos(y);
            targetAngle = targetAngle * 360 / (2 * Math.PI);
            mPreviousTargetAngle = targetAngle;
        } else {
            targetAngle = -Math.acos(y);
            targetAngle = targetAngle * 360 / (2 * Math.PI);
            mPreviousTargetAngle = targetAngle;
        }
        mLog.debug("magnitude: " + magnitude + ",  targetAngle: " + targetAngle + ",  y: " + y + ",  x: " + x);
        mLog.reminder("Test the Joystick y output and see if it needs to be inverted");
        mLog.reminder("Change the Swerve Constants");
        mLog.reminder("Double Check that you got your trig math right.  (Math.acos() returns the angle in radians)");

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