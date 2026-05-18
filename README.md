# OOP Lab 2 Assignment

This repository contains the Java solutions for Lab 2. Each exercise is located in its own folder under `solutions/` and includes a single Java source file with an implementation of the required system.

## Repository structure

- `Assignment_02.pdf` — assignment specification and problem statements.
- `solutions/` — contains all solution projects.
  - `p01/BankSystem.java`
  - `p02/LibrarySystem.java`
  - `p03/PayrollSystem.java`
  - `p04/ShopNow.java`
  - `p05/UniversitySystem.java`
  - `p06/TransportSystem.java`
  - `p07/HospitalSystem.java`
  - `p08/RestaurantSystem.java`
  - `p09/SchoolSystem.java`
  - `p10/SmartHomeSystem.java`
  - `p11/AirlineSystem.java`
  - `p12/InsuranceSystem.java`
  - `p13/RealEstateSystem.java`
  - `p14/TelecomSystem.java`
  - `p15/StaffPayrollSystem.java`
  - `p16/GamingPlatform.java`
  - `p17/InventorySystem.java`
  - `p18/StreamingSystem.java`
  - `p19/FarmManagementSystem.java`
  - `p20/PowerUtilitySystem.java`

## How to compile

Open a terminal in the repository root and use `javac` to compile a specific file. For example:

```bash
cd /Users/meheduzzaman/Java\ lab\ 2\ Assignment
javac solutions/p02/LibrarySystem.java
```

## How to run

After compiling, run the class with `java`, using the path to the `.class` file without the `.class` extension. For example:

```bash
java -cp solutions/p02 LibrarySystem
```

If the class is not in a package, you can also run it from the folder containing the compiled `.class` file:

```bash
cd solutions/p02
java LibrarySystem
```

## Notes

- Each exercise appears to be a standalone Java program.
- If multiple classes are required, compile all source files in the same directory using `javac *.java`.
- The assignment specification is available in `Assignment_02.pdf`.

## Contact

If you need to update any solution or add execution instructions for a specific program, edit the appropriate file under `solutions/` and update this README accordingly.
