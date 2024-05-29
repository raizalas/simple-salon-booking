package com.booking.models;

import java.util.List;
import java.util.Set;

import com.booking.service.IDGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Reservation {
    private String reservationId;
    private Customer customer;
    private Employee employee;
    private Set<Service> services;
    private double reservationPrice;
    private String workstage = "In Process";
    //   workStage (In Process, Finish, Canceled)

    public Reservation(Customer customer, Employee employee, Set<Service> services) {
        this.reservationId = IDGenerator.nextReservationID();
        this.customer = customer;
        this.employee = employee;
        this.services = services;
        this.reservationPrice = calculateReservationPrice();
    };

    public void setWorkstage(String workstage) {
        if(!workstage.equalsIgnoreCase("Finish") && !workstage.equalsIgnoreCase("Cancel")) {
            throw new IllegalArgumentException("Tahap invalid masukkan [Finish] atau [Cancel] ");
        }
        this.workstage = workstage;
    }

    private double calculateReservationPrice(){
        double total = 0;
        for (Service service: getServices()) {
            total += service.getPrice();
        }
        if (customer.getMember().getMembershipName().equalsIgnoreCase("silver")) {
            total = total - (total * 0.05);
        } else if (customer.getMember().getMembershipName().equalsIgnoreCase("gold")) {
            total = total - (total * 0.1);
        }

        return total;
    }
}
