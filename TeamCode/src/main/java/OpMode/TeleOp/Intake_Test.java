package OpMode.TeleOp;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.sun.source.doctree.StartElementTree;


@TeleOp
public class Intake_Test extends LinearOpMode {

    double multiplayer = 1.6;

    Boolean GameMode = true;

    boolean everythingDoneVertical = true;
    boolean everythingDoneHorizontal = true;

    boolean alliancerung = true;
    boolean ascentStarted = false;

    boolean everythingDoneVerticalRetraction = true;
    boolean everythingDoneHorizontalRetraction = true;
    ElapsedTime timervertical = new ElapsedTime();
    ElapsedTime timeralliance = new ElapsedTime();
    ElapsedTime timerhorizontal = new ElapsedTime();

    DcMotor frontLeftMotor = hardwareMap.dcMotor.get("leftFront");
    DcMotor backLeftMotor = hardwareMap.dcMotor.get("leftRear");
    DcMotor frontRightMotor = hardwareMap.dcMotor.get("rightFront");
    DcMotor backRightMotor = hardwareMap.dcMotor.get("rightRear");

    //slider motors
    DcMotor VSLMotor = hardwareMap.dcMotor.get("verticalLeft");
    DcMotor VSRMotor = hardwareMap.dcMotor.get("verticalRight");
    DcMotor HSldMotor = hardwareMap.dcMotor.get("horizontalSlide");


    Servo OutClaw = hardwareMap.servo.get("outClaw");
    Servo OutAngle = hardwareMap.servo.get("outAngle");
    Servo OutLeftPivot = hardwareMap.servo.get("outLeftPivot");
    Servo OutRightPivot = hardwareMap.servo.get("outRightPivot");
    Servo InClaw = hardwareMap.servo.get("inClaw");
    Servo InTwist = hardwareMap.servo.get("inTwist");
    Servo InAngle = hardwareMap.servo.get("inAngle");
    Servo InLeftPivot = hardwareMap.servo.get("inLeftPivot");
    Servo InRightPivot = hardwareMap.servo.get("inRightPivot");



    boolean init = false;
    boolean isHorizontalIn = true;
    boolean isIntakeLowered = false;
    boolean to_Transfer = false;
    boolean StartIntake=false;
    boolean Start_Transfer=false;
    boolean is_Outake=false;
    int HorizontalTarget = 0;
    int outHorizontal=0; ///De scris val
    int HorizontalRetras=0; ///De scris val
    int inHorizontal=0; ///De scris val
    ElapsedTime Intake_timer=new ElapsedTime();
    ElapsedTime Transfer_timer=new ElapsedTime();





    @Override
    public void runOpMode() throws InterruptedException {

        //values


        int VSPosUp = 1150; //2495 is max  // pozitia bara
        int hangPos = 1440;
        int VBasUp = 2500; // pozitie basket sus // 2500 // 2300
        //int HSPosExt = 100; ///1210 is max
        int extended_position = 1200; // 1200 // 850
        int retracted_position = 0;

        //mecanum motors

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

            frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

//        VSLMotor.setTargetPosition(0);
//        VSLMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        VSRMotor.setTargetPosition(0);
//        VSRMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            //HSldMotor.setTargetPosition(0);
            //HSldMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            //TODO:declare servos


            //reverse servos

            OutRightPivot.setDirection(Servo.Direction.REVERSE);
            InRightPivot.setDirection(Servo.Direction.REVERSE);
            InAngle.setDirection(Servo.Direction.REVERSE);
            OutClaw.setDirection(Servo.Direction.REVERSE);
            //InTwist.setDirection(Servo.Direction.REVERSE);


            /*InClaw.setPosition(0);
            InTwist.setPosition(0.78);
            InAngle.setPosition(1);
            InLeftPivot.setPosition(0);
            InRightPivot.setPosition(0);

            OutClaw.setPosition(1);
            OutAngle.setPosition(0.05);
            OutLeftPivot.setPosition(0.9);
            OutRightPivot.setPosition(0.9);*/





            //TODO: AICI START
            waitForStart();
            if (isStopRequested()) return;

            while (opModeIsActive()) {

                // extindere dupa init
                ///Schimbat val dupa init
                /* if (init == false) {
                    InLeftPivot.setPosition(0.45);
                    InRightPivot.setPosition(0.45);
                    //sleep(200);
                    //OutLeftPivot.setPosition(0.92); //0.92
                    //OutRightPivot.setPosition(0.92);
                    init = true;
                }*/

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

                //sliders function
                int VSliderPos = VSLMotor.getCurrentPosition();
                int HSld_value = HSldMotor.getCurrentPosition();
                int HSld_dest_value = HSldMotor.getTargetPosition();
                int VSR_value = VSRMotor.getCurrentPosition();
                int VSL_value = VSLMotor.getCurrentPosition();



//                if(init==false) {
//
//                    init=true;
//                }


                horizontalSliders();



                if(gamepad1.triangle && isHorizontalIn==true){
                    HorizontalTarget= outHorizontal ;
                    isHorizontalIn=false;
                    To_Intake_1();
                }
                else if(gamepad1.right_trigger>0.3 && isHorizontalIn==false){
                    To_Intake_2();
                    isIntakeLowered=true;
                }
                else if(isHorizontalIn==false) {
                    To_Intake_1();
                    isIntakeLowered=false;
                }
                else if(gamepad1.right_bumper && isIntakeLowered==true && StartIntake==false){
                    To_Intake_3();
                    StartIntake=true;
                    Intake_timer=new ElapsedTime();
                }
                else if(isIntakeLowered==true && StartIntake==true){
                    if(Intake_timer.time()>1000 && Intake_timer.time()<2000){
                        To_Intake_4();
                    }
                    else if(Intake_timer.time()>2000){
                        HorizontalTarget=inHorizontal;
                        isHorizontalIn=true;
                        isIntakeLowered=false;
                        StartIntake=false;
                        to_Transfer=true;
                    }
                }
                else if(isHorizontalIn==true && to_Transfer==true && Start_Transfer==false){
                    Transfer_timer=new ElapsedTime();
                    Start_Transfer=true;
                }
                else if(to_Transfer==true && Start_Transfer==true){
                    if(Transfer_timer.time()>1000 && Transfer_timer.time()<2000){
                        To_Transfer_1(); ///Ambele inchise
                    }
                    else if(Transfer_timer.time()>2000 && Transfer_timer.time()<3000){
                        To_Transfer_2(); /// 1 Inchs, 1 Deschs
                    }
                    else if(Transfer_timer.time()>3000 && Transfer_timer.time()<4000){
                        HorizontalTarget=HorizontalRetras;
                    }
                    else if(Transfer_timer.time()>4000 && Transfer_timer.time()<5000){
                        Outake_Out();
                        To_Intake_1();
                        is_Outake=true;
                    }
                    else if(Transfer_timer.time()>5000 &&  Transfer_timer.time()<6000){
                        HorizontalTarget=inHorizontal;
                        to_Transfer=false;
                        Start_Transfer=false;
                    }
                }




                //SPEEEED:)
                if (gamepad1.left_bumper) {
                    multiplayer = 0.7;
                }
                // Change Game mode (Basket sau Bara) // cos = true ; bara = false;
                if (gamepad1.dpad_left) {
                    GameMode = true;
                }

                if (gamepad1.dpad_right) {
                    GameMode = false;
                }







//                //up cos
//                if (gamepad1.triangle && GameMode == true || !everythingDoneVertical) {
//                    if (gamepad1.triangle) {
//                        everythingDoneVertical = false;
//                        timervertical.reset();
//                        timervertical.startTime();
//                    }
//                    OutClaw.setPosition(0.3);
//
//                    //timer.startTime();
//                    if (timervertical.milliseconds() > 300 && timervertical.milliseconds() < 400) {
//                        InClaw.setPosition(0);
//                        InAngle.setPosition(0.2);
//                    }
//                    //timer.reset();
//                    //timer.startTime();
//                    if (timervertical.milliseconds() > 450 && timervertical.milliseconds() < 750) {
//                        VSRMotor.setTargetPosition(VBasUp + 100);
//                        VSRMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//                        VSRMotor.setPower(1);
//                        VSLMotor.setTargetPosition(VBasUp + 100);
//                        VSLMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//                        VSLMotor.setPower(1);
//                        OutClaw.setPosition(0.3);
//                    }
//                    //timer.reset();
//                    //timer.startTime();
//                    if (timervertical.milliseconds() > 800) {
//                        OutLeftPivot.setPosition(0.4);
//                        OutRightPivot.setPosition(0.4);
//                        OutAngle.setPosition(0.3);
//                        everythingDoneVertical = true;
//                    }
//                }
//                //up bara
//                if (gamepad1.triangle && GameMode == false) {
//
//                }
//
//                //down cos
//                if (gamepad1.cross && GameMode == true || !everythingDoneVerticalRetraction) {
//                    if (gamepad1.cross && GameMode == true) {
//                        everythingDoneVerticalRetraction = false;
//                        timervertical.reset();
//                        timervertical.startTime();
//                    }
//                    OutClaw.setPosition(0);
//                    if (timervertical.milliseconds() > 400 && timervertical.milliseconds() < 450) {
//                        OutAngle.setPosition(0.2);
//                    }
//                    if (timervertical.milliseconds() > 500 && timervertical.milliseconds() < 700) {
//                        OutLeftPivot.setPosition(0.92);
//                        OutRightPivot.setPosition(0.92);
//                        OutAngle.setPosition(0.35);
//                    }
//                    if (timervertical.milliseconds() > 900) {
//                        VSLMotor.setTargetPosition(0);
//                        VSLMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//                        VSLMotor.setPower(1);
//                        VSRMotor.setTargetPosition(0);
//                        VSRMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//                        VSRMotor.setPower(1);
//                        everythingDoneVerticalRetraction = true;
//                    }
//                }
//
//                //dowm bara
//                if (gamepad1.cross && GameMode == false) {
//
//                }
//                //retract
//                if (gamepad1.circle && GameMode == true) {
//                    OutAngle.setPosition(0.35);
//                    OutClaw.setPosition(0);
//                    OutLeftPivot.setPosition(0.92);
//                    OutRightPivot.setPosition(0.92);
//                    InRightPivot.setPosition(0.75);
//                    InLeftPivot.setPosition(0.75);
//                    sleep(100);
//                    InClaw.setPosition(0.3); //0.35
//                    sleep(300);
//                    InRightPivot.setPosition(0.45);
//                    InLeftPivot.setPosition(0.45);
//                    sleep(50);  //100
//                    InTwist.setPosition(0.78);
//                    sleep(50);  //100
//                    InAngle.setPosition(1);
//                    sleep(50);
//                    HSldMotor.setTargetPosition(362);
//                    HSldMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//                    HSldMotor.setPower(1);
//                    sleep(700);
//                    HSldMotor.setTargetPosition(retracted_position);
//                    HSldMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//                    HSldMotor.setPower(1);
//
//
//                    multiplayer = 1.6;
//                }
//
//
//                //extand
//                if (gamepad1.square && GameMode == true || !everythingDoneHorizontal) {
//                    if (gamepad1.square) {
//                        timerhorizontal.reset();
//                        timerhorizontal.startTime();
//                        everythingDoneHorizontal = false;
//                    }
//                    HSldMotor.setTargetPosition(extended_position);
//                    HSldMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//                    HSldMotor.setPower(1);
//                    InClaw.setPosition(0);
//                    if (timerhorizontal.milliseconds() > 50 && timerhorizontal.milliseconds() < 75) {
//                        InAngle.setPosition(0);
//                    }
//
//                    if (timerhorizontal.milliseconds() > 100 && timerhorizontal.milliseconds() < 125) {
//                        InTwist.setPosition(0);
//                    }
//
//                    if (timerhorizontal.milliseconds() > 150) {
//                        InRightPivot.setPosition(0.65);
//                        InLeftPivot.setPosition(0.65);
//                        everythingDoneHorizontal = true;
//                    }
//
//                    multiplayer = 0.7;
//                }
//
//                //
//                if (gamepad1.cross && GameMode == false) {
//                    multiplayer = 0.7;
//                    OutLeftPivot.setPosition(0.86); //85
//                    OutRightPivot.setPosition(0.86); //85
//                    OutAngle.setPosition(0.45); //45
//                    sleep(50);
//                    OutClaw.setPosition(0);
//                    sleep(200);
//                    InAngle.setPosition(0.9);
//                    InLeftPivot.setPosition(0.03);
//                    InRightPivot.setPosition(0.03);
//                    VSLMotor.setTargetPosition(0);
//                    VSLMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//                    VSLMotor.setPower(1);
//                    VSRMotor.setTargetPosition(0);
//                    VSRMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//                    VSRMotor.setPower(1);
//
//                }
//
//                if (gamepad1.triangle && GameMode == false) {
//                    OutClaw.setPosition(0.3);
//                    sleep(325);
//                    OutAngle.setPosition(0.7);
//                    OutLeftPivot.setPosition(0.15);
//                    OutRightPivot.setPosition(0.15);
////                sleep(200);
//                    VSLMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//                    VSRMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//                    VSRMotor.setTargetPosition(VSPosUp - 100);
//                    VSLMotor.setTargetPosition(VSPosUp - 100);
//                    VSRMotor.setPower(1);
//                    VSLMotor.setPower(1);
//                }
//
//                if (gamepad2.dpad_up && VSliderPos < 2500) { // vslide down
//                    VSliderPos = VSliderPos + 100;
//                    VSLMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//                    VSRMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//                    VSRMotor.setTargetPosition(VSliderPos);
//                    VSLMotor.setTargetPosition(VSliderPos);
//                    VSRMotor.setPower(1);
//                    VSLMotor.setPower(1);
//                }
//
//                if (gamepad2.dpad_down && VSliderPos > 0) { // vslide up
//                    VSliderPos = VSliderPos - 100;
//                    VSLMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//                    VSRMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//                    VSRMotor.setTargetPosition(VSliderPos);
//                    VSLMotor.setTargetPosition(VSliderPos);
//                    VSRMotor.setPower(1);
//                    VSLMotor.setPower(1);
//                }
//
//                if (gamepad2.dpad_left && VSliderPos > 0) { //hslide out
//                    HSld_value = HSld_value - 100;
//                    HSldMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//                    HSldMotor.setTargetPosition(HSld_value);
//                    HSldMotor.setPower(1);
//                }
//
//
//                if (gamepad2.dpad_right && VSliderPos < 1200) { //vslide down
//                    HSld_value = HSld_value + 100;
//                    HSldMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//                    HSldMotor.setTargetPosition(HSld_value);
//                    HSldMotor.setPower(1);
//                }
//
//                if (gamepad2.right_bumper) { // twist 90
//                    InTwist.setPosition(0.4);
//                }
//                if (gamepad2.left_bumper) { // normal
//                    InTwist.setPosition(0);
//                }
//
//                if (gamepad2.square) { // open
//                    OutClaw.setPosition(0.3);
//                }
//
//                if (gamepad2.circle) { //closed
//                    OutClaw.setPosition(0);
//                }
//
//                if (gamepad2.triangle) { //pickup jos fara extend
//                    OutAngle.setPosition(0.35);
//                    OutClaw.setPosition(0);
//                    OutLeftPivot.setPosition(0.92);
//                    OutRightPivot.setPosition(0.92);
//                    InRightPivot.setPosition(0.75);
//                    InLeftPivot.setPosition(0.75);
//                    sleep(100);
//                    InClaw.setPosition(0.3); //0.35
//                    sleep(300);
//                    InRightPivot.setPosition(0.45);
//                    InLeftPivot.setPosition(0.45);
//                    sleep(50);  //100
//                    InTwist.setPosition(0.78);
//                    sleep(50);  //100
//                    InAngle.setPosition(1);
//                    sleep(50);
//                }
//
//
//                if (gamepad2.options) InClaw.setPosition(0.3);
//                if (gamepad2.share) InClaw.setPosition(0); //0.35// ascent
//                if (gamepad2.guide && !ascentStarted) {
//                    sleep(50);
//                    VSLMotor.setTargetPosition(hangPos);
//                    VSLMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//                    VSRMotor.setTargetPosition(hangPos);
//                    VSRMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//                    VSLMotor.setPower(1);
//                    VSRMotor.setPower(1);
//                    ascentStarted = true;
//                }
//                if (gamepad2.cross && ascentStarted) {
//                    sleep(50);
//                    HSldMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//                    VSLMotor.setTargetPosition(0);
//                    VSLMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//                    VSRMotor.setTargetPosition(0);
//                    VSRMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//                    VSLMotor.setPower(1);
//                    VSRMotor.setPower(1);
//                    InLeftPivot.setPosition(0);
//                    InRightPivot.setPosition(0);
//                    ascentStarted = false;
//                }

                // all telemetry
                telemetry.addData("mod", GameMode);
                telemetry.addData("horizontalValue", HSld_value);
                telemetry.addData("Horizontal Destination", HSld_dest_value);
                telemetry.addData("verticalRightValue", VSR_value);
                telemetry.addData("verticalLeftValue", VSL_value);
                telemetry.addData("verticalLeftValue", OutClaw.getPosition());
                telemetry.addData("multi", multiplayer);
                telemetry.update();
            }
        }
        private void To_Intake_1() {
            ///
        }
        private void To_Intake_2() {
            ///
        }
        private void To_Intake_3() {
            ///
        }
        private void To_Intake_4() {
            ///
        }
        private void To_Transfer_1() {
            ///
        }
        private void To_Transfer_2() {
            ///\
        }
        private void Outake_Out() {
            ///
        }
    private void horizontalSliders(){
        HSldMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        HSldMotor.setTargetPosition(HorizontalTarget);
        if(HSldMotor.getCurrentPosition()+50 > HorizontalTarget || HSldMotor.getCurrentPosition()-50< HorizontalTarget){
            HSldMotor.setPower(0.3);
        }else{
            HSldMotor.setPower(1);
        }
    }





    }
