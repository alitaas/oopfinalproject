# Movie Screening Schedule Management System
---
## Student information

- **Student:** *Altynai Asanbekova*
- **Group:** *COMSE-25*
---

## Project Overview

---
The **Movie Screening Schedule Management System** is a Java-based console application designed to manage movies and their screening schedules in a cinema environment.

It provides a structured system for handling movies, screenings, and schedules with full **CRUD functionality**, role-based access control, and persistent storage using **SQLite database** and **CSV file import/export**.

---

## Objectives

The main goals of this project are:

-  Build a functional movie screening management system using Java
-  Apply Object-Oriented Programming principles:
  - Encapsulation
  - Inheritance
  - Polymorphism
-  Implement full CRUD operations for movies and screenings
-  Ensure data validation and prevent invalid user input
-  Provide data persistence using SQLite and file storage (CSV)
-  Support import/export functionality for data backup
-  Implement role-based access control (Admin / User)
-  Create a modular and maintainable code structure
-  Handle errors gracefully and ensure system stability

---

##  Features

###  Movies Module
- Add new movies
- View all movies
- Update movie details
- Delete movies
- Export / Import movies (CSV)

---

###  Screenings Module
- Add screening sessions
- View scheduled screenings
- Update screenings
- Delete screenings
- Prevent time & hall conflicts
- Export / Import screenings (CSV)

---

###  User Roles
- **Admin**
  - Full system access (CRUD + import/export)
- **User**
  - View-only access

---

##  Technologies Used

-  Java (Core + OOP)
-  SQLite (JDBC)
-  File Handling (CSV)
-  Command Line Interface (CLI)

---

##  Data Persistence

The system supports two types of persistence:

-  **Database (SQLite)** – main storage
-  **CSV Files** – import/export for backup and transfer

---

##  Key Highlights

-  Screening conflict detection (time + hall validation)
-  Smart update system with validation checks
-  Clean schedule display grouped by hall
-  Role-based access control
-  Modular architecture (services + models)

---

##  Example Output
<img width="720" height="1280" alt="photo_5416043688741246314_y" src="https://github.com/user-attachments/assets/9c01007c-a92f-488b-8eee-a2f45cdc49a4" />
<img width="720" height="1280" alt="photo_5416043688741246315_y" src="https://github.com/user-attachments/assets/eaec1c14-628d-4a2f-963f-e22906fdd48f" />
<img width="720" height="1280" alt="photo_5416043688741246316_y" src="https://github.com/user-attachments/assets/6e6d96b4-677b-4348-ab46-a3542c083314" />
<img width="720" height="1000" alt="photo_5416043688741246317_y" src="https://github.com/user-attachments/assets/b9ac2b3f-9d0b-4037-a0dd-e9b6877524b2" />



---

##  Project Status

 Completed  
 Fully functional CLI application  
 Meets OOP requirements  
 Supports file-based persistence  

---

##  Notes

This project was developed as part of an **OOP Final Assignment** to demonstrate understanding of Java, object-oriented design, and data management systems.

