# QuickOrder: Online Order Delivery System

## Overview

**QuickOrder** is a Java EE-based web application designed for convenience stores to manage online orders, staff, and customer interactions. Built with **Servlets, JSP, and EJBs**, it follows a **multi-tier MVC architecture** and supports three user roles:

- **Managing Staff**: Full system control  
- **Delivery Staff**: Order status updates  
- **Customers**: Order placement and feedback  

## Key Features

- ✅ **Role-Based Access Control**  
- ✅ **Order Management with Intelligent Driver Assignment**  
- ✅ **Product & Staff CRUD Operations**  
- ✅ **Validated Feedback System**  
- ✅ **9+ Analytical Reports**  
- ✅ **Responsive UI with CSS Styling**

## Technology Stack

| Category        | Technologies Used         |
|----------------|---------------------------|
| **Frontend**   | JSP, CSS, JavaScript      |
| **Backend**    | Java Servlets, EJB        |
| **Database**   | Java DB (Derby)           |
| **Architecture** | MVC Pattern             |
| **Security**   | Role-Based Access         |

## System Architecture

```mermaid
graph TD
    A[Client] --> B[JSP - View]
    B --> C[Servlet - Controller]
    C --> D[EJB - Model]
    D --> E[Database]


## Getting Started

### Prerequisites
- Java JDK 8+
- Apache Tomcat 9.x
- Eclipse IDE with Java EE
- Java DB (Derby)

## Installation

1- Clone the repository:
git clone https://github.com/your-repo/QuickOrder.git

2- Import into Eclipse:
File → Import → Existing Projects into Workspace

3- Configure database in persistence.xml:
<jta-data-source>jdbc/derby://localhost:1527/QuickOrderDB</jta-data-source>

4- Deploy to Tomcat server


## Database Schema
<img width="425" alt="image" src="https://github.com/user-attachments/assets/0efbf4b4-3650-448e-b7e2-390f14925098" />




