import java.util.Scanner;
import java.math.BigInteger;

/*
 * Notes: 	The inputs are of form x = a (mod n)
 * 			M = n1n2...nk where k is the number of inputs
 * 			x = a1M1y1 + a2M2y2 + ... + akMkyk (mod M)
 * 			Mi = M/ni
 * 			Miyi = 1 (mod ni)
 * 
 * 			Also, I have not worked much with Big Integer so this program 
 * 			does not use Big Integers, but the functionality will need to be
 * 			added prior to submission.  Feel free to alter the code to work
 * 			with Big Integers while I am learning about them.
 * 
 * 			I am having trouble calculating the y's because you have to solve
 * 			for the inverse.  I believe that the Extended Euclidean Algorithm
 * 			is used to solve this.
 * 
 */
public class Congruent {

	public static void main(String[] args) {
		int cases = 0;
		Scanner input = new Scanner(System.in);
		
		//Getting user input
		System.out.println("Enter number of congruencies you want to solve: ");
		cases = input.nextInt();
		
		BigInteger[] numbers = new BigInteger[cases];
		BigInteger[] mods = new BigInteger[cases];
		
		for(int i = 0; i < cases; i++)
		{
			System.out.println("Enter a number: ");
			numbers[i] = BigInteger.valueOf(input.nextInt());
			
			System.out.println("Enter the modulo: ");
			mods[i] = BigInteger.valueOf(input.nextInt());
		}
		input.close();
		
		//output of method here
		System.out.println("X = " + calculateX(numbers, mods));
	}

	//Calculating X where X = a1M1y1 + a2M2y2 + ... + akMkyk (mod M)
	public static BigInteger calculateX(BigInteger[] numbers, BigInteger[] mods)
	{
		//variables
		int length = numbers.length;
		BigInteger M = BigInteger.valueOf(0);
		BigInteger sum = BigInteger.valueOf(0);
		BigInteger[] Mis = new BigInteger[length];
		BigInteger[] yis = new BigInteger[length];
		
		M = calculateM(length, mods);
		
		Mis = calculateMis(length, M, mods);
		
		yis = calculateYis(length, Mis, mods);

		sum = calculateSum(length, numbers, Mis, yis);
		
		//Returning X
		return sum.mod(M);
	}
	
	//Calculating M where M = n1n2...nk
	public static BigInteger calculateM(int length, BigInteger[] mods)
	{
		BigInteger M = BigInteger.valueOf(1);
		
		for(int i = 0; i < length; i++)
		{
			M = M.multiply(mods[i]);
		}
		return M;
	}
	
	//Calculating Mi where Mi = M/ni
	public static BigInteger[] calculateMis(int length, BigInteger M, BigInteger[] mods)
	{
		BigInteger[] Mis = new BigInteger[length];
		
		for(int i = 0; i <length; i++)
		{
			Mis[i] = M.divide(mods[i]);
		}
		return Mis;
	}
	
	//Calculating yi where Miyi = 1 (mod ni)  (congruent)
	//Not complete waiting for Extended Euclidean Algorithm to be completed
	public static BigInteger[] calculateYis(int length, BigInteger[] Mis, BigInteger[] mods)
	{
		BigInteger[] yis = new BigInteger[length];
		int temp = 0;
		
		//I believe this will be used after the gcd is calculated
		for(int i = 0; i < length; i++)
		{
			for(int j = 0; j < mods[i].intValue(); j++)
			{
				if((Mis[i].multiply(BigInteger.valueOf(j)).mod(mods[i])).compareTo(BigInteger.ONE) == 0)
				{
					temp = j;
					j = mods[i].intValue();
				}
			}
			yis[i] = BigInteger.valueOf(temp);
		}
		return yis;
	}
	
	//Calculating the sum of aiMiyi for 0 <= i <= k
	public static BigInteger calculateSum(int length, BigInteger[] numbers, BigInteger[] Mis, BigInteger[] yis)
	{
		BigInteger sum = BigInteger.valueOf(0);
		
		for(int i = 0; i < length; i++)
		{
			sum = sum.add(numbers[i].multiply(Mis[i].multiply(yis[i])));
		}
		return sum;
	}
}
