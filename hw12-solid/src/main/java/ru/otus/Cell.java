package ru.otus;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class Cell {

    private final Bill bill;
    private long availableQuantity;

    Cell(Bill bill) {
        Objects.requireNonNull(bill);
        this.bill = bill;
    }

    void putBill() {
        availableQuantity++;
    }

    List<Bill> retrieveBill(int maxNumNotes) {
        final List<Bill> bills = new ArrayList<>();
        long notesToRetrieve = (maxNumNotes < availableQuantity) ? maxNumNotes : availableQuantity;

        for (int i = 0; i < notesToRetrieve; i++) {
            bills.add(bill);
        }
        availableQuantity -= notesToRetrieve;
        return bills;
    }

    long getAvailableQuantity() {
        return availableQuantity;
    }
}
