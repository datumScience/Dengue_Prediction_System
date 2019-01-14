package tagging;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class GenderExtractor {
	public String getMonth(String path) throws FileNotFoundException,IOException
	{
		String sample = "";
		String result="M";
		
		FileInputStream fstream = new FileInputStream(path);
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		sample = br.readLine();
		while(sample!=null)
		{
			if(sample.indexOf("Sex")!=-1){
				if(sample.indexOf("M")!=-1)
					result= "M";
				else
					result= "F";
				break;
			}
			sample = br.readLine();
		}
		br.close();
		
		return result;
	}
}
