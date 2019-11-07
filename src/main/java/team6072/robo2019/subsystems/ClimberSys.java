
package team6072.robo2019.subsystems;

import javax.swing.MenuElement;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Subsystem;
import team6072.robo2019.constants.subsystems.ClimberSysConstants;
import team6072.robo2019.logging.*;

public class ClimberSys extends Subsystem {

    private static ClimberSys mInstance;

    public static ClimberSys getInstance() {
        if (mInstance == null) {
        mInstance = new ClimberSys();
        }
        return mInstance;
    }

    private NavXSys mNavX;

    private WPI_TalonSRX mClimbTalon;

    private WPI_TalonSRX mElvTalon;

    private static final boolean TALON_INVERT = false;
    private static final boolean TALON_SENSOR_PHASE = false;

    public static final int kTimeoutMs = 10;
    public static final int kPIDLoopIdx = 0;


    /**
     * The climber sys has to: move the intake to hab position and do pid hold move
     * elevator to hab position start moving elevator and climber in sync use NavX
     * pitch to detect how horzontal we a
     */
    private ClimberSys() {
            mNavX = NavXSys.getInstance();

            mClimbTalon = new WPI_TalonSRX(ClimberSysConstants.CLIMBER_MASTER);
            mClimbTalon.configFactoryDefault();
            mClimbTalon.set(ControlMode.PercentOutput, 0);

            mClimbTalon.setSensorPhase(TALON_SENSOR_PHASE);
            mClimbTalon.setInverted(TALON_INVERT);

    }

    public void initDefaultCommand(){
        
    }

}
