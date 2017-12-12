package com.ncut.example;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by zhouning on 2017/12/12.
 * desc:实例3：Recommender -- 通过User-based协同过滤推荐算法给用户1推荐20个商品
 */
public class RecommenderIntro {

    private RecommenderIntro() {
    }

    public static void main(String[] args) throws IOException, TasteException {
        DataModel model = new FileDataModel(new File("data/ua.base"));
        UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
        UserNeighborhood neighborhood = new NearestNUserNeighborhood(100, similarity, model);
        Recommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);

        //给用户1推荐20个物品
        List<RecommendedItem> recommendedItems = recommender.recommend(1, 20);
        for (RecommendedItem recommendedItem : recommendedItems) {
            System.out.println(recommendedItem);
        }
    }
}
