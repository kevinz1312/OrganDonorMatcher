# Organ Donor Matcher (Java)
#### Author: Kevin Zhou

## Description
Created a simple system that could be used for matching organ donors with potential transplant recipients.

## UML Diagram
<p align = "center">
  <img src = "https://www3.cs.stonybrook.edu/~cse214/hw/hw7-images/hw7umlsp20.png">
</p>

## Specifications

### 1. Public class Patient

- private String name - The name of the donor or recipient
- private String organ - The organ the patient is donating or receiving
- private int age - The age of the patient
- private BloodType bloodType - The blood type of the patient
- private int ID - The ID number of the patient. If a donor, this must be unique amongst all donors. If a recipient, this must be unique amongst all recipients. This is used to map this patient to an index - in the adjacency matrix, as it denotes which row or column this patient's connections are stored in.
- private boolean isDonor - true if this Patient is a donor, false if a recipient
- public Patient() - Constructor. Getters and setters.
- public int compareTo(Object o) - Compares this Patient object to o, comparing by ID such that the values should be sorted in ascending order.
- public String toString() - Returns a String representation of this Patient object. See Sample IO for details.

### 2. Public class BloodType
This class contains a String member variable bloodType to denote a specific blood type, and a static method which can be invoked to determine if two blood types are compatible with each other.

- public BloodType() - Constructor.
- public static boolean isCompatible(BloodType recipient, BloodType donor)
	- Determines whether two blood types are compatible, returning true if they are, and false otherwise.
	- The following table can be used to determine compatability:
		```
					Donor O			Donor A			Donor B			Donor AB
		Recipient O		TRUE			FALSE			FALSE			FALSE
		Recipient A		TRUE			TRUE			FALSE			FALSE
		Recipient B		TRUE			FALSE			TRUE			FALSE
		Recipient AB		TRUE			TRUE			TRUE			TRUE
		```

### 3. public class TransplantGraph
Fully-documented class TransplantGraph that contains an adjacency matrix for the organ donors and recipients.

- private ArrayList<Patient> donors - Contains all organ donors in our system
- private ArrayList<Patient> recipients - Contains all recipients in our system
- public static final int MAX_PATIENTS = 100 - The maximum number of donors or recipients our system can store (each).
- private boolean[][] connections - Adjacency matrix used to track compatibility.
	- NOTE: The connections matrix is used to map connections between donors and recipients, while the donors and recipients lists are used to actually store the information associated with those patients.
	- NOTE: The connections matrix should be of size MAX_PATIENTS-by-MAX_PATIENTS.

- public TransplantGraph() - Constructor.
- public static TransplantGraph buildFromFiles(String donorFile, String recipientFile)
Creates and returns a new TransplantGraph object, intialized with the donor information found in donorFile and the recipient information found in recipientFile.
- public void addRecipient(Patient patient) - Adds the specified Patient to the recipients list. This method must also update the connections adjacency matrix, as necessary.
- public void addDonor(Patient patient) - Adds the specified Patient to the donors list. This method must also update the connections adjacency matrix, as necessary.
- public void removeRecipient(String name) - Removes the specified Patient from the recipients list. This method must also update the connections adjacency matrix, removing all connections to this recipient, and removing the column associated with the patient from the matrix.
- public void removeDonor(String name) - Removes the specified Patient from the donors list. This method must also update the connections adjacency matrix, removing all connections to this donor, and removing the row associated with the patient from the matrix.
- public void printAllRecipients() - Prints all organ recipients' information in a neatly formatted table. See Sample I/O.
- public void printAllDonors() - Prints all organ donors' information in a neatly formatted table. See Sample I/O.

### 4. Comparators
Fully-documented Comparator classes that will be used to sort your patient lists.

- public class NumConnectionsComparator implements Comparator<Patient>
- public class BloodTypeComparator implements Comparator<Patient>
- public class OrganComparator implements Comparator<Patient>

### 5. public class TransplantDriver
Fully-documented class TransplantDriver which acts as the main driver for the application. This class contains the main method for the program, which will first attempt to load any previously serialized TransplantGraph object located in 'transplant.obj'.

- public static final String DONOR_FILE = "donors.txt"
- public static final String RECIPIENT_FILE = "recipients.txt"

- Menu-driven application, prompting the user for input among the following options:
	- (LR) - List all recipients: Displays a table of all patients in the recipients list.
	- (LO) - List all donors: Displays a table of all patients in the donors list.
	- (AO) - Add new donor: Adds a new donor to the system.
	- (AR) - Add new recipient: Adds a new recipient to the system.
	- (RO) - Remove donor: Removes a donor from the system.
	- (RR) - Remove recipient: Removes a recipient from the system.
	- (SR) - Sort recipients: Displays a submenu:
		- (I) Sort by ID
		- (N) Sort by Number of Donors
		- (B) Sort by Blood Type
		- (O) Sort by Organ Needed
		- (Q) Back to Main Menu
	- (SO) - Sort donors: Displays a submenu:
		- (I) Sort by ID
		- (N) Sort by Number of Recipients
		- (B) Sort by Blood Type
		- (O) Sort by Organ Donated
		- (Q) Back to Main Menu
	- (Q) - Quit