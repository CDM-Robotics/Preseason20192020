/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package team6072.robot2019;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import team6072.robot2019.logging.LogWrapper;
import team6072.robot2019.subsystems.DriveSys;
import team6072.robot2019.logging.LogWrapper.FileType;
import team6072.robot2019.logging.LogWrapper.Permission;

/**
 * This is a demo program showing the use of the RobotDrive class, specifically
 * it contains the code necessary to operate a robot with tank drive.
 */
public class Robot extends TimedRobot {

  private LogWrapper mLog;

  @Override
  public void robotInit() {
    mLog = new LogWrapper(FileType.ROBOT, "Robot.java", Permission.ALL);
    Scheduler.getInstance().removeAll();
    DriveSys.getInstance();
    ControlBoard.getInstance();
  }

  public void teleopInit() {
  }

  public void teleopPeriodic() {
    Scheduler.getInstance().run();
  }
}
