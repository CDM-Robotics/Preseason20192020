
package team6072.robo2019.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import java.awt.Robot;
import team6072.robo2019.constants.subsystems.PneumaticSysConstants;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import team6072.robo2019.logging.*;

public class PneumaticSys extends Subsystem {

    // define the logger for this class. This should be done for every class

    private Compressor mCompressor;

    private DoubleSolenoid mDriveTrainSolenoid;

    private DoubleSolenoid mHatchWristSolenoid;

    private DoubleSolenoid mIntakeSolenoid;

    private DoubleSolenoid mIntakeLockSolenoid;

    private static PneumaticSys mInstance;

    public static PneumaticSys getInstance() {
        try {
            if (mInstance == null) {
                mInstance = new PneumaticSys();
            }
            return mInstance;
        } catch (Exception ex) {
            throw ex;
        }
    }

    private PneumaticSys() {
        mCompressor = new Compressor(PneumaticSysConstants.PCM_ID);
        mCompressor.start();
        mDriveTrainSolenoid = new DoubleSolenoid(PneumaticSysConstants.PCM_ID, PneumaticSysConstants.PCM_DRIVE_HIGH,
                PneumaticSysConstants.PCM_DRIVE_LOW);
        mHatchWristSolenoid = new DoubleSolenoid(PneumaticSysConstants.PCM_ID, PneumaticSysConstants.PCM_HATCH_EXTEND,
                PneumaticSysConstants.PCM_HATCH_RETRACT);
        mIntakeSolenoid = new DoubleSolenoid(PneumaticSysConstants.PCM_ID, PneumaticSysConstants.PCM_INTAKE_OPEN,
                PneumaticSysConstants.PCM_INTAKE_CLOSED);
        mIntakeLockSolenoid = new DoubleSolenoid(PneumaticSysConstants.PCM_ID,
                PneumaticSysConstants.PCM_INTAKELOCK_OPEN, PneumaticSysConstants.PCM_INTAKELOCK_CLOSED);
    }

    @Override
    public void initDefaultCommand() {
    }

}
