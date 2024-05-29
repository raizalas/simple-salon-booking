package com.booking.service;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import com.booking.models.*;
import com.booking.repositories.PersonRepository;
import com.booking.repositories.ReservationRepository;
import com.booking.repositories.ServiceRepository;

public class PrintService {
    static NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("in", "ID"));
    public static void printMenu(String title, String[] menuArr){
        int num = 1;
        System.out.println(title);
        for (int i = 0; i < menuArr.length; i++) {
            if (i == (menuArr.length - 1)) {   
                num = 0;
            }
            System.out.println(num + ". " + menuArr[i]);   
            num++;
        }
    }

    public static String printServices(Set<Service> serviceList){
        String result = "";
        // Bisa disesuaikan kembali
        for (Service service : serviceList) {
            result += service.getServiceName() + ", ";
        }
        return result;
    }

    // Function yang dibuat hanya sebgai contoh bisa disesuaikan kembali
    public static void showRecentReservation(){
        List<Reservation> reservationList = ReservationRepository.getReservationList();
        int num = 1;
        System.out.printf("| %-4s | %-7s | %-11s | %-15s | %-15s | %-15s | %-10s |\n",
                "No.", "ID", "Nama Customer", "Service", "Biaya Service", "Pegawai", "Workstage");
        System.out.println("+====================================================================================================+");
        for (Reservation reservation : reservationList) {
            if (reservation.getWorkstage().equalsIgnoreCase("Waiting") || reservation.getWorkstage().equalsIgnoreCase("In process")) {
                System.out.printf("| %-4s | %-4s | %-11s | %-15s | %-15s | %-15s | %-10s |\n",
                num, reservation.getReservationId(), reservation.getCustomer().getName(), printServices(reservation.getServices()), currencyFormat.format(reservation.getReservationPrice()), reservation.getEmployee().getName(), reservation.getWorkstage());
                num++;
            }
        }
        System.out.println("+====================================================================================================+");
    }

    public static void showAllServices() {
        List<Service> serviceList = ServiceRepository.getAllService();
        String format = "| %-4s | %-7s | %-20s | %-20s |\n";
        System.out.printf(format, "No.", "ID", "Nama", "Harga");
        System.out.println("+==============================================================+");
        serviceList.stream()
                .map(s -> String.format(format, serviceList.indexOf(s) + 1, s.getServiceId(), s.getServiceName(), currencyFormat.format(s.getPrice())))
                .forEach(System.out::print);
        System.out.println("+==============================================================+");
    }

    public static void showAllCustomer(){
        String format = "| %-4s | %-7s | %-11s | %-15s | %-15s | %-20s |\n";
        System.out.printf("| %-4s | %-7s | %-11s | %-15s | %-15s | %-20s |\n",
                "No.", "ID", "Nama", "Alamat", "Membership", "Uang");
        System.out.println("+=========================================================================================+");
        List<Customer> customerList = PersonRepository.getAllCustomer();

        customerList.stream()
                .map( cust -> String.format(format, customerList.indexOf(cust) + 1, cust.getId(), cust.getName(), cust.getAddress(), cust.getMember().getMembershipName(), currencyFormat.format(cust.getWallet())))
                .forEach(System.out::print);

        System.out.println("+=========================================================================================+");

    }

    public static void showAllEmployee(){
        List<Employee> employeeList = PersonRepository.getAllEmployee();
        String format = "| %-4s | %-7s | %-11s | %-15s | %-15s |\n";
        System.out.printf(format, "No.", "ID", "Nama", "Alamat", "Pengalaman");
        System.out.println("+==================================================================+");

        employeeList.stream()
                .map(emp -> String.format(format, employeeList.indexOf(emp) + 1, emp.getId(), emp.getName(), emp.getAddress(), emp.getExperience()))
                .forEach(System.out::print);

        System.out.println("+==================================================================+");
    }

    public static void showHistoryReservation(){
        List<Reservation> reservationList = ReservationRepository.getReservationList();

        int num = 1;
        double keuntunganTotal = 0;
        System.out.printf("| %-4s | %-7s | %-15s | %-15s | %-15s | %-15s | %-10s |\n",
                "No.", "ID", "Nama Customer", "Service", "Biaya Service", "Pegawai", "Workstage");
        System.out.println("+=================================================================================================+");
        for (Reservation reservation : reservationList) {
            if (reservation.getWorkstage().equalsIgnoreCase("Finish")) {
                keuntunganTotal += reservation.getReservationPrice();
            }
            if (reservation.getWorkstage().equalsIgnoreCase("Finish") || reservation.getWorkstage().equalsIgnoreCase("Cancel")) {
                System.out.printf("| %-4s | %-7s | %-11s | %-15s | %-15s | %-15s | %-10s |\n",
                        num, reservation.getReservationId(), reservation.getCustomer().getName(), printServices(reservation.getServices()), currencyFormat.format(reservation.getReservationPrice()), reservation.getEmployee().getName(), reservation.getWorkstage());
                num++;
            }
        }
        System.out.println("+=================================================================================================+");
        System.out.printf("| %-45s | %-45s |\n", "Total Keuntungan", currencyFormat.format(keuntunganTotal));
        System.out.println("+=================================================================================================+");
    }
}
