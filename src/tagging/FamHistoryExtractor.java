package tagging;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class FamHistoryExtractor {
	public String getMonth(String path) throws FileNotFoundException,IOException
	{
		String sample = "";
		String []splitWords;
		FileInputStream fstream = new FileInputStream(path);
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		sample = br.readLine();
		while(sample!=null)
		{
		//	System.out.println(sample);
			if(sample.contains("Family History"))
			{
				//sample =br.readLine();
			//	System.out.println(sample);
				splitWords = sample.split(": ");

				for(String w : splitWords)
					if(w.equals("None")||w.equals("None."))
					{
						br.close();
						return "N";
					}
						
			}
			sample = br.readLine();
		}
		br.close();
		return "Y";
		//return sample;
	}
}
