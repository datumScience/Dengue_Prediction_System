package tagging;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;


public class SymptomExtractor {
	public ArrayList<String> rejectSymptoms(String path) throws FileNotFoundException,IOException{
		
		/* Read a file, split into words */
		
		 //String path = "F:/0.college/UG project/Second_Review/input_output/Dischargesummary/d1.txt";
		 //String path = "C:/Users/Pinky/Downloads/d_1.txt";
		 FileInputStream fstream = new FileInputStream(path);
		 DataInputStream in = new DataInputStream(fstream);
		 BufferedReader br = new BufferedReader(new InputStreamReader(in));
		 
		 String text = "";
		 String line = br.readLine();
		 while(line!=null){
			 text = text + line;
			 line = br.readLine();
		 }
		 //System.out.print(text);
		
		 String []sentences = text.split("\\.");
        
//         System.out.print(text.length());
//		 for (String sentence : sentences){
//			 System.out.println(sentence);
//		 }
		 
	
		 br.close();
		 
	
		 ArrayList<String> sentenceList = new ArrayList<String>(Arrays.asList(sentences));
		 ArrayList<String> rejectList = new ArrayList<String> ();
		 
		// System.out.println("LENGTH : "+sentenceList.size());
		 
		 
		 for (String sentence : sentenceList){
			 
			    fstream = new FileInputStream("F:/0.college/UG project/Second_Review/input_output/umls.txt");
				DataInputStream in2 = new DataInputStream(fstream);
				BufferedReader br2 = new BufferedReader(new InputStreamReader(in2));
				String symptomFromUmls = br2.readLine();
				while(symptomFromUmls !=null)
				{
					sentence = sentence.toLowerCase();
					int noPos = sentence.indexOf("no ");
					int deniesPos = sentence.indexOf("denies ");
					
					int symptomPos = sentence.indexOf(symptomFromUmls);
					if(symptomPos!=-1 &&( noPos!=-1) && (noPos< symptomPos) && symptomPos-noPos==3 ){
					//	System.out.print("no");  System.out.print(" :");  System.out.println(noPos);
						
					//	System.out.print(symptomFromUmls);  System.out.print(" :");  System.out.println(symptomPos);
						if(!rejectList.contains(symptomFromUmls)) 
							rejectList.add(symptomFromUmls);
					}
					/*else if(sentence.indexOf(symptomFromUmls)!=-1 && sentence.indexOf("denies ")!=-1 && sentence.indexOf("denies ")< sentence.indexOf(symptomFromUmls)){
					*/
					//System.out.println(symptomFromUmls);
					else if(symptomPos!=-1 &&( deniesPos!=-1) && (deniesPos< symptomPos)){	
						if(!rejectList.contains(symptomFromUmls))
							rejectList.add(symptomFromUmls);
					}
					symptomFromUmls = br2.readLine();
				}
				 br2.close();
		 }
		  
		
		 //System.out.println(path + "LENGTH : "+rejectList.size());
		// for (String word : rejectList)
			// System.out.println(path + word);
		 
		 return rejectList;
	}
}
