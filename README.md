# Campus Lost & Found Tracker (JDBC)

Anudeep Foundation Course Project — Standalone Console Application built with **Core Java**, **JDBC**, and **MySQL**, designed for **Eclipse IDE**.

---

## Project Structure

```
campus-lost-found-tracker/
│
├── .classpath                      # Eclipse IDE classpath configuration
├── .project                        # Eclipse IDE project configuration
├── README.md                       # Quickstart documentation
│
├── bin/                            # Compiled Java bytecode (.class files)
│   └── com/campus/lostfound/
│       ├── DBConnection.class
│       ├── Item.class
│       ├── ItemDAO.class
│       └── Main.class
│
├── docs/                           # Project reports and viva materials
│   ├── PROJECT_REPORT.md           # Full academic project report
│   └── VIVA_QUESTIONS.md           # 20 Viva questions and answers
│
├── lib/                            # JDBC driver library
│   └── mysql-connector-j-8.0.33.jar # Official MySQL JDBC Driver
│
├── sql/                            # Database setup
│   └── schema.sql                  # Database and table creation script + sample data
│
└── src/                            # Source code (.java files)
    └── com/campus/lostfound/
        ├── DBConnection.java       # Database credentials & connection logic
        ├── Item.java               # Model / POJO class
        ├── ItemDAO.java            # CRUD operations with PreparedStatement
        └── Main.java               # Console menu, user input & validation
```

---

## 1. Prerequisites

1. **Java Development Kit (JDK 8 or higher, JDK 17 recommended)**.
2. **MySQL Server (version 8.0 or higher)** installed and running.
3. **Eclipse IDE for Java Developers** (or any preferred terminal/IDE).
4. MySQL Connector/J driver (pre-included inside the `lib/` directory).

---

## 2. Database Setup (MySQL)

1. Open **MySQL Workbench**, **XAMPP phpMyAdmin**, or **MySQL Command Line Client**.
2. Run the script located in `sql/schema.sql`:
   ```sql
   CREATE DATABASE IF NOT EXISTS campus_lost_found;
   USE campus_lost_found;

   CREATE TABLE items (
       id INT AUTO_INCREMENT PRIMARY KEY,
       item_name VARCHAR(100) NOT NULL,
       category VARCHAR(50),
       description VARCHAR(255),
       location VARCHAR(100),
       report_type VARCHAR(10) NOT NULL CHECK (report_type IN ('LOST', 'FOUND')),
       reported_by VARCHAR(100),
       contact_number VARCHAR(20),
       reported_date DATE NOT NULL,
       status VARCHAR(20) NOT NULL DEFAULT 'LOST' CHECK (status IN ('LOST', 'FOUND', 'RETURNED'))
   );
   ```
3. Update your MySQL username and password in `src/com/campus/lostfound/DBConnection.java`:
   ```java
   private static final String USER = "root";
   private static final String PASSWORD = "root"; // <-- change this to your MySQL password
   ```

---

## 3. How to Open and Run in Eclipse IDE

### Step 1: Open Eclipse
Launch your Eclipse IDE and choose your desired workspace.

### Step 2: Import the Project
1. Go to **File -> Open Projects from File System...**
2. In the **Import source** field, click **Directory...** and browse to:
   ```
   C:\Users\Admin\.gemini\antigravity-ide\scratch\campus-lost-found-tracker
   ```
3. Click **Finish**. Eclipse will immediately load the project, `src` folder, and automatically resolve the pre-configured `lib/mysql-connector-j-8.0.33.jar`.

### Step 3: (If configuring manually) Add MySQL Connector/J to Build Path
*(Only needed if creating a new project from scratch without `.project`)*:
1. Right-click the project -> **Properties** -> **Java Build Path**.
2. Select the **Libraries** tab -> **Classpath** -> click **Add JARs...** (or **Add External JARs...**).
3. Select `lib/mysql-connector-j-8.0.33.jar` and click **Apply and Close**.

### Step 4: Run the Application
1. In the Project Explorer, open:
   `src -> com.campus.lostfound -> Main.java`
2. Right-click on `Main.java` -> **Run As -> Java Application**.
3. The interactive console menu will appear in the Eclipse **Console** view at the bottom of the screen!

---

## 4. How to Compile and Run via Terminal / Command Prompt

### Step 1: Open Terminal in the project root directory:
```bash
cd C:\Users\Admin\.gemini\antigravity-ide\scratch\campus-lost-found-tracker
```

### Step 2: Compile the Java classes:
```bash
javac -cp ".;lib\mysql-connector-j-8.0.33.jar" -d bin src\com\campus\lostfound\*.java
```

### Step 3: Run the application:
```bash
java -cp "bin;lib\mysql-connector-j-8.0.33.jar" com.campus.lostfound.Main
```

---

## 5. Main Menu & Features

```text
========================================
       CAMPUS LOST & FOUND TRACKER      
========================================
1. Report Lost Item
2. Report Found Item
3. View All Items
4. Search Item by ID
5. Search Items by Name
6. View Lost Items
7. View Found Items
8. Update Item Details
9. Mark Item as Returned
10. Delete Item
11. Exit
========================================
Enter your choice:
```

---

## 6. Project Documentation & Viva Preparation
- Comprehensive Project Report: `docs/PROJECT_REPORT.md`
- 20 Viva Questions with Simple Answers: `docs/VIVA_QUESTIONS.md`
