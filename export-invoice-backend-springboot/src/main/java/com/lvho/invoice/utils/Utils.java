package com.lvho.invoice.utils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public final class Utils {

    public final static Date getExpiration(long amountToAdd, ChronoUnit chronoUnit){
        Instant now = Instant.now();
		Instant validityInstant = now.plus(amountToAdd, chronoUnit);
		Date validity = Date.from(validityInstant);
        return validity;
    }
}
