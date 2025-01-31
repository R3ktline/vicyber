package org.firstinspires.ftc.teamcode.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;


@TeleOp
public class TeleOpServoTest extends LinearOpMode {
    public Servo InClaw;
    @Override

    //public void init(){

    //}
    public void runOpMode() throws InterruptedException {

        //decalre servos

        //Servo OutClaw = hardwareMap.servo.get("outClaw");
        //Servo OutAngle = hardwareMap.servo.get("outAngle");
        //Servo OutLeftPivot = hardwareMap.servo.get("outLeftPivot");
        //Servo OutRightPivot = hardwareMap.servo.get("outRightPivot");
        Servo InClaw = hardwareMap.servo.get("inClaw");
        Servo InTwist = hardwareMap.servo.get("inTwist");
        Servo InAngle = hardwareMap.servo.get("inAngle");
        Servo InLeftPivot = hardwareMap.servo.get("inLeftPivot");
        Servo InRightPivot = hardwareMap.servo.get("inRightPivot");

        //reverse servos

        //OutRightPivot.setDirection(Servo.Direction.REVERSE);
        InRightPivot.setDirection(Servo.Direction.REVERSE);
        InAngle.setDirection(Servo.Direction.REVERSE);
        //InTwist.setDirection(Servo.Direction.REVERSE);

        //init servos

        //OutClaw.setPosition(0);
        //OutAngle.setPosition(0);
        //OutLeftPivot.setPosition(0);
        //OutRightPivot.setPosition(0);
        InClaw.setPosition(0);
        InTwist.setPosition(0.78);
        InAngle.setPosition(1);
        InLeftPivot.setPosition(0);
        InRightPivot.setPosition(0);




        waitForStart();

        if (isStopRequested()) return;
        while (opModeIsActive()) {

                //claw

            /*if (gamepad1.triangle){
                InClaw.setPosition(0.2);
            }

            if (gamepad1.cross){
                InClaw.setPosition(0);
            }

             */
                // twist
            if (gamepad1.square){
                InTwist.setPosition(0);
            }

            if (gamepad1.circle){
                InTwist.setPosition(0.78);
            }
                //Angle

            if (gamepad1.dpad_up){
                InAngle.setPosition(1);
            }

            if (gamepad1.dpad_down){
                InAngle.setPosition(0);
            }
                //Pivot

            if (gamepad1.dpad_left){
                InRightPivot.setPosition(0);
                InLeftPivot.setPosition(0);
            }

            if (gamepad1.dpad_right){
                InRightPivot.setPosition(0.35);
                InLeftPivot.setPosition(0.35);
            }


            if (gamepad1.triangle){
                InClaw.setPosition(0);
                sleep(50);  //100
                InAngle.setPosition(0);
                sleep(50);  //100
                InTwist.setPosition(0);
                sleep(50);  //100
                InRightPivot.setPosition(0.35);
                InLeftPivot.setPosition(0.35);
            }

            if (gamepad1.cross){
                InClaw.setPosition(0.2);
                sleep(100); //100
                InRightPivot.setPosition(0);
                InLeftPivot.setPosition(0);
                sleep(50);  //100
                InTwist.setPosition(0.78);
                sleep(50);  //100
                InAngle.setPosition(1);


            }


            telemetry.addData("Claw", InClaw.getPosition());
            telemetry.addData("Twist", InTwist.getPosition());
            telemetry.addData("Angle", InAngle.getPosition());
            telemetry.addData("Right", InRightPivot.getPosition());
            telemetry.addData("Left", InLeftPivot.getPosition());
            telemetry.update();
        }


    }
}
