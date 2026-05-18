// Problem 14: Telecommunication Subscriber Billing System

interface Invoiceable { void generateInvoice(); }

abstract class Subscriber {
    private String subscriberId;
    private String subscriberName;
    private String email;

    public Subscriber(String subscriberId, String subscriberName, String email) {
        this.subscriberId = subscriberId; this.subscriberName = subscriberName; this.email = email;
    }
    public Subscriber(String subscriberId, String subscriberName) { this(subscriberId, subscriberName, ""); }
    public String getSubscriberId() { return subscriberId; }
    public String getSubscriberName() { return subscriberName; }
    public String getEmail() { return email; }
    public abstract void displayProfile();
}

abstract class MobileSubscriber extends Subscriber {
    private String phoneNumber;
    private String networkType;
    public static int totalSubscribers = 0;

    public MobileSubscriber(String subscriberId, String subscriberName, String email, String phoneNumber, String networkType) {
        super(subscriberId, subscriberName, email);
        this.phoneNumber = phoneNumber; this.networkType = networkType;
        totalSubscribers++;
    }
    public String getPhoneNumber() { return phoneNumber; }
    public String getNetworkType() { return networkType; }
}

class PostpaidSubscriber extends MobileSubscriber implements Invoiceable {
    private double monthlyLimit;
    private double dataUsedGB;
    private double ratePerExtraGB;

    public PostpaidSubscriber(String subscriberId, String subscriberName, String email, String phoneNumber, String networkType,
                              double monthlyLimit, double ratePerExtraGB) {
        super(subscriberId, subscriberName, email, phoneNumber, networkType);
        this.monthlyLimit = monthlyLimit; this.ratePerExtraGB = ratePerExtraGB;
    }
    public double getMonthlyLimit() { return monthlyLimit; }
    public double getRatePerExtraGB() { return ratePerExtraGB; }
    public double getDataUsedGB() { return dataUsedGB; }

    public double calculateBill() { return monthlyLimit; }
    public double calculateBill(double extraGB) {
        this.dataUsedGB = extraGB;
        return monthlyLimit + extraGB * ratePerExtraGB;
    }

    public void generateInvoice() {
        System.out.println();
        System.out.println("--- POSTPAID INVOICE ---");
        System.out.println("Subscriber: " + getSubscriberName() + " | Phone: " + getPhoneNumber());
        double extra = dataUsedGB * ratePerExtraGB;
        System.out.println("Monthly Limit: " + monthlyLimit + " | Extra Charge: " + extra + " | Total: " + (monthlyLimit + extra));
    }

    public void displayProfile() {
        System.out.println();
        System.out.println("=== Postpaid Subscriber ===");
        System.out.println("ID    : " + getSubscriberId() + " | Name: " + getSubscriberName() + " | Email: " + getEmail());
        System.out.println("Phone : " + getPhoneNumber() + " | Network: " + getNetworkType());
        System.out.println("Limit : " + monthlyLimit + " | Rate/ExtraGB: " + ratePerExtraGB);
    }
}

class PremiumPostpaid extends PostpaidSubscriber {
    private String managerName;
    private double rolloverDataGB;

    public PremiumPostpaid(String subscriberId, String subscriberName, String email, String phoneNumber, String networkType,
                           double monthlyLimit, double ratePerExtraGB, String managerName, double rolloverDataGB) {
        super(subscriberId, subscriberName, email, phoneNumber, networkType, monthlyLimit, ratePerExtraGB);
        this.managerName = managerName; this.rolloverDataGB = rolloverDataGB;
    }

    public double calculateBill() { return getMonthlyLimit(); }
    public double calculateBill(double extraGB) {
        double effectiveExtra = Math.max(0, extraGB - rolloverDataGB);
        return getMonthlyLimit() + effectiveExtra * getRatePerExtraGB();
    }

    public void generateInvoice() {
        System.out.println();
        System.out.println("--- PREMIUM INVOICE ---");
        System.out.println("Subscriber: " + getSubscriberName() + " | Manager: " + managerName);
        System.out.println("Base: " + getMonthlyLimit() + " | Rollover Applied: " + rolloverDataGB + " GB | Total: " + getMonthlyLimit());
    }

    public void displayProfile() {
        System.out.println();
        System.out.println("=== Premium Postpaid ===");
        System.out.println("ID    : " + getSubscriberId() + " | Name: " + getSubscriberName() + " | Network: " + getNetworkType());
        System.out.println("Limit : " + getMonthlyLimit() + " | Rollover: " + rolloverDataGB + " GB");
        System.out.println("Manager: " + managerName);
        System.out.println();
    }
}

class SimCard {
    private String simNumber;
    private String operator;
    private String activationDate;

    public SimCard(String simNumber, String operator, String activationDate) {
        this.simNumber = simNumber; this.operator = operator; this.activationDate = activationDate;
    }
    public String getSimNumber() { return simNumber; }

    public void displaySimInfo() {
        System.out.println("=== SIM Card Info ===");
        System.out.println("SIM : " + simNumber + " | Operator: " + operator + " | Activated: " + activationDate);
    }
}

public class TelecomSystem {
    public static void main(String[] args) {
        SimCard sim = new SimCard("8801911XXXXXX", "ConnectBD", "2023-06-01");

        PostpaidSubscriber ps = new PostpaidSubscriber("S001", "Ruhul", "ruhul@mail.com",
                "01911000001", "4G", 500.0, 30.0);

        PremiumPostpaid pp = new PremiumPostpaid("S002", "Farida", "farida@mail.com",
                "01922000002", "5G", 800.0, 25.0, "Mr. Alam", 3.0);

        sim.displaySimInfo();

        ps.displayProfile();
        System.out.println("Bill (8GB used) : " + ps.calculateBill(8));
        ps.generateInvoice();

        pp.displayProfile();
        System.out.println("Premium Bill       : " + pp.calculateBill());
        pp.generateInvoice();

        System.out.println();
        System.out.println("Total Subscribers: " + MobileSubscriber.totalSubscribers);
    }
}
