package com.java.rpncalc.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.NoSuchElementException;

import org.junit.Test;

import com.java.rpncalc.RPNCalculator;
import com.java.rpncalc.RPNCalculator.Command;
import com.java.rpncalc.RPNCalculator.State;

public class RPNCalculatorTest {
	
	
	/**
	 * RPNCalci class test case
	 * @throws Exception
	 */
	@Test
	public void rpnTest() throws Exception {
		RPNCalculator tester = new RPNCalculator();
		tester.main();
	}

	
	/**
	 * Test case for add operation testing by input as example 6 4 + 
	 * @throws Exception
	 */
	@Test
	public void addTest() throws Exception {
		RPNCalculator tester = new RPNCalculator();
		State state = tester.new State();
		String inputLine = "6 4 +";
		System.out.print("Input Values :: " + inputLine);
		Command cmd = tester.parseInput(inputLine);
		state = cmd.execute(state);
		System.out.println();
		tester.displayStack(state);
	}
	
	/**
	 * Test case for add operation testing by input as example 5 3 + from command line
	 * @throws Exception
	 */
	@Test
	public void addCmdLineTest() throws Exception {
		commadLineTest();
	}
	
	
	/**
	 * Test case for substract operation testing by input as example 6 4 - 
	 * @throws Exception
	 */
	@Test
	public void substractTest() throws Exception {
		RPNCalculator tester = new RPNCalculator();
		State state = tester.new State();
		String inputLine = "9 4 -";
		System.out.print("Input Values :: " + inputLine);
		Command cmd = tester.parseInput(inputLine);
		state = cmd.execute(state);
		System.out.println();
		tester.displayStack(state);
	}
	
	/**
	 * Test case for substract operation testing by input as example 6 4 - from command line 
	 * @throws Exception
	 */
	@Test
	public void substractCmdLineTest() throws Exception {
		commadLineTest();
	}
	
	

	/**
	 * Test case for multiply operation testing by input as example 6 4 *  
	 * @throws Exception
	 */
	@Test
	public void multiplyTest() throws Exception {
		RPNCalculator tester = new RPNCalculator();
		State state = tester.new State();
		String inputLine = "5 4 *";
		System.out.print("Input Values :: " + inputLine);
		Command cmd = tester.parseInput(inputLine);
		state = cmd.execute(state);
		System.out.println();
		tester.displayStack(state);
	}
	
	/**
	 * Test case for multiply operation testing by input as example 6 4 * from command line 
	 * @throws Exception
	 */
	@Test
	public void multiplyCmdLineTest() throws Exception {
		commadLineTest();
	}
	
	
	/**
	 * Method to run one arithmetic operation +,-,*,/ from command Line.
	 * @throws IOException
	 * @throws Exception
	 */
	private void commadLineTest() throws IOException, Exception {
		RPNCalculator tester = new RPNCalculator();
		State state = tester.new State();
		System.out.print("Enter your Input :: ");
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				System.in));
		String inputLine = reader.readLine().trim();
		Command cmd = tester.parseInput(inputLine);
		state = cmd.execute(state);
		tester.displayStack(state);
	}
	
	/**
	 * Test case for divide operation testing by input as example 20 5 / from command line 
	 * @throws Exception
	 */
	@Test
	public void divideCmdLineTest() throws Exception {
		commadLineTest();
	}
	
	/**
	 * Divide by Zero Test case for divide operation testing by input as example 20 0 /
	 * @throws Exception
	 */
	@Test(expected=ArithmeticException.class)
	public void divideByZeroTest() throws Exception {
		RPNCalculator tester = new RPNCalculator();
		State state = tester.new State();
		String inputLine = "20 0 /";
		System.out.print("Input Values :: " + inputLine);
		Command cmd = tester.parseInput(inputLine);
		state = cmd.execute(state);
		System.out.println();
		tester.displayStack(state);
	}

	
	/**
	 * Test case for divide operation testing by input as example 20 5 / 
	 * @throws Exception
	 */
	@Test
	public void divideTest() throws Exception {
		RPNCalculator tester = new RPNCalculator();
		State state = tester.new State();
		String inputLine = "20 5 /";
		System.out.print("Input Values :: " + inputLine);
		Command cmd = tester.parseInput(inputLine);
		state = cmd.execute(state);
		System.out.println();
		tester.displayStack(state);
	}
	
	/**
	 * Test case for sqrt operation testing by input as example 9 sqrt 
	 * @throws Exception
	 */
	@Test
	public void sqrtTest() throws Exception {
		RPNCalculator tester = new RPNCalculator();
		State state = tester.new State();
		String inputLine = "9 sqrt";
		System.out.print("Input Values :: " + inputLine);
		Command cmd = tester.parseInput(inputLine);
		state = cmd.execute(state);
		System.out.println();
		tester.displayStack(state);
	}
	
	/**
	 * Test case for clear operation 
	 * @throws Exception
	 */
	@Test
	public void clearTest() throws Exception {
		RPNCalculator tester = new RPNCalculator();
		State state = tester.new State();
		String inputLine = "clear";
		System.out.print("Input Values :: " + inputLine);
		Command cmd = tester.parseInput(inputLine);
		state = cmd.execute(state);
		System.out.println();
		tester.displayStack(state);
	}
	
	/**
	 * Test case for insufficient parameters error
	 * @throws Exception
	 */
	@Test(expected=NoSuchElementException.class)
	public void insufficientParamTest() throws Exception {
		RPNCalculator tester = new RPNCalculator();
		State state = tester.new State();
		String inputLine = "5 4 * *";
		System.out.print("Input Values :: " + inputLine);
		Command cmd = tester.parseInput(inputLine);
		state = cmd.execute(state);
		System.out.println();
		tester.displayStack(state);
	}
	
	
	/**
	 * Test case for undo operation
	 * @throws Exception
	 */
	@Test
	public void undoTest() throws Exception {
		RPNCalculator tester = new RPNCalculator();
		tester.main();
	}
	
	
	/**
	 * Test case to stop program termination
	 * @throws Exception
	 */
	@Test
	public void stopTest() throws Exception {
		RPNCalculator tester = new RPNCalculator();
		State state = tester.new State();
		String inputLine = "stop";
		System.out.print("Input Values :: " + inputLine);
		Command cmd = tester.parseInput(inputLine);
		state = cmd.execute(state);
		System.out.println();
		tester.displayStack(state);
	}
	
	/**
	 * Test case to push number
	 * @throws Exception
	 */
	@Test
	public void pushNumberTest() throws Exception {
		RPNCalculator tester = new RPNCalculator();
		State state = tester.new State();
		String inputLine = "5 4 ";
		System.out.print("Input Values :: " + inputLine);
		Command cmd = tester.parseInput(inputLine);
		state = cmd.execute(state);
		System.out.println();
		tester.displayStack(state);
	}
	
}
