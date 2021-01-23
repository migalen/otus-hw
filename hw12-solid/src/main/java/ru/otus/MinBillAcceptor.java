package ru.otus;

import ru.otus.exceptions.NoBillsException;

import java.util.*;

class MinBillAcceptor extends BillAcceptor {

    MinBillAcceptor(Map<Bill, Cell> noteCellMap) {
        super(noteCellMap);
    }

    @Override
    List<Bill> dispense(long neededAmount) {
        if (neededAmount <= 0) {
            return Collections.emptyList();
        }

        final List<Bill> notesToDispense = new ArrayList<>();
        final SortedMap<Bill, Cell> sortedMap = new TreeMap<>(Collections.reverseOrder());
        sortedMap.putAll(getStorage());

        for (var entry : sortedMap.entrySet()) {
            final List<Bill> notes = getNotesFromCell(entry.getKey(), neededAmount);
            neededAmount -= calculateBillValue(notes);
            notesToDispense.addAll(notes);
        }

        if (neededAmount != 0) {
            for (Bill note : notesToDispense) {
                getStorage().get(note).putBill();
            }
            throw new NoBillsException();
        }
        return notesToDispense;
    }

    private List<Bill> getNotesFromCell(Bill note, long neededAmount) {
        Cell cell = getStorage().get(note);
        int numNeededNotes = Math.toIntExact(neededAmount / note.getValue());

        if (cell.getAvailableQuantity() > 0 && numNeededNotes > 0) {
            return cell.retrieveBill(numNeededNotes);
        }
        return Collections.emptyList();
    }

    private long calculateBillValue(Collection<Bill> bills) {
        return bills
                .stream()
                .mapToLong(Bill::getValue)
                .sum();
    }
}
