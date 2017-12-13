package com.ncut.bookcrossing;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.eval.AverageAbsoluteDifferenceRecommenderEvaluator;
import org.apache.mahout.cf.taste.model.DataModel;

import java.io.File;
import java.io.IOException;

/**
 * Created by zhouning on 2017/12/13.
 * desc:
 */
public class BXBooleanRecommenderEvaluator {
    private BXBooleanRecommenderEvaluator() {
    }

    public static void main(String[] args) throws IOException, TasteException {
        File originalFile = new File("data/Book-Crossing/BX-Book-Ratings.csv");

//        DataModel dataModel = new BXDataModel(originalFile, true);
//        RecommenderIRStatsEvaluator evaluator = new GenericRecommenderIRStatsEvaluator();
//        IRStatistics stats = evaluator.evaluate(new BXBooleanRecommenderBuilder(), new BXDataModelBuilder(), dataModel, null, 3, Double.NEGATIVE_INFINITY, 1.0);
//        System.out.println("Precision is " + stats.getPrecision() + "; Recall is " + stats.getRecall() + "; F1 is " + stats.getF1Measure());

        DataModel dataModel = new BXDataModel(originalFile, true);
        RecommenderEvaluator evaluator = new AverageAbsoluteDifferenceRecommenderEvaluator();
        double score = evaluator.evaluate(new BXBooleanRecommenderBuilder(), null, dataModel, 0.9, 0.3);
        System.out.println("MAE score is " + score);
    }
}
