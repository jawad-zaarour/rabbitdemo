package com.jz.rabbitmqdemo.utils;

import org.apache.commons.lang3.exception.ExceptionUtils;

public class Utils {

    public static String getException(Exception e) {
        return ExceptionUtils.getStackTrace(e);
    }
    public static String getExceptionString(Exception e) {
        return ExceptionUtils.getMessage(e);
    }
}
