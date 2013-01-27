/*
 *  RapidMiner
 *
 *  Copyright (C) 2001-2013 by Rapid-I and the contributors
 *
 *  Complete list of developers available at our web site:
 *
 *       http://rapid-i.com
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Affero General Public License for more details.
 *
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see http://www.gnu.org/licenses/.
 */
package com.rapidminer.operator.learner.functions.kernel;

import java.util.Iterator;

import com.rapidminer.example.Attribute;
import com.rapidminer.example.Example;
import com.rapidminer.example.ExampleSet;


/** 
 *  A model generated by the RVMLearner.
 * 
 *  @author Piotr Kasprzak, Ingo Mierswa
 */
public class RVMModel extends KernelModel {
	
	private static final long serialVersionUID = -26935964796619097L;
	
	private com.rapidminer.operator.learner.functions.kernel.rvm.Model model = null;
    
	public RVMModel(ExampleSet exampleSet, com.rapidminer.operator.learner.functions.kernel.rvm.Model model) {
		super(exampleSet);	
		this.model = model;
	}
    
    @Override
	public boolean isClassificationModel() {
        return getLabel().isNominal();
    }
    
    @Override
	public double getAlpha(int index) {
        return model.getWeight(index);
    }
    
	@Override
	public double getBias() {
		return 0;
	}

	@Override
	public SupportVector getSupportVector(int index) {
		return null;
	}
	
    @Override
	public String getId(int index) {
        return null;
    }
    
    @Override
	public int getNumberOfSupportVectors() {
        return model.getNumberOfRelevanceVectors();
    }
    
    @Override
	public int getNumberOfAttributes() {
        return 0;
    }
    
    @Override
	public double getAttributeValue(int exampleIndex, int attributeIndex) {
        return Double.NaN;
    }
    
    @Override
	public String getClassificationLabel(int index) {
        return "?";
    }
    
    @Override
	public double getRegressionLabel(int index) {
        return Double.NaN;
    }
    
    @Override
	public double getFunctionValue(int index) {
        return Double.NaN;
    }
    
	/** Create an input vector from an example */
    public static double[] makeInputVector(Example e) {
        double[] vector = new double[e.getAttributes().size()];
        int i = 0;
        for (Attribute attribute : e.getAttributes()) {
            vector[i++] = e.getValue(attribute);
        }
        return vector;
    }
 
	@Override
	public ExampleSet performPrediction(ExampleSet exampleSet, Attribute predictedLabel) {		
		Iterator<Example> i = exampleSet.iterator();
		while (i.hasNext()) {
			Example e = i.next();
            double functionValue = model.applyToVector(makeInputVector(e));
            if (getLabel().isNominal()) {
                if (functionValue > 0)
                    e.setValue(predictedLabel, getLabel().getMapping().getPositiveIndex());
                else
                    e.setValue(predictedLabel, getLabel().getMapping().getNegativeIndex());
                // set confidence to numerical prediction, such that can be scaled later
                e.setConfidence(predictedLabel.getMapping().getPositiveString(), 1.0d / (1.0d + java.lang.Math.exp(-functionValue)));
                e.setConfidence(predictedLabel.getMapping().getNegativeString(), 1.0d / (1.0d + java.lang.Math.exp(functionValue)));
            } else {
                e.setValue(predictedLabel, functionValue);
            }
		}
		return exampleSet;
	}
}
