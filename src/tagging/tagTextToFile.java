/*** Main file that performs feature vector generation
 *   Uses FileSearcher class
 *   Uses DateExtractor class
 */
package tagging;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class tagTextToFile {


	
 public static void main(String[] args) throws IOException,
 ClassNotFoundException,Exception {


	 project.gui();
	 
 String tagged;
 
 // Initialize the tagger
 MaxentTagger tagger = new MaxentTagger("taggers/english-left3words-distsim.tagger");
 
 FileSearcher fs = new FileSearcher();
 DateExtractor de = new DateExtractor();
 AgeExtractor ae = new AgeExtractor();
 GenderExtractor ge = new GenderExtractor();
 historyExtractor he = new historyExtractor();
 FamHistoryExtractor fe = new FamHistoryExtractor();
 MaritalExtractor me = new MaritalExtractor();
 DiseaseExtractor dex = new DiseaseExtractor(); 
 HashMap<String,Integer> wordList = new HashMap<String,Integer>();
 SymptomExtractor se= new SymptomExtractor();
 
 String[] paths;
 String croppedWord = null;
 
 File folderPath=null;
 while(project.getFolder()==null)
	 {
	 System.out.println(".");
	 };
 folderPath = project.getFolder();
 System.out.println("Training set : " + folderPath.toString());
 //String folderPath = "F:/0.college/UG project/Second_Review/input_output/DischargeSummary/yazhini";
 //File folder = new File(folderPath);
 //paths = folder.list();
 paths = folderPath.list();
 
// for(String path : paths)
	// out.println(path);
 
 // The sample string
 String sample = null;

 /* Deleting output files if already present */
 File delFile = new File("filtered_output.txt");
 if(delFile.exists())
	 delFile.delete();
 
 delFile = new File("output.txt");
 if(delFile.exists())
	 delFile.delete();
 
 delFile = new File("distinct_filtered_output.txt");
 if(delFile.exists())
	 delFile.delete();
 
 delFile = new File("trainingSet.arff");
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
	 
	
	 FileWriter q = new FileWriter("output.txt",true);
	 BufferedWriter out =new BufferedWriter(q);
	 
	 FileWriter q2 = new FileWriter("filtered_output.txt",true);
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
	 
	 for(String word : tagged.split(" "))
	 {
		 for(String pos : posTags)
		 {
			 if(word.endsWith(pos))
			 {
				 croppedWord = word.substring(0,word.length()-pos.lenght());
				 if(word[1].equals("pain_NN"))
				 {
					 String newword = croppedword+word[1];
				 }
					 
			 }
		 }
	 }

	 out2.close();
	 }//closing while loop for each line in file
	 br.close();
 }//closing for loop for each file in folder
 
 
 
 /* Comparing with UMLS */
 String umlsFile = "umls.txt";
 FileWriter q3 = new FileWriter("distinct_filtered_output.txt",true);
 BufferedWriter out3 =new BufferedWriter(q3);
 for(String key : wordList.keySet())
 {
	 if(fs.findWordInList(key,umlsFile))
	 {	 out3.write(key.toLowerCase());
	 	out3.newLine();
	 }
 }
out3.close();

/* Creating arrays for each symptom */
int []fever={0,0,0,0,0,0,0,0,0,0,0,0,0};
int []headache={0,0,0,0,0,0,0,0,0,0,0,0,0};
int []nausea={0,0,0,0,0,0,0,0,0,0,0,0,0};
int []vomit={0,0,0,0,0,0,0,0,0,0,0,0,0};
int []pain_behind_eye={0,0,0,0,0,0,0,0,0,0,0,0,0};
int []fatigue={0,0,0,0,0,0,0,0,0,0,0,0,0};
int []muscle_pain={0,0,0,0,0,0,0,0,0,0,0,0,0};
int []skin_rash={0,0,0,0,0,0,0,0,0,0,0,0,0};

/* Creating feature vector */
FileWriter q4 = new FileWriter("trainingSet.arff",true);
BufferedWriter out4 =new BufferedWriter(q4);

FileInputStream fstream;
out4.write("@relation DENGUE");
out4.newLine();

out4.write("@attribute age numeric");
out4.newLine();
out4.write("@attribute gender(M/F) {M,F}");
out4.newLine();
out4.write("@attribute ad_mon(01-12) numeric");
out4.newLine();
out4.write("@attribute marital_stat(M/N) {M,N}");
out4.newLine();
out4.write("@attribute med_his(Y/N) {Y,N}");
out4.newLine();
out4.write("@attribute fam_his(Y/N) {Y,N}");
out4.newLine();
out4.write("@attribute fever(0/1) numeric");
out4.newLine();
out4.write("@attribute headache(0/1) numeric");
out4.newLine();
out4.write("@attribute nausea(0/1) numeric");
out4.newLine();
out4.write("@attribute vomit(0/1) numeric");
out4.newLine();
out4.write("@attribute Pain_behind_eye(0/1) numeric");
out4.newLine();
out4.write("@attribute Fatigue(0/1) numeric");
out4.newLine();
out4.write("@attribute muscle_pain(0/1) numeric");
out4.newLine();
out4.write("@attribute skin_rash(0/1) numeric");
out4.newLine();
out4.write("@attribute disease(Y/N) {Y,N}");
out4.newLine();
out4.write("@data");
out4.newLine();
ArrayList<String> reject = new ArrayList<String>();

for(String path : paths)
{
	reject = se.rejectSymptoms((folderPath+"/"+path));
	//System.out.println(reject);
	
	out4.write(ae.getMonth(folderPath+"/"+path)+", ");
	out4.write(ge.getMonth(folderPath+"/"+path)+", ");
	int month = Integer.parseInt(de.getMonth(folderPath+"/"+path));
	out4.write(String.valueOf(month)+", ");
	out4.write(me.getMonth(folderPath+"/"+path)+", ");
	out4.write(he.getMonth(folderPath+"/"+path)+", ");
	out4.write(fe.getMonth(folderPath+"/"+path)+", ");
	
	fstream = new FileInputStream("umls.txt");
	DataInputStream in = new DataInputStream(fstream);
	BufferedReader br = new BufferedReader(new InputStreamReader(in));
	sample = br.readLine();
	while(sample!=null)
	{
	//	System.out.print(sample+" ");
		
		    
			if(!reject.contains(sample) && fs.findWordInFile(sample, folderPath+"/"+path) )
			{
				//System.out.println(path +":" + sample);
				out4.write("1"+", ");
				switch(sample){
				case "fever":
					fever[month]++;
				case "headache":
					headache[month]++;
				case "nausea":
					nausea[month]++;
				case "vomit":
					vomit[month]++;
				case "pain behind eye":
					pain_behind_eye[month]++;
				case "fatigue":
					fatigue[month]++;
				case "muscle pain":
					muscle_pain[month]++;
				case "skin rash":
					skin_rash[month]++;
					
				}
			}
		
			
		else
			out4.write("0"+", ");
		sample = br.readLine();
		
	}
	out4.write(dex.getMonth(folderPath+"/"+path));
	
	out4.newLine();
	br.close();
}

out4.close();

/***/
/*File sourceFile = new File(
		"C:/Users/Pinky/Desktop/pro/trainingSet.arff");

File destFile = new File(
		"trainingSet.arff");
/***/
 
/*FileChannel source = null;
FileChannel destination = null;

source = new FileInputStream(sourceFile).getChannel();

destination = new FileOutputStream(destFile).getChannel();

if (destination != null && source != null) {
	destination.transferFrom(source, 0, source.size());
}
source.close();
destination.close();
*/
System.out.println("End");

//SMOTestFile.predict();
Chart chart = new Chart();

chart.setFeverDist(fever);
chart.setHeadacheDist(headache);
chart.setVomitDist(vomit);
chart.setNauseaDist(nausea);
chart.setPainBehindEyeDist(pain_behind_eye);
chart.setFatigueDist(fatigue);
chart.setMusclePainDist(muscle_pain);
chart.setSkinRashDist(skin_rash);

chart.setVisible(true);
}//closing Main
 
}