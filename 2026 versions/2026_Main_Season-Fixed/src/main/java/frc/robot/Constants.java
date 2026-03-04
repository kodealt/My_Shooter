// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
  }

  public static class ShooterConstants {
    public static final int left_shooter_id = 3;
    public static final int right_shooter_id = 2; // need to change all of these
    public static final int pivot_id = 4;

    public static final double radius_of_wheels = 0.5;
    public static final double heightofAprilTag = 1.6002; // need to change later

    public static final double SHOOTER_KP = 0.05; // need to change PIDs
    public static final double SHOOTER_KI = 0.0; 
    public static final double SHOOTER_KD = 0.0;

    public static final double AIM_KP = 0.05; // need to change PIDs
    public static final double AIM_KI = 0.0;
    public static final double AIM_KD = 0.0;


    public static final double AngleOfLimeLight = 25; // need to change
    public static final double heightOfLimeLight = 1;  // need to change
    public static final double heightOfTarget = 2.1336; // 7 feet in meters (need to change)

    public static final int LimeLightID_RED = 1;
    public static final int LimeLightID_BLUE = 2;
  }
}
