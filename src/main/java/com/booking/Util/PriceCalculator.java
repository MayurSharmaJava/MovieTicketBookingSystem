package com.booking.Util;

import com.booking.entity.Booking;
import com.booking.strategy.PriceStrategy;

import java.math.BigDecimal;

public class PriceCalculator {
    private PriceStrategy priceStrategy;

    public PriceCalculator(PriceStrategy priceStrategy){
        this.priceStrategy = priceStrategy;
    }

    public BigDecimal calculate(Booking booking){
        return priceStrategy.calculateTotal(booking);
    }
}
