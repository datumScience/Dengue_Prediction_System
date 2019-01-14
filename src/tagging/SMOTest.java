package tagging;

import java.io.*;

import weka.classifiers.Evaluation;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.classifiers.functions.SMO;

import java.util.Random;

public class SMOTest {
public static void useClassifier() throws Exception {
	
	FileWriter q4 = new FileWriter("evaluation.txt",false);
   	BufferedWriter out4 =new BufferedWriter(q4);
   	
	//out4.write("\n0. Loading data");
   	File train = new File("trainingSet.arff");
   	while(!train.exists()){
   		System.out.print(" ");
   		train = new File("trainingSet.arff");
   	}
    DataSource source = new DataSource("trainingSet.arff");
    Instances data = source.getDataSet();
   
    if (data.classIndex() == -1)
      data.setClassIndex(data.numAttributes() - 1);
    
    out4.write("\n1. Meta-classfier");
    out4.newLine();
    
    SMO base = new SMO();
    base.buildClassifier(data);


      ObjectOutputStream oos = new ObjectOutputStream(
                            new FileOutputStream("SMOTestModel.model"));
       oos.writeObject(base);
       oos.flush();
       oos.close();


    Evaluation evaluation = new Evaluation(data);


    evaluation.crossValidateModel(base, data, 10, new Random(1));
    out4.write(evaluation.toSummaryString("Evaluation",false));
    out4.newLine();
     
      /* Print Confusion Matrix*/
      out4.write("Confusion Matrix :");
      double[][] cm=evaluation.confusionMatrix();
      out4.write("Classified as");
      out4.write("Y\tN");
      for(int i=0;i<2;i++)
      {
          for(int j=0;j<2;j++)
          {
              out4.write(String.valueOf(cm[i][j]));
              out4.write("\t");
              
          }
        if(i==0)
            out4.write("Y");
        else
            out4.write("N");
      }
      
     
      out4.write("\nBase :\n"+base);
      
      out4.close();
  }
}
