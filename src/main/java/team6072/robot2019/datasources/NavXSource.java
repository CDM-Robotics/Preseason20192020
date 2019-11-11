package team6072.robot2019.datasources;

import team6072.robot2019.subsystems.NavXSys;

public class NavXSource extends DataSourceBase {

    private NavXSys mNavxSys;
    private NavXDataTypes mNavXDataTypes;

    public enum NavXDataTypes {
        YAW, TILT, PITCH;
    }

    public NavXSource(NavXDataTypes navXDataType) {
        mNavxSys = NavXSys.getInstance();
        mNavXDataTypes = navXDataType;
    }

    public double getData() {
        if (mNavXDataTypes == NavXDataTypes.YAW) {
            return mNavxSys.getYaw();
        } else if (mNavXDataTypes == NavXDataTypes.PITCH) {
            return mNavxSys.getPitch();
        } else if (mNavXDataTypes == NavXDataTypes.TILT) {
            return mNavxSys.getRoll();
        } else {
            return 0.0;
        }

    }

}