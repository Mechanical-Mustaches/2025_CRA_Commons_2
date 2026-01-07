package frc.robot.commands.swervedrive.vision;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.swervedrive.SwerveSubsystem;
import frc.robot.subsystems.swervedrive.Vision;


public class AprilTagAlignCommand extends Command {

    private final SwerveSubsystem swerve;
    private final Vision vision;

    private static final double DESIRED_DISTANCE = 1.0; // meters in front of tag
    private static final double POSITION_TOLERANCE = 0.05; // meters
    private static final double ANGLE_TOLERANCE_DEG = 3.0;

    public AprilTagAlignCommand(SwerveSubsystem swerve, Vision vision) {
        this.swerve = swerve;
        this.vision = vision;

        addRequirements(swerve);
    }

      /**
     * Returns the ID of the closest visible AprilTag.
     */
    private int getClosestVisibleTag() {
        double closestDistance = Double.MAX_VALUE;
        int bestTagId = 0;

        for (Vision.Cameras cam : Vision.Cameras.values()) {
            for (var result : cam.resultsList) {
                if (!result.hasTargets()) continue;

                for (var target : result.getTargets()) {
                    double distance = vision.getDistanceFromAprilTag(target.getFiducialId());
                    if (distance >= 0 && distance < closestDistance) {
                        closestDistance = distance;
                        bestTagId = target.getFiducialId();
                    }
                }
            }
        }

        return bestTagId;
    }

    @Override
    public void execute() {
        // Finds the closest visible tag
        int closestTagId = getClosestVisibleTag();
        SmartDashboard.putNumber("ClosestAprilTagID", closestTagId);
        if (closestTagId == 0) {
            // No tag visible -> don't move
            swerve.drive(new Translation2d(0, 0), 0, true);
            return;
            
        }

        // Drive to closest tag
        Pose2d targetPose = Vision.getAprilTagPose(closestTagId, new Transform2d(-DESIRED_DISTANCE, 0, null));
        swerve.driveToPose(targetPose);
    }

    @Override
    public boolean isFinished() {
        int closestTagId = getClosestVisibleTag();
        if (closestTagId == 0) {
            return false; // Wait until driver releases
        }

        Pose2d targetPose = Vision.getAprilTagPose(closestTagId, new Transform2d(-DESIRED_DISTANCE, 0, null));
        Pose2d currentPose = swerve.getPose();

        boolean positionAligned = currentPose.getTranslation().getDistance(targetPose.getTranslation()) < POSITION_TOLERANCE;
        boolean angleAligned = currentPose.getRotation().minus(targetPose.getRotation()).getDegrees() < ANGLE_TOLERANCE_DEG;

        return positionAligned && angleAligned;
    }

    @Override
    public void end(boolean interrupted) {
        swerve.drive(new Translation2d(0, 0), 0, true); // stop robot
    }

  
}