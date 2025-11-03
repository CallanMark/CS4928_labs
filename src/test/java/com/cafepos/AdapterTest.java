package com.cafepos;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import vendor.legacy.LegacyThermalPrinter;
import com.cafepos.printing.Printer;
import com.cafepos.printing.LegacyPrinterAdapter;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public final class AdapterTest {
    
    private int captureByteLength(Runnable testAction) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(baos));
        try {
            testAction.run();
            String output = baos.toString();
            Pattern pattern = Pattern.compile("\\[Legacy\\] printing bytes: (\\d+)");
            Matcher matcher = pattern.matcher(output);
            if (matcher.find()) {
                return Integer.parseInt(matcher.group(1));
            }
            return -1;
        } finally {
            System.setOut(originalOut);
        }
    }
    
    @Test
    void adapter_converts_text_to_bytes() {
        Printer p = new LegacyPrinterAdapter(new LegacyThermalPrinter());
        int byteLen = captureByteLength(() -> p.print("ABC"));
        Assertions.assertTrue(byteLen >= 3, 
            "Adapter should convert text to bytes (expected length >= 3, got " + byteLen + ")");
    }
    
    @Test
    void adapter_handles_empty_string() {
        Printer p = new LegacyPrinterAdapter(new LegacyThermalPrinter());
        int byteLen = captureByteLength(() -> p.print(""));
        Assertions.assertEquals(0, byteLen, 
            "Empty string should produce 0 bytes");
    }
    
    @Test
    void adapter_handles_multiline_receipt() {
        Printer p = new LegacyPrinterAdapter(new LegacyThermalPrinter());
        String receipt = "Order (LAT+L) x2\nSubtotal: 7.80\nTax (10%): 0.78\nTotal: 8.58";
        int byteLen = captureByteLength(() -> p.print(receipt));
        Assertions.assertTrue(byteLen > 0, 
            "Receipt should produce bytes");
        Assertions.assertTrue(byteLen >= 48, 
            "Receipt should be converted to bytes (expected >= 48, got " + byteLen + ")");
    }
}

