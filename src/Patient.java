// Kevin Zhou 112856427 R07
import java.io.Serializable;

/**
 * This class represents an Patient.
 *
 * @author Kevin Zhou
 *  e-mail: kevin.zhou.3@stonybrook.edu
 *  Stony Brook ID: 112856427
 */
public class Patient implements Serializable, Comparable{
    private String name;
    private String organ;
    private int age;
    private BloodType bloodType;
    private int ID;
    private boolean isDonor;
    private int numConnections;

    /**
     * Returns the Name.
     *
     * <dl>
     * <dt><b>Precondition:</b></dt>
     * <dd>This Patient object has been instantiated.</dd>
     * </dl>
     *
     * @return
     *   The Name of Patient object.
     **/
    public String getName() {
        return name;
    }

    /**
     * Returns the Organ.
     *
     * <dl>
     * <dt><b>Precondition:</b></dt>
     * <dd>This Patient object has been instantiated.</dd>
     * </dl>
     *
     * @return
     *   The Organ of Patient object.
     **/
    public String getOrgan() {
        return organ;
    }

    /**
     * Returns the Blood Type.
     *
     * <dl>
     * <dt><b>Precondition:</b></dt>
     * <dd>This Patient object has been instantiated.</dd>
     * </dl>
     *
     * @return
     *   The BloodType of Patient object.
     **/
    public BloodType getBloodType() {
        return bloodType;
    }

    /**
     * Returns the ID.
     *
     * <dl>
     * <dt><b>Precondition:</b></dt>
     * <dd>This Patient object has been instantiated.</dd>
     * </dl>
     *
     * @return
     *   The ID of Patient object.
     **/
    public int getID() {
        return ID;
    }

    /**
     * A method that sets the ID for Patient.
     *
     * <dl>
     * <dt><b>Precondition:</b></dt>
     * <dd>This Patient object has been instantiated.</dd>
     * </dl>
     *
     * @param ID
     * The IDe to be set for Patient.
     **/
    public void setID(int ID) {
        this.ID = ID;
    }

    /**
     * Returns the Age.
     *
     * <dl>
     * <dt><b>Precondition:</b></dt>
     * <dd>This Patient object has been instantiated.</dd>
     * </dl>
     *
     * @return
     *   The Age of Patient object.
     **/
    public int getAge() {
        return age;
    }

    /**
     * Returns the Num of Connections.
     *
     * <dl>
     * <dt><b>Precondition:</b></dt>
     * <dd>This Patient object has been instantiated.</dd>
     * </dl>
     *
     * @return
     *   The Num of Connections of Patient object.
     **/
    public int getNumConnections() {
        return numConnections;
    }

    /**
     * A method that sets the Num of Connections for Patient.
     *
     * <dl>
     * <dt><b>Precondition:</b></dt>
     * <dd>This Patient object has been instantiated.</dd>
     * </dl>
     *
     * @param numConnections
     * The Num of Connections to be set for Patient.
     **/
    public void setNumConnections(int numConnections) {
        this.numConnections = numConnections;
    }

    /**
     * Creates a new Patient object.
     *
     * <dl>
     * <dt><b>Postconditions:</b></dt>
     * <dd>This Patient object has been initialized.</dd>
     * </dl>
     *
     * @param name
     * The item code to be set for this Patient.
     *
     * @param  organ
     * The organ to be set for this Patient
     *
     * @param  age
     * The age to be set for this Patient
     *
     * @param  bloodType
     * The bloodType to be set for this Patient
     *
     * @param ID
     * The ID to be set for this Patient.
     *
     * @param  isDonor
     * Whether the patient is a donor or not.
     *
     **/
    public Patient(String name, String organ, int age, BloodType bloodType, int ID, boolean isDonor){
    this.name = name;
    this.organ = organ;
    this.age = age;
    this.bloodType = bloodType;
    this.ID = ID;
    this.isDonor = isDonor;
    this.numConnections = 0;
    }


    /**
     * Returns a Comparison of this Patient object by ID
     *
     * @param o
     * Object being compared to this patient.
     *
     * @return
     *  0 if the IDs of both Patient are same. 1 if this Patient's ID is greater, and -1 elsewise.
     */
    public int compareTo(Object o){
    Patient otherPatient = (Patient) o;
    if (this.getID() == otherPatient.getID())
       return 0;
    else if (this.getID() > otherPatient.getID())
        return 1;
    else
    return -1;
    }

    /**
     * Returns a String representation of this Patient object
     *
     * @return
     *  A String representation of this Patient object.
     */
    public String toString(){
    String output = String.format("%-5d %s %-18s %s %-3d %s %-9s %s %-6s %s",
    this.getID(),"|",this.getName(),"|",this.getAge(),"|    ",this.getOrgan(),"|    ",this.getBloodType().getBloodType(),"| ");
    return output;
    }
}
