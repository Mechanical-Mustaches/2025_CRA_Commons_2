// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.HashMap;
import java.util.Optional;

import com.revrobotics.spark.config.ClosedLoopConfig;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.util.Units;
import swervelib.math.Matter;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean constants. This
 * class should not be used for any other purpose. All constants should be declared globally (i.e. public static). Do
 * not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants
{
  public static final Vision USING_VISION = Vision.PHOTON_VISION;
  public static final boolean UPDATE_HEADING_FROM_VISION = true;  //if false heading is only from gyro
  public static final boolean GET_INITIAL_POSE_FROM_AUTO_ROUTINE = false;  
  public static final double ROBOT_MASS = (148 - 20.3) * 0.453592; // 32lbs * kg per pound
  public static final Matter CHASSIS    = new Matter(new Translation3d(0, 0, Units.inchesToMeters(8)), ROBOT_MASS);
  public static final double LOOP_TIME  = 0.13; //s, 20ms + 110ms sprk max velocity lag
  public static final double MAX_SPEED  = Units.feetToMeters(14.5);
  // Maximum speed of the robot in meters per second, used to limit acceleration.

//  public static final class AutonConstants
//  {
//
//    public static final PIDConstants TRANSLATION_PID = new PIDConstants(0.7, 0, 0);
//    public static final PIDConstants ANGLE_PID       = new PIDConstants(0.4, 0, 0.01);
//  }

  public static final class DrivebaseConstants
  {

    // Hold time on motor brakes when disabled
    public static final double WHEEL_LOCK_TIME = 10; // seconds
  }

  public static final class TankDrivebaseConstants
  {
    // CAN IDs
    public static final int LEFT_1  = 1;
    public static final int LEFT_2  = 2;
    public static final int RIGHT_1 = 3;
    public static final int RIGHT_2 = 4;
  }

  public static class OperatorConstants
  {

    // Joystick Deadband
    public static final double DEADBAND        = 0.1;
    public static final double LEFT_Y_DEADBAND = 0.1;
    public static final double RIGHT_X_DEADBAND = 0.1;
    public static final double TURN_CONSTANT    = 6;
  }

  public static class SensationConstants
  {
    public static final int enter = 3;
    public static final int hopperBack = 1;
    public static final int hopperFront = 0;
    public static final int exit = 2;
  }

  public static class Conveyor {
    public static final int MOTOR_CAN_ID = 15;
    public static final int CURRENT_LIMIT = 30;
  }

 public class Climber {
        public static final int MOTOR = 14;

        public static final double RADIANS_PER_REVOLUTION = (Math.PI * 2) / 125;

        public static final double P = 0;
        public static final double I = 0;
        public static final double D = 0;

        public static final double KS = 0;
        public static final double KG = 3.96;
        public static final double KV = 2.44;
        public static final double KA = 0.14;

        public static final ClosedLoopConfig CLOSED_LOOP_CONFIG = new ClosedLoopConfig()
                .p(P)
                .i(I)
                .d(D);
    }
  
  public enum Vision {
    NO_VISION,
    PHOTON_VISION,
    LIMELIGHT_VISION
  }

  public static class Positions {
    private static final HashMap<String, Pose2d> startingPoseOfAuto;

    static {
      startingPoseOfAuto = new HashMap<String, Pose2d>();
      startingPoseOfAuto.put("StandardLeft", new Pose2d(7.2, 7.5, Rotation2d.fromDegrees(-90))); 
      startingPoseOfAuto.put("StandardRight", new Pose2d(7.2, 0.5, Rotation2d.fromDegrees(90)));
      startingPoseOfAuto.put("StandardCenter", new Pose2d(7.165, 4, Rotation2d.fromDegrees(180)));
    }

    public static Optional<Pose2d> getPositionForRobot(String autoName) {
      Pose2d startingPose = startingPoseOfAuto.get(autoName);
      if (startingPose == null) {
        return Optional.empty();
      }
      else {
        return Optional.of(startingPose);
      }
        
    }       
  }
}