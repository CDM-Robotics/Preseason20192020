package team6072.robot2019.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import team6072.robot2019.subsystems.DriveSys;

/**
 * Define a command for driving
 */
public class ArcadeDriveCmd extends Command {

    private Joystick mStick;

    private DriveSys mDriveSys;

    /**
     * Specify the the command requires the DriveSys subsystem
     */
    public ArcadeDriveCmd(Joystick stick) {
        requires(DriveSys.getInstance());
        mStick = stick;
        mDriveSys = DriveSys.getInstance();
    }

    /**
     * Execute is called by the scheduler until the command returns finished or the
     * OI stops requesting - for example if the whileHeld() button command is used
     */
    protected void execute() {
        double mag = mStick.getY();
        double yaw = mStick.getX();

        mag = mag;// y comes out from stick as negative when going forward, so convert
        yaw = yaw * 0.8; // reduce sensitivity on turn
        
        mDriveSys.arcadeDrive(mag, yaw);
    }

    /**
     * @return Return true when command is completed
     */
    @Override
    protected boolean isFinished() {
        return false;
    }

}