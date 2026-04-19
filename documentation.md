# Cinema Management System

A simple cinema scheduling and movie management system built in Java with SQLite. The application allows administrators to manage movies and screenings (CRUD operations) and lets regular users view the movie list, screenings, and hall schedules.

## Problem and motivation

Cinemas often need a simple way to manage movies and daily screenings without relying on complex external tools. This project provides a **command‑line interface** where:

- **Admins** can add, update, delete movies and screenings, and export/import data via CSV files.  
- **Users** can only view movies, screenings, and the daily schedule by hall.

The system stores all data in a **SQLite database** and uses **plain Java** without heavy frameworks, making it easy to understand and extend.

## Overview of the structure

The project is organized into several Java classes and one main entry point:

- `Main.java` – Entry point with login logic and main menu.  
- `person` and `admin` – User/role hierarchy (base class `person`, subclass `admin`).  
- `movie` – Plain Java object representing a movie.  
- `screening` – Plain Java object representing a screening.  
- `movieservice` – Service class for managing movies (CRUD, CSV export/import).  
- `ScreeningService` – Service class for managing screenings (CRUD, time‑conflict checks, CSV export/import).  
- `database` – Utility class that manages the SQLite connection.

### Data structures

- `movie`:
  - `id: int`
  - `title: String`
  - `duration: int` (minutes)
  - `genre: String`
- `screening`:
  - `id: int`
  - `movieId: int`
  - `screeningTime: String` (formatted as `"yyyy-MM-dd HH:mm"`)
  - `hall: String` (one of `"1"`, `"2"`, `"3"`, `"IMAX"`)

These objects are stored in two SQLite tables:

- `movies (id, title, duration, genre)`.  
- `screenings (id, movie_id, screening_time, hall)`.

### Main algorithms and key logic

#### 1. Role‑based access control

- At startup, the user enters a username.
- If the username is `"admin"` (case‑insensitive) and the password is `"movie"`, the program creates an `admin` object.
- Otherwise, it creates a `person` object.
- The `isAdmin()` method is used in the main menu loop to decide which options are shown:
  - Admins see all CRUD options for movies and screenings.
  - Normal users see only viewing options.

#### 2. Time‑conflict checking for screenings

When adding or importing a screening, `ScreeningService.hasTimeConflict(...)` is called:

1. A new screening time and duration are parsed into `LocalDateTime` values (`newStart` and `newEnd`).  
2. The method queries all existing screenings in the same hall and converts each existing screening time and movie duration into `existingStart` and `existingEnd`.  
3. If any existing screening overlaps with the new one (i.e., `newStart < existingEnd` and `existingStart < newEnd`), the method returns `true` (conflict).  
4. If no conflicts are found, the screening is inserted into the database.

This prevents two movies from playing at the same time in the same hall.

#### 3. Validation logic

Several helper methods ensure data correctness:

- `isValidTime(String time)` – checks that the time follows the format `"yyyy-MM-dd HH:mm"` using a regular expression.  
- `isValidDuration(int duration)` – requires duration between 30 and 300 minutes.  
- `isValidHall(String hall)` – allows only `"1"`, `"2"`, `"3"`, `"IMAX"` (case‑insensitive).  
- `isValidTitle`, `isValidGenre` – ensure movie title and genre are non‑empty and contain only letters, numbers, and spaces.

These checks are performed in loops in `addMovie()` and `addScreening()`, so the user cannot proceed until valid input is given.

#### 4. CSV export and import

- `movieservice.exportMoviesToCSV()` and `movieservice.importMoviesFromCSV()` read movies from the `movies` table and write them to `movies.csv`, or read from `movies.csv` and insert them into the database.  
- `ScreeningService.exportScreeningsToCSV()` and `ScreeningService.importScreeningsFromCSV()` do the same for screenings, joining with the `movies` table to include the movie title in the CSV.  
- During import, the system:
  - Skips rows with invalid `movie_id`s (using `movieExists(...)`).  
  - Skips screenings that would cause time conflicts (using `hasTimeConflict(...)`).

### Functions / modules

- `Main`:
  - Handles login and main menu loop.  
  - Routes choices to `movieservice` and `ScreeningService` methods based on user role.

- `movieservice`:
  - `addMovie()`, `deleteMovie()`, `updateMovie()` – movie CRUD.  
  - `getMovies()` – displays all movies.  
  - `exportMoviesToCSV()`, `importMoviesFromCSV()` – CSV import/export.

- `ScreeningService`:
  - `addScreening()`, `deleteScreening()`, `updateScreening()` – screening CRUD.  
  - `getScreenings()`, `getScheduleByHall()` – view screenings.  
  - `hasTimeConflict(...)`, `getMovieDuration(...)`, `getConflictingScreeningId(...)` – conflict‑related logic.  
  - `exportScreeningsToCSV()`, `importScreeningsFromCSV()` – CSV operations.

- `database`:
  - `connect()` – creates and returns a `Connection` to the SQLite database (e.g., `jdbc:sqlite:C:\\Users\\БТ\\Documents\\movies.db`).

### Challenges faced

1. **Multiple database connections and SQLITE_BUSY**
   - At first, `movieservice` and `ScreeningService` each created their own `Connection` object, which led to `SQLITE_BUSY` errors when inserting screenings.  
   - **Solution**: Centralized the connection via a simple `Database` utility class to ensure only one (or properly managed) connection is used at a time.

2. **Date‑time validation and parsing**
   - The `isValidTime(...)` method used double‑escaped backslashes (`\\\\d`) in the regex, which caused valid inputs like `2026-04-23 17:00` to be rejected as having an invalid format.  
   - **Solution**: Changed the regex to `\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}` (single `\d`) so that the format matches the `LocalDateTime` parser.

3. **Concurrency‑like issues**
   - Because SQLite uses file‑level locking, keeping statements open or not closing resources properly occasionally caused database‑is‑locked errors.  
   - **Solution**: Adopted `try`‑with‑resources pattern for `PreparedStatement` and `Statement` where possible and ensured that statements are closed after use.


## Technologies used

- Language: Java.
- Database: SQLite (via JDBC).
- Data format: CSV for import/export.
- Environment: Command‑line interface (no GUI).

