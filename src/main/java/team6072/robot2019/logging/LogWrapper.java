package team6072.robot2019.logging;

import java.util.ArrayList;

public class LogWrapper {

    private String mName;
    private FileType mFileType;
    private Permission mPermission;

    private String mIdentifier; 
    private String mPrintString;
    private String mAlarmString;
    private String mWarningString;
    private String mErrorString;

    private String mDebugString;
    private String mDebugString2;
    private String mDebugString3;
    private String mDebugString4;
    
    

    public enum FileType {
        COMMAND, COMMAND_GROUP, PID, SUBSYSTEM, WATCHDOG, ROBOT, CONTROLBOARD;
    }

    public enum Permission {
        PERIODIC_OFF, ALL, WARNINGS_AND_ERRORS, ERRORS_ONLY;
    }

    public LogWrapper(FileType fileType, String name, Permission permission) {
        mName = name;
        mFileType = fileType;
        mPermission = permission;

        mIdentifier = mFileType.toString() + ": " + mName + ":";
        mPrintString = (mIdentifier + " %s \n");
        mAlarmString = "**ALARM: " + mPrintString;
        mWarningString = "****************************************************************************\nWARNING: "
                + mPrintString + "****************************************************************************\n";
        mErrorString = "****************************************************************************\nERROR: "
                + mPrintString + "****************************************************************************\n";

        mDebugString = mIdentifier + "  %s: %.3f\n";
        mDebugString2 = mIdentifier + "  %s: %.3f  %s: %.3f\n";
        mDebugString3 = mIdentifier + "  %s: %.3f  %s: %.3f  %s: %.3f\n";
        mDebugString4 = mIdentifier + "  %s: %.3f  %s: %.3f  %s: %.3f  %s: %.3f\n";

    }

    private int mIterations;

    public void periodicPrint(String s, int iterations) {
        if (mPermission == Permission.ALL) {
            if (mIterations % iterations == 0) {
                System.out.printf(mPrintString, s);
            }
            mIterations++;
        }
    }

    public void print(String s) {
        if (mPermission == Permission.ALL || mPermission == Permission.PERIODIC_OFF) {
            System.out.printf(mPrintString, s);
        }
    }

    public void alarm(String s) {
        if (mPermission == Permission.ALL || mPermission == Permission.PERIODIC_OFF) {
            System.out.printf(mAlarmString, s);
        }
    }

    public void warning(String s) {
        if (mPermission == Permission.ALL || mPermission == Permission.WARNINGS_AND_ERRORS
                || mPermission == Permission.PERIODIC_OFF) {
            System.out.printf(mWarningString, s);
        }
    }

    public void error(String s) {
        System.out.printf(mErrorString, s);

    }





    public void debug(String name, double var){
        if (mPermission == Permission.ALL || mPermission == Permission.PERIODIC_OFF) {
            System.out.printf(mDebugString, name, var);
        }
    }

    public void debug(String name, double var, String name2, double var2){
        if (mPermission == Permission.ALL || mPermission == Permission.PERIODIC_OFF) {
            System.out.printf(mDebugString2, name, var, name2, var2);
        }
    }

    public void debug(String name, double var, String name2, double var2, String name3, double var3){
        if (mPermission == Permission.ALL || mPermission == Permission.PERIODIC_OFF) {
            System.out.printf(mDebugString3, name, var, name2, var2, name3, var3);
        }
    }

    public void debug(String name, double var, String name2, double var2, String name3, double var3, String name4, double var4){
        if (mPermission == Permission.ALL || mPermission == Permission.PERIODIC_OFF) {
            System.out.printf(mDebugString4, name, var, name2, var2, name3, var3, name4, var4);
        }
    }

    public void periodicDebug(int iterations, String name, double var) {
        if (mPermission == Permission.ALL) {
            if (mIterations % iterations == 0) {
                System.out.printf(mDebugString, name, var);
            }
            mIterations++;
        }
    }

    public void periodicDebug(int iterations, String name, double var, String name2, double var2) {
        if (mPermission == Permission.ALL) {
            if (mIterations % iterations == 0) {
                System.out.printf(mDebugString2, name, var, name2, var2);
            }
            mIterations++;
        }
    }
    
    public void periodicDebug(int iterations, String name, double var, String name2, double var2, String name3, double var3) {
        if (mPermission == Permission.ALL) {
            if (mIterations % iterations == 0) {
                System.out.printf(mDebugString3, name, var, name2, var2, name3, var3);
            }
            mIterations++;
        }
    }

    public void periodicDebug(int iterations, String name, double var, String name2, double var2, String name3, double var3, String name4, double var4) {
        if (mPermission == Permission.ALL) {
            if (mIterations % iterations == 0) {
                System.out.printf(mDebugString4, name, var, name2, var2, name3, var3, name4, var4);
            }
            mIterations++;
        }
    }
}