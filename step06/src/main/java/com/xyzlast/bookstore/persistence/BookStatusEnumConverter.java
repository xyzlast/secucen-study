package com.xyzlast.bookstore.persistence;

import com.xyzlast.bookstore.entity.BookStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class BookStatusEnumConverter implements AttributeConverter<BookStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(BookStatus attribute) {
        return attribute.value();
    }

    @Override
    public BookStatus convertToEntityAttribute(Integer dbData) {
        if (0 == dbData) {
            return BookStatus.CanRent;
        } else if (1 == dbData) {
            return BookStatus.RentNow;
        }
        return BookStatus.Missing;
    }
}

