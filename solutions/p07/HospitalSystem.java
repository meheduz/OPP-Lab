// Problem 7: Hospital Patient Management System

abstract class Patient {
    private String patientID;
    private String name;
    private int age;
    public static int totalPatients = 0;

    public Patient(String patientID, String name, int age) {
        this.patientID = patientID;
        this.name = name;
        this.age = age;
        totalPatients++;
    }

    public String getPatientID() { return patientID; }
    public String getName() { return name; }
    public int getAge() { return age; }

    public abstract void displayInfo();
    public abstract double computeBill();
}

abstract class RegisteredPatient extends Patient {
    private String registrationDate;
    private String assignedDoctor;

    public RegisteredPatient(String patientID, String name, int age, String registrationDate, String assignedDoctor) {
        super(patientID, name, age);
        this.registrationDate = registrationDate;
        this.assignedDoctor = assignedDoctor;
    }

    public String getAssignedDoctor() { return assignedDoctor; }
    public String getRegistrationDate() { return registrationDate; }
}

class InPatient extends RegisteredPatient {
    private int wardNumber;
    private double dailyCharge;
    private int daysAdmitted;

    public InPatient(String patientID, String name, int age, String registrationDate, String assignedDoctor,
                     int wardNumber, double dailyCharge, int daysAdmitted) {
        super(patientID, name, age, registrationDate, assignedDoctor);
        this.wardNumber = wardNumber;
        this.dailyCharge = dailyCharge;
        this.daysAdmitted = daysAdmitted;
    }

    public double computeBill() {
        WardBillingPolicy wbp = new WardBillingPolicy();
        return wbp.compute(dailyCharge, daysAdmitted);
    }

    public void displayInfo() {
        System.out.println("=== InPatient ===");
        System.out.println("ID: " + getPatientID() + " | Name: " + getName() + " | Age: " + getAge());
        System.out.println("Doctor: " + getAssignedDoctor() + " | Registered: " + getRegistrationDate());
        System.out.println("Ward: " + wardNumber + " | Daily: " + dailyCharge + " | Days: " + daysAdmitted);
        System.out.println("Ward Cost (WardBillingPolicy): " + computeBill());
    }

    public static class WardBillingPolicy {
        public double compute(double dailyCharge, int days) {
            return dailyCharge * days;
        }
    }
}

class OutPatient extends RegisteredPatient {
    private double consultationFee;
    private double prescriptionCost;

    public OutPatient(String patientID, String name, int age, String registrationDate, String assignedDoctor,
                      double consultationFee, double prescriptionCost) {
        super(patientID, name, age, registrationDate, assignedDoctor);
        this.consultationFee = consultationFee;
        this.prescriptionCost = prescriptionCost;
    }

    public double computeBill() {
        PrescriptionChargePolicy pcp = new PrescriptionChargePolicy();
        return consultationFee + pcp.total(prescriptionCost);
    }

    public void displayInfo() {
        System.out.println();
        System.out.println("=== OutPatient ===");
        System.out.println("ID: " + getPatientID() + " | Name: " + getName() + " | Age: " + getAge());
        System.out.println("Doctor: " + getAssignedDoctor() + " | Registered: " + getRegistrationDate());
        System.out.println("Consultation: " + consultationFee);
        PrescriptionChargePolicy pcp = new PrescriptionChargePolicy();
        System.out.println("Prescription + Dispensing (PrescriptionChargePolicy): " + pcp.total(prescriptionCost));
    }

    public static class PrescriptionChargePolicy {
        public double total(double prescriptionCost) {
            return prescriptionCost + 20.0;
        }
    }
}

class MedicalTaxCalculator {
    public double computeTax(double income) {
        return income * 0.15;
    }
    public double computeTax(double income, int deps) {
        return (income - deps * 500.0) * 0.15;
    }
}

public class HospitalSystem {
    public static void main(String[] args) {
        InPatient ip = new InPatient("P001", "Rafi", 45, "01-06-2024", "Dr. Alam", 3, 1500.0, 7);
        OutPatient op = new OutPatient("P002", "Mim", 30, "02-06-2024", "Dr. Roy", 300.0, 450.0);

        ip.displayInfo();
        System.out.println("Bill: " + ip.computeBill());

        op.displayInfo();
        System.out.println("Bill: " + op.computeBill());

        MedicalTaxCalculator mtc = new MedicalTaxCalculator();
        System.out.println();
        System.out.println("Tax (no dep) : " + mtc.computeTax(10500));
        System.out.println("Tax (2 dep) : " + mtc.computeTax(10500, 2));

        System.out.println("Total Patients: " + Patient.totalPatients);
    }
}
