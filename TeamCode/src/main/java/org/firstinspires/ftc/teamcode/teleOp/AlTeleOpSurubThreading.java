package org.firstinspires.ftc.teamcode.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp
public class AlTeleOpSurubThreading extends LinearOpMode {

    // Subsystem states
    private enum DrivetrainState { IDLE, MOVING }
    private enum VerticalSliderState { IDLE, MOVING_UP, MOVING_DOWN, ADJUSTING_SERVO }
    private enum HorizontalSliderState { IDLE, EXTENDING, RETRACTING, ADJUSTING_SERVO }

    // State variables
    private DrivetrainState drivetrainState = DrivetrainState.IDLE;
    private VerticalSliderState verticalSliderState = VerticalSliderState.IDLE;
    private HorizontalSliderState horizontalSliderState = HorizontalSliderState.IDLE;

    // Motors and Servos
    private DcMotor frontLeftMotor, backLeftMotor, frontRightMotor, backRightMotor;
    private DcMotor VSLMotor, VSRMotor, HSldMotor;
    private Servo OutClaw, OutAngle, OutLeftPivot, OutRightPivot, InClaw, InTwist, InAngle, InLeftPivot, InRightPivot;

    // Timers
    private ElapsedTime stateTimer = new ElapsedTime();


    double multiplayer = 1.6;
    int VSPosUp = 1200, VBasUp = 2500, extended_position = 1200, retracted_position = 0;

    @Override
    public void runOpMode() throws InterruptedException {
        initializeHardware();

        waitForStart();
        if (isStopRequested()) return;

        while (opModeIsActive()) {
            handleDrivetrain();
            handleVerticalSlider();
            handleHorizontalSlider();
            updateTelemetry();
        }
    }

    private void initializeHardware() {
        // Initialize motors
        frontLeftMotor = hardwareMap.dcMotor.get("leftFront");
        backLeftMotor = hardwareMap.dcMotor.get("leftRear");
        frontRightMotor = hardwareMap.dcMotor.get("rightFront");
        backRightMotor = hardwareMap.dcMotor.get("rightRear");

        VSLMotor = hardwareMap.dcMotor.get("verticalLeft");
        VSRMotor = hardwareMap.dcMotor.get("verticalRight");
        HSldMotor = hardwareMap.dcMotor.get("horizontalSlide");

        // Set motor directions
        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        VSRMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        HSldMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        //
        VSLMotor.setTargetPosition(0);
        VSLMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        VSRMotor.setTargetPosition(0);
        VSRMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // Reset encoders
        VSLMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        VSRMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        HSldMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // Initialize servos
        OutClaw = hardwareMap.servo.get("outClaw");
        OutAngle = hardwareMap.servo.get("outAngle");
        OutLeftPivot = hardwareMap.servo.get("outLeftPivot");
        OutRightPivot = hardwareMap.servo.get("outRightPivot");
        InClaw = hardwareMap.servo.get("inClaw");
        InTwist = hardwareMap.servo.get("inTwist");
        InAngle = hardwareMap.servo.get("inAngle");
        InLeftPivot = hardwareMap.servo.get("inLeftPivot");
        InRightPivot = hardwareMap.servo.get("inRightPivot");

        OutRightPivot.setDirection(Servo.Direction.REVERSE);
        InRightPivot.setDirection(Servo.Direction.REVERSE);
        InAngle.setDirection(Servo.Direction.REVERSE);
        OutClaw.setDirection(Servo.Direction.REVERSE);

        // Set servo positions
        InClaw.setPosition(0);
        InTwist.setPosition(0.78);
        InAngle.setPosition(1);
        InLeftPivot.setPosition(0.05);
        InRightPivot.setPosition(0.05);
        OutClaw.setPosition(0);
        OutAngle.setPosition(0.35);
        OutLeftPivot.setPosition(0.92);
        OutRightPivot.setPosition(0.92);
    }

    private void handleDrivetrain() {
        switch (drivetrainState) {
            case IDLE:
                if (gamepad1.left_stick_y != 0 || gamepad1.left_stick_x != 0 || gamepad1.right_stick_x != 0) {
                    drivetrainState = DrivetrainState.MOVING;
                }
                break;

            case MOVING:
                double y = -gamepad1.left_stick_y * multiplayer;
                double x = gamepad1.left_stick_x * multiplayer;
                double rx = -gamepad1.right_stick_x * multiplayer;

                double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
                double frontLeftPower = (y + x + rx) / denominator;
                double backLeftPower = (y - x + rx) / denominator;
                double frontRightPower = (y - x - rx) / denominator;
                double backRightPower = (y + x - rx) / denominator;

                frontLeftMotor.setPower(frontLeftPower);
                backLeftMotor.setPower(backLeftPower);
                frontRightMotor.setPower(frontRightPower);
                backRightMotor.setPower(backRightPower);

                if (gamepad1.left_stick_y == 0 && gamepad1.left_stick_x == 0 && gamepad1.right_stick_x == 0) {
                    drivetrainState = DrivetrainState.IDLE;
                }
                break;
        }
    }

    private void handleVerticalSlider() {
        switch (verticalSliderState) {
            case IDLE:
                if (gamepad2.dpad_up) {
                    stateTimer.reset();
                    verticalSliderState = VerticalSliderState.MOVING_UP;
                } else if (gamepad2.dpad_down) {
                    stateTimer.reset();
                    verticalSliderState = VerticalSliderState.MOVING_DOWN;
                }
                break;

            case MOVING_UP:
                OutClaw.setPosition(0.3);
                if (stateTimer.milliseconds() > 300) {
                    InClaw.setPosition(0);
                    stateTimer.reset();
                }

                if (stateTimer.milliseconds() > 500 ) {
                    InClaw.setPosition(0);
                    InAngle.setPosition(0.9);
                    stateTimer.reset();
                }
                    VSLMotor.setTargetPosition(VBasUp);
                    VSRMotor.setTargetPosition(VBasUp);
                    VSLMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    VSRMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    VSLMotor.setPower(1);
                    VSRMotor.setPower(1);
                    OutClaw.setPosition(0.3);
                stateTimer.reset();
                if (stateTimer.milliseconds() > 100) {
                    OutLeftPivot.setPosition(0.4);
                    OutRightPivot.setPosition(0.4);
                    OutAngle.setPosition(0.3);
                } else {
                    if (!VSLMotor.isBusy() && !VSRMotor.isBusy()) {
                        verticalSliderState = VerticalSliderState.IDLE;
                    }
                }
                break;

            case MOVING_DOWN:
                if (stateTimer.milliseconds() < 300) {
                    OutClaw.setPosition(0);
                } else if (stateTimer.milliseconds() < 700) {
                    OutAngle.setPosition(0.35);
                } else if (stateTimer.milliseconds() < 1100) {
                    OutLeftPivot.setPosition(0.92);
                    OutRightPivot.setPosition(0.92);
                } else {
                    VSLMotor.setTargetPosition(0);
                    VSRMotor.setTargetPosition(0);
                    VSLMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    VSRMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    VSLMotor.setPower(1);
                    VSRMotor.setPower(1);

                    if (!VSLMotor.isBusy() && !VSRMotor.isBusy()) {
                        verticalSliderState = VerticalSliderState.IDLE;
                    }
                }
                break;
        }
    }

    private void handleHorizontalSlider() {
        switch (horizontalSliderState) {
            case IDLE:
                if (gamepad1.square) {
                    stateTimer.reset();
                    horizontalSliderState = HorizontalSliderState.EXTENDING;
                } else if (gamepad1.circle) {
                    stateTimer.reset();
                    horizontalSliderState = HorizontalSliderState.RETRACTING;
                }
                break;

            case EXTENDING:
                if (stateTimer.milliseconds() < 300) {
                    InClaw.setPosition(0);
                } else if (stateTimer.milliseconds() < 600) {
                    InAngle.setPosition(0);
                } else {
                    HSldMotor.setTargetPosition(extended_position);
                    HSldMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    HSldMotor.setPower(1);

                    if (!HSldMotor.isBusy()) {
                        horizontalSliderState = HorizontalSliderState.IDLE;
                    }
                }
                break;

            case RETRACTING:
                if (stateTimer.milliseconds() < 300) {
                    InClaw.setPosition(0.3);
                } else if (stateTimer.milliseconds() < 600) {
                    InAngle.setPosition(1);
                } else {
                    HSldMotor.setTargetPosition(retracted_position);
                    HSldMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    HSldMotor.setPower(1);

                    if (!HSldMotor.isBusy()) {
                        horizontalSliderState = HorizontalSliderState.IDLE;
                    }
                }
                break;
        }
    }

    private void updateTelemetry() {
        telemetry.addData("Drivetrain State", drivetrainState);
        telemetry.addData("Vertical Slider State", verticalSliderState);
        telemetry.addData("Horizontal Slider State", horizontalSliderState);
        telemetry.addData("OutClaw Position", OutClaw.getPosition());
        telemetry.addData("InAngle Position", InAngle.getPosition());
        telemetry.update();
    }
}