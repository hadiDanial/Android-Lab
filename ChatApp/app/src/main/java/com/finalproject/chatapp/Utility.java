package com.finalproject.chatapp;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.view.View;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Utility {


    public static Instant getCurrentDate() {
        return OffsetDateTime.now(ZoneOffset.UTC).toInstant();
    }

    public static String getCurrentDateString() {
        return getCurrentDate().atOffset(ZoneOffset.UTC).toString();
    }

    private static String formatDate(Instant date, String format) {
        ZonedDateTime dt = date.atZone(ZoneId.systemDefault());
        return String.format("%2d:%2d - %2d.%2d.%2d", dt.getHour(), dt.getMinute(), dt.getDayOfMonth(), dt.getMonthValue(), dt.getYear() % 100);
//        return DateTimeFormatter.ISO_INSTANT.format(date);
    }

    public static String getFormattedDateFromString(String dateString) {
        if (dateString == null) return null;
        return formatDate(getDateFromString(dateString), "HH:mm - dd.MM.yy");
    }

    public static Instant getDateFromString(String dateString) {
        if (dateString == null) return null;
        return Instant.from(DateTimeFormatter.ISO_INSTANT.parse(dateString)).atOffset(OffsetDateTime.now().getOffset()).toInstant();
    }

    public static ValueAnimator getValueAnimator(View view, int colorFrom, int colorTo, int duration) {
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        colorAnimation.setDuration(duration); // milliseconds

        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                view.setBackgroundColor((int) animator.getAnimatedValue());
            }

        });
        return colorAnimation;
    }

    public static ValueAnimator getValueAnimatorLooped(View view, int colorFrom, int colorTo, int duration) {
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo, colorFrom);
        colorAnimation.setDuration(duration); // milliseconds

        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                view.setBackgroundColor((int) animator.getAnimatedValue());
            }

        });
        return colorAnimation;
    }
}
