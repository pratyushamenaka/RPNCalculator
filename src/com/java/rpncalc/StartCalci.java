package com.java.rpncalc;

public class StartCalci {
	
	public static void main( String[] args )
    {
        try {
               Object calc = RPNCalculator.class.newInstance();
                ((RPNCalculator) calc).main();

        } catch(Exception ex) {
            System.err.println("Exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
