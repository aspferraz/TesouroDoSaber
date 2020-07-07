package com.seventech.tesourodosaber.utils;

public class Assert {
    public static void that(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
