package OpMode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;


    public class AlTeleOpSurubNew extends LinearOpMode {

    double multiplayer = 1.6;

    @Override
    public void runOpMode() throws InterruptedException {

        //values

        int VSPosUp = 1200; //2495 is max pozitia bara
        int hangPos =1440;
        int VBasUp = 2500; // pozitie basket sus
        //int HSPosExt = 100; ///1210 is max
        int extended_position = 1200;
        int retracted_position = 0;



        //slider motors
        DcMotor VSLMotor = hardwareMap.dcMotor.get("verticalLeft");
        DcMotor VSRMotor = hardwareMap.dcMotor.get("verticalRight");
        DcMotor HSldMotor = hardwareMap.dcMotor.get("horizontalSlide");


        //slider reverse
        VSRMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        HSldMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        //sliders reset/start encoder
        VSLMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        VSRMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        HSldMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        VSLMotor.setTargetPosition(0);
        VSLMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        VSRMotor.setTargetPosition(0);
        VSRMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //HSldMotor.setTargetPosition(0);
        //HSldMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //declare servos

        Servo OutClaw = hardwareMap.servo.get("outClaw");
        Servo OutAngle = hardwareMap.servo.get("outAngle");
        Servo OutLeftPivot = hardwareMap.servo.get("outLeftPivot");
        Servo OutRightPivot = hardwareMap.servo.get("outRightPivot");
        Servo InClaw = hardwareMap.servo.get("inClaw");
        Servo InTwist = hardwareMap.servo.get("inTwist");
        Servo InAngle = hardwareMap.servo.get("inAngle");
        Servo InLeftPivot = hardwareMap.servo.get("inLeftPivot");
        Servo InRightPivot = hardwareMap.servo.get("inRightPivot");

        //reverse servos

        OutRightPivot.setDirection(Servo.Direction.REVERSE);
        InRightPivot.setDirection(Servo.Direction.REVERSE);
        InAngle.setDirection(Servo.Direction.REVERSE);
        OutClaw.setDirection(Servo.Direction.REVERSE);
        //InTwist.setDirection(Servo.Direction.REVERSE);

        //init servos

        InClaw.setPosition(0);
        InTwist.setPosition(0.78);
        InAngle.setPosition(1);
        InLeftPivot.setPosition(0.05);
        InRightPivot.setPosition(0.05);

        //sleep(1000);

        OutClaw.setPosition(0);
        OutAngle.setPosition(0.35);
        OutLeftPivot.setPosition(0.92);
        OutRightPivot.setPosition(0.92);



        waitForStart();
        if (isStopRequested()) return;

        while (opModeIsActive()) {

            //mecanum functions
            int VSliderPos = VSLMotor.getCurrentPosition();

            //sliders function
            int HSld_value = HSldMotor.getCurrentPosition();
            int HSld_dest_value = HSldMotor.getTargetPosition();
            int VSR_value = VSRMotor.getCurrentPosition();
            int VSL_value = VSLMotor.getCurrentPosition();

            if(gamepad1.share){
                multiplayer = 0.7;
            }

                //vertical up function on game pad 1 Triangle button
            if (gamepad2.dpad_up){
                OutClaw.setPosition(0.3);
                sleep(300);
                InClaw.setPosition(0);
                InAngle.setPosition(0.9);
                sleep(400);
                VSRMotor.setTargetPosition(VBasUp);
                VSRMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                VSRMotor.setPower(1);
                VSLMotor.setTargetPosition(VBasUp);
                VSLMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                VSLMotor.setPower(1);
                OutClaw.setPosition(0.3);
                sleep(100);
                OutLeftPivot.setPosition(0.4);
                OutRightPivot.setPosition(0.4);
                OutAngle.setPosition(0.3);
            }

                //vertical down function on game pad 1 Cross button
            if (gamepad2.dpad_down) {
                OutClaw.setPosition(0);
                sleep(400);
                OutAngle.setPosition(0.35);
                sleep(100);
                OutLeftPivot.setPosition(0.92);
                OutRightPivot.setPosition(0.92);
                sleep(400);
                VSLMotor.setTargetPosition(0);
                VSLMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                VSLMotor.setPower(1);
                VSRMotor.setTargetPosition(0);
                VSRMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                VSRMotor.setPower(1);

            }


            /*if (gamepad2.triangle){
                VSRMotor.setTargetPosition(hangPos);
                VSRMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                VSRMotor.setPower(1);
            }
            if (gamepad2.triangle) {
                VSLMotor.setTargetPosition(hangPos);
                VSLMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                VSLMotor.setPower(1);
            }
            //vertical down function on game pad 2 cross button

            if (gamepad2.cross) {
                VSLMotor.setTargetPosition(0);
                VSLMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                VSLMotor.setPower(1);
            }
            if (gamepad2.cross) {
                VSRMotor.setTargetPosition(0);
                VSRMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                VSRMotor.setPower(1);
            }

             */

                //horizontal extend function on game pad 1 square button
            if (gamepad1.square) {
                HSldMotor.setTargetPosition(extended_position);
                HSldMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                HSldMotor.setPower(1);

                InClaw.setPosition(0);
                sleep(50);  //100
                InAngle.setPosition(0);
                sleep(50);  //100
                InTwist.setPosition(0);
                sleep(50);  //100
                InRightPivot.setPosition(0.3);
                InLeftPivot.setPosition(0.3);

                multiplayer = 0.7;
            }
                //horizontal retract function on game pad circle
            if (gamepad1.circle) {

                InRightPivot.setPosition(0.35);
                InLeftPivot.setPosition(0.35);
                sleep(100);
                InClaw.setPosition(0.3); //0.35
                sleep(300);
                InRightPivot.setPosition(0.1);
                InLeftPivot.setPosition(0.1);
                sleep(50);  //100
                InTwist.setPosition(0.78);
                sleep(50);  //100
                InAngle.setPosition(1);
                sleep(50);
                HSldMotor.setTargetPosition(retracted_position);
                HSldMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                HSldMotor.setPower(1);

                multiplayer = 1.6;
            }

            if (gamepad2.triangle) {
                OutClaw.setPosition(0.3);
                sleep(300);
                InClaw.setPosition(0);
                sleep(500);
                VSLMotor.setTargetPosition(VSPosUp);
                VSLMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                VSLMotor.setPower(1);
                VSRMotor.setTargetPosition(VSPosUp);
                VSRMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                VSRMotor.setPower(1);
                OutClaw.setPosition(0.3);
                sleep(100);
                OutLeftPivot.setPosition(0.2);
                OutRightPivot.setPosition(0.2);
                OutAngle.setPosition(0.3);
            }

            if (gamepad2.cross) {
                OutClaw.setPosition(0);
                sleep(200);
                OutAngle.setPosition(0.35);
                sleep(100);
                OutLeftPivot.setPosition(0.95);
                OutRightPivot.setPosition(0.95);
                sleep(300);
                VSLMotor.setTargetPosition(0);
                VSLMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                VSLMotor.setPower(1);
                VSRMotor.setTargetPosition(0);
                VSRMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                VSRMotor.setPower(1);
            }
            //clopen out claw
            if(gamepad1.left_bumper) {
                OutClaw.setPosition(0.3);
            }

            if(gamepad1.right_bumper) {
                OutClaw.setPosition(0);
            }
            // up/dowm vertcal slide

            if (gamepad2.right_bumper){
                VSliderPos=VSliderPos+100;
                VSLMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                VSRMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                VSRMotor.setTargetPosition(VSliderPos);
                VSLMotor.setTargetPosition(VSliderPos);
                VSRMotor.setPower(1);
                VSLMotor.setPower(1);
            }

            if (gamepad2.left_bumper){
                VSliderPos=VSliderPos-100;
                VSLMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                VSRMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                VSRMotor.setTargetPosition(VSliderPos);
                VSLMotor.setTargetPosition(VSliderPos);
                VSRMotor.setPower(1);
                VSLMotor.setPower(1);
            }

            if(gamepad1.dpad_left){
                InAngle.setPosition(0.7);
                sleep(200);
                OutClaw.setPosition(0);
                OutLeftPivot.setPosition(0.1);
                OutRightPivot.setPosition(0.1);
                OutAngle.setPosition(0.55);

            }

            if(gamepad1.dpad_right){
                OutClaw.setPosition(0.3);
                sleep(100);
                OutAngle.setPosition(0.8);
                sleep(200);
                OutLeftPivot.setPosition(0.4);
                OutRightPivot.setPosition(0.4);
                sleep(200);
                OutAngle.setPosition(0.2);

            }



            // all telemetry
            telemetry.addData("horizontalValue", HSld_value);
            telemetry.addData("Horizontal Destination", HSld_dest_value);
            telemetry.addData("verticalRightValue", VSR_value);
            telemetry.addData("verticalLeftValue", VSL_value);
            telemetry.addData("verticalLeftValue",OutClaw.getPosition());
            telemetry.update();
        }


    }

    public static class drivemecan extends LinearOpMode {
        @Override
        public void runOpMode() throws InterruptedException {

            //mecanum motors

            DcMotor frontLeftMotor = hardwareMap.dcMotor.get("leftFront");
            DcMotor backLeftMotor = hardwareMap.dcMotor.get("leftRear");
            DcMotor frontRightMotor = hardwareMap.dcMotor.get("rightFront");
            DcMotor backRightMotor = hardwareMap.dcMotor.get("rightRear");


            //mecanum reverse
            frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
            backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

            double multiplayer =1.6;

            waitForStart();
            if (isStopRequested()) return;

            while (opModeIsActive()) {


                //mecanum functions
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
            }
        }
    }
}

/*
    public void  moveDriveTrain() {
        double vertical = 0;
        double horizontal = 0;
        double pivot = 0;

        vertical = -gamepad1.left_stick_y;
        horizontal = gamepad1.left_stick_x * multiplayer;
        pivot = gamepad1.right_stick_x;


        RFMotor.setPower(pivot + (-vertical + horizontal));
        RBMotor.setPower(pivot + (-vertical - horizontal));
        LFMotor.setPower(pivot + (-vertical - horizontal));
        LBMotor.setPower(pivot + (-vertical + horizontal));

        LFMotor.setPower(frontLeftPower);
        RBMotor.setPower(backLeftPower);
        RFMotor.setPower(frontRightPower);
        LBMotor.setPower(backRightPower);

    }

    @Override
    public void init() {
        RFMotor = hardwareMap.get(DcMotor.class,"rightFront");
        LFMotor = hardwareMap.get(DcMotor.class,"leftFront");
        LBMotor = hardwareMap.get(DcMotor.class,"leftRear");
        RBMotor = hardwareMap.get(DcMotor.class,"rightRear");

        RFMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        RBMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        //RFMotor.
    }

    @Override
     public void init_loop() {

     }

     @Override
     public void loop() {

     }
}   */

