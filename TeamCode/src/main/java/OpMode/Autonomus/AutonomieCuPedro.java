package OpMode.Autonomus;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;
import com.pedropathing.util.Constants;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import pedroPathing.constants.FConstants;
import pedroPathing.constants.LConstants;

@Autonomous (name = "AutonomieCustom", group = "Autonomii")
public class AutonomieCuPedro extends OpMode {

    // 37cm latime 40.5 lungime
    // pedroPathing size: 14.5, 16 (in inches)

    private Follower follower;

    // current path state:
    private int state = 0;

    // start pose:
    private final Pose startPose = new Pose(10.1, 47.8);

    // paths:
    private PathChain toBarWithPreload,
                      toFirstSample, pushFirstSample,
                      toSecondSample, pushSecondSample,
                      toThirdSample, pushThirdSample,
                      toFirstSpecimen, toBarWithFirstSpecimen,
                      toSecondSpecimen, toBarWithSecondSpecimen,
                      toThirdSpecimen, toBarWithThirdSpecimen,
                      toPark;

    // extra data:
    private Telemetry telemetry;
    private final Timer timer = new Timer();

    // events:
    @Override
    public void init() {
        Constants.setConstants(FConstants.class, LConstants.class);
        follower = new Follower(hardwareMap);
        follower.setStartingPose(startPose);
        buildPaths();
    }

    @Override
    public void start() {
        timer.resetTimer();
        state = 0;
    }

    @Override
    public void stop() {
        log("All autonomous paths finished.");
    }

    @Override
    public void loop() {
        follower.update();
        startPathFollowing();
    }

    // logging:
    public void log(String message) {
        telemetry.addData(message, timer.getElapsedTime());
        telemetry.update();
        timer.resetTimer();
    }

    // decide what path to follow based on current path state and weather the follower is currently busy:
    public void startPathFollowing() {
        switch (state) {
            case 0:
                follower.followPath(toBarWithPreload, true);
                log("going to bar with preload");
                state = 1;
                break;
            case 1:
                if (!follower.isBusy()) {
                    // TODO: put preload on bar
                    log("putting preload on bar");
                    state = 2;
                }
                break;
            case 2:
                if (!follower.isBusy()) {
                    follower.followPath(toFirstSample, true);
                    log("going to first sample");
                    state = 2;
                }
                break;
            case 3:
                if (!follower.isBusy()) {
                    follower.followPath(pushFirstSample, true);
                    log("pushing first sample toward human player");
                    state = 3;
                }
                break;
            case 4:
                if (!follower.isBusy()) {
                    follower.followPath(toSecondSample, true);
                    log("going to second sample");
                    state = 4;
                }
                break;
            case 5:
                if (!follower.isBusy()) {
                    follower.followPath(pushSecondSample, true);
                    log("pushing second sample toward human player");
                    state = 5;
                }
                break;
            case 6:
                if (!follower.isBusy()) {
                    follower.followPath(toThirdSample, true);
                    log("going to third sample");
                    state = 6;
                }
                break;
            case 7:
                if (!follower.isBusy()) {
                    follower.followPath(pushThirdSample, true);
                    log("pushing third sample toward human player");
                    state = 8;
                }
                break;
            case 8:
                if(!follower.isBusy()) {
                    follower.followPath(toFirstSpecimen, true);
                    log("going to first specimen");
                    state = 9;
                }
                break;
            case 9:
                if (!follower.isBusy()) {
                    // TODO: make arm take first specimen
                    log("taking the first specimen");
                    state = 10;
                }
                break;
            case 10:
                if(!follower.isBusy()) {
                    follower.followPath(toBarWithFirstSpecimen);
                    log("moving toward bar with first specimen");
                    state = 11;
                }
                break;
            case 11:
                if (!follower.isBusy()) {
                    // TODO: make arm put first specimen on bar
                    log("putting first specimen on bar");
                    state = 12;
                }
                break;
            case 12:
                if(!follower.isBusy()) {
                    follower.followPath(toSecondSpecimen, true);
                    log("going to second specimen");
                    state = 13;
                }
                break;
            case 13:
                if (!follower.isBusy()) {
                    // TODO: make arm take second specimen
                    log("taking the second specimen");
                    state = 14;
                }
                break;
            case 14:
                if(!follower.isBusy()) {
                    follower.followPath(toBarWithSecondSpecimen);
                    log("moving toward bar with second specimen");
                    state = 15;
                }
                break;
            case 15:
                if (!follower.isBusy()) {
                    // TODO: make arm put second specimen on bar
                    log("putting second specimen on bar");
                    state = 16;
                }
                break;
            case 16:
                if(!follower.isBusy()) {
                    follower.followPath(toThirdSpecimen, true);
                    log("going to third specimen");
                    state = 17;
                }
                break;
            case 17:
                if (!follower.isBusy()) {
                    // TODO: make arm take third specimen
                    log("taking the third specimen");
                    state = 18;
                }
                break;
            case 18:
                if(!follower.isBusy()) {
                    follower.followPath(toBarWithThirdSpecimen);
                    log("moving toward bar with third specimen");
                    state = 19;
                }
                break;
            case 19:
                if (!follower.isBusy()) {
                    // TODO: make arm put third specimen on bar
                    log("putting third specimen on bar");
                    state = 20;
                }
                break;
            case 20:
                if(!follower.isBusy()) {
                    follower.followPath(toPark);
                    log("parking");
                    state = -1;
                }
                break;
        }
    }

    // build all paths:
    public void buildPaths() {
        toBarWithPreload = follower.pathBuilder()
                .addPath(
                        // Line 1
                        new BezierLine(
                                new Point(10.100, 47.782, Point.CARTESIAN),
                                new Point(34.395, 72.411, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                .build();

        toFirstSample = follower.pathBuilder()
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
                .build();

        pushFirstSample = follower.pathBuilder()
                .addPath(
                        // Line 4
                        new BezierLine(
                                new Point(61.385, 22.875, Point.CARTESIAN),
                                new Point(12.178, 23.040, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                .build();

        toSecondSample = follower.pathBuilder()
                .addPath(
                        // Line 5
                        new BezierCurve(
                                new Point(12.178, 23.040, Point.CARTESIAN),
                                new Point(67.639, 27.813, Point.CARTESIAN),
                                new Point(61.550, 12.178, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                .build();

        pushSecondSample = follower.pathBuilder()
                .addPath(
                        // Line 6
                        new BezierLine(
                                new Point(61.550, 12.178, Point.CARTESIAN),
                                new Point(12.507, 12.837, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                .build();

        toThirdSample = follower.pathBuilder()
                .addPath(
                        // Line 7
                        new BezierCurve(
                                new Point(12.507, 12.837, Point.CARTESIAN),
                                new Point(65.499, 14.647, Point.CARTESIAN),
                                new Point(61.714, 7.735, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                .build();

        pushThirdSample = follower.pathBuilder()
                .addPath(
                        // Line 8
                        new BezierLine(
                                new Point(61.714, 7.735, Point.CARTESIAN),
                                new Point(12.343, 7.899, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                .build();

        toFirstSpecimen = follower.pathBuilder()
                .addPath(
                        // Line 9
                        new BezierLine(
                                new Point(12.343, 7.899, Point.CARTESIAN),
                                new Point(16.691, 35.673, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                .build();

        toBarWithFirstSpecimen = follower.pathBuilder()
                .addPath(
                        // Line 10
                        new BezierLine(
                                new Point(16.691, 35.673, Point.CARTESIAN),
                                new Point(35.018, 71.673, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                .build();

        toSecondSpecimen = follower.pathBuilder()
                .addPath(
                        // Line 11
                        new BezierLine(
                                new Point(35.018, 71.673, Point.CARTESIAN),
                                new Point(16.527, 36.000, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                .build();

        toBarWithSecondSpecimen = follower.pathBuilder()
                .addPath(
                        // Line 12
                        new BezierLine(
                                new Point(16.527, 36.000, Point.CARTESIAN),
                                new Point(34.527, 67.909, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                .build();

        toThirdSpecimen = follower.pathBuilder()
                .addPath(
                        // Line 13
                        new BezierLine(
                                new Point(34.527, 67.909, Point.CARTESIAN),
                                new Point(16.691, 36.000, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                .build();

        toBarWithThirdSpecimen = follower.pathBuilder()
                .addPath(
                        // Line 14
                        new BezierLine(
                                new Point(16.691, 36.000, Point.CARTESIAN),
                                new Point(34.200, 64.964, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                .build();

        toPark = follower.pathBuilder()
                .addPath(
                        // Line 15
                        new BezierLine(
                                new Point(33.873, 62.182, Point.CARTESIAN),
                                new Point(7.899, 15.634, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                .build();
    }
}
