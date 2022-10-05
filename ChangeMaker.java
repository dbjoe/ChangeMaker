package p1;
import java.util.Scanner;
import java.io.*;
import java.math.BigDecimal;
import java.text.NumberFormat;
/**
 * A class that represents a change-maker with the ability to accept
 * a dollar amount and return the appropriate denominations of
 * coins.
 * @author Gabe Kucinich and Joe Zylla
 * @version 09/21/2022
 */
public class ChangeMaker {
	private double amount;
	private static boolean quartersAvail = true;
	private static boolean dimesAvail = true;
	private static boolean nickelsAvail = true;
	private static boolean penniesAvail = true;

	public ChangeMaker() {//Creates default ChangeMaker object
		this.amount = 0.00;
	}
	/**
	 * Creates ChangeMaker object based on a ChangeMaker parameter
	 * @param other ChangeMaker object
	 * @return none
	 * @throws  IllegalArgumentException if amount is invalid
	 */
	public ChangeMaker (ChangeMaker other) {
		setAmount(other.getAmount());  
	}
	/**
	 * Creates ChangeMaker Object with amount of double amount
	 * @param amount
	 * @return none
	 * @throws  IllegalArgumentException if amount is invalid
	 */
	public ChangeMaker (double amount) {
		setAmount(amount);
	}

	/**
	 * A method that type casts Object other to be a ChangeMaker
	 *  and returns true if other amount is the same as the current
	 *  ChangeMaker amount. Returns false if other parameter is null.
	 * @param other Object
	 * @return boolean of equality
	 */
	public boolean equals(Object other) {
		if (other == null) return false;

		if (other instanceof ChangeMaker) {
			ChangeMaker another = (ChangeMaker)other;
			return (this.amount == another.amount);
		}
		else
			return false;
	}


	/**
	 * A method that returns true if ChangeMaker parameter has the
	 *  same amount as the invoking ChangeMaker
	 * @param other ChangeMaker
	 * @return boolean of equality
	 */
	public boolean equals(ChangeMaker other) {
		return (this.amount == other.amount);
		//== works here because amount is a primitive double value
	}

	/**
	 * A static method that returns true if ChangeMaker object other1
	 *  is the same as ChangeMaker object other2.
	 * @param other1 ChangeMaker
	 * @param other2 ChangeMaker
	 * @return boolean of equality
	 */
	public static boolean equals(ChangeMaker other1,
			ChangeMaker other2) {
		return (other1.equals(other2));
	}

	/**
	 * A method that returns:
	 *  1 if “this” ChangeMaker object amount is
	 *  greater than the other ChangeMaker object amount,
	 * -1 if the “this” ChangeMaker object amount is less than the
	 *  other ChangeMaker amount,
	 * and 0 if the ChangeMaker object amounts are the same.
	 * @param other
	 * @return val stores the comparison value of 0, 1, or -1
	 */
	public int compareTo(ChangeMaker other) {
		int val;
		if(this.amount > other.getAmount())
			val = 1;
		else if(this.amount < other.getAmount())
			val = -1;
		else
			val = 0;
		return val;
	}

	/**
	 * A method that returns:
	 * 1 if  ChangeMaker other1 amount is greater than ChangeMaker
	 *  other2 amount,
	 * -1 if the ChangeMaker other1 amount is less than ChangeMaker
	 *  other2 amount,
	 * 0 if the ChangeMaker amounts are the same.
	 * @param other1
	 * @param other2
	 * @return val stores the comparison value of 0, 1, or -1
	 */
	public static int compareTo(ChangeMaker other1,
			ChangeMaker other2) {
		int val;
		if(other1.getAmount() > other2.getAmount())
			val = 1;
		else if(other1.getAmount() < other2.getAmount())
			val = -1;
		else
			val = 0;

		return val;
	}

	/**
	 * A method that adds a double amount to the current amount
	 * @param amount
	 * @return none
	 * @throws IllegalArgumentException from isValid method if amount
	 *                                  is not valid
	 */
	public void loadMachine (double amount) {
		checkValidity(amount);
		this.amount += amount;
	}

	/**
	 * A method that subtracts parameter otherAmount from the amount in
	 * ChangeMaker and returns a ChangeBag with the number of coins
	 * of each denomination used to make change.
	 * @param otherAmount the amount being withdrawn
	 * @return ChangeBag  object with number of quarters, dimes,
	 *                    nickels, and pennies equal to amount.
	 * @throws IllegalArgumentException  if the amount withdrawn is
	 *                                   > balance, or if there are
	 *                                   insufficient coins    
	 */
	public ChangeBag takeOut (double otherAmount) {
		checkValidity(otherAmount);
		if (otherAmount > this.amount) {
			throw new IllegalArgumentException(
					"Amount withdrawn cannot exceed current balance");
		}
		/*Multiply by 100 and cast to long to prevent
		 *floating point arithmetic errors*/
		long leftOver = (long) (100 * otherAmount);
		long numQuarters = 0;
		long numDimes = 0;
		long numNickels = 0;
		long numPennies = 0;

		/*If quarters are not suspended, we calculate the number of
		 * quarters that divide evenly into the dollar amount.
		 * Then, we subtract the dollar amount of that number of 
		 * quarters.
		 * */
		if(quartersAvail) {
			numQuarters = (long) Math.floor(leftOver / 25);
			leftOver = leftOver - (numQuarters * 25);
		}
		/*After finding the number of quarters and subtracting
		 * the dollar value of those quarters, we find the number of
		 * dimes in the same manner.*/
		if(dimesAvail) {
			numDimes = (long) Math.floor(leftOver / 10);
			leftOver = leftOver - (numDimes * 10);
		}

		if(nickelsAvail) {
			numNickels = (long) Math.floor(leftOver / 5);
			leftOver = leftOver - (numNickels * 5);    
		}

		if(penniesAvail) {
			numPennies = (long) Math.floor(leftOver / 1);
			leftOver = leftOver - (numPennies * 1);
		}

		/*If the amount leftOver is not zero, that means our available
		 * denominations could not add up exactly to the dollar value
		 * passed in as a parameter.*/
		if (leftOver != 0)
			throw new IllegalArgumentException("Error: Insufficient"
					+ " denominations to withdraw entered amount.");
		else {
			ChangeBag cb = new ChangeBag(numQuarters, numDimes,
					numNickels, numPennies);
			this.amount -= otherAmount; //TODO: here's our problem statement.
			//This will not be perfectly accurate because they're floating points  
			//this.amount = (100*this.amount - 100*otherAmount) / 100;
			return cb;
		}
	}

	/**
	 * toString method that formats a String such as the format:
	 * ChangeMaker{amount=$5.75}.
	 * @return A String of the form ChangeMaker{amount=$5.75}.
	 */
	public String toString() {
		NumberFormat formatter = NumberFormat.getCurrencyInstance();
		String moneyString = formatter.format(getAmount());
		return "ChangeMaker{amount=" + moneyString + "}.";
	}

	/**
	 * gets the amount from ChangeMaker object
	 * @return double amount of ChangeMaker
	 * @return this.amount  the amount of the invoking object
	 */
	public double getAmount() {
		return this.amount;
	}

	/**
	 * Method to set ChangeMaker amount to new amount
	 * Calls isValid(), which throws IllegalArgumentException if:
	 *     amount is negative
	 *     amount is greater than 1.0e15
	 *     amount has places past the hundredths
	 * @param double amount
	 * @return none
	 * @throws IllegalArgumentException if amount parameter not valid
	 */
	public void setAmount(double amount) {
		checkValidity(amount);
		this.amount = amount;
	}

	/**
	 * Returns current availability of quarters
	 * @return boolean true if quarters are available, false otherwise
	 */
	public static boolean getQuartersAvail() {
		return quartersAvail;
	}
	/**
	 * Returns current availability of dimes
	 * @return boolean true if dimes are available, false otherwise
	 */
	public static boolean getDimesAvail() {
		return dimesAvail;
	}
	/**
	 * Returns current availability of nickels
	 * @return boolean true if nickels are available, false otherwise
	 */
	public static boolean getNickelsAvail() {
		return nickelsAvail;
	}
	/**
	 * Returns current availability of pennies
	 * @return boolean true if pennies are available, false otherwise
	 */
	public static boolean getPenniesAvail() {
		return penniesAvail;
	}

	/**
	 * sets the quarters availability to either true or false
	 * @param avail a boolean
	 * @return none
	 */
	public static void setQuartersAvail(boolean avail) {
		quartersAvail = avail;
	}
	/**
	 * sets the dimes availability to either true or false
	 * @param avail a boolean
	 * @return none
	 */
	public static void setDimesAvail(boolean avail) {
		dimesAvail = avail;
	}
	/**
	 * sets the nickels availability to either true or false
	 * @param avail a boolean
	 * @return none
	 */
	public static void setNickelsAvail(boolean avail) {
		nickelsAvail = avail;
	}
	/**
	 * sets the pennies availability to either true or false
	 * @param avail a boolean
	 * @return none
	 */
	public static void setPenniesAvail(boolean avail) {
		penniesAvail = avail;
	}

	/**
	 * A method that saves the “this” ChangeMaker to a file.
	 * If the save operation cannot be completed correctly,
	 *  throw an IllegalArgumentException
	 * @param String fileName
	 * @return none
	 * @throws IllegalArgumentException  if save cannot be completed
	 */
	public void save (String fileName) {
		PrintWriter out = null;
		try {
			out = new PrintWriter(new BufferedWriter(new FileWriter(
					fileName)));
		} catch (IOException e) {
			throw new IllegalArgumentException("Unable to save file."
					+ " Please enter a valid file name.");
		}
		out.println(this.amount);
		out.close();
	}

	/**
	 * A method that loads a file from the parameter filename
	 * @param fileName chosen from JFileChooser in ChangeMakerPanel
	 * @return none
	 * @throws IllegalArgumentException  if file is unable to be read
	 */
	public void load (String fileName) {
		Scanner scanner = null;
		try {
			scanner = new Scanner(new File(fileName));
			setAmount(scanner.nextDouble());
		} catch (Exception e) {
			throw new IllegalArgumentException("Unable to read file."
					+ " Please select a valid text file containing a"
					+ " valid number.");
		}
	}

	/**
	 * Checks if passed value is negative, greater than 1.0E15, or
	 * has more than 2 decimal places.    
	 * @param amount double amount to test for validity
	 * @return boolean of validity
	 * @throws IllegalArgumentException if parameter is negative,
	 *                                  >1.0E15, or has >2 decimals
	 */
	private void checkValidity(double amount) {
		if (amount < 0.0)
			throw new IllegalArgumentException("Negative numbers"
					+ " are not allowed");
		if (this.amount + amount >= 1.0e15) //TODO: this creates a separate problem where
			//once you hit the limit, you can't take out 1.0e14 either.
			throw new IllegalArgumentException("Amount cannot exceed"
					+ " 1.0e15. Enter valid value.");
		/*https://stackoverflow.com/questions/32531910/how-do-i-check-
		 * whether-a-double-variable-has-more-than-two-decimals-in
		 * -java*/
		if (BigDecimal.valueOf(amount).scale() > 2)
			throw new IllegalArgumentException("Cannot have fractions"
					+ " of a cent");
	}
}


