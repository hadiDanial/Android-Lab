package com.finalproject.chatapp;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.view.View;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utility {
    private static final String FORMAT = "yyyy-MM-dd'T'HH:mm:sss'Z'";
    public static String getDate()
    {
        SimpleDateFormat ISO_8601_FORMAT = new SimpleDateFormat(FORMAT);
        return ISO_8601_FORMAT.format(new Date());
    }
    public static String getDate(Date date)
    {
        SimpleDateFormat ISO_8601_FORMAT = new SimpleDateFormat(FORMAT);
        return ISO_8601_FORMAT.format(date);
    }
    public static String getDate(Date date, String format)
    {
        SimpleDateFormat ISO_8601_FORMAT = new SimpleDateFormat(format);
        return ISO_8601_FORMAT.format(date);
    }

    public static String getFormattedDate(String dateString) {
        if(dateString == null) return null;
        DateFormat dateFormat = new SimpleDateFormat(FORMAT);
        try {
            Date date = dateFormat.parse(dateString);
            return getDate(date, "HH:mm - dd.MM.yy");
        } catch (ParseException e) {
            return "";
        }
    }

    public static Date getDateFromString(String dateString) {
        if(dateString == null) return null;
        DateFormat dateFormat = new SimpleDateFormat(FORMAT);
        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            return null;
        }
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
