package com.team3175.frc2022.lib.math;

import java.io.IOException;
import java.nio.file.Path;

import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Conversions {

    /**
     * 
     * Convert falcon counts to degrees
     * 
     * @param counts Falcon Counts
     * @param gearRatio Gear Ratio between Falcon and Mechanism
     * @return Degrees of Rotation of Mechanism
     * 
     */

    public static double falconToDegrees(double counts, double gearRatio) {

        return counts * (360.0 / (gearRatio * 2048.0));

    }

    /**
     * 
     * Convert degrees to falcon counts
     * 
     * @param degrees Degrees of rotation of Mechanism
     * @param gearRatio Gear Ratio between Falcon and Mechanism
     * @return Falcon Counts
     * 
     */

    public static double degreesToFalcon(double degrees, double gearRatio) {

        double ticks =  degrees / (360.0 / (gearRatio * 2048.0));
        return ticks;

    }

    /**
     * 
     * Convert native falcon units to RPM
     * 
     * @param velocityCounts Falcon Velocity Counts
     * @param gearRatio Gear Ratio between Falcon and Mechanism (set to 1 for Falcon RPM)
     * @return RPM of Mechanism
     * 
     */

    public static double falconToRPM(double velocityCounts, double gearRatio) {

        double motorRPM = velocityCounts * (600.0 / 2048.0);        
        double mechRPM = motorRPM / gearRatio;
        return mechRPM;

    }

    /**
     * 
     * Convert RPM to native falcon units
     * 
     * @param RPM RPM of mechanism
     * @param gearRatio Gear Ratio between Falcon and Mechanism (set to 1 for Falcon RPM)
     * @return RPM of Mechanism
     * 
     */

    public static double RPMToFalcon(double RPM, double gearRatio) {

        double motorRPM = RPM * gearRatio;
        double sensorCounts = motorRPM * (2048.0 / 600.0);
        return sensorCounts;

    }

    /**
     * 
     * Convert falcon native units to meters per second
     * 
     * @param velocitycounts Falcon Velocity Counts
     * @param circumference Circumference of Wheel
     * @param gearRatio Gear Ratio between Falcon and Mechanism (set to 1 for Falcon RPM)
     * @return Falcon Velocity Counts
     * 
     */

    public static double falconToMPS(double velocitycounts, double circumference, double gearRatio){

        double wheelRPM = falconToRPM(velocitycounts, gearRatio);
        double wheelMPS = (wheelRPM * circumference) / 60;
        return wheelMPS;

    }

    /**
     * 
     * Convert Meters per second to falcon native units
     * 
     * @param velocity Velocity MPS
     * @param circumference Circumference of Wheel
     * @param gearRatio Gear Ratio between Falcon and Mechanism (set to 1 for Falcon RPM)
     * @return Falcon Velocity Counts
     * 
     */

    public static double MPSToFalcon(double velocity, double circumference, double gearRatio){

        double wheelRPM = ((velocity * 60) / circumference);
        double wheelVelocity = RPMToFalcon(wheelRPM, gearRatio);
        return wheelVelocity;

    }

    /**
     * 
     * Convert a CSV file to a WPILIB Trajectory
     * 
     * @return A trajectory generated from a JSON csv file
     * 
     */

    public static Trajectory toTrajectory(String path) {

        String trajectoryJSON = path;
        try {
            Path trajectoryPath = Filesystem.getDeployDirectory().toPath().resolve(trajectoryJSON);
            Trajectory trajectory = TrajectoryUtil.fromPathweaverJson(trajectoryPath);
            return trajectory;
        } catch (IOException e) {
            SmartDashboard.putString("conversion error", e.toString());
            return null;
        }

    }

    

}
