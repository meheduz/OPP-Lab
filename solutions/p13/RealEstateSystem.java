// Problem 13: Real Estate Property Listing System

interface Listable { void generateListing(); }

abstract class Asset {
    private String assetId;
    private String ownerName;
    private String registrationDate;

    public Asset(String assetId, String ownerName, String registrationDate) {
        this.assetId = assetId; this.ownerName = ownerName; this.registrationDate = registrationDate;
    }
    public Asset(String assetId, String ownerName) { this(assetId, ownerName, ""); }
    public String getAssetId() { return assetId; }
    public String getOwnerName() { return ownerName; }
    public String getRegistrationDate() { return registrationDate; }
    public abstract void displayAssetInfo();
}

abstract class Property extends Asset implements Listable {
    private String location;
    private double areaSqFt;
    private double basePrice;
    public static int totalListings = 0;

    public Property(String assetId, String ownerName, String registrationDate,
                    String location, double areaSqFt, double basePrice) {
        super(assetId, ownerName, registrationDate);
        this.location = location; this.areaSqFt = areaSqFt; this.basePrice = basePrice;
        totalListings++;
    }
    public String getLocation() { return location; }
    public double getAreaSqFt() { return areaSqFt; }
    public double getBasePrice() { return basePrice; }
    public abstract double calculatePrice();
    public abstract double calculatePrice(double discountPct);
}

class ResidentialProperty extends Property {
    private int bedrooms;
    private int bathrooms;
    private boolean hasParking;

    public ResidentialProperty(String assetId, String ownerName, String registrationDate,
                               String location, double areaSqFt, double basePrice,
                               int bedrooms, int bathrooms, boolean hasParking) {
        super(assetId, ownerName, registrationDate, location, areaSqFt, basePrice);
        this.bedrooms = bedrooms; this.bathrooms = bathrooms; this.hasParking = hasParking;
    }
    public int getBedrooms() { return bedrooms; }
    public int getBathrooms() { return bathrooms; }
    public boolean getHasParking() { return hasParking; }

    public double calculatePrice() { return getBasePrice() + bedrooms * 50000.0; }
    public double calculatePrice(double discountPct) {
        return calculatePrice() * (1 - discountPct / 100.0);
    }

    public void generateListing() {
        System.out.println();
        System.out.println("*** RESIDENTIAL LISTING ***");
        System.out.println("[" + getAssetId() + "] " + bedrooms + "BHK at " + getLocation() +
                " | " + (int) getAreaSqFt() + " sqft | Parking: " + (hasParking ? "YES" : "NO"));
        System.out.println();
    }

    public void displayAssetInfo() {
        System.out.println("=== Residential Property ===");
        System.out.println("Asset ID : " + getAssetId() + " | Owner: " + getOwnerName() + " | Reg: " + getRegistrationDate());
        System.out.println("Location : " + getLocation() + " | Area: " + getAreaSqFt() + " sqft");
        System.out.println("Bedrooms : " + bedrooms + " | Bathrooms: " + bathrooms + " | Parking: " + (hasParking ? "YES" : "NO"));
    }
}

class LuxuryResidence extends ResidentialProperty {
    private boolean hasPool;
    private boolean hasGym;
    private double luxuryMarkupPercent;

    public LuxuryResidence(String assetId, String ownerName, String registrationDate,
                           String location, double areaSqFt, double basePrice,
                           int bedrooms, int bathrooms, boolean hasParking,
                           boolean hasPool, boolean hasGym, double luxuryMarkupPercent) {
        super(assetId, ownerName, registrationDate, location, areaSqFt, basePrice, bedrooms, bathrooms, hasParking);
        this.hasPool = hasPool; this.hasGym = hasGym; this.luxuryMarkupPercent = luxuryMarkupPercent;
    }

    public double calculatePrice() {
        double price = getBasePrice() * (1 + luxuryMarkupPercent / 100.0);
        if (hasPool) price += 500000;
        if (hasGym) price += 200000;
        return price;
    }
    public double calculatePrice(double discountPct) {
        return calculatePrice() * (1 - discountPct / 100.0);
    }

    public void generateListing() {
        System.out.println();
        System.out.println("*** LUXURY LISTING ***");
        System.out.println("[" + getAssetId() + "] LUXURY " + getBedrooms() + "BHK at " + getLocation() +
                " | Pool: " + (hasPool ? "YES" : "NO") + " | Gym: " + (hasGym ? "YES" : "NO"));
        System.out.println();
    }

    public void displayAssetInfo() {
        System.out.println();
        System.out.println("=== Luxury Residence ===");
        System.out.println("Asset ID : " + getAssetId() + " | Owner: " + getOwnerName() + " | Location: " + getLocation());
        System.out.println("Area: " + getAreaSqFt() + " sqft | Beds: " + getBedrooms() + " | Baths: " + getBathrooms());
        System.out.println("Pool: " + (hasPool ? "YES" : "NO") + " | Gym: " + (hasGym ? "YES" : "NO") + " | Markup: " + luxuryMarkupPercent + "%");
    }
}

class PropertyAgent {
    private String agentId;
    private String agentName;
    private double commissionRate;

    public PropertyAgent(String agentId, String agentName, double commissionRate) {
        this.agentId = agentId; this.agentName = agentName; this.commissionRate = commissionRate;
    }
    public String getAgentName() { return agentName; }
    public double calculateCommission(double salePrice) { return salePrice * commissionRate / 100.0; }

    public void displayAgentInfo() {
        System.out.println();
        System.out.println("=== Agent Info ===");
        System.out.println("ID: " + agentId + " | Name: " + agentName + " | Commission: " + commissionRate + "%");
        System.out.println();
    }
}

public class RealEstateSystem {
    public static void main(String[] args) {
        PropertyAgent agent = new PropertyAgent("AG01", "Sabbir", 2.5);

        ResidentialProperty rp = new ResidentialProperty("A001", "Hasan", "2024-01-10",
                "Sylhet City", 1200.0, 3500000.0, 3, 2, true);

        LuxuryResidence lr = new LuxuryResidence("A002", "Mina", "2024-03-20",
                "Gulshan", 3500.0, 12000000.0, 5, 4, true, true, true, 25.0);

        rp.displayAssetInfo();
        rp.generateListing();
        System.out.println("Sale Price   : " + rp.calculatePrice());
        System.out.println("With 5% Disc : " + rp.calculatePrice(5));

        lr.displayAssetInfo();
        lr.generateListing();
        System.out.println("Luxury Price : " + String.format("%.1f", lr.calculatePrice()));

        agent.displayAgentInfo();
        System.out.println("Agent Comm   : " + agent.calculateCommission(lr.calculatePrice()));

        System.out.println("Total Listings: " + Property.totalListings);
    }
}
