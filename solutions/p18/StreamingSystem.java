// Problem 18: Media Streaming Platform Subscription System

interface Streamable { void startStream(); }

abstract class Account {
    private String accountId;
    private String ownerEmail;
    private String password;

    public Account(String accountId, String ownerEmail, String password) {
        this.accountId = accountId; this.ownerEmail = ownerEmail; this.password = password;
    }
    public Account(String accountId, String ownerEmail) { this(accountId, ownerEmail, ""); }
    public String getAccountId() { return accountId; }
    public String getOwnerEmail() { return ownerEmail; }
    public abstract void displayAccountInfo();
}

abstract class StreamingAccount extends Account {
    private double watchedHours;
    private double dataLimitGB;
    public static int totalAccounts = 0;

    public StreamingAccount(String accountId, String ownerEmail, String password, double dataLimitGB) {
        super(accountId, ownerEmail, password);
        this.dataLimitGB = dataLimitGB;
        totalAccounts++;
    }
    public double getWatchedHours() { return watchedHours; }
    public double getDataLimitGB() { return dataLimitGB; }
    public void addWatchTime(double hrs) { watchedHours += hrs; }

    public abstract double calculateMonthlyCharge();
    public abstract double calculateMonthlyCharge(int extraDataGB);
}

class PaidAccount extends StreamingAccount implements Streamable {
    private String planName;
    private double monthlyFee;
    private boolean isHDEnabled;

    public PaidAccount(String accountId, String ownerEmail, String password, double dataLimitGB,
                       String planName, double monthlyFee, boolean isHDEnabled) {
        super(accountId, ownerEmail, password, dataLimitGB);
        this.planName = planName; this.monthlyFee = monthlyFee; this.isHDEnabled = isHDEnabled;
    }
    public String getPlanName() { return planName; }
    public double getMonthlyFee() { return monthlyFee; }
    public boolean isHDEnabled() { return isHDEnabled; }

    public double calculateMonthlyCharge() { return monthlyFee; }
    public double calculateMonthlyCharge(int extraDataGB) { return monthlyFee + extraDataGB * 50.0; }

    public void startStream() {
        System.out.println();
        System.out.println("Streaming started for " + getOwnerEmail() + " | HD: " + (isHDEnabled ? "YES" : "NO"));
    }

    public void displayAccountInfo() {
        System.out.println();
        System.out.println("=== Paid Account ===");
        System.out.println("Account ID : " + getAccountId() + " | Email: " + getOwnerEmail());
        System.out.println("Data Limit : " + getDataLimitGB() + " GB | Plan: " + planName);
        System.out.println("Fee        : " + monthlyFee + " | HD: " + (isHDEnabled ? "ENABLED" : "DISABLED"));
        System.out.println();
    }
}

class FamilyPlanAccount extends PaidAccount {
    private int maxProfiles;
    private int activeProfiles;
    private double sharedDataPoolGB;

    public FamilyPlanAccount(String accountId, String ownerEmail, String password, double dataLimitGB,
                             String planName, double monthlyFee, boolean isHDEnabled,
                             int maxProfiles, double sharedDataPoolGB) {
        super(accountId, ownerEmail, password, dataLimitGB, planName, monthlyFee, isHDEnabled);
        this.maxProfiles = maxProfiles; this.activeProfiles = 1; this.sharedDataPoolGB = sharedDataPoolGB;
    }

    public double calculateMonthlyCharge() { return getMonthlyFee() + activeProfiles * 100.0; }
    public double calculateMonthlyCharge(int extraDataGB) { return calculateMonthlyCharge() + extraDataGB * 50.0; }

    public void startStream() {
        System.out.println();
        System.out.println("Family streaming started | Active Profiles: " + activeProfiles + "/" + maxProfiles);
    }

    public void displayAccountInfo() {
        System.out.println();
        System.out.println("=== Family Plan Account ===");
        System.out.println("Account ID    : " + getAccountId() + " | Email: " + getOwnerEmail());
        System.out.println("Plan          : " + getPlanName() + " | Fee: " + getMonthlyFee());
        System.out.println("Max Profiles  : " + maxProfiles + " | Active: " + activeProfiles);
        System.out.println("Shared Pool   : " + sharedDataPoolGB + " GB");
        System.out.println();
    }
}

class ContentItem {
    private String contentId;
    private String title;
    private int durationMins;

    public ContentItem(String contentId, String title, int durationMins) {
        this.contentId = contentId; this.title = title; this.durationMins = durationMins;
    }
    public String getTitle() { return title; }

    public void displayContentInfo() {
        System.out.println("=== Content Info ===");
        System.out.println("ID: " + contentId + " | Title: " + title + " | Duration: " + durationMins + " mins");
    }
}

public class StreamingSystem {
    public static void main(String[] args) {
        ContentItem ci = new ContentItem("C001", "Dragon Chronicles", 142);

        PaidAccount pa = new PaidAccount("A001", "rana@mail.com", "pass123",
                50.0, "Standard", 499.0, true);

        FamilyPlanAccount fa = new FamilyPlanAccount("A002", "mitu@mail.com", "pass456",
                200.0, "Family", 999.0, true, 5, 200.0);

        ci.displayContentInfo();

        pa.displayAccountInfo();
        System.out.println("Monthly Charge : " + pa.calculateMonthlyCharge());
        System.out.println("With 5GB extra : " + pa.calculateMonthlyCharge(5));
        pa.startStream();

        fa.displayAccountInfo();
        System.out.println("Family Charge       : " + fa.calculateMonthlyCharge());
        fa.startStream();

        System.out.println("Total Accounts: " + StreamingAccount.totalAccounts);
    }
}
