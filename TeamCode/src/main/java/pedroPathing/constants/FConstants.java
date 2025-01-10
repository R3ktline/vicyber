package pedroPathing.constants;

import com.pedropathing.localization.Localizers;
import com.pedropathing.follower.FollowerConstants;
import com.pedropathing.util.CustomFilteredPIDFCoefficients;
import com.pedropathing.util.CustomPIDFCoefficients;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class FConstants {
    static {
<<<<<<< Updated upstream
        FollowerConstants.localizers = Localizers.THREE_WHEEL;
=======
        FollowerConstants.localizers = Localizers.PINPOINT;
>>>>>>> Stashed changes

        FollowerConstants.leftFrontMotorName = "leftFront";
        FollowerConstants.leftRearMotorName = "leftRear";
        FollowerConstants.rightFrontMotorName = "rightFront";
        FollowerConstants.rightRearMotorName = "rightRear";

<<<<<<< Updated upstream
        FollowerConstants.leftFrontMotorDirection = DcMotorSimple.Direction.REVERSE;
        FollowerConstants.leftRearMotorDirection = DcMotorSimple.Direction.REVERSE;
        FollowerConstants.rightFrontMotorDirection = DcMotorSimple.Direction.FORWARD;
        FollowerConstants.rightRearMotorDirection = DcMotorSimple.Direction.FORWARD;

        FollowerConstants.mass = 13;
=======
        FollowerConstants.rightFrontMotorDirection = DcMotorSimple.Direction.REVERSE;
        FollowerConstants.rightRearMotorDirection = DcMotorSimple.Direction.REVERSE;
        FollowerConstants.leftFrontMotorDirection = DcMotorSimple.Direction.FORWARD;
        FollowerConstants.leftRearMotorDirection = DcMotorSimple.Direction.FORWARD;

        FollowerConstants.mass = 12;
>>>>>>> Stashed changes

        FollowerConstants.xMovement = 57.8741;
        FollowerConstants.yMovement = 52.295;

        FollowerConstants.forwardZeroPowerAcceleration = -41.278;
        FollowerConstants.lateralZeroPowerAcceleration = -59.7819;

        FollowerConstants.translationalPIDFCoefficients.setCoefficients(0.1,0,0.01,0);
        FollowerConstants.useSecondaryTranslationalPID = false;
        FollowerConstants.secondaryTranslationalPIDFCoefficients.setCoefficients(0.1,0,0.01,0); // Not being used, @see useSecondaryTranslationalPID

<<<<<<< Updated upstream
        FollowerConstants.headingPIDFCoefficients.setCoefficients(2,0,0.1,0);
=======
        FollowerConstants.headingPIDFCoefficients.setCoefficients(0.1,0,0.0002,0);
>>>>>>> Stashed changes
        FollowerConstants.useSecondaryHeadingPID = false;
        FollowerConstants.secondaryHeadingPIDFCoefficients.setCoefficients(2,0,0.1,0); // Not being used, @see useSecondaryHeadingPID

        FollowerConstants.drivePIDFCoefficients.setCoefficients(0.1,0,0,0.6,0);
        FollowerConstants.useSecondaryDrivePID = false;
        FollowerConstants.secondaryDrivePIDFCoefficients.setCoefficients(0.1,0,0,0.6,0); // Not being used, @see useSecondaryDrivePID

        FollowerConstants.zeroPowerAccelerationMultiplier = 4;
        FollowerConstants.centripetalScaling = 0.0005;

        FollowerConstants.pathEndTimeoutConstraint = 500;
        FollowerConstants.pathEndTValueConstraint = 0.995;
        FollowerConstants.pathEndVelocityConstraint = 0.1;
        FollowerConstants.pathEndTranslationalConstraint = 0.1;
        FollowerConstants.pathEndHeadingConstraint = 0.007;
<<<<<<< Updated upstream
=======

        FollowerConstants.xMovement = 70.03001196044288; // 69.45196336858995  old  70.40736287698053 new old
        FollowerConstants.yMovement = 48.432988056107; // 46.48921833604725 old
        FollowerConstants.forwardZeroPowerAcceleration = -38.15856893715635; //-34.09519785 old -52.64758122042905 new?
        FollowerConstants.lateralZeroPowerAcceleration = -68.36322293798163; // -87.12521308 old

>>>>>>> Stashed changes
    }
}
