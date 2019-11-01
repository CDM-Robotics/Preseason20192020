package team6072.robo2019.pid;

import team6072.robo2019.constants.PIDControllerConstants;
import team6072.robo2019.pid.DataSource;
import java.util.ArrayList;

public class MyPIDController extends Thread {

    private static ArrayList<MyPIDController> mPIDs;
    
    private static void addPID(MyPIDController myPIDController){
        if(mPIDs == null){
            mPIDs = new ArrayList<MyPIDController>();
        }
        mPIDs.add(myPIDController);
    }

    public static void diableAllPIDs(){
        for(MyPIDController myPIDController : mPIDs){
            myPIDController.end();
        }
    }

    private final int TIME_INBETWEEN_EXECUTIONS = PIDControllerConstants.TIME_INBETWEEN_EXECUTIONS;

    // Blocker booleans // to prevent things from going too early or too long
    private boolean mRunnable = false;
    private boolean hasDeadBand = false;

    // constants //
    private double mP;
    private double mI;
    private double mD;
    private double mF;
    private double mUpperDeadband;
    private double mLowerDeadband;
    private double mBasePercentOut;
    private double mMaxOutput;
    private double mMinOutput;
    private double mSetpoint;

    private double mPriorPosition;
    private double mAccumulatedError = 0;
    private double mOutput = 0;

    private DataSource mDataSource;

    /**
     * Remember to use PIDCalc.start() to start the thread and PIDCalc.stop() to
     * stop the thread
     * 
     * @param p
     * @param i
     * @param d
     * @param f
     * @param deadband
     * @param dataSource
     * @param maxOutput
     * @param minOutput
     */
    public MyPIDController(double p, double i, double d, double f, DataSource dataSource, double maxOutput,
            double minOutput) {
        mDataSource = dataSource;
        mP = Math.abs(p);
        mI = Math.abs(i);
        mD = Math.abs(d);
        mF = Math.abs(f);
        mMaxOutput = maxOutput;
        mMinOutput = minOutput;
        mPriorPosition = dataSource.getData();
        mSetpoint = 0.0;
        addPID(this);
    }

    public void end() {
        mRunnable = false;
        mAccumulatedError = 0;
        mOutput = 0;
    }

    public double getOutput() {
        return mOutput;
    }

    public void setSetpoint(double setpoint) {
        mSetpoint = setpoint;
        mAccumulatedError = 0;
    }

    public boolean polarity(double err) {
        if (err > 0) {
            return true;
        } else {
            return false;
        }
    }

    public void run() {
        mRunnable = true;
        while (mRunnable) {
            double curnPosition = mDataSource.getData();
            boolean polarity = polarity(mSetpoint - curnPosition);
            double err = Math.abs(mSetpoint - curnPosition);
            double rateOfChange = Math.abs(mPriorPosition - curnPosition) / TIME_INBETWEEN_EXECUTIONS;

            double output = (err * mP) + (mAccumulatedError * mI) + -(rateOfChange * mD) + mF;

            if (!polarity) {
                output = output * -1;
            }
            if (output > mMaxOutput) {
                output = mMaxOutput;
            }
            if (output < mMinOutput) {
                output = mMinOutput;
            }
            if (hasDeadBand) {
                if (output > mBasePercentOut) {
                    double targetPercentOut = (output - mBasePercentOut) / (mMaxOutput - mBasePercentOut);
                    double scaledOutput = (targetPercentOut * (mMaxOutput - mUpperDeadband)) + mUpperDeadband;
                    output = scaledOutput;
                } else if (output < mBasePercentOut) {
                    double targetPercentOut = (output - mBasePercentOut) / (mMinOutput - mBasePercentOut);
                    double scaledOutput = (targetPercentOut * (mMinOutput - mLowerDeadband)) - mLowerDeadband;
                    output = scaledOutput;
                }
            }
            mOutput = output;
            mPriorPosition = curnPosition;
            mAccumulatedError = mAccumulatedError + err * TIME_INBETWEEN_EXECUTIONS;

            try {
                Thread.sleep(TIME_INBETWEEN_EXECUTIONS);
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }

    }

    public void setDeadband(double upperDeadband, double lowerDeadband, double basePercentOut) {
        hasDeadBand = true;
        mUpperDeadband = upperDeadband;
        mLowerDeadband = lowerDeadband;
        mBasePercentOut = basePercentOut;

    }

}