package ru.otus;

import java.util.Collection;
import java.util.List;

public interface ATM {

    boolean acceptBill(Bill bill);
    boolean acceptBill(Collection<Bill> bills);
    List<Bill> dispenseBill(long amount);
    long getBalance();
}
