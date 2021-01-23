package ru.otus;

import ru.otus.exceptions.InsufficientFundsException;
import ru.otus.exceptions.NoSuchCellException;

import java.util.Collection;
import java.util.List;

public class ATMImpl implements ATM {

    private final BillAcceptor billAcceptor;

    ATMImpl(BillAcceptor billAcceptor) {
        this.billAcceptor = billAcceptor;
    }

    @Override
    public boolean acceptBill(Bill bill) {

        return acceptBill(List.of(bill));
    }

    @Override
    public boolean acceptBill(Collection<Bill> bills) {
        var noteCellMap = billAcceptor.getStorage();

        for (Bill note : bills) {
            Cell cell = noteCellMap.get(note);
            if (cell == null) {
                throw new NoSuchCellException("No cell for a bill with value: " + note.getValue());
            }
            cell.putBill();
        }
        return true;
    }

    @Override
    public List<Bill> dispenseBill(long amount) {
        long balance = getBalance();
        if (balance < amount) {
            throw new InsufficientFundsException(
                    "Your balance: (" + balance + ") is less than requested amount - " + amount);
        }

        return billAcceptor.dispense(amount);
    }

    @Override
    public long getBalance() {
        var noteCellMap = billAcceptor.getStorage();

        long balance = 0;
        for (var entry : noteCellMap.entrySet()) {
            balance += (entry.getKey().getValue() * entry.getValue().getAvailableQuantity());
        }
        return balance;
    }
}
