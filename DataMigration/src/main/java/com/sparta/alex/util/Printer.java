package com.sparta.alex.util;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Printer {
    public static void print (String sentence) {
        System.out.println(sentence);
    }

    public static void print (int integer) {
        System.out.println(integer);
    }

    public static void print (List list) {
        System.out.println(list);
    }
}
