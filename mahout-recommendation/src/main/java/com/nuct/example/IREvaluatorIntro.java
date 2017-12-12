package com.nuct.example;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.IRStatistics;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderIRStatsEvaluator;
import org.apache.mahout.cf.taste.impl.eval.GenericRecommenderIRStatsEvaluator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.apache.mahout.common.RandomUtils;

import java.io.File;

/**
 * Created by zhouning on 2017/12/12.
 * desc:
 */
public class IREvaluatorIntro {
    private IREvaluatorIntro() {
    }

    public static void main(String[] args) throws Exception {
        RandomUtils.useTestSeed();

        DataModel model = new FileDataModel(new File("data/ua.base"));
        RecommenderIRStatsEvaluator evaluator = new GenericRecommenderIRStatsEvaluator();

        RecommenderBuilder recommenderBuilder = new RecommenderBuilder() {
            @Override
            public Recommender buildRecommender(DataModel dataModel) throws TasteException {
                UserSimilarity similarity = new PearsonCorrelationSimilarity(dataModel);
                UserNeighborhood neighborhood = new NearestNUserNeighborhood(100, similarity, dataModel);
                return new GenericUserBasedRecommender(dataModel, neighborhood, similarity);
            }
        };

        //推荐5个，
        IRStatistics statistics = evaluator.evaluate(recommenderBuilder, null, model, null, 5, GenericRecommenderIRStatsEvaluator.CHOOSE_THRESHOLD, 1.0);

        System.out.println(statistics.getPrecision());
        System.out.println(statistics.getRecall());
        System.out.println(statistics.getF1Measure());
    }
}
