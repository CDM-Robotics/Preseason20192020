package team6072.robot2019.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import team6072.robot2019.subsystems.DriveSys;

import team6072.robot2019.logging.LogWrapper;
import team6072.robot2019.logging.LogWrapper.FileType;
import team6072.robot2019.constants.commands.RelativeDriveCmdConstants;
import team6072.robot2019.constants.logging.LoggerConstants;

public class RelativeDriveCmd extends Command {

    private Joystick mStick;
    private DriveSys mDriveSys;
    private double mPreviousTargetAngle = 0;
    private LogWrapper mLog;

    public RelativeDriveCmd(Joystick stick) {
        mLog = new LogWrapper(FileType.COMMAND, "RelativeDriveCmd", LoggerConstants.RELATIVE_DRIVE_CMD);
        requires(DriveSys.getInstance());
        mDriveSys = DriveSys.getInstance();
        mStick = stick;
    }

    public void initialize() {
        mLog.warning("This driving function still has the PAST_ZERO_PROBLEM----Fix before you use this algorithm");
        mDriveSys.initRelativeDrive();
    }

    public void execute() {
        // compute angle from joysticks
        double y = mStick.getY();
        double x = mStick.getX();
        double magnitude = Math.sqrt((y * y) + (x * x));
        double targetAngle = 0.0;

        y = -y;
        try{
            y = y / magnitude;
        }catch (Exception err){
        }
        if(magnitude > RelativeDriveCmdConstants.SET_ANGLE_THRESHOLD){
            if (x < 0) {
                targetAngle = -Math.acos(y);
                targetAngle = ((targetAngle * 360) / (2 * Math.PI));
                mPreviousTargetAngle = targetAngle;
            } else {
                targetAngle = Math.acos(y);
                targetAngle = ((targetAngle * 360) / (2 * Math.PI));
                mPreviousTargetAngle = targetAngle;
            }
        }
        mLog.periodicDebug("magnitude: " + magnitude + ", targetAngle: " + targetAngle + ", mPerviousAngle: " + mPreviousTargetAngle, 25);
        /**
         * This all assumes that the joystick's output is how I assume it is. Double check that later
         * may need this line 
         * y = -y
         */
        mDriveSys.executeRelativeDrive(mPreviousTargetAngle, -magnitude);
    }

    public boolean isFinished() {
        return false;
    }

}