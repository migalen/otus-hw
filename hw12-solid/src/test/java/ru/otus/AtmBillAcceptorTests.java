package ru.otus;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AtmBillAcceptorTests {

    private BillAcceptor billAcceptor;
    private ATM atm;

    private Map<Bill, Cell> noteCellMap = Map.of(
            Bill.FIVE_HUNDRED_RUB, new Cell(Bill.FIVE_HUNDRED_RUB),
            Bill.THOUSAND_RUB, new Cell(Bill.THOUSAND_RUB)
    );

    @BeforeEach
    void prepareAtm() {
        billAcceptor = new MinBillAcceptor(noteCellMap);
        atm = new ATMImpl(billAcceptor);
    }

    @Test
    void checkBalanceAfterDispense() {
        final var billList = List.of(
                Bill.THOUSAND_RUB,
                Bill.FIVE_HUNDRED_RUB);

        assertTrue(atm.acceptBill(billList),
                "Bill are not accepted");
        assertEquals(atm.getBalance(), 1500L,
                "Balance is not correct");
        atm.dispenseBill(500L);
        assertEquals(atm.getBalance(), 1000L,
                "Balance is not correct");
    }
}
