// Kevin Zhou 112856427 R07
import java.util.Comparator;

/**
 * This class represents an OrganComparator Comparator.
 *
 * @author Kevin Zhou
 *  e-mail: kevin.zhou.3@stonybrook.edu
 *  Stony Brook ID: 112856427
 */
public class OrganComparator implements Comparator<Patient> {

    /**
     * Returns a Comparison of this Patient object by Connections
     *
     * @param patientOne
     * Patient One to be compared.
     *
     * @param patientTwo
     * Patient Two to be compared.
     *
     * @return
     *  0 if the Organ of both Patient are same. 1 if this Patient 1 Organ is greater, and -1 else wise.
     */
    public int compare(Patient patientOne, Patient patientTwo) {
        return patientOne.getOrgan().compareTo(patientTwo.getOrgan());
    }
}

