import java.security.AccessControlContext;
import java.util.Iterator;
import java.util.concurrent.TimeoutException;

import javax.naming.event.NamespaceChangeListener;

import org.sat4j.reader.GroupedCNFReader;

public class Assignment2 {

	   public static void main(String[] args) {
		   
	   }

	/*-----------------------
	 *| Part A - tasks 1-11 |
	 * ----------------------*/

	// task 1
	public static boolean isSquareMatrix(boolean[][] matrix){
		boolean issquare=true; //the length is equal
		if(matrix != null)	{
			if(matrix.length==0)
				return false;
			else {
				for (int i = 0; i < matrix.length & issquare; i++) //check if every array in matrix have the same length as matrix
					if(matrix[i]==null || matrix[i].length!=matrix.length)
						issquare=false;

				return issquare;
			}
		}
		else 
			return false;
	}


	// task 2
	//assumes 'matrix' is matrix input
	public static boolean isSymmetricMatrix(boolean[][] matrix) {
		boolean issymetric=true;
		int m=0; //reset j index
		for (int i = 0; i < matrix.length; i++) {
			for (int j=m ; issymetric & i<j & j< matrix[i].length; j++) { //if we have the same values: matrix[i][j]=matrix[j][i]
				if(matrix[i][j]!=matrix[j][i])
					issymetric=false;
			}
			m++; //promote to the next corner of the square;
		}
		return issymetric;
	}

	// task 3
	//assumes 'matrix' is matrix anti reflexive input
	public static boolean isAntiReflexiveMatrix(boolean[][] matrix)	{
		boolean isanti=true;
		for (int i = 0; isanti & i < matrix.length; i++){ //checks if all the values in the i==i are false
			if(matrix[i][i]!=false)
				isanti=false;
		}	
		return isanti;
	}

	// task 4
	public static boolean isLegalInstance(boolean[][] matrix) {
		if(isSquareMatrix(matrix) && isSymmetricMatrix(matrix) && isAntiReflexiveMatrix(matrix))
			return true;

		return false;
	}

	// task 5
	//assumes array isn't null
	public static boolean isPermutation(int[] array){
		boolean isperm=true, found=false;	
		for (int i = 0 ; isperm & i < array.length; i++) {
			for (int j=0 ; !found & j<array.length ; j++) {
				if(i==array[j]) //checks if every i have j exactly one time
					found=true;
			}
			if (!found)
				isperm=false;
		}
		return isperm;
	}

	// task 6
	//assumes flight is legal input, tour length is n and it's values range is[0,n-1]
	public static boolean hasLegalSteps(boolean[][] flights, int[] tour) {
		boolean legal=true;
		for (int i = 0 ; legal & i < tour.length-1; i++) {
			if(flights[tour[i]][tour[i+1]]!=true) //if every following cities have flight connection
				legal=false;

		}
		if(flights[tour[tour.length-1]][tour[0]]!=true) //if there is connection between the last city and the first
			legal=false;

		return legal;
	}

	// task 7
	//assumes flight is legal input on n>0 cities
	public static boolean isSolution(boolean[][] flights, int[] tour) {
		if(tour==null || !isPermutation(tour)) //checks the first condition of definition 2
			throw new IllegalArgumentException();

		if(tour[0]!=0 || !hasLegalSteps(flights, tour)) //checks the second and the third condition of definition 2
			return false;

		return true;
	}

	// task 8
	//assumes cnf and assign are legal input
	public static boolean evaluate(int[][] cnf, boolean[] assign) {
		boolean istrue=true;
		for (int i = 0; i < cnf.length & istrue; i++) {
			istrue=false;
			for (int j = 0; j < cnf[i].length & !istrue; j++){ //check in every clause if there is false statement
				int value=cnf[i][j];
				if (value>0) { 
					if(assign[value])
						istrue=true;}
				else
					if(!assign[value*(-1)])
						istrue=true;
			}
			if(!istrue) //if there wasn't at least one true-return false
				return false;
		}
		return istrue;
	}

	// task 9
	//assumes 'lits' isn't null, his length is bigger than 0 and contains natural numbers different from 0
	public static int[][] atLeastOne(int[] lits) {
		int [][] cnf=new int [1][lits.length];
		for (int i = 0; i < lits.length; i++) //build cnf by the literals, all in one clause- there is or between theme
			cnf[0][i]=lits[i];

		return cnf;
	}

	// task 10
	//assumes 'lits' isn't null, his length is bigger than 0 and contains natural numbers different from 0
	public static int[][] atMostOne(int[] lits)	{
		int numclauses=0; // how much clauses is going to be;
		int clausenumber=0 ; // marks the clause number;
		for(int i=0 ; i<lits.length ;i++) //calculates the amount of the conditions, every literal have the option to go with the otherss on time
			numclauses=numclauses+i;
		int [][] cnf=new int [numclauses][2]; //[the number of the conditions][compare 2 literals]

		for (int i = 0; i < lits.length-1; i++) 
			for (int j = 0; j < lits.length-1-i; j++) {//enter every clause to have options of 2 'not' literals 
				cnf[clausenumber][0]=lits[i]*(-1);
				cnf[clausenumber][1]=lits[j+i+1]*(-1);
				clausenumber++; 
			}

		return cnf;
	}

	// task 11
	//assumes 'lits' isn't null, his length is bigger than 0 and contains natural numbers different from 0
	public static int[][] exactlyOne(int[] lits) {
		int [][] cnForAtMostOne= atMostOne(lits);
		int [] [] cnf= new int [cnForAtMostOne.length+1][lits.length];

		for (int i = 0; i < cnForAtMostOne.length; i++) //copy the conditions from the AtMostOne cnf
			cnf[i]=cnForAtMostOne[i];

		for (int i = 0; i < lits.length; i++) 
			cnf[cnf.length-1][i]=(atLeastOne(lits))[0][i];

		return cnf;
	}

	/*------------------------
	 *| Part B - tasks 12-20 |
	 * -----------------------*/

	// task 12a
	//assumes 0<=-i,j<n & n>0
	public static int map(int i, int j, int n) {
		return (1+n*i +j);
	}

	// task 12b
	//returns (i,j)
	//assumes 1<-k<=n*n & n>0
	public static int[] reverseMap(int k, int n) {
		int [] place= new int[2];
		for (int i = 0; i < n ; i++) 
			for (int j = 0; j < n; j++) 
				if (k== (1+n*i+j)) {
					place[0]= i;
					place[1]=j;
				}
		return place;
	}
	//assisting function- task 13
	//calculate the number of the clauses of the function ExactlyOne
	public static int NumConditionExactOne (int numlits){
		int numcondition=0;
		int [] lits= new int [numlits];
		for (int i = 0; i < numlits; i++) { //creats array of 0-numlits literals
			lits[i]=i;
		}
		numcondition=exactlyOne(lits).length; //gets the number of conditions that exactlyOne do on the array
		return numcondition;
	}

	// task 13
	//assumes n>0
	public static int[][] oneCityInEachStep(int n){
		int numconditionsClause=NumConditionExactOne(n);
		int [][] cnf=new int[(numconditionsClause*n)][]; //the cnf has n times every clause
		int clausenumber=0;
		for (int x = 0; x < n; x++) { // creates clauses for every different i
			int[] clause=new int[n];
			for (int y = 1; y <= n; y++)  // creates clause of every match i (different j)
				clause[y-1]=reverseMap(y+n*x,n)[1]+n*x+1; //k=y+n*x, we add x*n+1 to that every clause will be one every other n Xk

			for (int p=0 ; p < numconditionsClause ; p++) {
				cnf[clausenumber]=exactlyOne(clause)[p];
				clausenumber++;
			}
		}
		return cnf; 
	}

	// task 14
	//assumes n>0
	public static int[][] eachCityIsVisitedOnce(int n) {
		int numconditionsClause=NumConditionExactOne(n);
		int [][] cnf=new int[(numconditionsClause*n)][]; //the cnf has n times every clause
		int clausenumber=0;
		for (int x = 0; x < n; x++) { // creates clauses for every different j
			int[] clause=new int[n];
			for (int y = 1; y <= n; y++) // creates clause of every match j (different i)
				clause[y-1]=reverseMap(y+n*x,n)[0]+(y-1)*n+1; //k=y+n*x, we add (y-1)*n+1 to that every clause will be one every other n Xk

			for (int p=0 ; p < numconditionsClause ; p++) {
				cnf[clausenumber]=exactlyOne(clause)[p];
				clausenumber++;
			}
		}
		return cnf; 	
	}

	// task 15
	//assumes n>0
	public static int[][] fixSourceCity(int n) {
		int [][]cnf= {{map(0, 0, n)}}; //add k=1 (i=0,j=0) to the cnf
		return cnf;

	}

	//assisting function- task 16
	//assumes flights is not null
	//return array of the false relevant values
	public static int HowMuchFalseValues (boolean [][] flights){
		int counter=0; //count the false values without the ones that k==j (the same city)
		for (int k = 0; k < flights.length; k++) 
			for (int j = 0; j < flights.length; j++) 
				if(k!=j & !flights[k][j])
					counter++;
		return counter;			
	}

	//assisting function- task 16
	//assumes flights is not null
	//return array of the places in flights that have false values [0]=k,[1]=j
	public static int [][] whichPlaceFalse(boolean [][] flights) {
		int falsevalue=0;
		int [][] FalsesPlaces=new int [HowMuchFalseValues(flights)][2];
		for (int k = 0; k < flights.length; k++) 
			for (int j=0 ; j< flights.length ; j++)
				if(!flights[k][j] && k!=j) {
					FalsesPlaces[falsevalue][0]=k;
					FalsesPlaces[falsevalue][1]=j;
					falsevalue++;
				}
		return FalsesPlaces;
	}

	// task 16
	//assumes flights is legal input by definition 1 and his length is bigger than 0
	public static int[][] noIllegalSteps(boolean[][] flights) {
		int conditioNumber=0, n=flights.length, cnfcondotionumber=0;;
		int [][] falsePlaces= whichPlaceFalse(flights);
		int [][]cnf=new int [falsePlaces.length*n][2]; //[for every stage*for every falsePlace][2 options k|j]
		for(int x=0 ; x<falsePlaces.length; x++ , conditioNumber++)
			for (int i=0 ; i<n; i++ ,cnfcondotionumber++) {
				int [] clause=new int [2];
				clause[0]=map(i, falsePlaces[conditioNumber][0], n);
				clause[1]=map((i+1)%n, falsePlaces[conditioNumber][1], n);
				cnf[cnfcondotionumber]=exactlyOne(clause)[0];
			}
		return cnf;
	}


	// task 17
	//assumes flights is legal input by definition 1
	public static int[][] encode(boolean[][] flights) {
		int n=flights.length;
		int t13l=oneCityInEachStep(n).length;
		int t14l=eachCityIsVisitedOnce(n).length;
		int t15l=(fixSourceCity(n)).length;
		int t16l=(noIllegalSteps(flights)).length;
		int numcondition=0; //points on the index of the condition in the cnf
		int [][]cnf=new int[t13l+t14l+t15l+t16l][];
		for (int i = 0; i < t13l; i++,numcondition++) 
			cnf[numcondition]=oneCityInEachStep(n)[i];

		for (int i = 0; i < t14l; i++,numcondition++) 
			cnf[numcondition]=eachCityIsVisitedOnce(n)[i];

		for (int i = 0; i < t15l; i++,numcondition++) 
			cnf[numcondition]=fixSourceCity(n)[i];

		for (int i = 0; i < t16l; i++,numcondition++) 
			cnf[numcondition]=(noIllegalSteps(flights))[i];

		return cnf;
	}

	// task 18
	//assumes n>0
	public static int[] decode(boolean[] assignment, int n) {
		if(assignment==null || assignment.length!=n*n+1)
			throw new IllegalArgumentException();
		int [] tour=new int [n];
		int pointerassigmentplace=0; //represent the place we check in the assignment
		for (int i = 0; i < n; i++) 
			for (int j = 0; j < n; j++, pointerassigmentplace++) 
				if(assignment[pointerassigmentplace+1])
					tour[i]=j;
		return tour;
	}

	// task19
	public static int[] solve(boolean[][] flights) {
		if(!isLegalInstance(flights))
			throw new IllegalArgumentException("flight represents incorrect value");
		SATSolver.init(flights.length*flights.length); 
		SATSolver.addClauses(encode(flights));
		boolean [] assignment=SATSolver.getSolution(); //Assignment gets the boolean array solution from sat
		int [] tour= decode(assignment, flights.length); //tour gets the array of the movements between the cities
		if(!isSolution(flights, tour))
			throw new IllegalArgumentException("solution is illigal");
		else if(assignment.length==0) //if there is no assignment 
			throw new IllegalArgumentException("there is no solution");
		return tour; //can be null or full
	}

	// task20
	public static boolean solve2(boolean[][] flights) {
		boolean istheretwosolve = true;
		int [] solve1=solve(flights);
		SATSolver.init(flights.length*flights.length); 
		SATSolver.addClauses(encode(flights));
		int [] clauseNothesametour= new int [flights.length-1];
		for (int i = 1; i < flights.length; i++) //enter clause of not the same tour
			clauseNothesametour[i-1]=(-1)*(map(i, solve1[i], flights.length));
		SATSolver.addClause(clauseNothesametour);
		int [] clauseNotheopossitetour= new int [flights.length-1];
		for (int i = 1; i < flights.length; i++) 
			clauseNotheopossitetour[i-1]=(-1)*(map(flights.length-i, solve1[i], flights.length));//enter clause of the opposite tour
		SATSolver.addClause(clauseNotheopossitetour);
		boolean [] assignment2=SATSolver.getSolution(); //Assignment gets the boolean array solution from sat
		if(assignment2==null)
			return false;
		else if(assignment2.length==0) //if there is no assignment 
			istheretwosolve=false;

		return istheretwosolve;
	}

}
