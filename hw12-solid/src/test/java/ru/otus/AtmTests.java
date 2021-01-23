package ru.otus;

import ru.otus.exceptions.InsufficientFundsException;
import ru.otus.exceptions.NoSuchCellException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class AtmTests {

    private BillAcceptor billAcceptor;
    private ATM atm;

    private Map<Bill, Cell> noteCellMap = Map.of(
            Bill.FIFTY_RUB, new Cell(Bill.FIFTY_RUB),
            Bill.HUNDRED_RUB, new Cell(Bill.HUNDRED_RUB),
            Bill.FIVE_HUNDRED_RUB, new Cell(Bill.FIVE_HUNDRED_RUB),
            Bill.THOUSAND_RUB, new Cell(Bill.THOUSAND_RUB)
    );

    @BeforeEach
    void prepareAtm() {
        billAcceptor = Mockito.mock(BillAcceptor.class);
        atm = new ATMImpl(billAcceptor);
    }

    @Test
    void acceptSuccessfully() {
        final var billList = List.of(
                Bill.FIFTY_RUB,
                Bill.HUNDRED_RUB);
        when(billAcceptor.getStorage()).thenReturn(noteCellMap);
        assertTrue(atm.acceptBill(billList),
                "Bill are not accepted");
    }

    @Test
    void checkBalance() {
        final var billList = List.of(
                Bill.FIFTY_RUB,
                Bill.HUNDRED_RUB);
        when(billAcceptor.getStorage()).thenReturn(noteCellMap);

        assertTrue(atm.acceptBill(billList),
                "Bills are not accepted");
        assertEquals(atm.getBalance(), 150L,
                "Balance is not correct");
    }

    @Test
    void dispenseBills() {
        final var billList = List.of(
                Bill.FIVE_HUNDRED_RUB,
                Bill.THOUSAND_RUB);
        when(billAcceptor.getStorage()).thenReturn(noteCellMap);
        when(billAcceptor.dispense(1500L)).thenReturn(billList);

        assertTrue(atm.acceptBill(billList),
                "Bills are not accepted");
        assertTrue(atm.dispenseBill(1500L).containsAll(billList));
    }

    @Test
    void testNoSuchCellException() {
        when(billAcceptor.getStorage()).thenReturn(
                Map.of(Bill.THOUSAND_RUB, new Cell(Bill.THOUSAND_RUB)));
        assertThrows(NoSuchCellException.class,
                () -> atm.acceptBill(Bill.HUNDRED_RUB));
    }

    @Test
    void testInsufficientFundsException() {
        assertThrows(InsufficientFundsException.class,
                () -> atm.dispenseBill(100L));
    }
}
