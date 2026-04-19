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

##  Test cases and outputs
<img width="1280" height="719" alt="photo_5416043688741246504_y" src="https://github.com/user-attachments/assets/1900cd90-c345-4cd5-8b01-8db52645b3e2" />
<img width="1280" height="719" alt="photo_5416043688741246503_y" src="https://github.com/user-attachments/assets/3f238375-a882-41c8-b2d9-7be4fe2ba241" />
<img width="1280" height="719" alt="photo_5416043688741246502_y" src="https://github.com/user-attachments/assets/04dca73f-dfb2-43d9-b29c-54d38b0e4067" />
<img width="1280" height="719" alt="photo_5416043688741246501_y" src="https://github.com/user-attachments/assets/4529e7cd-d4b6-4d21-8296-70a688507534" />
<img width="1280" height="719" alt="photo_5416043688741246500_y" src="https://github.com/user-attachments/assets/1c87d721-c7d0-4bb1-8579-3bf334c4e244" />
<img width="1280" height="719" alt="photo_5416043688741246499_y" src="https://github.com/user-attachments/assets/2fe8bbac-bef6-46ad-8c61-bd761f85023f" />
<img width="1280" height="719" alt="photo_5416043688741246498_y" src="https://github.com/user-attachments/assets/627687a5-ef1a-4e5a-88f5-019ff287d6ee" />
<img width="1280" height="719" alt="photo_5416043688741246497_y" src="https://github.com/user-attachments/assets/39ac50c0-5b97-4633-9708-23a4a43e49ef" />
<img width="1280" height="719" alt="photo_5416043688741246496_y" src="https://github.com/user-attachments/assets/5d90dbcc-7a5f-4fb5-95f3-b024c5270cee" />
<img width="1280" height="719" alt="photo_5416043688741246495_y" src="https://github.com/user-attachments/assets/a200d24a-5655-49b5-acca-c65a3cc840e1" />
<img width="1280" height="719" alt="photo_5416043688741246505_y" src="https://github.com/user-attachments/assets/a6e9cfca-940f-4426-b205-2866f212dc7a" />



---

## Project Requirements

This project must satisfy the following functional and quality requirements:

1. **CRUD operations for movies**  
   Users must be able to:
   - Create a new movie record (`addMovie`).  
   - Read the list of existing movies (`getMovies`).  
   - Update an existing movie's title, duration, and genre (`updateMovie`).  
   - Delete a movie from the database (`deleteMovie`).  

2. **CRUD operations for screenings**  
   Users must be able to:
   - Create a new screening for a movie (`addScreening`).  
   - View all screenings (`getScreenings`).  
   - Update the movie, time, or hall of an existing screening (`updateScreening`).  
   - Delete a screening (`deleteScreening`).  

3. **Command‑Line Interface (CLI) with clear menus**  
   The application must provide a text‑based menu system where:
   - The user sees a numbered list of options.  
   - Each option is clearly labeled (e.g., “1. View Movies”, “4. Add Movie”).  
   - After each action, the user is returned to the main menu or explicitly asked to exit.

4. **Role‑based access control in the CLI**  
   - The system must distinguish between **admin** and **normal user** roles.  
   - Only admins can perform CRUD operations for movies and screenings.  
   - Normal users can only view data (view movies, screenings, and schedule).  

5. **Input validation**  
   - The system must validate user input and prevent invalid entries, such as:
     - Empty movie title or genre.  
     - Invalid duration (outside 30–300 minutes).  
     - Incorrect date‑time format for screenings (must match `yyyy-MM-dd HH:mm`).  
     - Invalid hall name (not `"1"`, `"2"`, `"3"`, or `"IMAX"`).  
   - Invalid input must be rejected with a clear error message, and the user must re‑enter the data.

6. **Data persistence in SQLite database**  
   - All movie and screening records must be stored in an SQLite database file.  
   - Data must persist between sessions: when the program is restarted, previously added, updated, and deleted records must be preserved.  

7. **CSV import and export**  
   - The application must allow exporting movies and screenings to CSV files (`exportMoviesToCSV` and `exportScreeningsToCSV`).  
   - The application must allow importing movies and screenings from CSV files (`importMoviesFromCSV` and `importScreeningsFromCSV`).  
   - During import, duplicate or conflicting records (e.g., time conflicts) must be skipped with an appropriate message.

8. **Modular design**  
   - The code must be split into logical modules/classes such as:
     - `Main` – main program flow and menu.  
     - `person` / `admin` – user roles.  
     - `movie` – movie data model.  
     - `screening` – screening data model.  
     - `movieservice` – service for movie operations.  
     - `ScreeningService` – service for screening operations.  
     - `database` – database connection utility.  
   - Each module must have a single clear responsibility.

9. **Encapsulation (OOP)**  
   - All data fields in classes like `movie` and `screening` must be `private`.  
   - Access to these fields must be provided via public getters and setters:  
     - Example: `getId()`, `setTitle(String title)`, etc.  

10. **Inheritance (OOP)**  
    - The project must define at least one parent class and one child class.  
    - Example: `person` as the parent class and `admin` as the child class that inherits from `person` and overrides methods such as `showRole()` and `isAdmin()`.  

11. **Polymorphism (OOP)**  
    - The project must demonstrate polymorphism, for example:
      - The same method name `showRole()` behaves differently for `person` and `admin` objects.  
      - A variable of type `person` can refer to both a `person` and an `admin` object, and the correct `showRole()` is called at runtime.  

12. **Error handling**  
    - The program must handle unexpected situations gracefully, such as:
      - Invalid user input (e.g., entering text instead of a number).  
      - Database errors (e.g., connection failure, locked database).  
      - Time‑conflict situations when adding or importing screenings.  
    - These cases must produce user‑friendly error messages without crashing the program.

---

## Presentation

You can find the project presentation here:  
[View presentation](https://drive.google.com/file/d/1_FPqU6OnNNgvT-AVjZtuH8mOQzCzO-XF/view?usp=sharing)  

---
## Documentation
[Open documentation](documentation.md)

---

##  Notes

This project was developed as part of an **OOP Final Assignment** to demonstrate understanding of Java, object-oriented design, and data management systems.



