package OpMode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class TeleOpOutTaka extends LinearOpMode{

    @Override

    public void runOpMode() throws InterruptedException {

        //declare servos, InXx - intake servos, OutXx - outtake servos

        Servo OutClaw = hardwareMap.servo.get("outClaw");
        Servo OutAngle = hardwareMap.servo.get("outAngle");
        Servo OutLeftPivot = hardwareMap.servo.get("outLeftPivot");
        Servo OutRightPivot = hardwareMap.servo.get("outRightPivot");
        //Servo InClaw = hardwareMap.servo.get("inClaw");
        //Servo InTwist = hardwareMap.servo.get("inTwist");
        //Servo InAngle = hardwareMap.servo.get("inAngle");
        //Servo InLeftPivot = hardwareMap.servo.get("inLeftPivot");
        //Servo InRightPivot = hardwareMap.servo.get("inRightPivot");

        //reverse servos

        //OutRightPivot.setDirection(Servo.Direction.REVERSE);
        OutRightPivot.setDirection(Servo.Direction.REVERSE);
        OutClaw.setDirection(Servo.Direction.REVERSE);
        //OutAngle.setDirection(Servo.Direction.REVERSE);
        //InTwist.setDirection(Servo.Direction.REVERSE);

        //init servos

        //OutClaw.setPosition(0);
        //OutAngle.setPosition(0.5);
        OutLeftPivot.setPosition(0.95);
        OutRightPivot.setPosition(0.95);
        OutClaw.setPosition(0);
        OutAngle.setPosition(0.35);
        //OutLeftPivot.setPosition(0);
        //OutRightPivot.setPosition(0);


        waitForStart();
        if (isStopRequested()) return;
        while (opModeIsActive()) {

        if (gamepad1.square){
            OutClaw.setPosition(0);
        }

        if (gamepad1.circle){
            OutClaw.setPosition(0.3);
        }

        if (gamepad1.triangle){
            OutAngle.setPosition(0.4);
        }

        if (gamepad1.cross){
            OutAngle.setPosition(0.2);
        }

        if (gamepad1.dpad_up){
            OutLeftPivot.setPosition(0.95);
            OutRightPivot.setPosition(0.95);
        }

        if (gamepad1.dpad_down){
            OutLeftPivot.setPosition(0.2);
            OutRightPivot.setPosition(0.2);
        }

            if (gamepad1.dpad_left){
                OutClaw.setPosition(0.3);
                sleep(100);
                OutLeftPivot.setPosition(0.2);
                OutRightPivot.setPosition(0.2);
                OutAngle.setPosition(0.3);


            }

            if (gamepad1.dpad_right){
                OutAngle.setPosition(0.65);
                sleep(500);
                OutClaw.setPosition(0);
                sleep(200);
                OutAngle.setPosition(0.35);
                sleep(100);
                OutLeftPivot.setPosition(0.95);
                OutRightPivot.setPosition(0.95);

            }


        telemetry.addData("Claw", OutClaw.getPosition());
        telemetry.addData("Angle", OutAngle.getPosition());
        telemetry.addData("RightPivot", OutRightPivot.getPosition());
        telemetry.addData("LeftPivot", OutLeftPivot.getPosition());
        telemetry.update();
        }
    }
}