package team6072.robo2019.logging;

import team6072.robo2019.datasources.DataSourceBase;

public class SuperLog extends Thread{

    private DataSourceBase mDataSourceBase;

    public SuperLog(DataSourceBase dataSourceBase, boolean on) {
        mDataSourceBase = dataSourceBase;
    }

    public void run(){
        // send data to excel sheet
        
    }
    

    public void end(){
        // ends the thread
    }

}