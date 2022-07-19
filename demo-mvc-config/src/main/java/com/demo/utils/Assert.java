package com.demo.utils;

import java.util.Collection;
import java.util.Map;

public class Assert extends org.springframework.util.Assert {
    public Assert() {
    }

    public static boolean isEmpty(String str) {
        return str == null || "".equals(str);
    }

    public static boolean isEmpty(Object obj) {
        if (obj instanceof String) {
            return "".equals(obj);
        } else if (obj instanceof Collection) {
            return ((Collection) obj).isEmpty();
        } else if (obj instanceof Object[]) {
            return ((Object[]) obj).length == 0;
        } else if (!(obj instanceof Map)) {
            return obj == null;
        } else {
            return ((Map) obj).isEmpty();
        }
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }

    public static void isEmpty(Object reference, Object errorMessage) {
        if (isNotEmpty(reference)) {
            throw new IllegalArgumentException(String.valueOf(errorMessage));
        }
    }

    public static void isNotEmpty(Object reference, Object errorMessage) {
        if (isEmpty(reference)) {
            throw new IllegalArgumentException(String.valueOf(errorMessage));
        }
    }

    public static void isTrue(boolean condition, String message) {
        if (!condition) {
            fail(message);
        }

    }

    public static void isTrue(boolean condition) {
        isTrue(condition, (String) null);
    }

    public static void isFalse(boolean condition, String message) {
        isTrue(!condition, message);
    }

    public static void isFalse(boolean condition) {
        isFalse(condition, (String) null);
    }

    public static void fail(String message) {
        if (message == null) {
            throw new RuntimeException("断言失败，不符合预设逻辑！");
        } else {
            throw new RuntimeException(message);
        }
    }

    public static void fail() {
        fail((String) null);
    }

    public static void equals(Object expected, Object actual, String message) {
        if (expected != null || actual != null) {
            if (expected == null || !isEquals(expected, actual)) {
                failNotEquals(message, expected, actual);
            }
        }
    }

    private static boolean isEquals(Object expected, Object actual) {
        return expected.equals(actual);
    }

    public static void equals(Object expected, Object actual) {
        equals(expected, actual, (String) null);
    }

    public static void equals(double expected, double actual, double delta, String message) {
        if (Double.compare(expected, actual) != 0) {
            if (Math.abs(expected - actual) > delta) {
                failNotEquals(message, expected, actual);
            }

        }
    }

    public static void equals(long expected, long actual) {
        equals(expected, actual, null);
    }

    public static void equals(long expected, long actual, String message) {
        equals(expected, actual, message);
    }

    public static void equals(double expected, double actual, double delta) {
        equals(expected, actual, delta, (String) null);
    }

    public static void notNull(Object object, String message) {
        isTrue(object != null, message);
    }

    public static void notNull(Object object) {
        notNull(object, (String) null);
    }

    public static void isNull(Object object, String message) {
        isTrue(object == null, message);
    }

    public static void isNull(Object object) {
        isNull(object, (String) null);
    }

    public static void same(Object expected, Object actual, String message) {
        if (expected != actual) {
            failNotSame(message, expected, actual);
        }
    }

    public static void same(Object expected, Object actual) {
        same(expected, actual, (String) null);
    }

    public static void notSame(Object unexpected, Object actual, String message) {
        if (unexpected == actual) {
            failSame(message);
        }

    }

    public static void notSame(Object unexpected, Object actual) {
        notSame(unexpected, actual, (String) null);
    }

    private static void failSame(String message) {
        String formatted = "";
        if (message != null) {
            formatted = message + " ";
        }

        fail(formatted + "expected not same");
    }

    private static void failNotSame(String message, Object expected, Object actual) {
        String formatted = "";
        if (message != null) {
            formatted = message + " ";
        }

        fail(formatted + "expected same:<" + expected + "> was not:<" + actual + ">");
    }

    private static void failNotEquals(String message, Object expected, Object actual) {
        fail(format(message, expected, actual));
    }

    private static String format(String message, Object expected, Object actual) {
        String formatted = "";
        if (message != null && !message.equals("")) {
            formatted = message + " ";
        }

        String expectedString = String.valueOf(expected);
        String actualString = String.valueOf(actual);
        return expectedString.equals(actualString) ? formatted + "expected: " + formatClassAndValue(expected, expectedString) + " but was: " + formatClassAndValue(actual, actualString) : formatted + "expected:<" + expectedString + "> but was:<" + actualString + ">";
    }

    private static String formatClassAndValue(Object value, String valueString) {
        String className = value == null ? "null" : value.getClass().getName();
        return className + "<" + valueString + ">";
    }
}
