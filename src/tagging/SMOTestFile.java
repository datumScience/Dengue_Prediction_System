package tagging;

/*
 *    This program is free software; you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation; either version 2 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program; if not, write to the Free Software
 *    Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */

/*
 *    SMOTest.java
 *    Copyright (C) 2009 University of Waikato, Hamilton, New Zealand
 *
 */

//package wekaexamples.attributeSelection;

/***/
import weka.classifiers.Evaluation;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.core.converters.ConverterUtils.DataSink;
import weka.classifiers.functions.SMO;

import java.io.File;
import java.util.Random;

public class SMOTestFile {

 
  public static String predict() throws Exception {
 
	File train = new File("trainingSet.arff");
	while(!train.exists()){
		System.out.print("");
		train = new File("trainingSet.arff");
	}
	DataSource source = new DataSource("trainingSet.arff");
    Instances data = source.getDataSet();
    if (data.classIndex() == -1)
      data.setClassIndex(data.numAttributes() - 1);
  
	DataSource unlabeledFile = new DataSource("unlabelledSet.arff");
     Instances unlabeledData = unlabeledFile.getDataSet();
	 if (unlabeledData.classIndex() == -1)
      unlabeledData.setClassIndex(unlabeledData.numAttributes() - 1);

    SMO base = new SMO();
    base.buildClassifier(data);
	 
    Evaluation evaluation = new Evaluation(data);

    evaluation.crossValidateModel(base, data, 10, new Random(1));
    //evaluation.evaluateModel(base,unlabeledData);  
      double clsLabel = 1;
	  Instances labeled = new Instances(unlabeledData);
	  for(int i=0;i<unlabeledData.numInstances();i++){
		  clsLabel = base.classifyInstance(unlabeledData.instance(i));
		  labeled.instance(i).setClassValue(clsLabel);
	  }
	  DataSink.write("predictedLabels.arff",labeled);
	  
	  if(labeled.classAttribute().value((int)clsLabel).equals("Y"))
		  return("PREDICTED POSITIVE FOR DENGUE");
	  else
		  return("PREDICTED NEGATIVE FOR DENGUE");
	  
  }
}
