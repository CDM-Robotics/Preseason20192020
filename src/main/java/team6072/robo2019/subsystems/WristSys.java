package team6072.robo2019.subsystems;

import java.util.TimerTask;

import java.util.Timer;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import team6072.robo2019.constants.subsystems.WristSysConstants;

/**
 * Add your docs here.
 */
public class WristSys extends Subsystem {

    public boolean mIntakeLockExtend = false;
    private WPI_TalonSRX mTalon;
    private static WristSys mInstance;

    public static WristSys getInstance() {
        if (mInstance == null) {
            mInstance = new WristSys();
        }
        return mInstance;
    }

    @Override
    public void initDefaultCommand() {
        // setDefaultCommand(new WristMoveUpSlow());
    }

    public WristSys() {
        mTalon = new WPI_TalonSRX(WristSysConstants.WRIST_MASTER);
        mTalon.configFactoryDefault();
    }

}
