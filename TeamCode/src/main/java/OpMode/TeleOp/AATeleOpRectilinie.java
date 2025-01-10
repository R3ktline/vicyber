package OpMode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp
        public class AATeleOpRectilinie extends LinearOpMode {

    double multiplayer = 1.6;
    boolean everythingDoneVertical = true;
    boolean everythingDoneHorizontal = true;
    boolean everythingDoneHorizontal2 = true;
    boolean everythingDoneHorizontal3 = true;
    boolean alliancerung = true;
    boolean ascentStarted = false;

    boolean everythingDoneVerticalRetraction = true;
    boolean everythingDoneHorizontalRetraction = true;
    ElapsedTime timervertical = new ElapsedTime();
    ElapsedTime timeralliance = new ElapsedTime();
    ElapsedTime timerhorizontal = new ElapsedTime();

    ElapsedTime timerhorizontal2 = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {

        //values

        int VSPosUp = 1150; //2495 is max  // pozitia bara
        int hangPos =1440;
        int VBasUp = 2500; // pozitie basket sus
        //int HSPosExt = 100; ///1210 is max
        int extended_position = 1200;
        int retracted_position = 0;

        //mecanum motors

        DcMotor frontLeftMotor = hardwareMap.dcMotor.get("leftFront");
        DcMotor backLeftMotor = hardwareMap.dcMotor.get("leftRear");
        DcMotor frontRightMotor = hardwareMap.dcMotor.get("rightFront");
        DcMotor backRightMotor = hardwareMap.dcMotor.get("rightRear");

        //slider motors
        DcMotor VSLMotor = hardwareMap.dcMotor.get("verticalLeft");
        DcMotor VSRMotor = hardwareMap.dcMotor.get("verticalRight");
        DcMotor HSldMotor = hardwareMap.dcMotor.get("horizontalSlide");

        //mecanum reverse
        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        //slider reverse
        VSRMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        HSldMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        //sliders reset/start encoder
        VSLMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        VSRMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        HSldMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        VSRMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        VSLMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        HSldMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);



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
        InLeftPivot.setPosition(0);
        InRightPivot.setPosition(0);

        //sleep(1000);

        OutClaw.setPosition(1);
        OutAngle.setPosition(0.05);
        OutLeftPivot.setPosition(0.9); //0.92
        OutRightPivot.setPosition(0.9);

         boolean init = false;

         boolean safe = false;

        waitForStart();
        if (isStopRequested()) return;

        while (opModeIsActive()) {

            // extindere dupa init
            if (init == false) {
                InLeftPivot.setPosition(0.45);
                InRightPivot.setPosition(0.45);
                //sleep(200);
                //OutLeftPivot.setPosition(0.92); //0.92
                //OutRightPivot.setPosition(0.92);
                init = true;
            }



            if(gamepad1.ps) {
                InLeftPivot.setPosition(0.45);
                InRightPivot.setPosition(0.45);
                sleep(200);
                OutLeftPivot.setPosition(0.92); //0.92
                OutRightPivot.setPosition(0.92);
            }



            //mecanum functions
            int VSliderPos = VSLMotor.getCurrentPosition();
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

            //sliders function
            int HSld_value = HSldMotor.getCurrentPosition();
            int HSld_dest_value = HSldMotor.getTargetPosition();
            int VSR_value = VSRMotor.getCurrentPosition();
            int VSL_value = VSLMotor.getCurrentPosition();

            if(gamepad1.share){
                multiplayer = 0.7;
            }

            if(gamepad1.options){
                multiplayer =1.6;
            }

                //vertical up function on game pad 1 Triangle button

            if (gamepad2.dpad_up || !everythingDoneVertical){
                if (gamepad2.dpad_up){
                    everythingDoneVertical = false;
                    timervertical.reset();
                    timervertical.startTime();
                }
                OutClaw.setPosition(0.3);

                //timer.startTime();
                if(timervertical.milliseconds()>300 && timervertical.milliseconds() < 400) {
                    InClaw.setPosition(0);
                    InAngle.setPosition(0.2);
                }
                //timer.reset();
                //timer.startTime();
                if (timervertical.milliseconds() > 450  && timervertical.milliseconds() < 750) {
                    VSRMotor.setTargetPosition(VBasUp+100);
                    VSRMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    VSRMotor.setPower(1);
                    VSLMotor.setTargetPosition(VBasUp+100);
                    VSLMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    VSLMotor.setPower(1);
                    OutClaw.setPosition(0.3);
                }
                //timer.reset();
                //timer.startTime();
                if(timervertical.milliseconds()>800) {
                    OutLeftPivot.setPosition(0.4);
                    OutRightPivot.setPosition(0.4);
                    OutAngle.setPosition(0.3);
                    everythingDoneVertical = true;
                }
            }
                //vertical down function on game pad 1 Cross button
            if (gamepad2.dpad_down || !everythingDoneVerticalRetraction) {
                if (gamepad2.dpad_down){
                    everythingDoneVerticalRetraction = false;
                    timervertical.reset();
                    timervertical.startTime();
                }
                OutClaw.setPosition(0);
                if (timervertical.milliseconds() > 400 && timervertical.milliseconds()<450) {
                    OutAngle.setPosition(0.2);
                }
                if (timervertical.milliseconds()>500 && timervertical.milliseconds()<700) {
                    OutLeftPivot.setPosition(0.92);
                    OutRightPivot.setPosition(0.92);
                    OutAngle.setPosition(0.35);
                }
                if (timervertical.milliseconds()>900) {
                    VSLMotor.setTargetPosition(0);
                    VSLMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    VSLMotor.setPower(1);
                    VSRMotor.setTargetPosition(0);
                    VSRMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    VSRMotor.setPower(1);
                    everythingDoneVerticalRetraction = true;
                }
            }
            if ((gamepad2.cross) ) { //|| !everythingDoneVerticalRetraction) {
                OutAngle.setPosition(0.3);
                OutLeftPivot.setPosition(0);
                OutRightPivot.setPosition(0);
                VSLMotor.setTargetPosition(800);
                VSLMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                VSLMotor.setPower(1);
                VSRMotor.setTargetPosition(800);
                VSRMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                VSRMotor.setPower(1);
                sleep(700);
                OutClaw.setPosition(0);
                sleep(300);
                OutAngle.setPosition(0.35);
                OutLeftPivot.setPosition(0.92);
                OutRightPivot.setPosition(0.92);
                VSLMotor.setTargetPosition(0);
                VSLMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                VSLMotor.setPower(1);
                VSRMotor.setTargetPosition(0);
                VSRMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                VSRMotor.setPower(1);
                /*if (gamepad1.cross){
                    everythingDoneVerticalRetraction = false;
                    timervertical.reset();
                    timervertical.startTime();
                }
                    OutAngle.setPosition(0.3);
                    OutLeftPivot.setPosition(0.92);
                    OutRightPivot.setPosition(0.92);
                if (timervertical.milliseconds()>500 && timervertical.milliseconds()<700) {
                    OutAngle.setPosition(0.35);
                    OutLeftPivot.setPosition(0);
                    OutRightPivot.setPosition(0);
                }
                if (timervertical.milliseconds()>900) {

                    VSLMotor.setTargetPosition(0);
                    VSLMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    VSLMotor.setPower(1);
                    VSRMotor.setTargetPosition(0);
                    VSRMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    VSRMotor.setPower(1);
                    everythingDoneVerticalRetraction = true;
                }
                */
            }

            if(gamepad1.triangle || !everythingDoneHorizontal3)
            {
                if(gamepad1.triangle) {
                    timerhorizontal2.reset();
                    timerhorizontal2.startTime();
                    everythingDoneHorizontal3 =false;
                }
                InClaw.setPosition(0.3);

                if (timerhorizontal2.milliseconds()>50 && timerhorizontal2.milliseconds()<75) {
                    InAngle.setPosition(0);
                }

                if (timerhorizontal2.milliseconds()>100 && timerhorizontal2.milliseconds()<125) {
                    InTwist.setPosition(0);
                }

                if (timerhorizontal2.milliseconds()>150 && timerhorizontal2.milliseconds()<175) {
                    InRightPivot.setPosition(0.65);
                    InLeftPivot.setPosition(0.65);
                    everythingDoneHorizontal3=true;
                }
                //sleep(1000);
                /*if(timerhorizontal2.milliseconds()>550) {
                    InClaw.setPosition(0);
                    everythingDoneHorizontal3=true;
                }*/
                multiplayer = 0.7;
            }
            if(gamepad1.dpad_right)
            {
                InClaw.setPosition(0);
            }
                //horizontal extend function on game pad 1 square button
            if (gamepad1.cross || !everythingDoneHorizontal2) {
                if(gamepad1.cross) {
                    timerhorizontal2.reset();
                    timerhorizontal2.startTime();
                    everythingDoneHorizontal2 =false;
                }
                InClaw.setPosition(0);

                if (timerhorizontal2.milliseconds()>50 && timerhorizontal2.milliseconds()<75) {
                    InAngle.setPosition(0);
                }

                if (timerhorizontal2.milliseconds()>100 && timerhorizontal2.milliseconds()<125) {
                    InTwist.setPosition(0);
                }

                if (timerhorizontal2.milliseconds()>150) {
                    InRightPivot.setPosition(0.65);
                    InLeftPivot.setPosition(0.65);
                    everythingDoneHorizontal2=true;
                }
                multiplayer = 0.7;
            }

            if (gamepad1.square || !everythingDoneHorizontal) {
                if(gamepad1.square) {
                    timerhorizontal.reset();
                    timerhorizontal.startTime();
                    everythingDoneHorizontal =false;
                }
                HSldMotor.setTargetPosition(extended_position);
                HSldMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                HSldMotor.setPower(1);
                InClaw.setPosition(0);

                if (timerhorizontal.milliseconds()>50 && timerhorizontal.milliseconds()<75) {
                    InAngle.setPosition(0);
                }

                if (timerhorizontal.milliseconds()>100 && timerhorizontal.milliseconds()<125) {
                    InTwist.setPosition(0);
                }

                if (timerhorizontal.milliseconds()>150) {
                    InRightPivot.setPosition(0.65);
                    InLeftPivot.setPosition(0.65);
                    everythingDoneHorizontal=true;
                }

                multiplayer = 0.7;
            }
                //horizontal retract function on game pad circle
            if (gamepad1.circle) {
                OutAngle.setPosition(0.35);
                OutClaw.setPosition(0);
                OutLeftPivot.setPosition(0.92);
                OutRightPivot.setPosition(0.92);
                InRightPivot.setPosition(0.75);
                InLeftPivot.setPosition(0.75);
                sleep(100);
                InClaw.setPosition(0.3); //0.35
                sleep(300);
                InRightPivot.setPosition(0.45);
                InLeftPivot.setPosition(0.45);
                sleep(50);  //100
                InTwist.setPosition(0.78);
                sleep(50);  //100
                InAngle.setPosition(1);
                sleep(50);
                HSldMotor.setTargetPosition(362);
                HSldMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                HSldMotor.setPower(1);
                sleep(700);
                HSldMotor.setTargetPosition(retracted_position);
                HSldMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                HSldMotor.setPower(1);

                multiplayer = 1.6;
            }
            // alliance rung up
            if (gamepad2.triangle || !alliancerung) {
                if(gamepad2.triangle){
                    alliancerung = false;
                    timeralliance.reset();
                    timeralliance.startTime();
                }
                OutClaw.setPosition(0.3);
                if (timeralliance.milliseconds()> 200 && timeralliance.milliseconds()<300) {
                    InClaw.setPosition(0);
                }

                if (timeralliance.milliseconds()>600 && timeralliance.milliseconds()<900) {
                    VSLMotor.setTargetPosition(VSPosUp);
                    VSLMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    VSLMotor.setPower(1);
                    VSRMotor.setTargetPosition(VSPosUp);
                    VSRMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    VSRMotor.setPower(1);
                    OutClaw.setPosition(0.3);
                }

                if (timeralliance.milliseconds()>1000) {
                    OutLeftPivot.setPosition(0.1);
                    OutRightPivot.setPosition(0.1);
                    OutAngle.setPosition(0.7);
                    alliancerung= true;
                }
            }
           /* // ascent
            if(gamepad1.triangle && !ascentStarted ){
                sleep(50);
                VSLMotor.setTargetPosition(hangPos);
                VSLMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                VSRMotor.setTargetPosition(hangPos);
                VSRMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                VSLMotor.setPower(1);
                VSRMotor.setPower(1);


                ascentStarted = true;
            }
            else if(gamepad1.triangle && ascentStarted) {
                sleep(50);
                HSldMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                VSLMotor.setTargetPosition(-10);
                VSLMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                VSRMotor.setTargetPosition(-10);
                VSRMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                VSLMotor.setPower(1);
                VSRMotor.setPower(1);
                InLeftPivot.setPosition(0);
                InRightPivot.setPosition(0);
                ascentStarted = false;
            }*/


            //clopen out claw
            if(gamepad2.dpad_right) {
                OutClaw.setPosition(0.3);
            }

            if(gamepad2.dpad_left) {
                OutClaw.setPosition(0);
            }
            // twist cand iei sample
            if(gamepad1.left_bumper){
                InTwist.setPosition(0.4);
            }
            if(gamepad1.right_bumper){
                InTwist.setPosition(0);
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
            //  pozitie gard
            if(gamepad2.square){
                multiplayer=0.7;
                InAngle.setPosition(0.7);
                sleep(200);
                OutClaw.setPosition(0);
                OutLeftPivot.setPosition(0.07);
                OutRightPivot.setPosition(0.07);
                OutAngle.setPosition(0.55);

            }
            // luat gard
            if(gamepad2.circle){
                OutClaw.setPosition(0.3);
                sleep(200);
                OutAngle.setPosition(0.7);
                OutLeftPivot.setPosition(0.04);
                OutRightPivot.setPosition(0.04);
                /*sleep(200);
                OutLeftPivot.setPosition(0.4);
                OutRightPivot.setPosition(0.4);
                sleep(200);*/

            }



            // all telemetry
            telemetry.addData("horizontalValue", HSld_value);
            telemetry.addData("Horizontal Destination", HSld_dest_value);
            telemetry.addData("verticalRightValue", VSR_value);
            telemetry.addData("verticalLeftValue", VSL_value);
            telemetry.addData("verticalLeftValue",OutClaw.getPosition());
            telemetry.addData("multi",multiplayer);
            telemetry.update();
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

