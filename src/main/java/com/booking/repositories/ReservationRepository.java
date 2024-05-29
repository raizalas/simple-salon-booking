package com.booking.repositories;

import com.booking.models.Customer;
import com.booking.models.Employee;
import com.booking.models.Reservation;
import com.booking.models.Service;
import com.booking.service.PrintService;
import com.booking.service.ValidationService;
import lombok.Getter;

import java.util.*;

public class ReservationRepository {
    private static final List<Employee> employeeList = PersonRepository.getAllEmployee();
    private static final List<Customer> customerList = PersonRepository.getAllCustomer();
    private static final List<Service> serviceList = ServiceRepository.getAllService();
    @Getter
    private static final List<Reservation> reservationList = new ArrayList<>();

    public static void addReservationList() {
        Set<Service> services = new HashSet<>();
        PrintService.showAllCustomer();
        Customer cust = ValidationService.pencarianList(
                customerList,
                (customer, id) -> customer.getId().equalsIgnoreCase(id),
                "Masukkan ID customer ",
                "Customer tidak ditemukan!"
        );
        if (cust == null) {
            return;
        }
        PrintService.showAllEmployee();
        Employee emp = ValidationService.pencarianList(
                employeeList,
                (employee, id) -> employee.getId().equalsIgnoreCase(id),
                "Masukkan ID Employee ",
                "Employee tidak ditemukan!"
        );
        if (emp ==null) {
            return;
        }
        PrintService.showAllServices();
        boolean serviceSelect = false;
        do {
            Service service = ValidationService.pencarianList(
                    serviceList,
                    (item, id) -> item.getServiceId().equalsIgnoreCase(id),
                    "Masukkan ID Service",
                    "Service tidak ditemukan"
            );
            if (service == null) {
                return;
            }
            if (!services.add(service)) {
                System.out.println("Servis sudah dipilih!");
            }

            boolean addMore = ValidationService.yesOrNoPrompt("Pilih service lain");
            if (!addMore) {
                serviceSelect = true;
            }
            if(services.size() == 5){
                System.out.println("Servis maksimal 5");
                serviceSelect = true;
            }
        } while(!serviceSelect);

        reservationList.add(new Reservation(cust, emp, services));
    }

    public static void changeWorkstage() {
        PrintService.showRecentReservation();
        Reservation reservation = ValidationService.pencarianList(
                reservationList,
                (item, id) -> item.getReservationId().equalsIgnoreCase(id),
                "Masukkan id reservasi",
                "reservasi tidak ditemukan"
        );

        try {
            System.out.println("Selesaikan reservasi: ");
            Scanner scanner = new java.util.Scanner(System.in);
            assert reservation != null;
            String input = scanner.nextLine();
            reservation.setWorkstage(input);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            changeWorkstage();
        }

        if (reservation.getWorkstage().equalsIgnoreCase("finish")) {
            double totalWallet = reservation.getCustomer().getWallet() - reservation.getReservationPrice();
            reservation.getCustomer().setWallet(totalWallet);
        }

        System.out.println("Reservasi dengan id " + reservation.getReservationId() + " telah di "  +
                reservation.getWorkstage());
    }
}
