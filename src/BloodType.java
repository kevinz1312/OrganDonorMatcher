// Kevin Zhou 112856427 R07
import java.io.Serializable;

/**
 * This class represents an Blood Type.
 *
 * @author Kevin Zhou
 *  e-mail: kevin.zhou.3@stonybrook.edu
 *  Stony Brook ID: 112856427
 */
public class BloodType implements Serializable{
    private String bloodType;

    /**
     * Returns the Blood Type.
     *
     * <dl>
     * <dt><b>Precondition:</b></dt>
     * <dd>This BloodType object has been instantiated.</dd>
     * </dl>
     *
     * @return
     *   The Blood Type of bloodType object.
     **/
    public String getBloodType() {
        return bloodType;
    }


    /**
     * Creates a new BloodType object.
     *
     * <dl>
     * <dt><b>Postconditions:</b></dt>
     * <dd>This BloodType object has been initialized.</dd>
     * </dl>
     *
     * @param  bloodType
     * The bloodType to be set for this BloodType
     **/
    public BloodType(String bloodType){
        this.bloodType = bloodType.toUpperCase();
    }


    /**
     * Returns if blood type between recipient and donor is compatiable
     *
     * @param recipient
     * Recipient Bloodtype being compared to Donor BloodType.
     *
     * @param donor
     * Donor Bloodtype being compared to Recipient BloodType.
     *
     * @return
     *  true if compatible, false otherwise.
     */
    public static boolean isCompatible(BloodType recipient, BloodType donor){
        if (recipient.bloodType.equals("O")){
            if (donor.bloodType.equals("O"))
                return true;
        }
        else if (recipient.bloodType.equals("A")){
            if (donor.bloodType.equals("O") || donor.bloodType.equals("A"))
                return true;
        }
        else if (recipient.bloodType.equals("B")){
            if (donor.bloodType.equals("O") || donor.bloodType.equals("B"))
                return true;
        }
        else if (recipient.bloodType.equals("AB")){
            return true;
        }
        return false;
    }
}
