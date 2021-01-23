package ru.otus;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CellTests {

    @Test
    void putBillsSuccess() {
        Cell cell = new Cell(Bill.HUNDRED_RUB);
        cell.putBill();
        assertEquals(1, cell.getAvailableQuantity(),
                "Bills was not put to a cell");
    }

    @Test
    void retrieveAllNeededNotes() {
        Cell cell = new Cell(Bill.HUNDRED_RUB);
        for (int i = 0; i < 5; i++) {
            cell.putBill();
        }
        assertEquals(4, cell.retrieveBill(4).size(),
                "Retrieved incorrect number of bills");
    }

    @Test
    void retrieveAllAvailableNotes() {
        Cell cell = new Cell(Bill.HUNDRED_RUB);
        for (int i = 0; i < 5; i++) {
            cell.putBill();
        }
        assertEquals(5, cell.retrieveBill(10).size(),
                "Retrieved incorrect number of bills");
    }

    @Test
    void cellWithNullBillsNotCreated() {
        assertThrows(NullPointerException.class,
                () -> new Cell(null));
    }
}
