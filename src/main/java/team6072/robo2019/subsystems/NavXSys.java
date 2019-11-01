package team6072.robo2019.subsystems;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Subsystem;

public class NavXSys extends Subsystem {

    private AHRS mNavx;
    private static NavXSys mNavxSys;

    public static NavXSys getInstance() {
        if (mNavxSys == null) {
            mNavxSys = new NavXSys();
        }
        return mNavxSys;
    }

    private NavXSys() {
        mNavx = new AHRS(SPI.Port.kMXP);
        mNavx.zeroYaw();
    }

    public void initDefaultCommand() {
    }

    public double getYaw() {
        return mNavx.getYaw();
    }

    public double getRoll() {
        return mNavx.getRoll();
    }

    public double getPitch() {
        return mNavx.getPitch();
    }

}