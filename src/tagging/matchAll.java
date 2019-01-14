package tagging;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

import edu.stanford.nlp.io.EncodingPrintWriter.out;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class matchAll {
 
 public static void main(String[] args) throws IOException,
 ClassNotFoundException {
 
 String tagged;
 
 // Initialize the tagger
 MaxentTagger tagger = new MaxentTagger("taggers/english-left3words-distsim.tagger");
 
 FileSearcher fs = new FileSearcher();
 
 HashMap<String,Integer> wordList = new HashMap<String,Integer>();
 
 String[] paths;
 String croppedWord = null;
 String folderPath = "F:/0.college/UG project/First review/data_txt";
 File folder = new File(folderPath);
 paths = folder.list();
 
 for(String path : paths)
	 out.println(path);
 
 // The sample string
 String sample = null;
 
 /* Deleting output files if already present */
 File delFile = new File("F:/0.college/UG project/First review/input_output/filtered_output.txt");
 if(delFile.exists())
	 delFile.delete();
 
 delFile = new File("F:/0.college/UG project/First review/input_output/output.txt");
 if(delFile.exists())
	 delFile.delete();
 
 delFile = new File("F:/0.college/UG project/First review/input_output/distinct_filtered_output.txt");
 if(delFile.exists())
	 delFile.delete();
 
 delFile = new File("F:/0.college/UG project/First review/input_output/feature_vector.txt");
 if(delFile.exists())
	 delFile.delete();
 
 for(String path : paths)
 {
	 /* next we will pick up some sentences from a file input.txt and store the output of
	 tagged sentences in another file output.txt. So make a file input.txt and write down
	 some sentences separated by a new line */
	 
	 FileInputStream fstream = new FileInputStream(folderPath+"/"+path);
	 DataInputStream in = new DataInputStream(fstream);
	 BufferedReader br = new BufferedReader(new InputStreamReader(in));
	 
	 //we will now pick up sentences line by line from the file input.txt and store it in the string sample
	 sample = br.readLine();
	 while(sample!=null)
	 {
	 //tag the string
	 tagged = tagger.tagString(sample);
	 
	
	 FileWriter q = new FileWriter("F:/0.college/UG project/First review/input_output/output.txt",true);
	 BufferedWriter out =new BufferedWriter(q);
	 
	 FileWriter q2 = new FileWriter("F:/0.college/UG project/First review/input_output/filtered_output.txt",true);
	 BufferedWriter out2 =new BufferedWriter(q2);
	 
	 /* Write it to the file output.txt */
	 sample = br.readLine();
	 out.write(tagged);
	 out.newLine();
	 out.close();
	 
	 String[] posTags={"_NN","_NNP","_NNS","_NNPS"};
	 
	 for(String word : tagged.split(" "))
	 {
		 for(String pos : posTags)
		 {
			 if(word.endsWith(pos))
			 {
				 croppedWord = word.substring(0,word.length()-pos.length());
				 croppedWord = croppedWord.toLowerCase();
				 out2.write(croppedWord);
				 out2.newLine();
				 if(wordList.containsKey(croppedWord))
					 wordList.put(croppedWord,wordList.get(croppedWord)+1);
				 else
					 wordList.put(croppedWord,0);
			 }
		 }
		 
	 }

	 out2.close();
	 }//closing while loop for each line in file
	 br.close();
 }//closing for loop for each file in folder
 
 
 
 /* Comparing with UMLS */
 String umlsFile = "F:/0.college/UG project/First review/input_output/umls.txt";
 FileWriter q3 = new FileWriter("F:/0.college/UG project/First review/input_output/distinct_filtered_output.txt",true);
 BufferedWriter out3 =new BufferedWriter(q3);
 for(String key : wordList.keySet())
 {
	 if(fs.findWordInList(key,umlsFile))
	 {	 out3.write(key.toLowerCase());
	 	out3.newLine();
	 }
 }
out3.close();

/* Creating feature vector */
FileWriter q4 = new FileWriter("F:/0.college/UG project/First review/input_output/feature_vector.txt",true);
BufferedWriter out4 =new BufferedWriter(q4);

FileInputStream fstream;

for(String path : paths)
{
	out4.write(path+" ");
	
	fstream = new FileInputStream("F:/0.college/UG project/First review/input_output/distinct_filtered_output.txt");
	DataInputStream in = new DataInputStream(fstream);
	BufferedReader br = new BufferedReader(new InputStreamReader(in));
	sample = br.readLine();
	while(sample!=null)
	{
	//	System.out.print(sample+" ");
		if(fs.findWordInFile(sample,folderPath+"/"+path))
			out4.write("1 ");
		else
			out4.write("0 ");
		sample = br.readLine();
	}
	out4.newLine();
	br.close();
}

out4.close();

System.out.println("End");
 
}//closing Main
}