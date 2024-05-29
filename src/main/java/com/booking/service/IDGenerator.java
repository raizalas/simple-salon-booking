package com.booking.service;

public class IDGenerator {
    private static int customerCount = 0;
    private static int empCount = 0;
    private static int reservationCount = 0;

    private static final int PADDING = 3;

    private static final String CUSTOMER_PREFIX = "Cust-";
    private static final String EMPLOYEE_PREFIX = "Emp-";
    private static final String RESERVATION_PREFIX = "RSV-";

    //unused but might be useful
    public synchronized static String nextCustomerID() {
        customerCount++;
        return CUSTOMER_PREFIX + String.format("%0"+PADDING + "d", customerCount);
    }

    //unused but might be useful
    public synchronized static String nextEmployeeID() {
        empCount++;
        return EMPLOYEE_PREFIX + String.format("%0"+PADDING + "d", empCount);
    }

    public synchronized static String nextReservationID() {
        reservationCount++;
        return RESERVATION_PREFIX + String.format("%0"+PADDING + "d", reservationCount);
    }
}
