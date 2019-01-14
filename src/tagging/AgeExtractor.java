package tagging;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;


public class AgeExtractor {
	public int getMonth(String path) throws FileNotFoundException,IOException,ParseException
	{
		
		String sample = "";
		int ageVal=1;
		String []splitWords;
		FileInputStream fstream = new FileInputStream(path);
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		sample = br.readLine();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	   Date date = null;
	   int age;
	
		while(sample!=null)
		{
		
			
			if(sample.contains("Date of Birth"))
			{
		
				splitWords = sample.split(" ");

				for(String w : splitWords)
					if(w.matches(".*\\d{4}-\\d{2}-\\d{2}.*"))
					{
						
						
						w=w.replace('*', ' ');
						w=w.replace('[', ' ');
						w=w.replace(']', ' ');
						date = (Date)dateFormat.parse((w));
						Calendar dob = Calendar.getInstance();  
						dob.setTime((date));  
						Calendar today = Calendar.getInstance(); 
							
						 age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);  
						if (today.get(Calendar.MONTH) < dob.get(Calendar.MONTH)) {
						age--;  
						} 
						else if (today.get(Calendar.MONTH) == dob.get(Calendar.MONTH)&& today.get(Calendar.DAY_OF_MONTH) < dob.get(Calendar.DAY_OF_MONTH)) {
					age--;  
						}
						ageVal = age;
						break;
					}
						
			}
			
			sample = br.readLine();
		}
		br.close();
		return ageVal;
		
	}
}
