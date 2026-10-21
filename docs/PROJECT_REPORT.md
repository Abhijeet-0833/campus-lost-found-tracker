# PROJECT REPORT

## PROJECT TITLE: CAMPUS LOST & FOUND TRACKER (JDBC)
**Course:** Anudeep Foundation Java Development Program  
**Domain:** Core Java, JDBC & Relational Database Management (MySQL)  
**Type:** Console-Based Standalone Application  
**IDE Used:** Eclipse IDE / Java SE 17  

---

### 1. INTRODUCTION
In academic institutions and university campuses, hundreds of students, faculty members, and staff navigate classrooms, seminar halls, cafeterias, playgrounds, and libraries daily. Consequently, losing valuable items—such as student identity cards, scientific calculators, laptops, chargers, books, keys, and water bottles—is a routine occurrence.

Currently, most college campuses rely on ad-hoc, informal methods such as notice boards, WhatsApp groups, or word of mouth to recover lost items. These methods often fail because notices get buried or lost, causing inconvenience and financial loss to students.

The **Campus Lost & Found Tracker** is a robust, lightweight, and console-based database application built using **Core Java** and **JDBC (Java Database Connectivity)** connected to a **MySQL** relational database. It provides an organized, automated platform to record lost items, register found items, search items by identity or name, update statuses, and log successful returns with transparency and accountability.

---

### 2. OBJECTIVES
The primary objectives of this project are:
1. **Centralized Record Keeping:** Provide a unified database where all lost and found items on campus can be tracked.
2. **Easy Retrieval & Search:** Enable quick search capabilities by item ID or keywords (partial name search) to instantly discover matching records.
3. **Status Lifecycle Tracking:** Track an item through its lifecycle: `LOST` -> `FOUND` -> `RETURNED`.
4. **Data Integrity & Security:** Prevent SQL injection vulnerabilities using parameterized SQL queries with `PreparedStatement`.
5. **Practical Application of Core Java & JDBC:** Demonstrate solid understanding of OOP (Object-Oriented Programming), Exception Handling, DAO design pattern, try-with-resources, and MySQL database management.

---

### 3. PROBLEM STATEMENT & SCOPE

#### Problem Statement:
Campus administrators and security personnel lack a systematic, persistent system to record lost personal effects and reconcile them with found items turned in by students. This leads to unclaimed items lying in security rooms or students being unable to retrieve their critical belongings.

#### Scope of the Project:
- **In-Scope:**
  - Recording detailed reports of lost items with reporter info, location, date, and descriptions.
  - Recording items found by honest individuals with handover location.
  - Searching items by unique ID or keyword.
  - Filtering items by type (`LOST` or `FOUND`).
  - Updating item details and changing statuses to `RETURNED`.
  - Deleting obsolete or duplicate records.
  - Safe user input handling and automated resource cleanup via JDBC try-with-resources.
- **Out of Scope:**
  - Web UI, Mobile apps, or RESTful APIs (strictly console-based as mandated by academic curriculum).
  - Online payments or courier shipping.

---

### 4. SOFTWARE & HARDWARE REQUIREMENTS

#### Software Requirements:
- **Operating System:** Windows 10/11, Linux, or macOS
- **Programming Language:** Java SE 8 or higher (Tested with Java SE 17)
- **Database:** MySQL Server 8.0+ or MariaDB
- **Database Driver:** MySQL Connector/J (`mysql-connector-j-8.0.33.jar`)
- **Integrated Development Environment (IDE):** Eclipse IDE for Java Developers (2022+ recommended) / VS Code / Terminal

#### Hardware Requirements:
- **Processor:** Dual-core Intel/AMD 2.0 GHz or higher
- **RAM:** Minimum 4 GB (8 GB recommended for Eclipse IDE)
- **Storage:** Minimum 500 MB free hard disk space
- **Input/Output:** Standard keyboard and console terminal display

---

### 5. SYSTEM ARCHITECTURE & MODULES

The application follows the industry-standard **DAO (Data Access Object) Pattern**, which cleanly separates data persistence logic from business and presentation logic.

```
+-------------------------------------------------------------+
|                      PRESENTATION LAYER                     |
|            Main.java (Console Menu, User Prompts)           |
+-------------------------------------------------------------+
                              |
                              v (Passes Item POJO / Criteria)
+-------------------------------------------------------------+
|                     DATA ACCESS LAYER                       |
|           ItemDAO.java (CRUD logic, PreparedStatement)       |
+-------------------------------------------------------------+
               |                               |
               v (Retrieves Connection)        v (Maps ResultSet)
+-------------------------------+   +-------------------------+
|     DBConnection.java         |   |       Item.java         |
|  (DriverManager, JDBC Driver) |   |  (Model / Entity POJO)  |
+-------------------------------+   +-------------------------+
               |
               v (TCP / Port 3306)
+-------------------------------------------------------------+
|                      DATABASE LAYER                         |
|             MySQL Server: database 'campus_lost_found'      |
|                       Table: 'items'                        |
+-------------------------------------------------------------+
```

#### Modules Description:
1. **Model Layer (`Item.java`):**
   - Encapsulates all data attributes of a lost or found item.
   - Contains getters, setters, constructors, and overridden `toString()` method.
2. **Database Connection Utility (`DBConnection.java`):**
   - Manages connection URL, database credentials, and driver loading (`com.mysql.cj.jdbc.Driver`).
   - Offers connection testing and diagnosis utilities.
3. **Data Access Object (`ItemDAO.java`):**
   - Implements SQL queries using `PreparedStatement`.
   - Maps database rows from `ResultSet` into `Item` objects.
   - Houses all CRUD (Create, Read, Update, Delete) methods.
4. **User Interface / Controller (`Main.java`):**
   - Interactive CLI menu with 11 choices.
   - Validates user input (empty name checks, numeric validation, date formatting).
   - Renders tabular displays for multi-record output.

---

### 6. DATABASE DESIGN

#### Database Name: `campus_lost_found`
#### Table Name: `items`

| Column Name | Data Type | Constraints | Description |
|---|---|---|---|
| `id` | INT | PRIMARY KEY, AUTO_INCREMENT | Unique identifier for each reported item |
| `item_name` | VARCHAR(100) | NOT NULL | Name or title of the item (e.g. "Student ID Card") |
| `category` | VARCHAR(50) | NULL | Category (e.g. Electronics, Documents, Stationery) |
| `description` | VARCHAR(255) | NULL | Specific details (color, brand, distinguishing marks) |
| `location` | VARCHAR(100) | NULL | Place where the item was lost or discovered |
| `report_type` | VARCHAR(10) | NOT NULL, CHECK in ('LOST','FOUND') | Indicates if the item is lost or found |
| `reported_by` | VARCHAR(100) | NULL | Name of the student / person reporting |
| `contact_number` | VARCHAR(20) | NULL | Phone or contact info of reporter |
| `reported_date` | DATE | NOT NULL | Date when the item was lost/found |
| `status` | VARCHAR(20) | NOT NULL, CHECK in ('LOST','FOUND','RETURNED') | Current lifecycle state of the item |

---

### 7. IMPLEMENTATION DETAILS

#### 1. Preventing SQL Injection:
All queries use `PreparedStatement` with placeholders (`?`). Parameters are bound using typed setter methods such as `ps.setString()`, `ps.setInt()`, and `ps.setDate()`. User input is never concatenated directly into SQL strings.

#### 2. Automatic Resource Management (try-with-resources):
Database connections, statements, and result sets implement the `AutoCloseable` interface. Wrapping them in try-with-resources ensures they are closed automatically even when runtime exceptions occur, preventing memory and socket leaks.

#### 3. Graceful Error Handling:
All database operations catch `SQLException` and log human-readable diagnostic messages without terminating the application. Console input methods check for `NumberFormatException` and reprompt the user rather than crashing.

---

### 8. TESTING & TEST CASES

| Test ID | Test Scenario | Input Given | Expected Result | Actual Result | Status |
|---|---|---|---|---|---|
| TC-01 | Database Connectivity | Run application | "Connected successfully" / Warning if DB off | Handled gracefully | PASS |
| TC-02 | Add Lost Item (Valid) | Name="Watch", Category="Personal", etc. | Record inserted into DB with AUTO_INCREMENT ID | Item saved, ID assigned | PASS |
| TC-03 | Add Item (Empty Name) | Pressed Enter with blank name | Re-prompts: "This field cannot be empty" | User re-prompted | PASS |
| TC-04 | Non-numeric Menu Choice | Input="abc" | Handled, prompts for valid integer | Reprompts without crash | PASS |
| TC-05 | Search by Non-existent ID| ID=9999 | Display: "[!] No item found with ID: 9999" | Informative message shown | PASS |
| TC-06 | Search by Partial Name | Input="ear" | Matches "Wireless Earbuds" | Displayed in table | PASS |
| TC-07 | Update Item Details | ID=2, new description | Description updated in MySQL | Updated successfully | PASS |
| TC-08 | Mark as Returned | ID=1, Confirm='Y' | Status updated to 'RETURNED' | Status set to RETURNED | PASS |
| TC-09 | Delete with Confirmation | ID=3, Confirm='N' | Deletion cancelled; record retained | Deletion aborted safely | PASS |

---

### 9. CONCLUSION & FUTURE SCOPE

#### Conclusion:
The **Campus Lost & Found Tracker** successfully provides a straightforward, user-friendly, and fault-tolerant console application for college campuses. By leveraging Core Java, JDBC, and MySQL, the project demonstrates core software engineering principles such as modular design, encapsulation, data integrity, and defensive programming. It fulfills all requirements stipulated by the Anudeep Foundation training curriculum.

#### Future Scope:
1. **Web / Mobile Interface:** In the future, a Spring Boot backend with a responsive React or Flutter frontend can be developed.
2. **Image Uploading:** Allow students to upload photos of lost or found items.
3. **Automated Matching Algorithm:** Implement fuzzy text matching to notify students when a newly reported "FOUND" item matches an existing "LOST" item.
4. **Email / SMS Notifications:** Automatically alert owners when an item matching their description is turned in.
