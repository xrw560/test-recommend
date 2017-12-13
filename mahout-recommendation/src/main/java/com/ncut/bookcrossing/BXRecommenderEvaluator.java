package com.ncut.bookcrossing;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.eval.AverageAbsoluteDifferenceRecommenderEvaluator;
import org.apache.mahout.cf.taste.model.DataModel;

import java.io.File;
import java.io.IOException;

/**
 * Created by Zhou Ning on 2017/12/12.
 * <p>
 * Desc:
 */
public class BXRecommenderEvaluator {
    private BXRecommenderEvaluator() {
    }

    public static void main(String[] args) throws IOException, TasteException {
        DataModel dataModel = new BXDataModel(new File("data/Book-Crossing/BX-Book-Ratings.csv"), false);

        RecommenderEvaluator evaluator = new AverageAbsoluteDifferenceRecommenderEvaluator();
        double score = evaluator.evaluate(new BXRecommenderBuilder(), null, dataModel, 0.9, 0.3);
        System.out.println("MAE score is " + score);

    }
} 