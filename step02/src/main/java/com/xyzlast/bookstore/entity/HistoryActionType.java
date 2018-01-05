package com.xyzlast.bookstore.entity;

public enum HistoryActionType {
    RENT_BOOK(0),
    RETURN_BOOK(1);

    private final int value;

    HistoryActionType(int typeValue) {
        this.value = typeValue;
    }

    public int value() {
        return this.value;
    }

    public static HistoryActionType parse(int value) {
        switch(value) {
            case 0 : return RENT_BOOK;
            case 1 : return RETURN_BOOK;
            default:
                throw new IllegalArgumentException();
        }
    }
}
