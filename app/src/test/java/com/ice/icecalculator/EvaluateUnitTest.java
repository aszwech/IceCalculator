package com.ice.icecalculator;

import org.junit.Test;

import java.util.Locale;

import static org.junit.Assert.*;

public class EvaluateUnitTest {
    @Test
    public void evaluate_isCorrect() {
        assertEquals("4.00", String.format(Locale.US, "%.2f", new ExtendedDoubleEvaluator().evaluate("2+2"))+ "");
        assertEquals("4.80", String.format(Locale.US,"%.2f", new ExtendedDoubleEvaluator().evaluate("2.0*2.4"))+ "");
        assertEquals("3.00", String.format(Locale.US,"%.2f", new ExtendedDoubleEvaluator().evaluate("sqrt(9.00)"))+ "");
        assertEquals("4.00", String.format(Locale.US,"%.2f", new ExtendedDoubleEvaluator().evaluate("(2)^2"))+ "");
    }
}