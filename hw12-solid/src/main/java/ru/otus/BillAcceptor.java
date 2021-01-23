package ru.otus;

import java.util.List;
import java.util.Map;

abstract class BillAcceptor {

    private final Map<Bill, Cell> noteCellMap;
    BillAcceptor(Map<Bill, Cell> noteCellMap) {
        this.noteCellMap = noteCellMap;
    }
    abstract List<Bill> dispense(long neededAmount);
    Map<Bill, Cell> getStorage() {
        return noteCellMap;
    }

}
