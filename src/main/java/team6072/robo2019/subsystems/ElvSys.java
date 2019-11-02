package team6072.robo2019.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import team6072.robo2019.constants.subsystems.*;
import edu.wpi.first.wpilibj.command.Subsystem;

public class ElvSys extends Subsystem {

    private static ElvSys mElvSys;

    private TalonSRX mTalon_Master;
    private TalonSRX mTalon_Slave;

    public static ElvSys getInstance() {
        if (mElvSys == null) {
            mElvSys = new ElvSys();
        }
        return mElvSys;
    }

    private ElvSys() {

        mTalon_Master = new TalonSRX(ElvSysConstants.ELV_TALON);
        mTalon_Slave = new TalonSRX(ElvSysConstants.ELV_SLAVE0);

        mTalon_Master.setInverted(ElvSysConstants.ELV_INVERT);
        mTalon_Slave.setInverted(ElvSysConstants.ELV_INVERT);

        mTalon_Slave.follow(mTalon_Master);

        mTalon_Master.configOpenloopRamp(ElvSysConstants.ELV_CONFIG_OPEN_LOOP_RAMP, ElvSysConstants.ELV_TIME_OUT);
        mTalon_Master.setNeutralMode(ElvSysConstants.ELV_NEUTRAL_MODE);

    }

    public void initDefaultCommand() {
    }

}