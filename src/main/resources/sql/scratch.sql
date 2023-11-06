create database Elite_Real_Estate_Management_System;

use Elite_Real_Estate_Management_System;

create table Admin (
    Admin_id varchar(10) primary key,
    Name varchar(15) not null,
    Address varchar(30) not null,
    Mobile int(12) not null,
    Email varchar(20)
);

create table Employee (
     Employee_id varchar(10) primary key,
     Admin_id varchar(10) not null,
     Name varchar (15) not null,
     Address varchar(30) not null,
     Mobile int(12) not null,
     Position varchar(10) not null,
     Email varchar(20),
     foreign key(Admin_id) references Admin(Admin_id)
);

create table Salary (
     Salary_id varchar(10) primary key,
     Employee_id varchar(10) not null,
     Date date not null,
     Status varchar(10) not null,
     Description varchar(30),
     foreign key(Employee_id) references  Employee(Employee_id)
);

create table Loan (
     Loan_id varchar(10) primary key,
     Admin_id varchar(10) not null,
     Date date not null,
     Rate varchar(5) not null,
     Details varchar(20) not null,
     foreign key(Admin_id)  references  Admin(Admin_id)
);

create table Agent (
     Agent_id varchar(10) primary key,
     Name varchar(15) not null,
     Address varchar(30) not null,
     Mobile int(12) not null,
     Email varchar(20)
);

create table Property (
     Property_id varchar(10) primary key,
     Agent_id varchar(10) not null,
     Price int(10) not null,
     Address varchar(30) not null,
     Status varchar(10) not null,
     foreign key(Agent_id)  references Agent(Agent_id)
);

create table Property_details (
     Property_id varchar(10) not null,
     Admin_id varchar(10) not null,
     Description varchar(30),
     foreign key(Property_id) references Property(Property_id),
     foreign key(Admin_id) references Admin(Admin_id)
);

create table Renting(
     Rent_id varchar(10) primary key,
     Agent_id varchar(10) not null,
     Date date not null,
     Description varchar(30) not null,
     foreign key(Agent_id)  references Agent(Agent_id)
);

create table Renting_details (
     Rent_id varchar(10) not null,
     Agent_id varchar(10) not null,
     Durating varchar(10) not null,
     foreign key(Agent_id)  references Agent(Agent_id),
     foreign key(Rent_id)  references Renting(Rent_id)
);

create table Payment (
     Payment_id varchar(10) primary key,
     Date date not null,
     Status varchar(10) not null,
     Description varchar(30) not null
);

create table Maintain (
     Maintain_id varchar(10) primary key,
     Payment_id varchar(10) not null,
     Date date not null,
     Description varchar(30) not null,
     foreign key(Payment_id) references Payment(Payment_id)
);

create table Shedule (
     Shedule_id varchar(10) primary key,
     Admin_id varchar(10) not null,
     Date date not null,
     Time time not null,
     foreign key(Admin_id) references Admin(Admin_id)
);

create table Seller (
     Seller_id varchar(10) primary key,
     Name varchar(15) not null,
     Address varchar(30) not null,
     Mobile int(12) not null
);

create table Buyer (
     Buyer_id varchar(10) primary key,
     Name varchar(15) not null,
     Address varchar(30) not null,
     Mobile int(12) not null
);

create table Feedback (
    Feedback_id varchar(10) primary key,
    Date date not null,
    Rate varchar(10) not null,
    Description varchar(30) not null
);