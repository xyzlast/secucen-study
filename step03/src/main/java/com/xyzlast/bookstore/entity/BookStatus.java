package com.xyzlast.bookstore.entity;

public enum BookStatus {
    CanRent(0),
    RentNow(1),
    Missing(2);

    private int value;
    BookStatus(int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }

    public static BookStatus parse(int value) {
        switch(value) {
            case 0 : return CanRent;
            case 1 : return RentNow;
            case 2 : return Missing;
            default:
                throw new IllegalArgumentException();
        }
    }
}
