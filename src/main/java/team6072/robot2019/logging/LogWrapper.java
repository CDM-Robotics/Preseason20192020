package team6072.robot2019.logging;

public class LogWrapper {

    private String mName;
    private FileType mFileType;
    private Permission mPermission;

    public enum FileType {
        COMMAND, COMMAND_GROUP, PID, SUBSYSTEM, WATCHDOG, ROBOT, CONTROLBOARD;
    }

    public enum Permission {
        ALL, REMINDERS_WARNINGS_ERRORS, WARNINGS_AND_ERRORS, ERRORS_ONLY;
    }

    public LogWrapper(FileType fileType, String name, Permission permission) {
        mName = name;
        mFileType = fileType;
        mPermission = permission;
    }

    public void debug(String s) {
        if (mPermission == Permission.ALL) {
            System.out.print(mFileType.toString() + ": " + mName + ": " + s + "\n");
        }
    }

    public void reminder(String s) {
        if (mPermission == Permission.ALL || mPermission == Permission.REMINDERS_WARNINGS_ERRORS) {
            System.out.print("----------------------------------------------------------------------------\n"
                    + "REMINDER: " + mFileType.toString() + ": " + mName + ": " + s
                    + "\n----------------------------------------------------------------------------\n");
        }
    }

    public void warning(String s) {
        if (mPermission != Permission.ERRORS_ONLY) {
            System.out.print("****************************************************************************\n"
                    + "WARNING: " + mFileType.toString() + ": " + mName + ": " + s
                    + "\n****************************************************************************\n");

        }
    }

    public void error(String s) {
        System.out.print("****************************************************************************\n" + "ERROR: "
                + mFileType.toString() + ": " + mName + ": " + s
                + "\n****************************************************************************\n");

    }
}