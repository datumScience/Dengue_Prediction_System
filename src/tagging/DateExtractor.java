package tagging;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class DateExtractor {
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
			if(sample.contains("Admission Date"))
			{
			//	System.out.println(sample);
				splitWords = sample.split(" ");

				for(String w : splitWords)
					if(w.matches(".*\\d{4}-\\d{2}-\\d{2}.*")||w.matches(".*\\d{4}-\\d{1}-\\d{2}.*"))
					{
						br.close();
						return ((w.split("-"))[1]);
					}
						
			}
			sample = br.readLine();
		}
		br.close();
		return "01";
		//return sample;
	}
}
