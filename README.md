# Hostel Management System 🏠

A Java-based Hostel Management System that helps manage hostel operations such as student registration, login, room allotment, payments, complaints, laundry requests, and food management.

## 🚀 Features

### Student Module
- Student signup and login
- Password reset using OTP verification
- View student details
- Make hostel payments
- Add and view complaints
- Submit laundry requests
- Apply for leave
- View food menu

### Rector Module
- View all student details
- Room allotment management
- Manage complaints
- View student payments
- Manage laundry requests
- Approve leave requests
- Add hostel food menu

## 🛠️ Tech Stack

- Java
- JDBC
- MySQL
- Object-Oriented Programming (OOP)
- Data Structures

## 🗄️ Database

- MySQL database is used for storing student information, payments, complaints, laundry requests, and leave records.
- Database connection is handled using JDBC.

## 📂 Project Structure

```
HostelManagementSystem/
│
├── src/
│   ├── Main.java
│   ├── Student.java
│   ├── Login.java
│   ├── Signup.java
│   ├── Rector.java
│   ├── Payment.java
│   ├── ComplaintList.java
│   ├── Food.java
│   └── connection2.java
│
├── dbfile.sql
│
└── README.md
```

## ⚙️ How to Run

### 1. Setup Database

- Install XAMPP/MySQL
- Create a database named:

```
hostel
```

- Import `dbfile.sql`

### 2. Configure Database Connection

Update database details in:

```
connection2.java
```

### 3. Run Application

Open the project in IntelliJ IDEA or any Java IDE.

Run:

```
Main.java
```

## 💻 Application Menu

```
1. Login
2. SignUp
3. Forgot Password
4. Exit
```

## 🔮 Future Improvements

- GUI-based application
- Online payment gateway
- Web-based hostel management system
- More advanced security features

## 👨‍💻 Author

**Sheet Savaliya**

GitHub:
https://github.com/sheet2597
