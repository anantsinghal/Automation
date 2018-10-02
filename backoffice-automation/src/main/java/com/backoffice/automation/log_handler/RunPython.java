package com.backoffice.automation.log_handler;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by NEX9ZKA on 9/20/2018.
 */
public class RunPython {
    public static void main(String args[]) {
        try {
            Process p = Runtime.getRuntime().exec("python JsonProcessor.py");
            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            System.out.println(in.readLine());
        } catch (Exception e) {
        }
    }
} 