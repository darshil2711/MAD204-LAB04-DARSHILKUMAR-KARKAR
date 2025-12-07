# MAD204-01 - Lab 4: Notes + Reminder App

**Course:** MAD204-01 Java Development for MA
**Author:** Darshilkumar Karkar (A00203357)
**Date:** 07/12/2025

---

## Overview
In this lab, the project combines local database storage (SQLite/Room) with background tasks and notifications. 

This app stores notes in a database, allows retrieval and updates, and performs a background task that triggers a notification. This lab strengthens skills in persistent data storage, asynchronous execution, and user communication.

## Scenario
This project creates a **Notes + Reminder App** where:
* Users can add, view, update, and delete notes using a local database.
* Notes are stored using the Room Persistence Library.
* A background Service runs and, after a short delay, sends a notification reminding the user about their notes.
* A BroadcastReceiver listens for device events (e.g., connectivity change or boot complete) and logs them.

## Learning Targets
* Practice creating a local database with SQLite/Room.
* Implement CRUD operations via DAO and RoomDatabase.
* Understand and use background Services.
* Register and handle events with BroadcastReceivers.
* Build and display user Notifications.
* Reinforce GitHub collaboration with commits and pull requests.

## Implementation Details

### Part A: Project Setup
* Project Name: `Lab4NotesReminderApp`
* Includes Room dependencies in `build.gradle`.

### Part B: Database Setup (Room)
* **Entity Class:** `Note` with fields `id`, `title`, `content`.
* **DAO Interface:** Includes methods for `insert`, `getAllNotes`, `update`, and `delete`.
* **RoomDatabase:** `NotesDatabase` class exposing the DAO.

### Part C & D: UI Design & CRUD
* **Main Screen:**
    * Displays all notes in a `RecyclerView`.
    * Includes a button to add new notes.
* **Item Layout:**
    * Shows title and content.
    * Supports Delete functionality via long press (with Snackbar Undo).
    * Supports Update functionality by clicking an item.

### Part E: Background Task + Service
* **Service:** `ReminderService`
* **Functionality:** When started, the service waits 5 seconds and then posts a notification using `NotificationCompat`.
    * **Title:** "Reminder"
    * **Text:** "Check your notes!"

### Part F: BroadcastReceiver
* **Receiver:** Listens for `CONNECTIVITY_CHANGE` (or similar system events like Airplane Mode).
* **Action:** Logs or displays a Toast when the state changes.
* Registered in `AndroidManifest.xml`.

---
*Verified against Lab 4 Requirements.*
