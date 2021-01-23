package ru.otus;

import ru.otus.exceptions.NoBillsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class BillAcceptorTests {

    private BillAcceptor billAcceptor;

    private final Cell cell_50 = new Cell(Bill.FIFTY_RUB);
    private final Cell cell_100 = new Cell(Bill.HUNDRED_RUB);
    private final Cell cell_500 = new Cell(Bill.FIVE_HUNDRED_RUB);
    private final Cell cell_1000 = new Cell(Bill.THOUSAND_RUB);

    private Map<Bill, Cell> noteCellMap = Map.of(
            Bill.FIVE_HUNDRED_RUB, cell_500,
            Bill.FIFTY_RUB, cell_50,
            Bill.THOUSAND_RUB, cell_1000,
            Bill.HUNDRED_RUB, cell_100
    );

    @BeforeEach
    void prepareDispenser() {
        billAcceptor = new MinBillAcceptor(noteCellMap);
    }

    @Test
    void dispenseFullNeededAmount() {
        cell_1000.putBill();
        cell_100.putBill();
        assertEquals(2, billAcceptor.dispense(1100).size(),
                "Invalid number of dispensed bills");
    }

    @Test
    void dispensePartNeededAmount() {
        for (int i = 0; i < 3; i++) {
            cell_100.putBill();
        }
        assertThrows(NoBillsException.class,
                () -> billAcceptor.dispense(250).size(),
                "Invalid number of dispensed bills");
        assertEquals(3, billAcceptor.dispense(300).size(),
                "Invalid number of dispensed bills");
    }

    @Test
    void checkMinimalNumBillsDispensed() {
        for (int i = 0; i < 5; i++) {
            cell_100.putBill();
        }
        cell_500.putBill();
        assertEquals(1, billAcceptor.dispense(500).size(),
                "Invalid number of dispensed bills");
    }

    @Test
    void testNoBanknotesException() {
        cell_1000.putBill();
        assertThrows(NoBillsException.class,
                () -> billAcceptor.dispense(100));
    }

    @Test
    void testSomeErrors() {
        assertTrue(billAcceptor.dispense(0L).isEmpty(),
                "Bills for 0 amount is not empty");
        assertTrue(billAcceptor.dispense(-5L).isEmpty(),
                "Bills for -5 amount is not empty");
    }
}
