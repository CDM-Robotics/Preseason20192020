/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package team6072.robo2019;

import edu.wpi.first.wpilibj.Joystick;
import team6072.robo2019.commands.ArcadeDriveCmd;
import team6072.robo2019.commands.RelativeDriveCmd;
import team6072.robo2019.constants.ControlBoardConstants;
import team6072.robo2019.constants.logging.LoggerConstants;
import team6072.robo2019.logging.LogWrapper;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.buttons.POVButton;
import team6072.robo2019.logging.LogWrapper.FileType;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class ControlBoard {

    private static ControlBoard mControlBoard;
    public Joystick mJoystick0;
    public Joystick mJoystick1;

    private LogWrapper mLog;

    public static ControlBoard getInstance() {
        if (mControlBoard == null) {
            mControlBoard = new ControlBoard();
        }
        return mControlBoard;
    }

    private ControlBoard() {
        mLog = new LogWrapper(FileType.CONTROLBOARD, "ControlBoard", LoggerConstants.CONTROL_BOARD_PERMISSION);

        mJoystick0 = new Joystick(ControlBoardConstants.JOYSTICK0);
        mJoystick1 = new Joystick(ControlBoardConstants.JOYSTICK1);

        Scheduler.getInstance().add(new ArcadeDriveCmd(mJoystick0));
        // Scheduler.getInstance().add(new SwerveDriveCommand(mJoystick0));

        mLog.debug("ControlBoard Initialized");
    }

    private void MapCmdToBut(Joystick stick, int button, Command pressCmd, Command releaseCmd) {
        JoystickButton but = new JoystickButton(stick, button);
        if (pressCmd != null) {
            but.whenPressed(pressCmd);
        }
        if (releaseCmd != null) {
            but.whenReleased(releaseCmd);
        }
    }

    public enum PovAngle {
        Deg_000(0), Deg_045(45), Deg_090(90), Deg_135(135), Deg_180(180), Deg_225(225), Deg_270(270), Deg_315(315);

        private int mAngle;

        PovAngle(int angle) {
            mAngle = angle;
        }

        public int getAngle() {
            return mAngle;
        }
    }

    /**
     * See PovButton here:
     * http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/buttons/POVButton.html
     * 
     * PovButton is the small rotating button on top of the joystick
     */
    private void MapCmdToPovBut(Joystick stick, PovAngle angle, Command pressCmd, Command releaseCmd) {
        POVButton but = new POVButton(stick, angle.getAngle());
        if (pressCmd != null) {
            but.whenPressed(pressCmd);
        }
        if (releaseCmd != null) {
            but.whenReleased(releaseCmd);
        }
    }

}
