package tagging;

import java.io.FileNotFoundException;
import java.io.IOException;

public class MaritalExtractor {
	public String getMonth(String path) throws FileNotFoundException,IOException
	{
	/*	String sample = "";
		String []splitWords;
		FileInputStream fstream = new FileInputStream(path);
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		sample = br.readLine();
		while(sample!=null)
		{
		
			//	System.out.println(sample);
				splitWords = sample.split(" ");

				for(String w : splitWords)
					if(w.matches("husband")||w.matches("wife")||w.matches("Husband")||w.matches("Wife")||w.matches("HUSBAND")||w.matches("WIFE"))
					{
						br.close();
						return "M";
					}
						
			
			sample = br.readLine();
		}
		br.close();
		return "N";
	*/
	FileSearcher fs = new FileSearcher();
	if(fs.findWordInFile("wife", path)||fs.findWordInFile("husband", path))
		return "M";
	return "N";
		//return sample;
	}
}
