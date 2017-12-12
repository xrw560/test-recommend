package com.ncut.movielens;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.impl.eval.RMSRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.CachingRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by Zhou Ning on 2017/12/12.
 * <p>
 * Desc:
 */
public class UserRecommenderMovieLens {

    private UserRecommenderMovieLens() {
    }

    public static void main(String[] args) throws IOException, TasteException {
        File resultFile = new File(System.getProperty("java.io.tmpdir"), "userRcomed.csv");

        DataModel dataModel = new MovieLensDataModel(new File("data/MovieLens/ml-1m/ratings.dat"));
        UserSimilarity similarity = new PearsonCorrelationSimilarity(dataModel);
        UserNeighborhood neighborhood = new NearestNUserNeighborhood(100, similarity, dataModel);

        Recommender recommender = new GenericUserBasedRecommender(dataModel, neighborhood, similarity);
        Recommender cachingRecommender = new CachingRecommender(recommender);

        //Evaluate
        RMSRecommenderEvaluator evaluator = new RMSRecommenderEvaluator();
        RecommenderBuilder recommenderBuilder = new RecommenderBuilder() {
            @Override
            public Recommender buildRecommender(DataModel dataModel) throws TasteException {
                UserSimilarity similarity = new PearsonCorrelationSimilarity(dataModel);
                UserNeighborhood neighborhood = new NearestNUserNeighborhood(100, similarity, dataModel);
                return new GenericUserBasedRecommender(dataModel, neighborhood, similarity);
            }
        };
        double score = evaluator.evaluate(recommenderBuilder, null, dataModel, 0.9, 0.5);
        System.out.println("RMSE score is " + score);

        try (PrintWriter writer = new PrintWriter(resultFile)) {
            for (int userId = 1; userId <= dataModel.getNumUsers(); userId++) {
                List<RecommendedItem> recommendedItems = cachingRecommender.recommend(userId, 2);
                String line = userId + " : ";
                for (RecommendedItem recommendedItem : recommendedItems) {
                    line += recommendedItem.getItemID() + ":" + recommendedItem.getValue() + ",";
                }
                if (line.endsWith(",")) {
                    line = line.substring(0, line.length() - 1);
                }
                writer.write(line);
                writer.write("\n");
            }
        } catch (IOException e) {
            resultFile.delete();
            throw e;
        }
        System.out.println("Recommended for " + dataModel.getNumUsers() + " users and saved them to " + resultFile.getAbsolutePath());
    }
} 