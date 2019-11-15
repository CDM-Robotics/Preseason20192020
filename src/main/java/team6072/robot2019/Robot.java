/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package team6072.robot2019;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import team6072.robot2019.commands.ArcadeDriveCmd;
import team6072.robot2019.logging.LogWrapper;
import team6072.robot2019.logging.SuperLogMaster;
import team6072.robot2019.logging.LogWrapper.FileType;
import team6072.robot2019.pid.MyPIDController;
import team6072.robot2019.subsystems.DriveSys;
import team6072.robot2019.commands.RelativeDriveCmd;
import team6072.robot2019.subsystems.ElvSys;
import team6072.robot2019.subsystems.NavXSys;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

  private Scheduler mScheduler;
  private LogWrapper mLog;
  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {
    mLog = new LogWrapper(FileType.ROBOT, "Robot", team6072.robot2019.logging.LogWrapper.Permission.ALL);
    mScheduler = Scheduler.getInstance();

    ControlBoard.getInstance();
    DriveSys.getInstance();
    NavXSys.getInstance();
  }

  public void disabledInit(){
    // MyPIDController.disableAllPIDs();
  }

  public void autonomousInit(){
    mScheduler.removeAll();
    RelativeDriveCmd relativeDriveCmd = new RelativeDriveCmd(ControlBoard.getInstance().mJoystick0);
    Scheduler.getInstance().add(relativeDriveCmd);
    mLog.alarm("Autonomous");
    NavXSys.getInstance().resetAll();
  }

  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();
  }

  public void teleopInit(){
    mScheduler.removeAll();
    ArcadeDriveCmd arcadeDriveCmd = new ArcadeDriveCmd(ControlBoard.getInstance().mJoystick0);
    Scheduler.getInstance().add(arcadeDriveCmd);
    mLog.alarm("TelopInit");
    NavXSys.getInstance().resetAll();
  }

  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
  }

}
