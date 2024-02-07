package ir.example.finalPart03.dto.orderDto;

import ir.example.finalPart03.model.Comments;
import ir.example.finalPart03.model.Customer;
import ir.example.finalPart03.model.SubServices;

import java.time.LocalDateTime;

public class OrderRequestDto {
    private Double suggestedPrice;

    String workDescription;

    LocalDateTime startDayOfWork;

    String address;

    SubServices subServices;

    Customer customer;

    Comments comments;

    public OrderRequestDto(Double suggestedPrice, String workDescription, LocalDateTime startDayOfWork, String address, SubServices subServices, Customer customer, Comments comments) {
        this.suggestedPrice = suggestedPrice;
        this.workDescription = workDescription;
        this.startDayOfWork = startDayOfWork;
        this.address = address;
        this.subServices = subServices;
        this.customer = customer;
        this.comments = comments;
    }

    public Double getSuggestedPrice() {
        return suggestedPrice;
    }

    public void setSuggestedPrice(Double suggestedPrice) {
        this.suggestedPrice = suggestedPrice;
    }

    public String getWorkDescription() {
        return workDescription;
    }

    public void setWorkDescription(String workDescription) {
        this.workDescription = workDescription;
    }

    public LocalDateTime getStartDayOfWork() {
        return startDayOfWork;
    }

    public void setStartDayOfWork(LocalDateTime startDayOfWork) {
        this.startDayOfWork = startDayOfWork;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public SubServices getSubServices() {
        return subServices;
    }

    public void setSubServices(SubServices subServices) {
        this.subServices = subServices;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Comments getComments() {
        return comments;
    }

    public void setComments(Comments comments) {
        this.comments = comments;
    }
}
