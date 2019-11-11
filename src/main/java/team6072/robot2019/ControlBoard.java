package team6072.robot2019;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Scheduler;
import team6072.robot2019.commands.ArcadeDriveCmd;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.buttons.POVButton;
import team6072.robot2019.commands.DriveDistCmd;

public class ControlBoard {

    private static ControlBoard mControlBoard;

    public static ControlBoard getInstance() {
        if (mControlBoard == null) {
            mControlBoard = new ControlBoard();
        }
        return mControlBoard;
    }

    private ControlBoard(){
        Joystick stick0 = new Joystick(0);
        Joystick stick1 = new Joystick(1);
        JoystickButton topLeftButton = new JoystickButton(stick0, 5);
        Scheduler scheduler = Scheduler.getInstance();

        ArcadeDriveCmd arcadeDriveCmd = new ArcadeDriveCmd(stick0);
        DriveDistCmd driveDistCmd = new DriveDistCmd(.5, 3);

        scheduler.add(arcadeDriveCmd);
        topLeftButton.whenPressed(driveDistCmd);

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