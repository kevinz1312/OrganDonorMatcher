// Kevin Zhou 112856427 R07
import java.io.*;
import java.util.*;

/**
 * This class represents an Transplant Graph.
 *
 * @author Kevin Zhou
 *  e-mail: kevin.zhou.3@stonybrook.edu
 *  Stony Brook ID: 112856427
 */
public class TransplantGraph implements Serializable{
    private ArrayList<Patient> donors;
    private ArrayList<Patient> recipients;
    private int numDonors;
    private int numRecipients;
    public static final int MAX_PATIENTS = 100;
    private boolean[][] connections;

    /**
     * Returns the ArrayList of donors.
     *
     * <dl>
     * <dt><b>Precondition:</b></dt>
     * <dd>This TransplantGraph object has been instantiated.</dd>
     * </dl>
     *
     * @return
     *   The ArrayList of donors of TransplantGraph object.
     **/
    public ArrayList<Patient> getDonors() {
        return donors;
    }

    /**
     * Returns the ArrayList of recipients.
     *
     * <dl>
     * <dt><b>Precondition:</b></dt>
     * <dd>This TransplantGraph object has been instantiated.</dd>
     * </dl>
     *
     * @return
     *   The ArrayList of recipients of TransplantGraph object.
     **/
    public ArrayList<Patient> getRecipients() {
        return recipients;
    }
    public TransplantGraph() {
        numDonors = 0;
        numRecipients = 0;
        donors = new ArrayList<Patient>();
        recipients = new ArrayList<Patient>();
        connections = new boolean[MAX_PATIENTS][MAX_PATIENTS];
    }

    /**
     * A method that adds donors/recipients from file to TransplantGraph.
     *
     * @param donorFile
     * The donor File to get donors from to be added.
     *
     * @param recipientFile
     * The recipient File to get recipient from to be added.
     *
     * @exception IOException
     * Exception thrown if IO Error
     *
     * @return TransplantGraph
     * Transplant Graph generated from the files.
     *
     **/
    public static TransplantGraph buildFromFiles(String donorFile, String recipientFile) throws IOException {
        TransplantGraph transplantGraph = new TransplantGraph();
        try {
            System.out.println("Loading data from \'" + donorFile + "\'...");
            FileInputStream fis = new FileInputStream(donorFile);
            InputStreamReader instream = new InputStreamReader(fis);
            BufferedReader reader = new BufferedReader(instream);
            String temp = reader.readLine();
            while (temp != null) {
                if (transplantGraph.numDonors >= 100)
                    throw new MaxPatientException("First 100 recipients added. Max Reached.\n");
                String[] arrSplit = temp.split(", ");
                arrSplit[3] = arrSplit[3].substring(0, 1).toUpperCase() + arrSplit[3].substring(1);
                transplantGraph.addDonor(new Patient(arrSplit[1], arrSplit[3], Integer.parseInt(arrSplit[2]),
                        new BloodType(arrSplit[4]), Integer.parseInt(arrSplit[0]), true));
                temp = reader.readLine();
            }
        }
        catch (MaxPatientException e){
            System.out.println(e.getMessage());
        }
        System.out.println("Loading data from \'" + recipientFile + "\'...");
        try {
            FileInputStream fis = new FileInputStream(recipientFile);
            InputStreamReader instream = new InputStreamReader(fis);
            BufferedReader reader = new BufferedReader(instream);
            String temp = reader.readLine();
            while (temp != null) {
                if (transplantGraph.numRecipients >= 100)
                    throw new MaxPatientException("First 100 donors added. Max Reached.");
                String[] arrSplit = temp.split(", ");
                arrSplit[3] = arrSplit[3].substring(0, 1).toUpperCase() + arrSplit[3].substring(1);
                transplantGraph.addRecipient(new Patient(arrSplit[1], arrSplit[3], Integer.parseInt(arrSplit[2]),
                        new BloodType(arrSplit[4]), Integer.parseInt(arrSplit[0]), true));
                temp = reader.readLine();
            }
        }
        catch (MaxPatientException e){
            System.out.println(e.getMessage());
            return transplantGraph;
        }
        return transplantGraph;
    }

    /**
     * A method that adds Recipient to Transplant Graph.
     *
     * <dl>
     * <dt><b>Precondition:</b></dt>
     * <dd>This TransplantGraph object has been instantiated.</dd>
     * </dl>
     *
     * @param patient
     * The patient to be added.
     *
     * @throws MaxPatientException
     * Thrown if the Max Patient amount has been reached
     **/
    public void addRecipient(Patient patient) throws MaxPatientException {
        if (numRecipients >= 100)
            throw new MaxPatientException("There are already 100 recipients. Max Reached.");
        recipients.add(patient);
        numRecipients++;
        this.redoConnections();
    }

    /**
     * A method that adds Donor to Transplant Graph.
     *
     * <dl>
     * <dt><b>Precondition:</b></dt>
     * <dd>This TransplantGraph object has been instantiated.</dd>
     * </dl>
     *
     * @param patient
     * The patient to be added.
     *
     * @throws MaxPatientException
     * Thrown if the Max Patient amount has been reached
     **/
    public void addDonor(Patient patient) throws MaxPatientException {
        if (numDonors >= 100)
            throw new MaxPatientException("There are already 100 recipients. Max Reached.");
        donors.add(patient);
        numDonors++;
        this.redoConnections();
    }

    /**
     * A method that removes Recipient from Transplant Graph.
     *
     * <dl>
     * <dt><b>Precondition:</b></dt>
     * <dd>This TransplantGraph object has been instantiated.</dd>
     * </dl>
     *
     * @param name
     * The patient to be removed.
     *
     * @exception PatientNotFoundException
     * Exception thrown if patient is not found with name.
     *
     **/
    public void removeRecipient(String name) throws PatientNotFoundException {
        boolean removed = false;
        for (int i = 0; i < recipients.size(); i++) {
            if (recipients.get(i).getName().equals(name)) {
                System.out.println("\n"+recipients.get(i).getName() + " was removed from the organ transplant waitlist.\n");
                recipients.remove(i);
                numRecipients--;
                removed = true;
                for (int j = i; j < recipients.size(); j++){
                    recipients.get(j).setID(j);
                }
                break;
            }
        }
        if (!removed)
            throw new PatientNotFoundException("Patient not found! No one was removed from the organ transplant waitlist.");
        this.redoConnections();
    }

    /**
     * A method that removes Donor from Transplant Graph.
     *
     * <dl>
     * <dt><b>Precondition:</b></dt>
     * <dd>This TransplantGraph object has been instantiated.</dd>
     * </dl>
     *
     * @param name
     * The patient to be removed.
     *
     * @exception PatientNotFoundException
     * Exception thrown if patient is not found with name.
     *
     **/
    public void removeDonor(String name) throws PatientNotFoundException {
        boolean removed = false;
        for (int i = 0; i < donors.size(); i++) {
            if (donors.get(i).getName().equals(name)) {
                System.out.println("\n"+donors.get(i).getName() + " was removed from the organ donor list.\n");
                donors.remove(i);
                numDonors--;
                removed = true;
                for (int j = i; j < donors.size(); j++){
                    donors.get(j).setID(j);
                }
                break;
            }
        }
        if (!removed)
            throw new PatientNotFoundException("Patient not found! No one was removed from the organ donor list.");
        this.redoConnections();
    }

    /**
     *
     * Prints a String representation of all Recipients in a nicely formatted table.
     *
     */
    public void printAllRecipients() {
        String temp = "\nIndex | Donor Name         | Age | Organ Donated | Blood Type | Recipient IDs\n" +
                "=============================================================================\n";
        for (int i = 0; i < recipients.size(); i++) {
            temp += recipients.get(i).toString();
            for (int j = 0; j < donors.size(); j++){
                if (connections[j][i]){
                    temp += + j + ", ";
                }
            }
            if (temp.substring(temp.length()-2).equals(", "))
            temp = temp.substring(0,temp.length()-2) + "\n";
            else
                temp += "\n";
        }
        System.out.println(temp);
    }

    /**
     *
     * Prints a String representation of all Donors in a nicely formatted table.
     *
     */
    public void printAllDonors() {
        String temp = "\nIndex | Recipient Name     | Age | Organ Needed  | Blood Type | Donor IDs\n" +
                "==========================================================================\n";
        for (int i = 0; i < donors.size(); i++) {
            temp += donors.get(i).toString();
            for (int j = 0; j < recipients.size(); j++){
                if (connections[i][j]){
                    temp += j + ", ";
                }
            }
            if (temp.substring(temp.length()-2).equals(", "))
                temp = temp.substring(0,temp.length()-2) + "\n";
            else
                temp += "\n";
        }
        System.out.println(temp);
    }

    /**
     *
     * A method to redo the matrix for connections containing compatiable donors/recipients.
     *
     */
    public void redoConnections(){
        connections = new boolean[MAX_PATIENTS][MAX_PATIENTS];
        for (int i = 0; i < donors.size(); i++) {
            donors.get(i).setNumConnections(0);
        }
        for (int i = 0; i < recipients.size(); i++){
            recipients.get(i).setNumConnections(0);
        }
        for (int i = 0; i < donors.size(); i++){
            for (int j = 0; j < recipients.size(); j++){
                if (donors.get(i).getOrgan().equals(recipients.get(j).getOrgan())
                        && BloodType.isCompatible(recipients.get(j).getBloodType(),donors.get(i).getBloodType())){
                    connections[i][j] = true;
                    donors.get(i).setNumConnections(donors.get(i).getNumConnections() + 1);
                    recipients.get(j).setNumConnections(recipients.get(j).getNumConnections() + 1);
                }
                else
                    connections[i][j] = false;
            }
        }
    }
}