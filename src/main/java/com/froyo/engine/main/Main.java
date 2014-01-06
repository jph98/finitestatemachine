package com.froyo.engine.main;

import com.froyo.engine.CoreExecutableEngine;
import com.froyo.processor.NONMEMProcessor;

public class Main {

    private static final String NONMEM = "nonmem";

    public static void main(String[] args) {
        
        // Ideally this would be an XML definition for processors
        CoreExecutableEngine engine = new CoreExecutableEngine();
        engine.add(NONMEM, new NONMEMProcessor(5));
        engine.start();
        
        engine.submitTo(NONMEM, "<jobxml name='NONMEM'></jobxml>");
    }
}
