package com.ncut.example;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.eval.AverageAbsoluteDifferenceRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.eval.RMSRecommenderEvaluator;
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
 * desc:实例4：推荐模型评估 -- 评估实例3的推荐系统的优劣
 */
public class EvaluatorIntro {
    private EvaluatorIntro() {
    }

    public static void main(String[] args) throws Exception {
        RandomUtils.useTestSeed();//用来使得每次随机都一样，仅限用于测试，不要在实际中使用

        final DataModel model = new FileDataModel(new File("data/ua.base"));
        //计算平均差值
        RecommenderEvaluator evaluator = new AverageAbsoluteDifferenceRecommenderEvaluator();
        //计算均方根差
        RecommenderEvaluator recommenderEvaluator = new RMSRecommenderEvaluator();

        RecommenderBuilder recommenderBuilder = new RecommenderBuilder() {
            @Override
            public Recommender buildRecommender(DataModel dataModel) throws TasteException {
                //用户相似度，使用基于皮尔逊相关系数计算相似度
                UserSimilarity similarity = new PearsonCorrelationSimilarity(dataModel);
                //对每个用户取固定数量N个最近邻居
                UserNeighborhood neighborhood = new NearestNUserNeighborhood(100, similarity, dataModel);
                return new GenericUserBasedRecommender(dataModel, neighborhood, similarity);
            }
        };
        //指标，约接近0，效果越好
        double score = evaluator.evaluate(recommenderBuilder, null, model, 0.7, 1.0);
        double rmse = recommenderEvaluator.evaluate(recommenderBuilder, null, model, 0.7, 1.0);
        System.out.println(score);
        System.out.println(rmse);
    }


}
