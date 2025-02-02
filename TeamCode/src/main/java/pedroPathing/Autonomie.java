package pedroPathing;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.Path;
import com.pedropathing.pathgen.PathBuilder;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;
import com.pedropathing.util.Constants;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import pedroPathing.constants.FConstants;
import pedroPathing.constants.LConstants;

@Autonomous (name = "AutonomieCustom", group = "Autonomii")
public class Autonomie extends OpMode{

    // width 204mm, length 406mm
    // 37cm latime 40.5 lungime

    private Follower follower;
    private PathChain mainPath;

    private final Pose startPose = new Pose(10.1, 47.8);

    @Override
    public void init() {
        Constants.setConstants(FConstants.class, LConstants.class);
        follower = new Follower(hardwareMap);
        follower.setStartingPose(startPose);
        buildPaths();
    }

    @Override
    public void loop() {
        follower.update();
        startPathFollowing();
    }

    public void startPathFollowing() {
        if(!follower.isBusy()) {
            follower.followPath(mainPath, true);
        }
    }

    public void buildPaths() {
        mainPath = follower.pathBuilder()
                .addPath(
                        // Line 1
                        new BezierLine(
                                new Point(10.100, 47.782, Point.CARTESIAN),
                                new Point(34.395, 72.411, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                .addPath(
                        // Line 2
                        new BezierLine(
                                new Point(34.395, 72.411, Point.CARTESIAN),
                                new Point(36.370, 37.358, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                .addPath(
                        // Line 3
                        new BezierCurve(
                                new Point(36.370, 37.358, Point.CARTESIAN),
                                new Point(62.043, 33.902, Point.CARTESIAN),
                                new Point(61.385, 22.875, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                .addPath(
                        // Line 4
                        new BezierLine(
                                new Point(61.385, 22.875, Point.CARTESIAN),
                                new Point(12.178, 23.040, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                .addPath(
                        // Line 5
                        new BezierCurve(
                                new Point(12.178, 23.040, Point.CARTESIAN),
                                new Point(67.639, 27.813, Point.CARTESIAN),
                                new Point(61.550, 12.178, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                .addPath(
                        // Line 6
                        new BezierLine(
                                new Point(61.550, 12.178, Point.CARTESIAN),
                                new Point(12.507, 12.837, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                .addPath(
                        // Line 7
                        new BezierCurve(
                                new Point(12.507, 12.837, Point.CARTESIAN),
                                new Point(65.499, 14.647, Point.CARTESIAN),
                                new Point(61.714, 7.735, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                .addPath(
                        // Line 8
                        new BezierLine(
                                new Point(61.714, 7.735, Point.CARTESIAN),
                                new Point(12.343, 7.899, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                .addPath(
                        // Line 9
                        new BezierLine(
                                new Point(12.343, 7.899, Point.CARTESIAN),
                                new Point(16.691, 35.673, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                .addPath(
                        // Line 10
                        new BezierLine(
                                new Point(16.691, 35.673, Point.CARTESIAN),
                                new Point(35.018, 71.673, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                .addPath(
                        // Line 11
                        new BezierLine(
                                new Point(35.018, 71.673, Point.CARTESIAN),
                                new Point(16.527, 36.000, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                .addPath(
                        // Line 12
                        new BezierLine(
                                new Point(16.527, 36.000, Point.CARTESIAN),
                                new Point(34.527, 67.909, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                .addPath(
                        // Line 13
                        new BezierLine(
                                new Point(34.527, 67.909, Point.CARTESIAN),
                                new Point(16.691, 36.000, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                .addPath(
                        // Line 14
                        new BezierLine(
                                new Point(16.691, 36.000, Point.CARTESIAN),
                                new Point(34.200, 64.964, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                .addPath(
                        // Line 15
                        new BezierLine(
                                new Point(34.200, 64.964, Point.CARTESIAN),
                                new Point(16.364, 35.509, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                .addPath(
                        // Line 16
                        new BezierLine(
                                new Point(16.364, 35.509, Point.CARTESIAN),
                                new Point(33.873, 62.182, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                .addPath(
                        // Line 17
                        new BezierLine(
                                new Point(33.873, 62.182, Point.CARTESIAN),
                                new Point(7.899, 15.634, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                .build();
    }
}
