package com.booking.strategy;

import com.booking.entity.Booking;

import java.math.BigDecimal;

public interface PriceStrategy {

    public BigDecimal calculateTotal(Booking booking);
}
