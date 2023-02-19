package com.finalproject.chatapp;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

import java.time.Instant;
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

    public static String formatDate(Instant date) {
        ZonedDateTime dt = date.atZone(ZoneId.systemDefault());
        return String.format("%02d:%02d - %02d.%02d.%02d", dt.getHour(), dt.getMinute(), dt.getDayOfMonth(), dt.getMonthValue(), dt.getYear() % 100);
//        return DateTimeFormatter.ISO_INSTANT.format(date);
    }

    public static String getFormattedDateFromString(String dateString) {
        if (dateString == null) return null;
        return formatDate(getDateFromString(dateString));
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

    public static void generateAlertDialog(DialogInterface.OnClickListener positiveButtonAction, DialogInterface.OnClickListener negativeButtonAction,
                                           String alertMessage, String positiveButtonText, String negativeButtonText, boolean isCancelable, Context context) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMessage(alertMessage);
        alertDialogBuilder.setCancelable(isCancelable);

        alertDialogBuilder.setPositiveButton(positiveButtonText, positiveButtonAction);
        alertDialogBuilder.setNegativeButton(negativeButtonText, negativeButtonAction);

        alertDialogBuilder.create().show();
    }
}
