package org.firstinspires.ftc.teamcode.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;


@TeleOp
public class TeleOpMotor extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        //values

        int VSPosUp = 2495; ///2495
        //int HSPosExt = 100; ///1210
        int extended_position = 200;
        int retracted_position = 10;

        //mecanum motors

        //DcMotor frontLeftMotor = hardwareMap.dcMotor.get("leftFront");
        //DcMotor backLeftMotor = hardwareMap.dcMotor.get("leftRear");
       // DcMotor frontRightMotor = hardwareMap.dcMotor.get("rightFront");
       // DcMotor backRightMotor = hardwareMap.dcMotor.get("rightRear");

        //slider motors
        //DcMotor VSLMotor = hardwareMap.dcMotor.get("verticalLeft");
        //DcMotor VSRMotor = hardwareMap.dcMotor.get("verticalRight");
        DcMotor HSldMotor = hardwareMap.dcMotor.get("horizontalSlide");

        //mecanum reverse
        //frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        //backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        //slider reverse
        //VSRMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        //sliders reset/start encoder
        //VSLMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //VSRMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        HSldMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //VSLMotor.setTargetPosition(0);
        //VSLMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //VSRMotor.setTargetPosition(0);
        //VSRMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        HSldMotor.setTargetPosition(0);
        HSldMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);


        waitForStart();
        if (isStopRequested()) return;

        while (opModeIsActive()) {

            //mecanum functions



            //sliders function
            int HSld_value = HSldMotor.getCurrentPosition();
            int HSld_dest_value = HSldMotor.getTargetPosition();

            //vertical up function on game pad triangle


            //horizontal extend function on game pad square
            if (gamepad1.square) {
                HSldMotor.setTargetPosition(extended_position);
                //HSldMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                HSldMotor.setPower(0.1);
            }
            //horizontal retract function on game pad circle
            if (gamepad1.circle) {
                HSldMotor.setTargetPosition(retracted_position);
                HSldMotor.setPower(0.1);
            }

            // all telemetry
            telemetry.addData("horizontalValue", HSld_value);
            telemetry.addData("Horizontal Destination", HSld_dest_value);
            telemetry.update();
        }


    }
}
