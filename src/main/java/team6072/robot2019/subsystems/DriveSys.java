package team6072.robot2019.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import team6072.robot2019.constants.*;
import team6072.robot2019.pid.MyPIDController;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import team6072.robot2019.datasources.NavXSource;
import team6072.robot2019.datasources.NavXSource.NavXDataTypes;
import team6072.robot2019.constants.subsystems.DriveSysConstants;

public class DriveSys extends Subsystem {

    private static DriveSys mDriveSys;

    private WPI_TalonSRX mLeft_Master;
    private WPI_TalonSRX mLeft_Slave0;
    private WPI_TalonSRX mLeft_Slave1;
    private WPI_TalonSRX mRight_Master;
    private WPI_TalonSRX mRight_Slave0;
    private WPI_TalonSRX mRight_Slave1;

    private DifferentialDrive mRoboDrive;

    public static DriveSys getInstance() {
        if (mDriveSys == null) {
            mDriveSys = new DriveSys();
        }
        return mDriveSys;
    }

    private DriveSys() {
        mLeft_Master = new WPI_TalonSRX(DriveSysConstants.LEFT_TALON_MASTER);
        mLeft_Slave0 = new WPI_TalonSRX(DriveSysConstants.LEFT_TALON_SLAVE0);
        mLeft_Slave1 = new WPI_TalonSRX(DriveSysConstants.LEFT_TALON_SLAVE1);
        mRight_Master = new WPI_TalonSRX(DriveSysConstants.RIGHT_TALON_MASTER);
        mRight_Slave0 = new WPI_TalonSRX(DriveSysConstants.RIGHT_TALON_SLAVE0);
        mRight_Slave1 = new WPI_TalonSRX(DriveSysConstants.RIGHT_TALON_SLAVE1);

        mLeft_Slave0.follow(mLeft_Master);
        // mLeft_Slave1.follow(mLeft_Master);
        mRight_Slave0.follow(mRight_Master);
        // mRight_Slave1.follow(mRight_Master);

        mLeft_Master.setSensorPhase(DriveSysConstants.LEFT_TALON_MASTER_SENSOR_PHASE);
        mLeft_Slave0.setSensorPhase(DriveSysConstants.LEFT_TALON_SLAVE0_SENSOR_PHASE);
        mLeft_Slave1.setSensorPhase(DriveSysConstants.LEFT_TALON_SLAVE1_SENSOR_PHASE);
        mRight_Master.setSensorPhase(DriveSysConstants.RIGHT_TALON_MASTER_SENSOR_PHASE);
        mRight_Slave0.setSensorPhase(DriveSysConstants.RIGHT_TALON_SLAVE0_SENSOR_PHASE);
        mRight_Slave1.setSensorPhase(DriveSysConstants.RIGHT_TALON_SLAVE1_SENSOR_PHASE);

        // mLeft_Master.setInverted(DriveSysConstants.DRIVE_LEFT_INVERT);
        // mLeft_Slave0.setInverted(DriveSysConstants.DRIVE_LEFT_INVERT);
        // mLeft_Slave1.setInverted(DriveSysConstants.DRIVE_LEFT_INVERT);
        // mRight_Master.setInverted(DriveSysConstants.DRIVE_RIGHT_INVERT);
        // mRight_Slave0.setInverted(DriveSysConstants.DRIVE_RIGHT_INVERT);
        // mRight_Slave1.setInverted(DriveSysConstants.DRIVE_RIGHT_INVERT);

        mLeft_Master.configOpenloopRamp(DriveSysConstants.DRIVE_CONFIG_OPEN_LOOP_RAMP,
                DriveSysConstants.DRIVE_TIME_OUT);
        mRight_Master.configOpenloopRamp(DriveSysConstants.DRIVE_CONFIG_OPEN_LOOP_RAMP,
                DriveSysConstants.DRIVE_TIME_OUT);

        mLeft_Master.setNeutralMode(DriveSysConstants.DRIVE_NEUTRAL_MODE);
        mRight_Master.setNeutralMode(DriveSysConstants.DRIVE_NEUTRAL_MODE);

        mRoboDrive = new DifferentialDrive(mLeft_Master, mRight_Master);

    }

    /***********************************************************
     * 
     *
     * Arcade Drive stuff
     * 
     * 
     ***********************************************************/

    public void arcadeDrive(double mag, double yaw) {
        mRoboDrive.arcadeDrive(mag, -yaw, true);
    }

    /***********************************************************
     * 
     *
     * Swerve Drive stuff
     * 
     * 
     ***********************************************************/

    private final double SWERVE_P = DriveSysConstants.SWERVE_P;
    private final double SWERVE_D = DriveSysConstants.SWERVE_D;
    private final double SWERVE_I = DriveSysConstants.SWERVE_I;
    private final double SWERVE_F = DriveSysConstants.SWERVE_F;

    private final double SWERVE_UPPER_DEADBAND = DriveSysConstants.SWERVE_UPPER_DEADBAND;
    private final double SWERVE_LOWER_DEADBAND = DriveSysConstants.SWERVE_LOWER_DEADBAND;
    private final double BASE_PERCENT_OUT = DriveSysConstants.BASE_PERCENT_OUT;

    private final double SWERVE_TURN_TOLERANCE = DriveSysConstants.SWERVE_TURN_TOLERANCE;

    private MyPIDController mSwervePIDController;
    private NavXSource mNavXSource;

    public void initRelativeDrive() {
        // set up Navx
        // set up NavXSource
        mNavXSource = new NavXSource(NavXDataTypes.YAW);
        // initialize PID with deadband
        mSwervePIDController = new MyPIDController(SWERVE_P, SWERVE_I, SWERVE_D, SWERVE_F, mNavXSource, 1.0, -1.0);
        mSwervePIDController.setDeadband(SWERVE_UPPER_DEADBAND, SWERVE_LOWER_DEADBAND, BASE_PERCENT_OUT);
        mSwervePIDController.start();
        // set up PID
    }

    public void executeRelativeDrive(double targetAngle, double magnitude) {
        mSwervePIDController.setSetpoint(targetAngle);
        double yaw = mSwervePIDController.getOutput();
        if(yaw > SWERVE_TURN_TOLERANCE){
            arcadeDrive(0.0, yaw);
        }else{
            arcadeDrive(magnitude, yaw);
        }
        // if the angle difference is within a certian tolerance
        // then just change the motor difference
        // if the angle difference is greater than the tolerance
        // then it will cut the motor magnitude and only turn
    }

    public void initDefaultCommand() {

    }
}