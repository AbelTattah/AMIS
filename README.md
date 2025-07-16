### Atinka Meds Inventory System (AMIS)

Inventory System for Atinka Meds


### How to clone the project

1. Open your terminal
2. Type this command:
```bash
git clone -b develop https://github.com/AbelTattah/AMIS.git
```

Make sure you have git installed on your computer for this to work. If you don't have git installed, you can download it from [here](https://git-scm.com/)

---

### What's Inside

The project folder looks like this:
```
AMIS/
├── src/                          (All your code goes here)
│   └── main/
│       ├── java/                 (Java files)
│       │   └── com/
│       │       └── gp27/
│       │           └── amis/
│       │               ├── App.java          (Main program)
│       │               └── utils/
│       │                   └── Helper.java   (Extra tools)
│       └── resources/            (Settings file)
│           └── config.properties (Configuration)
├── .gitignore                    (Files to ignore)
├── LICENSE                       (Rules for using this)
└── README.md                     (This guide)
```

---

# Atinka Meds Inventory System (AMIS)

## Prerequisites
- **Java Development Kit (JDK) 11 or higher**
  - Download from [Oracle](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) or [AdoptOpenJDK](https://adoptopenjdk.net/)
  - Verify installation: `java -version` and `javac -version`
- **PowerShell 5.1 or later** (included with Windows 10/11)
  - Verify version: `$PSVersionTable.PSVersion`

## Quick Start with PowerShell Script

The easiest way to run AMIS is using the provided PowerShell script:

1. **Open PowerShell**
   - Press `Win + X` and select "Windows PowerShell" or "Terminal"
   - Navigate to the project directory:
     ```powershell
     cd "C:\path\to\AMIS"  # Replace with your actual path
     ```

2. **Run the script**
   ```powershell
   .\run.ps1
   ```
   - First run will create necessary directories and empty data files
   - The application will compile and start automatically

3. **Troubleshooting**
   - If you get a security error, run:
     ```powershell
     Set-ExecutionPolicy -ExecutionPolicy RemoteSigned -Scope CurrentUser
     ```
   - Make sure Java is in your system PATH

## Manual Build and Run

### Windows (Command Prompt)
```cmd
:: Create output directory
mkdir out

:: Compile all Java files
javac -d out src\main\java\com\gp27\amis\*.java src\main\java\com\gp27\amis\**\*.java

:: Run the application
java -cp out com.gp27.amis.App
```

### Linux/macOS
```bash
# Create output directory
mkdir -p out

# Compile all Java files
find src -name "*.java" > sources.txt
javac -d out @sources.txt

# Run the application
java -cp out com.gp27.amis.App
```

## Project Structure
```
AMIS/
├── data/                  # Data files (auto-created on first run)
│   ├── drugs.txt          # Drug inventory
│   ├── suppliers.txt      # Supplier information
│   ├── customers.txt      # Customer records
│   ├── purchase_history.txt
│   └── sales_log.txt
├── out/                   # Compiled .class files
├── src/                   # Source code
│   └── main/
│       └── java/
│           └── com/gp27/amis/
│               ├── App.java           # Main application class
│               ├── datastructures/    # Custom data structures
│               ├── io/                # File I/O operations
│               ├── models/            # Data models
│               └── utils/             # Utility classes
├── .github/workflows/     # CI/CD configuration
├── run.ps1               # PowerShell build and run script
└── README.md             # This file
```

## Development Workflow

1. **Running Tests**
   ```powershell
   # After compiling with run.ps1 or manually
   java -cp out org.junit.runner.JUnitCore com.gp27.amis.tests.AllTests
   ```

2. **Code Style**
   - Follow Java naming conventions
   - Use 4 spaces for indentation
   - Add Javadoc for public methods and classes

3. **Version Control**
   - Create feature branches for new features
   - Write meaningful commit messages
   - Open pull requests for code review

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

#### Step 2: Go to the Project Folder
Type this in terminal (change path to where you saved AMIS):
```bash
cd path/to/your/AMIS
```

#### Step 3: Create Output Folder
```bash
mkdir out
```

#### Step 4: Compile the Code
```bash
javac -d out src/main/java/com/gp27/amis/App.java src/main/java/com/gp27/amis/utils/Helper.java
```

#### Step 5: Copy Settings File
```bash
copy src\main\resources\config.properties out  # For Windows
# OR
cp src/main/resources/config.properties out   # For Mac/Linux
```

#### Step 6: Run the Program
```bash
java -cp out com.gp27.amis.App
```

---

### How to Modify the Project

1. **Add new Java files**:
   - Put them in `src/main/java/com/gp27/amis/` or the `utils` folder
   - Add them to the compile command in Step 4

2. **Change settings**:
   - Edit `src/main/resources/config.properties`
   - Remember to copy it again (Step 5) after changes

3. **Add new features**:
   - Create new classes in the `amis` folder
   - Use them in `App.java`

---

### Troubleshooting

If something doesn't work:
1. Check for typos in commands
2. Make sure you're in the AMIS folder
3. Verify files are in correct locations:
   - `App.java` should be in `src/main/java/com/gp27/amis/`
   - `config.properties` should be in `src/main/resources/`

---

### Project Tips
- Keep all Java files in the `java` folder
- Put settings files in `resources`
- Compile often to catch errors early


