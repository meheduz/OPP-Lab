// Problem 10: Smart Home Appliance Control System

interface Controllable {
    void turnOn();
    void turnOff();
}

abstract class Appliance implements Controllable {
    private String deviceId;
    private String brand;
    private double powerWatts;
    private boolean isOn;
    public static int totalDevices = 0;

    public Appliance(String deviceId, String brand, double powerWatts) {
        this.deviceId = deviceId;
        this.brand = brand;
        this.powerWatts = powerWatts;
        this.isOn = false;
        totalDevices++;
    }

    public Appliance(String deviceId, String brand) {
        this(deviceId, brand, 0.0);
    }

    public String getDeviceId() { return deviceId; }
    public String getBrand() { return brand; }
    public double getPowerWatts() { return powerWatts; }
    public boolean isActive() { return isOn; }
    protected void setOn(boolean v) { isOn = v; }

    public abstract double energyUsed(double hours);
    public abstract void displayStatus();

    public static class DeviceSpec {
        public String modelNumber;
        public int manufacturingYear;

        public DeviceSpec(String modelNumber, int manufacturingYear) {
            this.modelNumber = modelNumber;
            this.manufacturingYear = manufacturingYear;
        }

        public void display() {
            System.out.println("Device Spec | Model: " + modelNumber + " | Year: " + manufacturingYear);
        }
    }
}

class LightingDevice extends Appliance {
    private int brightnessLevel;
    private String color;

    public LightingDevice(String deviceId, String brand, double powerWatts, int brightnessLevel, String color) {
        super(deviceId, brand, powerWatts);
        this.brightnessLevel = brightnessLevel;
        this.color = color;
    }

    public int getBrightnessLevel() { return brightnessLevel; }
    public void setBrightnessLevel(int b) { brightnessLevel = b; }
    public String getColor() { return color; }

    public void turnOn() {
        setOn(true);
        System.out.println("LightingDevice " + getDeviceId() + " (" + getBrand() + ") is ON.");
    }

    public void turnOff() {
        setOn(false);
        System.out.println("LightingDevice " + getDeviceId() + " (" + getBrand() + ") is OFF.");
    }

    public double energyUsed(double hours) {
        return getPowerWatts() * hours / 1000.0;
    }

    public void displayStatus() {
        System.out.println("=== Lighting Device Status ===");
        System.out.println("Device ID  : " + getDeviceId());
        System.out.println("Brand      : " + getBrand());
        System.out.println("Power      : " + getPowerWatts() + " W");
        System.out.println("Status     : " + (isActive() ? "ON" : "OFF"));
        System.out.println("Brightness : " + brightnessLevel + "%");
        System.out.println("Color      : " + color);
    }

    public class SceneConfig {
        public String sceneName;
        public int targetBrightness;

        public SceneConfig(String sceneName, int targetBrightness) {
            this.sceneName = sceneName;
            this.targetBrightness = targetBrightness;
        }

        public void applyScene() {
            System.out.println();
            System.out.println("--- Scene Config ---");
            System.out.println("Scene Name     : " + sceneName);
            System.out.println("Target Bright  : " + targetBrightness + "%");
            System.out.println("Current Color  : " + getColor());
            System.out.println("Current Bright : " + getBrightnessLevel() + "% -> applying " + targetBrightness + "%");
            setBrightnessLevel(targetBrightness);
            System.out.println();
        }
    }
}

class SmartLightingDevice extends LightingDevice {
    private boolean voiceControlEnabled;
    private String automationSchedule;

    public SmartLightingDevice(String deviceId, String brand, double powerWatts, int brightnessLevel,
                               String color, boolean voiceControlEnabled, String automationSchedule) {
        super(deviceId, brand, powerWatts, brightnessLevel, color);
        this.voiceControlEnabled = voiceControlEnabled;
        this.automationSchedule = automationSchedule;
    }

    public void turnOn() {
        setOn(true);
        System.out.println("SmartLightingDevice " + getDeviceId() + " (" + getBrand() + ") is ON. [Voice Control: " +
                (voiceControlEnabled ? "Enabled" : "Disabled") + "]");
        System.out.println();
    }

    public void turnOff() {
        setOn(false);
        System.out.println("SmartLightingDevice " + getDeviceId() + " (" + getBrand() + ") is OFF.");
    }

    public double energyUsed(double hours) {
        return super.energyUsed(hours) * 0.85;
    }

    public void displayStatus() {
        System.out.println("=== Smart Lighting Device Status ===");
        System.out.println("Device ID           : " + getDeviceId());
        System.out.println("Brand               : " + getBrand());
        System.out.println("Power               : " + getPowerWatts() + " W");
        System.out.println("Status              : " + (isActive() ? "ON" : "OFF"));
        System.out.println("Brightness          : " + getBrightnessLevel() + "%");
        System.out.println("Color               : " + getColor());
        System.out.println("Voice Control       : " + (voiceControlEnabled ? "Enabled" : "Disabled"));
        System.out.println("Automation Schedule : " + automationSchedule);
    }
}

class ClimateDevice extends Appliance {
    private double temperatureC;
    private String mode;

    public ClimateDevice(String deviceId, String brand, double powerWatts, double temperatureC, String mode) {
        super(deviceId, brand, powerWatts);
        this.temperatureC = temperatureC;
        this.mode = mode;
    }

    public void turnOn() {
        setOn(true);
        System.out.println("ClimateDevice " + getDeviceId() + " (" + getBrand() + ") is ON.");
        System.out.println();
    }

    public void turnOff() {
        setOn(false);
        System.out.println("ClimateDevice " + getDeviceId() + " (" + getBrand() + ") is OFF.");
    }

    public double energyUsed(double hours) {
        double factor = "Cool".equalsIgnoreCase(mode) ? 1.2 : 1.0;
        return getPowerWatts() * hours * factor / 1000.0;
    }

    public void displayStatus() {
        System.out.println("=== Climate Device Status ===");
        System.out.println("Device ID   : " + getDeviceId());
        System.out.println("Brand       : " + getBrand());
        System.out.println("Power       : " + getPowerWatts() + " W");
        System.out.println("Status      : " + (isActive() ? "ON" : "OFF"));
        System.out.println("Temperature : " + temperatureC + " C");
        System.out.println("Mode        : " + mode);
    }
}

public class SmartHomeSystem {
    public static void main(String[] args) {
        Appliance.DeviceSpec spec = new Appliance.DeviceSpec("PH-L900", 2024);
        spec.display();

        SmartLightingDevice sld = new SmartLightingDevice("L001", "Philips", 10.0, 75,
                "Warm White", true, "22:00 OFF / 06:00 ON");

        ClimateDevice cd = new ClimateDevice("C001", "Gree", 1500.0, 24.0, "Cool");

        sld.turnOn();
        sld.displayStatus();
        System.out.println("Energy (8 hrs): " + sld.energyUsed(8) + " kWh");

        LightingDevice.SceneConfig scene = sld.new SceneConfig("Movie Mode", 30);
        scene.applyScene();

        sld.turnOff();

        cd.turnOn();
        cd.displayStatus();
        System.out.println("Energy (5 hrs): " + cd.energyUsed(5) + " kWh");

        cd.turnOff();

        System.out.println("Total Devices: " + Appliance.totalDevices);
    }
}
