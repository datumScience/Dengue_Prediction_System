package tagging;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class DiseaseExtractor {
	public String getMonth(String path) throws FileNotFoundException,IOException
	{
		String sample = "";
	
		String result="N";
		FileInputStream fstream = new FileInputStream(path);
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		sample = br.readLine();
		while(sample!=null)
		{
			sample=sample.toLowerCase();
			if(sample.indexOf("final diagnosis:")!=-1||sample.indexOf("discharge diagnosis:")!=-1)
			{
				//sample =br.readLine();
			//	System.out.println(sample);
			//	splitWords = sample.split(" ");

				//for(String w : splitWords)
				//{
					if(sample.indexOf("dengue")!=-1)
					{
						//br.close();
						result="Y";
						break;
					}
			//	}
					
						
			}
			sample = br.readLine();
		}
		br.close();
	
		return result;
	}
}
