/*
 * Jerry Tian
 * ICS4U1
 * Dec.15, 2019
 * For Mr.Radulovic
 * ADT assignment
 * 
 *  This class mutates the given a gene with 3 rules,
 *  load the data and store it from a given file and 
 *  uses the breadth first search to search for the target gene
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class DNA {
	
	 int maxlength, numValidGenes, maxstep, numGenes;
	 String[] ValidGenes, initialGenes, mutatedGenes;
	 boolean[] ifvisited;
	 int[] steps;
	 double[] probability;
	 private double rule1Prob = 0.02, rule2Prob = 0.06, rule3Prob = 0.08;
	public static void main(String[] args) throws FileNotFoundException {
		DNA dna = new DNA();
		dna.loadfile("src//DATA.txt");// load the file
		
		for(int i = 0; i < dna.numGenes; i++) {
			Node<String> startGene = new MyNode<String>(dna.initialGenes[i]);
			Node<String> endGene = new MyNode<String>(dna.mutatedGenes[i]);
			dna.BFS(startGene, endGene);
		}
		
		}
	
	
	public void loadfile(String filepath) throws FileNotFoundException {
		File file = new File(filepath);
		Scanner scan = new Scanner(file);
		maxlength = Integer.parseInt(scan.nextLine());
		numValidGenes = Integer.parseInt(scan.nextLine())
				+ Integer.parseInt(scan.nextLine());
		ValidGenes = new String[numValidGenes];
		
		for(int i = 0; i < numValidGenes; i++) {
			ValidGenes[i] = scan.nextLine();
		} 
		
		maxstep = Integer.parseInt(scan.nextLine());
		numGenes = Integer.parseInt(scan.nextLine());
		initialGenes = new String[numGenes];
		mutatedGenes = new String[numGenes];
		
		for(int i = 0; i < numGenes; i++) {
			String[] line = scan.nextLine().split(" ");
			initialGenes[i] = line[0];
			mutatedGenes[i] = line[1];
		}
		scan.close();
		
		
		ifvisited = new boolean[numValidGenes]; 
		// an array to check if the node is visited
		for(int i = 0; i < numValidGenes; i++) {
			ifvisited[i] = false;
			// initialize every index to unvisited
		}
		
		steps = new int[numValidGenes];
		// an array to count the number of steps;
		probability = new double[numValidGenes];
		// an array to store the probability
		for(int i = 0; i < numValidGenes; i++) {
			probability[i] = 1.0;
		}
		
	}

	public String rule1(Node<String> Genename) {
		String genename = Genename.getValue();
		// convert from node to string
		char first = genename.charAt(0);
		char last = genename.charAt(genename.length()-1);
		// swap the last and first character of the string
		genename = Character.toString(last) + 
				genename.substring(1, genename.length()-1) +
				Character.toString(first);
		return genename;
	}
	
	public ArrayList<String> rule2(Node<String> Genename) {
		String genename = Genename.getValue();
		// convert from node to string
		
		ArrayList<String> genes = new ArrayList<String>();
		// an arraylist that stores the mutated genes by using rule2
		
		for(int i = 0; i < genename.length()-1; i++) {
			// check each index if the following index has the same letter 
			if(genename.charAt(i) == genename.charAt(i+1) 
					&& genename.length() < maxlength+2)
			{
				// if they have the same letter, replace them with one letter
				genes.add(genename.substring(0, i) + "A" + 
				genename.substring(i+2));
				
				genes.add(genename.substring(0, i) + "G" + 
			    genename.substring(i+2));
				
				genes.add(genename.substring(0, i) + "C" + 
				genename.substring(i+2));
				
				genes.add(genename.substring(0, i) + "T" + 
				genename.substring(i+2)) ;
			}
		}
		return genes;
	}
	
	public ArrayList<String> rule3(Node<String> Genename) {
		String genename = Genename.getValue();
		// convert from node to string
		ArrayList<String> genes = new ArrayList<String>();
		// an arraylist stores the mutated genes by using rule3
		for(int i = 0; i < genename.length()-1; i++) {
			// if T is followed by G or G followed by T, add another random gene in the middle
			if((genename.charAt(i) == 'G' && genename.charAt(i+1) == 'T')
				|| (genename.charAt(i) == 'T' && genename.charAt(i+1) == 'G')){
				String newgene = genename.substring(0, i+1) + "A" + 
						genename.substring(i+1, genename.length());
				
				if(newgene.length() <= maxlength) {
					genes.add(newgene);
				}
				
				newgene = genename.substring(0, i+1) + "G" + 
						genename.substring(i+1, genename.length());
				if(newgene.length() <= maxlength) {
					genes.add(newgene);
				}
				
				newgene = genename.substring(0, i+1) + "C" + 
						genename.substring(i+1, genename.length());
				if(newgene.length() <= maxlength) {
					genes.add(newgene);
				}
					
				newgene = genename.substring(0, i+1) + "T" + 
						genename.substring(i+1, genename.length());
				if(newgene.length() <= maxlength) {
					genes.add(newgene);
				}
				
			}
		}
		return genes;
	}
	
	public void BFS(Node<String> initGene, Node<String>resultGene) {
		
		Queue<String> q = new Queue<String>();
		// create a queue with strings
		q.enqueue(initGene); // the initial gene(also the root gene)
		
		// find the index of the target mutated gene
		int result_index = 0;
		for(int i = 0; i < numValidGenes; i++) {
			if(resultGene.getValue().equals(ValidGenes[i]))
				result_index = i;
		}
		
		// if the it goes through the bfs and got nothing, it will print NO
		boolean ifsearched = false;
		
		while(!q.isEmpty()) {
			ifsearched = true;
			
			// get the node from the queue (the one that is about to be dequeued)
			Node<String> childnode = q.peek();
			int root_index = 0;
			for(int i = 0; i < numValidGenes; i++) {
				if(childnode.getValue().equals(ValidGenes[i])){
					root_index = i;
					ifvisited[i] = true;
					// locate the root node
				}
			}
			
			ArrayList<Node<String>> temp = new ArrayList<Node<String>>();
			// an arraylist that stores the nodes so that one specific node doesnt change
			
			
			// check if rule1 gives a new gene in the valid gene list
			Node<String> rule1node = new MyNode<String>(rule1(q.peek()));
			for(int i = 0; i < numValidGenes; i++) {
				if(rule1node.getValue().equals(ValidGenes[i])) {
					if(ifvisited[i]) { // if already visited, quit the for loop
						break;
					}
					steps[i] = steps[root_index] + 1;
					if(steps[i] > maxstep) {
						break; // if the number of steps exceeds the maximum number of steps, quit the for loop
					}
					ifvisited[i] = true; // mark visited
					probability[i] = probability[root_index]*rule1Prob; // set the probability
					temp.add(rule1node);
					// same thing with rule2 and rule3
				}
			}
			
			// check if rule1 gives a new gene in the valid gene list
			Node<String> rule2node = q.peek();
			// run rule2 method 1 time to get the size so that the size doesnt change inside the for loop
			// I tried to do the size thing in the for loop parameter but the sizes changes everytime the node changes
			
			// same thing with rule3
			int size2 = rule2(rule2node).size(); 
			for(int i = 0; i < size2; i++) {
				rule2node = new MyNode<String>(rule2(q.peek()).get(i));
				for(int j = 0; j < numValidGenes; j++) { 
					if(rule2node.getValue().equals(ValidGenes[j])) {
						if(ifvisited[j]) {
							break; // see rule1 
						}
						steps[j] = steps[root_index] + 1;
						if(steps[j] > maxstep) {
							break; // see rule1
						}
						ifvisited[j] = true;
						probability[j] = probability[root_index]*rule2Prob; // see rule1
						temp.add(rule2node);
					}
				}
			}
			
			Node<String> rule3node = q.peek();
			int size3 = rule3(rule3node).size();
			// same reason, see rule2.
			for(int i = 0; i < size3; i++) { 
				rule3node = new MyNode<String>(rule3(q.peek()).get(i));
				for(int j = 0; j < numValidGenes; j++) {
					if(rule3node.getValue().equals(ValidGenes[j])) {
						if(ifvisited[j]) {
							break; // see rule1
						}
						steps[j] = steps[root_index] + 1;
						if(steps[j] >  maxstep) {
							break; // see rule1
						}
						ifvisited[j] = true;
						probability[j] = probability[root_index]*rule3Prob; // see rule1
						temp.add(rule3node);
					}
				}
			} 
			
			// add the nodes in the temporary arraylist to the queue
			for(int i = 0; i < temp.size(); i++) {
				q.enqueue(temp.get(i));
			}
			
			// if the target gene is already visited, print out its data
			if(ifvisited[result_index]) {
				ifsearched = false;
				System.out.println("YES");
				System.out.println(probability[result_index]);
				break;
			}
			q.dequeue(); // dequeue the node just used for 3 rules above
		}
		
		// if the target gene is never visited, print NO
		if(ifsearched) {
			System.out.println("NO");
		}
	}
}