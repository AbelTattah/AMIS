### Atinka Meds Inventory System (AMIS)

Inventory System for Atinka Meds

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

### How to Run the Project

#### Prerequisites
- Java Development Kit (JDK) 11 or higher. Download it by clicking [here](https://www.techspot.com/downloads/5553-java-jdk.html)
- Text editor or Integrated Development Environment (IDE) like IntelliJ IDEA or Eclipse

#### Step 1: Open Terminal
- On Windows: Press `Windows + R`, type `cmd`, press Enter
- On Mac: Open Spotlight (`Cmd + Space`), type `Terminal`, press Enter

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


