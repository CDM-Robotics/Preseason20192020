package team6072.robot2019.logging;

import java.util.ArrayList;

public class LogWrapper {

    private String mName;
    private FileType mFileType;
    private Permission mPermission;

    private String mDebugString;
    private String mAlarmString;
    private String mWarningString;
    private String mErrorString;

    public enum FileType {
        COMMAND, COMMAND_GROUP, PID, SUBSYSTEM, WATCHDOG, ROBOT, CONTROLBOARD;
    }

    public enum Permission {
        PERIODIC_DEBUG_OFF, ALL, WARNINGS_AND_ERRORS, ERRORS_ONLY;
    }

    public LogWrapper(FileType fileType, String name, Permission permission) {
        mName = name;
        mFileType = fileType;
        mPermission = permission;

        mDebugString = (mFileType.toString() + ": " + mName + ": %s \n");
        mAlarmString = "**ALARM: " + mDebugString;
        mWarningString = "****************************************************************************\nWARNING: "
                + mDebugString + "****************************************************************************\n";
        mErrorString = "****************************************************************************\nERROR: "
                + mDebugString + "****************************************************************************\n";

    }

    private int mIterations;

    public void periodicDebug(String s, int iterations) {
        if (mPermission == Permission.ALL) {
            if (mIterations % iterations == 0) {
                System.out.printf(mDebugString, s);
            }
            mIterations++;
        }
    }

    public void debug(String s) {
        if (mPermission == Permission.ALL || mPermission == Permission.PERIODIC_DEBUG_OFF) {
            System.out.printf(mDebugString, s);
        }
    }

    public void alarm(String s) {
        if (mPermission == Permission.ALL || mPermission == Permission.PERIODIC_DEBUG_OFF) {
            System.out.printf(mAlarmString, s);
        }
    }

    public void warning(String s) {
        if (mPermission == Permission.ALL || mPermission == Permission.WARNINGS_AND_ERRORS
                || mPermission == Permission.PERIODIC_DEBUG_OFF) {
            System.out.printf(mWarningString, s);
        }
    }

    public void error(String s) {
        System.out.printf(mErrorString, s);

    }
}