// Kevin Zhou 112856427 R07
import java.io.*;
import java.util.*;

/**
 * This class represents an Transplant Driver.
 *
 * @author Kevin Zhou
 *  e-mail: kevin.zhou.3@stonybrook.edu
 *  Stony Brook ID: 112856427
 */
public class TransplantDriver {
    public static final String DONOR_FILE = "donors.txt";
    public static final String RECIPIENT_FILE = "recipients.txt";

    /**
     * The main driver for the class.
     *
     * @param args
     *  Command line arguments.
     *
     */
    public static void main(String args[]) {
        TransplantGraph transplantGraph = null;
        boolean loaded = false;
        try {
            File tempFile = new File("transplant.obj");
            if (tempFile.exists())
                System.out.println("Loading data from transplant.obj...\n");
            FileInputStream file = new FileInputStream("transplant.obj");
            ObjectInputStream fin  = new ObjectInputStream(file);
            transplantGraph = (TransplantGraph) fin.readObject();
            fin.close();
        } catch(IOException | ClassNotFoundException e) {
                System.out.println("transplant.obj not found. Creating new TransplantGraph object...");
            try {
                transplantGraph = TransplantGraph.buildFromFiles(DONOR_FILE, RECIPIENT_FILE);
            } catch (IOException f) {
                System.out.println("IO Exception!");
            }
            transplantGraph.redoConnections();
        }
        while (true) {
            try {
                Scanner stdin = new Scanner(System.in);
                String option = "";
                System.out.println("\nMenu: ");
                System.out.println("    (LR) - List all recipients: Displays a table of all patients in the recipients list.");
                System.out.println("    (LO) - List all donors: Displays a table of all patients in the donors list.");
                System.out.println("    (AO) - Add new donor: Adds a new donor to the system.");
                System.out.println("    (AR) - Add new recipient: Adds a new recipient to the system.");
                System.out.println("    (RO) - Remove donor: Removes a donor from the system.");
                System.out.println("    (RR) - Remove recipient: Removes a recipient from the system.");
                System.out.println("    (SR) - Sort recipients:");
                System.out.println("    (SO) - Sort donors: Displays a submenu:");
                System.out.println("    (Q) - Quit");
                System.out.print("\nPlease select an option: ");
                option = stdin.nextLine().toUpperCase();
                if (option.equals("LR")) {
                    transplantGraph.printAllRecipients();
                }
                if (option.equals("LO")) {
                    transplantGraph.printAllDonors();
                }
                if (option.equals("AO")) {
                    try {
                        System.out.print("\nPlease enter the organ donor name: ");
                        String name = stdin.nextLine();
                        System.out.print("Please enter the organs " + name + "is donating: ");
                        String organs = stdin.nextLine();
                        System.out.print("Please enter the blood type of " + name + ": ");
                        BloodType bloodType = new BloodType(stdin.nextLine());
                        System.out.print("Please enter the age of " + name + ": ");
                        int age = stdin.nextInt();
                        Patient patient = new Patient(name, organs, age, bloodType, transplantGraph.getDonors().size(), true);
                        transplantGraph.addDonor(patient);
                        System.out.println("\nThe organ donor with ID " + patient.getID() + " was successfully added to the donor list!\n");
                    } catch (MaxPatientException e) {
                        System.out.println(e.getMessage());
                    }
                }
                if (option.equals("AR")) {
                    try {
                        System.out.print("Please enter the recipient's  name: ");
                        String name = stdin.nextLine();
                        System.out.print("Please enter the recipient's blood type: ");
                        BloodType bloodType = new BloodType(stdin.nextLine());
                        System.out.print("Please enter the recipient's age: ");
                        int age = stdin.nextInt();
                        stdin.nextLine();
                        System.out.print("Please enter the organ needed: ");
                        String organs = stdin.nextLine();
                        Patient patient = new Patient(name, organs, age, bloodType, transplantGraph.getDonors().size(), false);
                        transplantGraph.addRecipient(patient);
                        System.out.println("\n" + name + " is now on the organ transplant waitlist!\n");
                    } catch (MaxPatientException e) {
                        System.out.println(e.getMessage());
                    }
                }
                if (option.equals("RO")) {
                    try {
                        System.out.print("\nPlease enter the name of the organ donor to remove: ");
                        transplantGraph.removeDonor(stdin.nextLine());
                    } catch (PatientNotFoundException e) {
                        System.out.println(e.getMessage());
                    }
                }
                if (option.equals("RR")) {
                    try {
                        System.out.print("\nPlease enter the name of the recipient to remove: ");
                        transplantGraph.removeRecipient(stdin.nextLine());
                    } catch (PatientNotFoundException e) {
                        System.out.println(e.getMessage());
                    }
                }
                if (option.equals("SR")) {
                    boolean x = true;
                    while (x) {
                        String tempOption = "";
                        System.out.println("    (I) Sort by ID");
                        System.out.println("    (N) Sort by Number of Donors");
                        System.out.println("    (B) Sort by Blood Type");
                        System.out.println("    (O) Sort by Organ Needed");
                        System.out.println("    (Q) Back to Main Menu");
                        System.out.print("\nPlease select an option: ");
                        tempOption = stdin.nextLine().toUpperCase();
                        if (tempOption.equals("I")) {
                            Collections.sort(transplantGraph.getRecipients());
                            transplantGraph.redoConnections();
                            transplantGraph.printAllRecipients();
                        }
                        if (tempOption.equals("N")) {
                            Collections.sort(transplantGraph.getRecipients(), new NumConnectionsComparator());
                            transplantGraph.redoConnections();
                            transplantGraph.printAllRecipients();
                        }
                        if (tempOption.equals("B")) {
                            Collections.sort(transplantGraph.getRecipients(), new BloodTypeComparator());
                            transplantGraph.redoConnections();
                            transplantGraph.printAllRecipients();
                        }
                        if (tempOption.equals("O")) {
                            Collections.sort(transplantGraph.getRecipients(), new OrganComparator());
                            transplantGraph.redoConnections();
                            transplantGraph.printAllRecipients();
                        }
                        if (tempOption.equals("Q")) {
                            System.out.println("Returning to main menu.");
                            Collections.sort(transplantGraph.getRecipients());
                            transplantGraph.redoConnections();
                            x = false;
                        }
                    }
                }
                if (option.equals("SO")) {
                    boolean x = true;
                    while (x) {
                        String tempOption = "";
                        System.out.println("    (I) Sort by ID");
                        System.out.println("    (N) Sort by Number of Donors");
                        System.out.println("    (B) Sort by Blood Type");
                        System.out.println("    (O) Sort by Organ Needed");
                        System.out.println("    (Q) Back to Main Menu");
                        System.out.print("\nPlease select an option: ");
                        tempOption = stdin.nextLine().toUpperCase();
                        if (tempOption.equals("I")) {
                            Collections.sort(transplantGraph.getDonors());
                            transplantGraph.redoConnections();
                            transplantGraph.printAllDonors();
                        }
                        if (tempOption.equals("N")) {
                            Collections.sort(transplantGraph.getDonors(), new NumConnectionsComparator());
                            transplantGraph.redoConnections();
                            transplantGraph.printAllDonors();
                        }
                        if (tempOption.equals("B")) {
                            Collections.sort(transplantGraph.getDonors(), new BloodTypeComparator());
                            transplantGraph.redoConnections();
                            transplantGraph.printAllDonors();
                        }
                        if (tempOption.equals("O")) {
                            Collections.sort(transplantGraph.getDonors(), new OrganComparator());
                            transplantGraph.redoConnections();
                            transplantGraph.printAllDonors();
                        }
                        if (tempOption.equals("Q")) {
                            System.out.println("Returning to main menu.");
                            Collections.sort(transplantGraph.getDonors());
                            transplantGraph.redoConnections();
                            x = false;
                        }
                    }
                }
                if (option.equals("Q")) {
                    try {
                        System.out.println("\nWriting data to transplant.obj...");
                        FileOutputStream file = new FileOutputStream("transplant.obj");
                        ObjectOutputStream fout = new ObjectOutputStream(file);
                        fout.writeObject(transplantGraph); // Here "objToWrite" is the object to serialize
                        fout.close();
                        System.out.println("\nProgram terminating normally...");
                        System.exit(0);
                    } catch (IOException e) {
                        System.out.println("IO Exception!");
                    }
                }
            }
            catch (InputMismatchException e){
                System.out.println("Invalid Input!");
            }
        }
    }
}
