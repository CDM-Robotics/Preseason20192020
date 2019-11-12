package team6072.robot2019.subsystems;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Subsystem;
import team6072.robot2019.logging.LogWrapper;
import team6072.robot2019.logging.LogWrapper.FileType;
import team6072.robot2019.constants.logging.LoggerConstants;

public class NavXSys extends Subsystem {

    private AHRS mAHRS;
    private static NavXSys mNavxSys;
    private double mStartingPosition;
    private LogWrapper mLog;

    public static NavXSys getInstance() {
        if (mNavxSys == null) {
            mNavxSys = new NavXSys();
        }
        return mNavxSys;
    }

    private NavXSys() {
        mLog = new LogWrapper(FileType.SUBSYSTEM, "NavXSys", LoggerConstants.NAVXSYS_PERMISSION);
        mAHRS = new AHRS(SPI.Port.kMXP, (byte) 100);
        mStartingPosition = mAHRS.getYaw();
        // mLog.debug("Starting Yaw: " + mAHRS.getYaw() + ", mStartingPosition: " + mStartingPosition); 
    }

    public void initDefaultCommand() {
    }

    public double getYaw() {
        return mAHRS.getYaw();
    }

    public double getRoll() {
        return mAHRS.getRoll();
    }

    public double getPitch() {
        return mAHRS.getPitch();
    }

    public void zeroYawHeading(){
        mAHRS.zeroYaw();
    }

}