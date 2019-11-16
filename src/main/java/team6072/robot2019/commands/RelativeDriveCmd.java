package team6072.robot2019.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import team6072.robot2019.subsystems.DriveSys;

import team6072.robot2019.logging.LogWrapper;
import team6072.robot2019.logging.LogWrapper.FileType;
import team6072.robot2019.constants.commands.RelativeDriveCmdConstants;
import team6072.robot2019.constants.logging.LoggerConstants;
import team6072.robot2019.datasources.NavXSource;
import team6072.robot2019.datasources.NavXSource.NavXDataTypes;

public class RelativeDriveCmd extends Command {

    private Joystick mStick;
    private DriveSys mDriveSys;
    private double mLastValidJoystickTarget = 0;
    private LogWrapper mLog;
    private NavXSource mYawSource;
    private NavXSource mAccumulatedYawSource;

    public RelativeDriveCmd(Joystick stick) {
        mLog = new LogWrapper(FileType.COMMAND, "RelativeDriveCmd", LoggerConstants.RELATIVE_DRIVE_CMD);
        requires(DriveSys.getInstance());
        mDriveSys = DriveSys.getInstance();
        mStick = stick;
    }

    public void initialize() {
        mLog.warning("This driving function still has the PAST_ZERO_PROBLEM----Fix before you use this algorithm");
        mYawSource = new NavXSource(NavXDataTypes.YAW);
        mAccumulatedYawSource = new NavXSource(NavXDataTypes.TOTAL_YAW);
        mDriveSys.initRelativeDrive();
    }

    public void execute() {
        // compute angle from joysticks
        double y = mStick.getY();
        double x = mStick.getX();
        y = -y;

        double magnitude = Math.sqrt((y * y) + (x * x));
        double joystickTarget = 0.0;

        try {
            y = y / magnitude;
        } catch (Exception err) {
        }
        if (magnitude > RelativeDriveCmdConstants.SET_ANGLE_THRESHOLD) {
            if (x < 0) {
                joystickTarget = -Math.acos(y);
                joystickTarget = ((joystickTarget * 360) / (2 * Math.PI)) + 360.0;
                mLastValidJoystickTarget = joystickTarget;
            } else {
                joystickTarget = Math.acos(y);
                joystickTarget = ((joystickTarget * 360) / (2 * Math.PI));
                mLastValidJoystickTarget = joystickTarget;
            }
        }

        int numOfRobotRevolutions = (int) ((mAccumulatedYawSource.getData() / 360.0));
        double currentRevPercent = (mAccumulatedYawSource.getData() / 360.0) % 1.0;
        double currentRevPosition = currentRevPercent * 360.0;
        double err = Math.abs(mLastValidJoystickTarget - currentRevPosition);
        if (err > 180.0) {
            if (Math.abs((mLastValidJoystickTarget + 360) - currentRevPosition) < 180) {
                mLastValidJoystickTarget = mLastValidJoystickTarget + 360;
            } else if (Math.abs((mLastValidJoystickTarget - 360) - currentRevPosition) < 180) {
                mLastValidJoystickTarget = mLastValidJoystickTarget - 360;
            }
        }
        mLastValidJoystickTarget = mLastValidJoystickTarget + numOfRobotRevolutions * 360.0;
        mLog.periodicDebug("magnitude: " + magnitude + ", mPerviousAngle: " + mLastValidJoystickTarget + ", mYaw: "
                + mYawSource.getData() + ", mAccumulatedYaw: " + mAccumulatedYawSource.getData(), 25);

        mDriveSys.executeRelativeDrive(mLastValidJoystickTarget, -magnitude);
    }

    public boolean isFinished() {
        return false;
    }

}