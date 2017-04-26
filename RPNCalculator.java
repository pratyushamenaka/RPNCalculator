package com.java.rpncalc;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Deque;
import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author
 * @Desc This program will read the input from the console and perform Reverse
 *       Polish Notation The eligible input is Numbers and +, -, *, /, undo,
 *       clear, sqrt and stop operator
 * 
 *       Designed on Command Pattern each input argument is treated as command
 *       If input is numeric pushed onto stack if Operator the corresponding
 *       operation is performed on top two elements from stack.
 * 
 * 
 * 
 */

public class RPNCalculator {
	/**
	 * Stores all the input args as commands.
	 * 
	 */
	private Map<String, Command> cmdMap = new HashMap<String, Command>();

	/**
	 * Position indicator
	 */
	int position = 0;
	
	/**
	 * Constructor to load and perform all the operations.
	 * 
	 */
	public RPNCalculator() {

		addCommand();

		substractCommand();

		multiplyCommand();

		divideCommand();

		sqrtCommand();

		clearCommand();

		undoCommand();

		stopCommand();
	}
	
	/**
	 * Abstract Command class with default state.
	 * 
	 */
	public abstract class Command {
		public State execute(State in) {
			return in;
		}
	}
	

	/**
	 * @Desc State After each command execution state is saved.
	 * 
	 */
	public class State {
		State prevState;
		Deque<Double> stack = null;

		public State() {
			prevState = null;
			stack = new LinkedList<Double>();
		}

		State(State originalState) {
			prevState = originalState;
			stack = new LinkedList<Double>(originalState.stack);
		}

		// Called only during undo operation
		State(State originalState, State undoPrev) {
			prevState = undoPrev;
			stack = new LinkedList<Double>(undoPrev.stack);
		}
	}
	
	
	/**
	 * Command to push Input Number .
	 * 
	 */
	private class PushCommand extends Command {
		private Double inputNum;

		PushCommand(Double inputNum) {
			this.inputNum = inputNum;
		}

		public State execute(State inpState) {
			State outState = new State(inpState);

			outState.stack.push(inputNum);
			position++;

			return outState;
		}
	}
	
	
	/**
	 * Method to call each respective command execute method for Push,+,*,-,/,undo, clear, stop, sqrt command
	 * @return State after each command execution
	 */
	private class RunCommand extends Command {
		private List<Command> cmdList = new LinkedList<Command>();

		RunCommand(Collection<Command> subCmds) {
			this.cmdList.addAll(subCmds);
		}

		public State execute(State inputState) {
			State outState = new State(inputState);

			for (Command cmd : cmdList)
				outState = cmd.execute(outState);

			return outState;
		}
	}

	/**
	 * Method to terminate the Program when stop is passed as Input.
	 */
	private void stopCommand() {
		cmdMap.put("stop", new Command() {
			public State execute(State in) {
				position++;
				return null;
			}
		});
	}

	/**
	 * Method to undo previous operation
	 */
	private void undoCommand() {
		cmdMap.put("undo", new Command() {
			public State execute(State in) {
				State out = new State(in.prevState, in.prevState.prevState);
				return out;
			}
		});
	}

	/**
	 * Method to clear stack 
	 */
	private void clearCommand() {
		cmdMap.put("clear", new Command() {
			public State execute(State in) {
				State out = new State(in);
				out.stack.clear();
				return out;
			}
		});
	}

	/**
	 * sqrt operation
	 */
	private void sqrtCommand() {
		cmdMap.put("sqrt", new Command() {
			public State execute(State in) {
				position++;
				State out = new State(in);
				Double x  = null;
				try {
				x = out.stack.pop();
				out.stack.push(Math.sqrt(x));
				return out;
				} catch (NoSuchElementException e) {
					out.stack.push(x);
					System.out.println(" operator sqrt (position :  " + position+ ")  : insufficient parameters " );
					displayStack(out.prevState);
					throw new NoSuchElementException();
				}
			}
		});
	}

	/**
	 * divide operation
	 */
	private void divideCommand() {
		cmdMap.put("/", new Command() {
			public State execute(State in) {
				position++;
				State out = new State(in);
				Double x = null;
				try {
					isStackEmpty(out.stack);
					x = out.stack.pop();
					Double y = out.stack.pop();
					out.stack.push(y / x);
					if(out.stack.getLast().isInfinite()){
						out.stack.clear();
						throw new ArithmeticException();
					}
				} catch (ArithmeticException exe) {
					throw new ArithmeticException();
				} catch (NoSuchElementException e) {
					out.stack.push(x);
					System.out.println(" operator / (position :  " + position+ ")  : insufficient parameters " );
					displayStack(out.prevState);
					throw new NoSuchElementException();
				}

				return out;
			}
		});
	}

	/**
	 *  multiply operation
	 */
	private void multiplyCommand() {
		cmdMap.put("*", new Command() {
			public State execute(State in) {
				position++;
				State out = new State(in);
				Double x = null;
				try {
					isStackEmpty(out.stack);
					x = out.stack.pop();
					Double y = out.stack.pop();
					out.stack.push(x * y);
				} catch (NoSuchElementException e) {
					out.stack.push(x);
					System.out.println(" operator * (position :  " + position+ ")  : insufficient parameters " );
					displayStack(out.prevState);
					throw new NoSuchElementException();
				}

				return out;
			}
		});
	}

	/**
	 * substract operation
	 */
	private void substractCommand() {
		cmdMap.put("-", new Command() {
			public State execute(State in) {
				position++;
				State out = new State(in);
				Double x = null;
				try {
					isStackEmpty(out.stack);
					x = out.stack.pop();
					Double y = out.stack.pop();

					out.stack.push(y - x);
				} catch (NoSuchElementException e) {
					out.stack.push(x);
					System.out.println(" operator - (position :  " + position+ ")  : insufficient parameters " );
					displayStack(out.prevState);
					throw new NoSuchElementException();
				}

				return out;
			}
		});
	}

	/**
	 * Add operation
	 */
	private void addCommand() {
		cmdMap.put("+", new Command() {
			public State execute(State in) {
				position++;
				State out = new State(in);
				Double x = null;
				try {
					isStackEmpty(out.stack);
					x = out.stack.pop();
					Double y = out.stack.pop();
					out.stack.push(x + y);

				} catch (NoSuchElementException e) {
					out.stack.push(x);
					System.out.println(" operator + (position :  " + position+ ")  : insufficient parameters " );
					displayStack(out.prevState);
					throw new NoSuchElementException();
				}

				return out;
			}
		});
	}

	/**
	 * Method to display Current Stack elements.
	 */
	public void displayStack(State currentState) {
		System.out.print("Stack : ");
		for (Iterator<Double> itr = currentState.stack.descendingIterator(); itr
				.hasNext();) {
			Double dispVal = itr.next();
			System.out.print(dispVal + " ");
		}
		System.out.println();
	}

	/**
	 * @param inputCmd
	 * @return Command based on input paramter if Numeric value PushCommand, 
	 * input is operator then corresponding command is returned, if not designated value then exception
	 * @throws Exception
	 */
	private Command parseCommand(String inputCmd) throws Exception {
		Command cmd = cmdMap.get(inputCmd);

		if (cmd != null)
			return cmd;
		else if (null != inputCmd && isNumeric(inputCmd)) {
			return new PushCommand(Double.parseDouble(inputCmd));
		} else {
			throw new NumberFormatException();
		}

	}

	/**
	 * Method to determine inputValue is numeric or not
	 * @param val
	 * @return
	 */
	private static boolean isNumeric(String val) {
		String NUMERIC_PATTERN = "^[0-9]*$";
		Pattern pattern = Pattern.compile(NUMERIC_PATTERN);
		Matcher matcher = pattern.matcher(val);
		return matcher.matches();
	}

	public Command parseInput(String inputString) throws Exception {
		List<Command> inputCmdList = new LinkedList<Command>();

		for (String subCmdStr : inputString.split("\\s+"))
			inputCmdList.add(parseCommand(subCmdStr));

		return new RunCommand(inputCmdList);
	}

	/**
	 * Method verify whether stack is empty or not
	 * @param stack
	 */
	protected void isStackEmpty(Deque<Double> stack) {
		if (stack.isEmpty())
			throw new EmptyStackException();
	}

	/**
	 * Main method - Read input from console
	 * Prints current stack ,parses the input string and executes the respective Command based on input
	 * @throws Exception
	 */
	public void main() throws Exception {
		
		State state = new State();
		try {
			while (state != null) {
				System.out.println();
				displayStack(state);
				position = 0;
				System.out.print("Enter your Input :: ");
				
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(System.in));
				String inputLine = reader.readLine().trim();

				if (inputLine == null || inputLine.isEmpty() || inputLine.equalsIgnoreCase("  "))
					break;

				Command cmd = parseInput(inputLine);

				state = cmd.execute(state);
				
			}
		} catch (NumberFormatException numexe) {
			System.out.println(" ::: Invalid input passed :: ");
		} catch (NoSuchElementException numexe) {
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
