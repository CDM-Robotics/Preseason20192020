package team6072.robo2019.watchdog;

import java.util.TimerTask;
import team6072.robo2019.datasources.DataSourceBase;
import java.util.Timer;

public class Watchdog {

    private Timer mWatchDog;
    private TimerTask mWatchDogTask;
    private boolean mMoveable;
    private DataSourceBase mDataSourceBase;

    /**
     * Watchdog sets up the timer and timser task for you - Note that the thing
     * being tracked are the Quadtrature encoder ticks
     * 
     * - This function is different in that it does not set the Talon to its
     * defaultOutput
     * 
     * @param limit The number you want the motor to stop at
     * @param talon The Talon being affected
     */
    public Watchdog(int limit, DataSourceBase dataSourceBase) {
        mDataSourceBase = dataSourceBase;
        mMoveable = true;
        mWatchDog = new Timer();
        mWatchDogTask = new TimerTask() {

            @Override
            public void run() {
                double currentPosition = mDataSourceBase.getData();
                if (currentPosition > limit) {
                    mMoveable = false;
                } else {
                    mMoveable = true;
                }
            }
        };
        mWatchDog.schedule(mWatchDogTask, 1000, 50);

    }

    /**
     * Watchdog sets up the timer and timser task for you - Note that the thing
     * being tracked are the Quadtrature encoder ticks
     * 
     * - This function is different in that it will not re-enable the motor until
     * the sensor moves back within a specified enable limit
     * 
     * @param limit         The number you want the motor to stop at
     * @param defaultOutput The number the motor will become when the limit is
     *                      reached
     * @param talon         The Talon being affected
     * @param enableLimit   The number that will turn the motor back on
     */
    public Watchdog(int limit, DataSourceBase dataSourceBase, int enableLimit) {
        mDataSourceBase = dataSourceBase;
        mMoveable = true;
        mWatchDog = new Timer();
        mWatchDogTask = new TimerTask() {

            @Override
            public void run() {
                double currentPosition = mDataSourceBase.getData();
                if (currentPosition > limit) {
                    mMoveable = false;
                } else if (currentPosition < enableLimit) {
                    mMoveable = true;
                }

            }
        };
        mWatchDog.schedule(mWatchDogTask, 1000, 50);

    }

    /**
     * Returns if the motor is allowed to move or not To use this, you may - 1 set
     * this in front of each move function to properly kill the motor if it goes
     * outside the limit - 2 or set this in front of a repeating function to kill
     * the motor
     */
    public boolean canMove() {
        return mMoveable;
    }

}