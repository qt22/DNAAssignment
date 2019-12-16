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
		dna.loadfile("src//DATA.txt");
		
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
		char first = genename.charAt(0);
		char last = genename.charAt(genename.length()-1);
		genename = Character.toString(last) + 
				genename.substring(1, genename.length()-1) +
				Character.toString(first);
		return genename;
	}
	
	public ArrayList<String> rule2(Node<String> Genename) {
		String genename = Genename.getValue();
		ArrayList<String> genes = new ArrayList<String>();
		
		for(int i = 0; i < genename.length()-1; i++) {
			
			if(genename.charAt(i) == genename.charAt(i+1) 
					&& genename.length() < maxlength+2)
			{
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
		ArrayList<String> genes = new ArrayList<String>();
		
		for(int i = 0; i < genename.length()-1; i++) {
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
		q.enqueue(initGene); // the initial gene(also the root gene)
		
		int result_index = 0;
		for(int i = 0; i < numValidGenes; i++) {
			if(resultGene.getValue().equals(ValidGenes[i]))
				result_index = i;
		}
		
		boolean ifsearched = false;
		
		while(!q.isEmpty()) {
			ifsearched = true;
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
			
			Node<String> rule1node = new MyNode<String>(rule1(q.peek()));
			for(int i = 0; i < numValidGenes; i++) {
				if(rule1node.getValue().equals(ValidGenes[i])) {
					if(ifvisited[i]) {
						break;
					}
					steps[i] = steps[root_index] + 1;
					if(steps[i] > maxstep) {
						break;
					}
					ifvisited[i] = true;
					probability[i] = probability[root_index]*rule1Prob;
					temp.add(rule1node);
					// same thing with rule2 and rule3
				}
			}
			
			Node<String> rule2node = q.peek();
			int size2 = rule2(rule2node).size();
			for(int i = 0; i < size2; i++) {
				rule2node = new MyNode<String>(rule2(q.peek()).get(i));
				for(int j = 0; j < numValidGenes; j++) { 
					if(rule2node.getValue().equals(ValidGenes[j])) {
						if(ifvisited[j]) {
							break;
						}
						steps[j] = steps[root_index] + 1;
						if(steps[j] > maxstep) {
							break;
						}
						ifvisited[j] = true;
						probability[j] = probability[root_index]*rule2Prob;
						temp.add(rule2node);
					}
				}
			}
			
			Node<String> rule3node = q.peek();
			int size3 = rule3(rule3node).size();
			for(int i = 0; i < size3; i++) { 
				rule3node = new MyNode<String>(rule3(q.peek()).get(i));
				for(int j = 0; j < numValidGenes; j++) {
					if(rule3node.getValue().equals(ValidGenes[j])) {
						if(ifvisited[j]) {
							break;
						}
						steps[j] = steps[root_index] + 1;
						if(steps[j] >  maxstep) {
							break;
						}
						ifvisited[j] = true;
						probability[j] = probability[root_index]*rule3Prob;
						temp.add(rule3node);
					}
				}
			} 
			
			for(int i = 0; i < temp.size(); i++) {
				q.enqueue(temp.get(i));
			}
			
			
			if(ifvisited[result_index]) {
				ifsearched = false;
				System.out.println("YES");
				System.out.println(probability[result_index]);
				break;
			}
			q.dequeue();
		}
		if(ifsearched) {
			System.out.println("NO");
		}
	}
}