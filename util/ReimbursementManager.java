package com.dsu.anu.util;

import java.sql.ResultSet;
import java.util.Scanner;

import com.dsu.anu.model.Reimbursement;
import com.dsu.anu.model.User;
import com.dsu.anu.service.ReimbursementService;
import com.dsu.anu.service.UserService;

public class ReimbursementManager {

    Scanner sc;
    ReimbursementService rservice;
    UserService us;

    public ReimbursementManager() {
        sc = new Scanner(System.in);
        rservice = new ReimbursementService();
        us = new UserService();
    }

    // 🔹 MAIN LOGIN LOOP
    public void showMenu() {
        while (true) {

            System.out.println("\n===== LOGIN PORTAL =====");
            System.out.println("Enter User ID:");
            String uid = sc.next();

            System.out.println("Enter Password:");
            String pass = sc.next();

            User u = us.loginserviice(uid, pass);

            if (u != null) {
                System.out.println("Login Successful");
                System.out.println("Welcome " + u.getRole() + " " + u.getUserName());

                switch (u.getRole().trim().toUpperCase()) {
                    case "ADMIN":
                        adminMenu();
                        break;

                    case "EMPLOYEE":
                        employeeMenu(u);
                        break;

                    case "MANAGER":
                        managerMenu(u);
                        break;

                    case "FINANCE":
                        financeMenu();
                        break;

                    default:
                        System.out.println("Invalid Role");
                }

            } else {
                System.out.println("Invalid Credentials");
            }

            System.out.println("\nDo you want to login again? (yes/no)");
            String choice = sc.next();
            if (!choice.equalsIgnoreCase("yes")) {
                System.out.println("Thank You!");
                break;
            }
        }
    }

    // 🔹 ADMIN MENU
    private void adminMenu() {
        int choice;
        boolean running = true;

        while (running) {
            System.out.println("\n--- ADMIN MENU ---");
            System.out.println("1. Add New User");
            System.out.println("2. Exit");

            choice = sc.nextInt();
            sc.nextLine(); // clear buffer

            switch (choice) {
                case 1:
                    System.out.println("Enter User ID:");
                    String uid = sc.nextLine();

                    System.out.println("Enter User Name:");
                    String uname = sc.nextLine();

                    System.out.println("Enter Password:");
                    String pass = sc.nextLine();

                    System.out.println("Enter Department:");
                    String dept = sc.nextLine();

                    System.out.println("Enter Role (EMPLOYEE / MANAGER / FINANCE):");
                    String role = sc.nextLine().toUpperCase();

                    String managerId = null;

                    if (role.equals("EMPLOYEE")) {
                        System.out.println("Enter Manager ID:");
                        managerId = sc.nextLine();
                    }

                    User newUser = new User(uid, pass, uname, dept, role, managerId);

                    if (us.addUser(newUser)) {
                        System.out.println("User Added Successfully");
                    } else {
                        System.out.println("Failed to Add User");
                    }
                    break;

                case 2:
                    System.out.println("Exiting Admin Menu");
                    running = false;
                    break;

                default:
                    System.out.println("Invalid Choice");
            }
        }
    }

    // 🔹 EMPLOYEE MENU
    private void employeeMenu(User u) {
        int choice;
        boolean running = true;

        while (running) {
            System.out.println("\n--- EMPLOYEE MENU ---");
            System.out.println("1. Create Request");
            System.out.println("2. View My Requests");
            System.out.println("3. Update Request");
            System.out.println("4. Delete Request");
            System.out.println("5. Exit");

            choice = sc.nextInt();
            sc.nextLine(); // clear buffer

            switch (choice) {

                case 1:
                    System.out.println("Enter bill type: Travel: Medical : Relocation");
                    String type = sc.nextLine();

                    System.out.println("Enter amount:");
                    double amt = sc.nextDouble();
                    sc.nextLine();

                    Reimbursement r = new Reimbursement(
                            u.getUserId(), type, amt, "OPEN"
                    );

                    if (rservice.createRequestsvc(r)) {
                        System.out.println("Request Created");
                    }
                    break;

                case 2:
                    try (ResultSet rs = rservice.viewRequestSvc(u.getUserId())) {
                    	boolean found=false;
                        while (rs.next()) {
                        	found=true;
                            System.out.println(
                                    rs.getInt("reqId") + " | " +
                                    rs.getString("billType") + " | " +
                                    rs.getDouble("amount") + " | " +
                                    rs.getString("status")
                            );
                        }
                        if(!found) {
                        	System.out.println("No requests found");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case 3:
                    System.out.println("Enter Request ID to update:");
                    int updateId = sc.nextInt();
                    sc.nextLine();

                    System.out.println("Enter new bill type: Travel: Medical: Relocation");
                    String newType = sc.nextLine();

                    System.out.println("Enter new amount:");
                    double newAmt = sc.nextDouble();
                    sc.nextLine();

                    if (rservice.updateRequestSvc(updateId, u.getUserId(), newType, newAmt)) {
                        System.out.println("Request Updated Successfully");
                    } else {
                        System.out.println("Update Failed (Only OPEN requests allowed)");
                    }
                    break;

                case 4:
                    System.out.println("Enter Request ID to delete:");
                    int deleteId = sc.nextInt();

                    if (rservice.deleteRequestSvc(deleteId, u.getUserId())) {
                        System.out.println("Request Deleted Successfully");
                    } else {
                        System.out.println("Delete Failed (Only OPEN requests allowed)");
                    }
                    break;

                case 5:
                    System.out.println("Exiting Employee Menu");
                    running = false;
                    break;
            }
            }
        }

    // 🔹 MANAGER MENU
    public void managerMenu(User user) {
        int choice;
        boolean running = true;

        while (running) {
            System.out.println("\n--- MANAGER MENU ---");
            System.out.println("1. View Employee Requests");
            System.out.println("2. Approve/Reject Request");
            System.out.println("3. Exit");

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    try (ResultSet rs = rservice.getManagerRequests(user.getUserId())) {
                        System.out.println("ID | User | Type | Amount | Status");

                        while (rs.next()) {
                            System.out.println(
                                    rs.getInt("reqId") + " | " +
                                    rs.getString("userId") + " | " +
                                    rs.getString("billType") + " | " +
                                    rs.getDouble("amount") + " | " +
                                    rs.getString("status")
                            );
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case 2:
                    System.out.println("Enter Request ID:");
                    int id = sc.nextInt();

                    System.out.println("1. Approve  2. Reject");
                    int ch = sc.nextInt();
                    sc.nextLine();

                    System.out.println("Enter Comment:");
                    String comment = sc.nextLine();

                    String status = (ch == 1) ? "APPROVED" : "REJECTED";

                    if (rservice.updateManagerStatusSvc(id, status, comment)) {
                        System.out.println("Updated Successfully");
                    } else {
                        System.out.println("Update Failed");
                    }
                    break;

                case 3:
                    System.out.println("Exiting Manager Menu");
                    running = false;
                    break;

                default:
                    System.out.println("Invalid Choice");
            }
        }
    }

    // 🔹 FINANCE MENU
    private void financeMenu() {
        int choice;
        boolean running = true;

        while (running) {
            System.out.println("\n--- FINANCE MENU ---");
            System.out.println("1. View Approved Requests");
            System.out.println("2. Credit & Close Request");
            System.out.println("3. Exit");

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    try (ResultSet rs = rservice.getApprovedRequestsSvc()) {

                        boolean found = false;

                        while (rs.next()) {
                            found = true;
                            System.out.println(
                                    rs.getInt("reqId") + " | " +
                                    rs.getString("userId") + " | " +
                                    rs.getString("billType") + " | " +
                                    rs.getDouble("amount") + " | " +
                                    rs.getString("status")
                            );
                        }

                        if (!found) {
                            System.out.println("No pending approved requests");
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case 2:
                    System.out.println("Enter Request ID:");
                    int id = sc.nextInt();

                    System.out.println("Enter Amount to Credit:");
                    double amount = sc.nextDouble();
                    sc.nextLine();

                    System.out.println("Enter Comment:");
                    String comment = sc.nextLine();

                    if (rservice.creditRequest(id, amount, comment)) {
                        System.out.println("Amount Credited & Request Closed");
                    } else {
                        System.out.println("Operation Failed");
                    }
                    break;

                case 3:
                    System.out.println("Exiting Finance Menu");
                    running = false;
                    break;

                default:
                    System.out.println("Invalid Choice");
            }
        }
    }
}