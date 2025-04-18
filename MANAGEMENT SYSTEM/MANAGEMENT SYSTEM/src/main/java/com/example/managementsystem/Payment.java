package com.example.managementsystem;

import javafx.beans.binding.BooleanExpression;
import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;

public class Payment {
    private final IntegerProperty id;
    private final IntegerProperty bookingId;
    private final StringProperty customerName;
    private final DoubleProperty amount;
    private final StringProperty paymentDate;
    private final StringProperty method;
    private final StringProperty status;

    public Payment(int id, int bookingId, String customerName,
                   double amount, String paymentDate, String method, String status) {
        this.id = new SimpleIntegerProperty(id);
        this.bookingId = new SimpleIntegerProperty(bookingId);
        this.customerName = new SimpleStringProperty(customerName);
        this.amount = new SimpleDoubleProperty(amount);
        this.paymentDate = new SimpleStringProperty(paymentDate);
        this.method = new SimpleStringProperty(method);
        this.status = new SimpleStringProperty(status);
    }

    public Payment(int id, int bookingId, double amount, String method, String date, IntegerProperty id1, IntegerProperty bookingId1, StringProperty customerName, DoubleProperty amount1, StringProperty paymentDate, StringProperty method1, StringProperty status) {
        this.id = id1;
        this.bookingId = bookingId1;
        this.customerName = customerName;
        this.amount = amount1;
        this.paymentDate = paymentDate;
        this.method = method1;
        this.status = status;
    }

    // Property getters
    public IntegerProperty idProperty() { return id; }
    public IntegerProperty bookingIdProperty() { return bookingId; }
    public StringProperty customerNameProperty() { return customerName; }
    public DoubleProperty amountProperty() { return amount; }
    public StringProperty paymentDateProperty() { return paymentDate; }
    public StringProperty methodProperty() { return method; }
    public StringProperty statusProperty() { return status; }

    // Regular getters
    public int getId() { return id.get(); }
    public int getBookingId() { return bookingId.get(); }
    public String getCustomerName() { return customerName.get(); }
    public double getAmount() { return amount.get(); }
    public String getPaymentDate() { return paymentDate.get(); }
    public String getMethod() { return method.get(); }
    public String getStatus() { return status.get(); }

    public ObservableValue<String> dateProperty() {
        return null;
    }

    public BooleanExpression paymentIdProperty() {
        return null;
    }
}