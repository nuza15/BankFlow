# BankFlow

This is an intentionally incomplete starter-stage scaffold based on the original BankFlow package and file structure.

## Included
- Original package layout and Java filenames.
- A basic `Database` data container.
- `TaskRepository` storage interface.
- `FileTaskRepository` using Java object serialization.
- `AppContext` and a runnable `App.main()` that loads and saves the database.

## Not implemented yet
The model, service, and Swing UI classes are placeholders with TODO comments. The database's default status initialization is also intentionally incomplete because the full `Status` model has not been implemented.

## Compile and run
From the project folder:

The application stores its data in `~/.bankflow/bankflow.dat`.


## Progress
| Week | Content |
|------|---------|
| 1 | Starter scaffold: package layout, `Database`, `TaskRepository`, file storage, `App` |
| 2 | Domain model (users, tasks, recurrence, subtasks, notes, history), PBKDF2 password hashing, default statuses/types and demo data, `AuthService` (login/register) |

## Week 2 demo
`App` now loads the data file, logs in as `admin` / `admin123` (wrong password is rejected) and saves again.
Demo accounts: admin/admin123, nino/nino123, luka/luka123, giorgi/giorgi123.
Delete `~/.bankflow/bankflow.dat` if you ran week 1 earlier, so the demo data is created.
