// Problem 19: Agricultural Farm Management & Yield System

interface Harvestable { void generateHarvestReport(); }

abstract class FarmAsset {
    private String assetId;
    private String farmName;
    private String ownerName;

    public FarmAsset(String assetId, String farmName, String ownerName) {
        this.assetId = assetId; this.farmName = farmName; this.ownerName = ownerName;
    }
    public FarmAsset(String assetId, String farmName) { this(assetId, farmName, ""); }
    public String getAssetId() { return assetId; }
    public String getFarmName() { return farmName; }
    public String getOwnerName() { return ownerName; }
    public abstract void displayAssetInfo();
}

abstract class Crop extends FarmAsset {
    private String cropName;
    private String plantingDate;
    private double areaAcres;
    public static int totalCropTypes = 0;

    public Crop(String assetId, String farmName, String ownerName, String cropName, String plantingDate, double areaAcres) {
        super(assetId, farmName, ownerName);
        this.cropName = cropName; this.plantingDate = plantingDate; this.areaAcres = areaAcres;
        totalCropTypes++;
    }
    public String getCropName() { return cropName; }
    public String getPlantingDate() { return plantingDate; }
    public double getAreaAcres() { return areaAcres; }
    public abstract double calculateRevenue();
    public abstract double calculateRevenue(double actualYieldKg);
}

class CashCrop extends Crop implements Harvestable {
    private double marketPricePerKg;
    private double expectedYieldKgPerAcre;
    private String harvestDate;

    public CashCrop(String assetId, String farmName, String ownerName, String cropName, String plantingDate,
                    double areaAcres, double marketPricePerKg, double expectedYieldKgPerAcre, String harvestDate) {
        super(assetId, farmName, ownerName, cropName, plantingDate, areaAcres);
        this.marketPricePerKg = marketPricePerKg; this.expectedYieldKgPerAcre = expectedYieldKgPerAcre;
        this.harvestDate = harvestDate;
    }
    public double getMarketPricePerKg() { return marketPricePerKg; }
    public double getExpectedYieldKgPerAcre() { return expectedYieldKgPerAcre; }
    public String getHarvestDate() { return harvestDate; }

    public double calculateRevenue() {
        return getAreaAcres() * expectedYieldKgPerAcre * marketPricePerKg;
    }
    public double calculateRevenue(double actualYieldKg) {
        return actualYieldKg * marketPricePerKg;
    }

    public void generateHarvestReport() {
        System.out.println();
        System.out.println("--- HARVEST REPORT: " + getCropName() + " ---");
        System.out.println("Total Expected: " + (getAreaAcres() * expectedYieldKgPerAcre) + " kg | Revenue: " + calculateRevenue());
        System.out.println();
    }

    public void displayAssetInfo() {
        System.out.println();
        System.out.println("=== Cash Crop Details ===");
        System.out.println("Asset ID : " + getAssetId() + " | Farm: " + getFarmName() + " | Owner: " + getOwnerName());
        System.out.println("Crop     : " + getCropName() + " | Area: " + getAreaAcres() + " acres | Planted: " + getPlantingDate());
        System.out.println("Market Price: " + marketPricePerKg + "/kg | Expected Yield: " + expectedYieldKgPerAcre + " kg/acre | Harvest: " + harvestDate);
        System.out.println();
    }
}

class ExportCrop extends CashCrop {
    private String exportDestination;
    private String certificationLabel;
    private double exportPremiumPercent;

    public ExportCrop(String assetId, String farmName, String ownerName, String cropName, String plantingDate,
                      double areaAcres, double marketPricePerKg, double expectedYieldKgPerAcre, String harvestDate,
                      String exportDestination, String certificationLabel, double exportPremiumPercent) {
        super(assetId, farmName, ownerName, cropName, plantingDate, areaAcres, marketPricePerKg, expectedYieldKgPerAcre, harvestDate);
        this.exportDestination = exportDestination; this.certificationLabel = certificationLabel;
        this.exportPremiumPercent = exportPremiumPercent;
    }

    public double calculateRevenue() {
        double base = getExpectedYieldKgPerAcre() * getMarketPricePerKg();
        return base * (1 + exportPremiumPercent / 100.0);
    }
    public double calculateRevenue(double actualYieldKg) {
        double base = actualYieldKg * getMarketPricePerKg();
        return base * (1 + exportPremiumPercent / 100.0);
    }

    public void generateHarvestReport() {
        double base = getExpectedYieldKgPerAcre() * getMarketPricePerKg();
        double premium = base * exportPremiumPercent / 100.0;
        System.out.println();
        System.out.println("--- EXPORT HARVEST REPORT: " + getCropName() + " ---");
        System.out.println("Destination: " + exportDestination + " | Revenue: " + base + " | Premium: " + premium + " | Total: " + (base + premium));
        System.out.println();
    }

    public void displayAssetInfo() {
        System.out.println();
        System.out.println("=== Export Crop Details ===");
        System.out.println("Asset ID : " + getAssetId() + " | Crop: " + getCropName());
        System.out.println("Area: " + getAreaAcres() + " acres | Price: " + getMarketPricePerKg() + "/kg | Dest: " + exportDestination);
        System.out.println("Certification: " + certificationLabel + " | Export Premium: " + exportPremiumPercent + "%");
        System.out.println();
    }
}

class FarmPlot {
    private String plotId;
    private String soilType;
    private String irrigationType;

    public FarmPlot(String plotId, String soilType, String irrigationType) {
        this.plotId = plotId; this.soilType = soilType; this.irrigationType = irrigationType;
    }
    public String getPlotId() { return plotId; }

    public void displayPlotInfo() {
        System.out.println("=== Farm Plot Info ===");
        System.out.println("Plot ID    : " + plotId);
        System.out.println("Soil       : " + soilType);
        System.out.println("Irrigation : " + irrigationType);
    }
}

public class FarmManagementSystem {
    public static void main(String[] args) {
        FarmPlot fp = new FarmPlot("PL01", "Alluvial", "Drip Irrigation");

        CashCrop cc = new CashCrop("FA01", "GreenAcre", "Rahim",
                "Paddy", "2025-01-15", 10.0, 30.0, 800.0, "2025-06-01");

        ExportCrop ec = new ExportCrop("FA02", "GreenAcre", "Rahim",
                "Hilsa Fish", "2025-02-01", 5.0, 1200.0, 200.0,
                "2025-07-01", "Japan", "HACCP", 20.0);

        fp.displayPlotInfo();

        cc.displayAssetInfo();
        System.out.println("Expected Revenue: " + cc.calculateRevenue());
        System.out.println("Actual (7500kg) : " + cc.calculateRevenue(7500));
        cc.generateHarvestReport();

        ec.displayAssetInfo();
        System.out.println("Export Revenue      : " + ec.calculateRevenue());
        ec.generateHarvestReport();

        System.out.println("Total Crop Types: " + Crop.totalCropTypes);
    }
}
