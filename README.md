# Home-Appliance-Repair-Service-Scheduling-System
A comprehensive Java-based program designed to manage, assign, and track home appliance repair service appointments. The system bridges the gap between customers needing appliance maintenance, skilled technicians managing their work queues, and administrators overseeing the business logic.

This project is built using pure Java, emphasizing modular design and strict Object-Oriented Programming (OOP) architectures.

## System Features & Roles

The application divides workflows into three distinct roles managed through an integrated login system:

### 1. Customer Portal
* **Account Registration & Login:** Create secure profiles with unique usernames and contact details.
* **Book Repair Appointments:** Submit new service requests specifying the appliance type (e.g., Refrigerator, Washing Machine, Air Conditioner), problem details, and preferred appointment dates.
* **Track Request Status:** Monitor real-time status updates (*Pending, Assigned, In Progress, Completed*).
* **Feedback & Service History:** View past completed repairs and leave performance summaries.

### 2. Technician Dashboard
* **Work Order Schedule:** Access a clear list of daily or upcoming repair appointments assigned explicitly to them.
* **Status Updates:** Modify job progress from *Assigned* to *In Progress* and mark as *Completed* once the physical repair is finished.

### 3. Admin Control Panel
* **Technician Allocation:** Review unassigned customer requests and manually or dynamically allocate them to available technicians.
* **User Management:** Register new technician profiles, edit customer details, and update schedules.

---

## Core Java OOP Principles Applied

This software uses rigorous Object-Oriented Programming principles to ensure clean, decoupled, and maintainable source code:

* **Encapsulation:** All critical entity fields (such as user credentials, schedules, and repair fees) are restricted using `private` data fields, safely accessed and modified via explicit public `getter` and `setter` methods.
* **Inheritance:** A foundational base class `User` holds shared attributes like `username` and `password`. This is cleanly inherited and extended by specialized classes: `Customer`, `Technician`, and `Admin`.

---

## Getting Started

### Prerequisites
* **Java Development Kit (JDK):** Version 8 or higher.
* **IDE:** Eclipse IDE for Java Developers (compatible with IntelliJ IDEA or NetBeans).

### Installation & Setup
1. **Clone the repository:**
   ```bash
   git clone [https://github.com/Peiyu2004/Home-Appliance-Repair-Service-Scheduling-System.git](https://github.com/Peiyu2004/Home-Appliance-Repair-Service-Scheduling-System.git)
