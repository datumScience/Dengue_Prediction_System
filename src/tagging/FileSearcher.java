package tagging;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileSearcher {
	public boolean findWordInList(String word,String filePath) throws FileNotFoundException,IOException
	{
		 String sample="";
		 FileInputStream fstream = new FileInputStream(filePath);
		 DataInputStream in = new DataInputStream(fstream);
		 BufferedReader br = new BufferedReader(new InputStreamReader(in));
		 sample = br.readLine();
		 while(sample!=null)
		 {
			 if(sample.equalsIgnoreCase(word))
			 {
				 br.close();
				 return true;
			 }
				
			 else
				 sample = br.readLine();
		 }
		 br.close();
		 return false;
	}
	public boolean findWordInFile(String word,String filePath) throws FileNotFoundException,IOException
	{
		 String sample="";
		 FileInputStream fstream = new FileInputStream(filePath);
		 DataInputStream in = new DataInputStream(fstream);
		 BufferedReader br = new BufferedReader(new InputStreamReader(in));
		 sample = br.readLine();
		 while(sample!=null)
		 {
			 if(sample.contains(word))
			 {
				 br.close();
				 return true;
			 }
				
			 else
				 sample = br.readLine();
		 }
		 br.close();
		 return false;
	}
}
