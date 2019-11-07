package team6072.robo2019.subsystems;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.command.Subsystem;
import team6072.robo2019.constants.subsystems.IntakeSysConstants;
import team6072.robo2019.logging.*;



/**
 * Intake sys has a single talon, and just has to drive the wheels in or out
 * do need a sensor to detect when ball loaded and stop driving wheels in, so it does not shred the ball
 */
public class IntakeSys extends Subsystem {

    private static IntakeSys mInstance;


    private WPI_TalonSRX mTalon;


    public static IntakeSys getInstance() {
        if (mInstance == null) {
            mInstance = new IntakeSys();
        }
        return mInstance;
    }



    public IntakeSys() {
        mTalon = new WPI_TalonSRX(IntakeSysConstants.INTAKE_MASTER);
        // mTalon.configFactoryDefault();
        // mTalon.setSafetyEnabled(false);
        // mTalon.setNeutralMode(NeutralMode.Brake);
        mTalon.set(ControlMode.PercentOutput, 0);
    }


    @Override
    public void initDefaultCommand() {
    }

}
