package team6072.robot2019.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import team6072.robot2019.subsystems.DriveSys;

public class DriveDistCmd extends Command{

    private double mDistanceInFeet;
    private double mTargetDistanceInTicks;
    private double mSpeed;
    private DriveSys mDriveSys;
    
    public DriveDistCmd(double speed, double distanceInFeet){
        mSpeed = speed;
        mDistanceInFeet = distanceInFeet;
        mDriveSys = DriveSys.getInstance();
        requires(mDriveSys);
    }
     
    public void initialize(){
        mTargetDistanceInTicks = (mDistanceInFeet * 12) * (4092 / (6 * Math.PI));
        if(mDriveSys.getCurrentPosition() < mTargetDistanceInTicks){
            mSpeed = Math.abs(mSpeed); // makes speed positive
        }else {
            mSpeed = -Math.abs(mSpeed); // makes speed negative
        }
    }

    public void execute(){
        mDriveSys.arcadeDrive(mSpeed, 0.0);
    }

    public boolean isFinished(){
        if(mDriveSys.getCurrentPosition() > mTargetDistanceInTicks){
            return true;
        }
        return false;
    }
}
