package com.booking.strategy;

import com.booking.entity.Booking;
import com.booking.entity.Seat;

import java.math.BigDecimal;

public class NoDiscountStrategy implements PriceStrategy{

    @Override
    public BigDecimal calculateTotal(Booking booking) {
        BigDecimal total= BigDecimal.valueOf(0);
        for (Seat seat: booking.getSeats()) {
            total = total.add(seat.getPrice());
        }
        return total;
    }
}
