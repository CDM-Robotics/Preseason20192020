package team6072.robo2019.logging;

import java.util.ArrayList;

public class LogWrapper {

    private String mName;
    private FileType mFileType;
    private Permission mPermission;

    public enum FileType {
        COMMAND, COMMAND_GROUP, PID, SUBSYSTEM, WATCHDOG, ROBOT, CONTROLBOARD;
    }

    public enum Permission {
        ALL, WARNINGS_AND_ERRORS, ERRORS_ONLY;
    }

    public LogWrapper(FileType fileType, String name, Permission permission) {
        mName = name;
        mFileType = fileType;
        mPermission = permission;
    }

    public void debug(String s) {
        if (mPermission == Permission.ALL) {
            System.out.print(mFileType.toString() + ": " + mName + ": " + s);
        }
    }

    public void alarm(String s) {
        if (mPermission == Permission.ALL) {
            System.out.print("**ALARM: " + mFileType.toString() + ": " + mName + ": " + s);
        }
    }

    public void warning(String s) {
        if(mPermission == Permission.ALL || mPermission == Permission.WARNINGS_AND_ERRORS){
            System.out.print("****************************************************************************\n" + "WARNING: "
                + mFileType.toString() + ": " + mName + ": " + s
                + "\n****************************************************************************");

        }
    }

    public void error(String s) {
        System.out.print("****************************************************************************\n" + "ERROR: "
                + mFileType.toString() + ": " + mName + ": " + s
                + "\n****************************************************************************");

    }
}