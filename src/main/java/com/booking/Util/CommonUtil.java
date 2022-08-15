package com.booking.Util;

import com.booking.entity.Booking;

public class CommonUtil {
    public CommonUtil() {
    }

    public static String getLockPattern(Booking booking) {

        StringBuilder seatIds = new StringBuilder();
        booking.getSeats().forEach(seat -> seatIds.append(seat.getId()));

        String showId = String.valueOf(booking.getMovieShow().getId());

        return showId + seatIds;
    }
}