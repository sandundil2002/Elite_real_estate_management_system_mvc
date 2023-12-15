create database Elite_Real_Estate_Management_System;

use Elite_Real_Estate_Management_System;

create table Admin (
    Admin_id varchar(10) primary key,
    Name varchar(20) not null,
    Address varchar(30) not null,
    Mobile int(12) not null,
    Password varchar(10) not null,
    Email varchar(50) not null
);

create table Schedule (
    Shedule_id varchar(10) primary key,
    Admin_id varchar(10) not null,
    Date date not null,
    Time time not null,
    foreign key(Admin_id) references Admin(Admin_id) on delete cascade ON update cascade
);
create table Employee (
     Employee_id varchar(10) primary key,
     Admin_id varchar(10) not null,
     Name varchar (15) not null,
     Address varchar(30) not null,
     Mobile int(12) not null,
     Position varchar(10) not null,
     foreign key(Admin_id) references Admin(Admin_id) on delete cascade on update cascade
);

create table Salary (
     Salary_id varchar(10) primary key,
     Employee_id varchar(10) not null,
     Date date not null,
     Amount varchar(30),
     foreign key(Employee_id) references Employee(Employee_id) on delete cascade ON update cascade
);

create table Loan (
     Loan_id varchar(10) primary key,
     Admin_id varchar(10) not null,
     Date date not null,
     Rate varchar(5) not null,
     Details varchar(20) not null,
     foreign key(Admin_id)  references  Admin(Admin_id)  on delete cascade ON update cascade
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
     Price varchar(10) not null,
     Address varchar(30) not null,
     Type varchar(10) not null,
     Perches varchar(10) not null,
     Status varchar(20) not null,
     foreign key(Agent_id)  references Agent(Agent_id) on delete cascade on update cascade
);

create table customer (
     Customer_id varchar(10) primary key,
     Shedule_id varchar(10) not null,
     Name varchar(15) not null,
     Address varchar(30) not null,
     Mobile int(12) not null,
     Email varchar(20)not null,
     foreign key(Shedule_id) references Schedule(Shedule_id) on delete cascade on update cascade
);

create table Payment (
     Payment_id varchar(10) primary key,
     Customer_id varchar(10) not null,
     Property_id varchar(10) not null,
     Date date not null,
     Method varchar(10) not null,
     Price varchar(20) not null,
     foreign key (Customer_id) references Customer(Customer_id) on delete cascade on update cascade,
     foreign key (Property_id) references Property(Property_id) on delete cascade on update cascade
);

create table Payment_details (
     Property_id varchar(10) not null,
     Payment_id varchar(10) not null,
     Description varchar(30),
     foreign key(Property_id) references Property(Property_id) on delete cascade on update cascade,
     foreign key(Payment_id) references payment(Payment_id) on delete cascade on update cascade
);

create table Renting(
     Rent_id varchar(10) primary key,
     Property_id varchar(10) not null,
     Customer_id varchar(10) not null,
     Date date not null,
     Duration varchar(20) not null,
     foreign key(Property_id)  references Property(Property_id) on delete cascade ON update cascade,
     foreign key(Customer_id)  references Customer(Customer_id) on delete cascade ON update cascade
);

create table Renting_details (
     Rent_id varchar(10) not null,
     Property_id varchar(10) not null,
     Description varchar(20) not null,
     foreign key(Rent_id)  references Renting(Rent_id) on delete cascade ON update cascade,
     foreign key(Property_id)  references Property(Property_id) on delete cascade ON update cascade
);

create table Maintain (
     Maintain_id varchar(10) primary key,
     Rent_id varchar(10) not null,
     Date date not null,
     Description varchar(30) not null,
     foreign key(Rent_id) references renting(Rent_id) on delete cascade ON update cascade
);


