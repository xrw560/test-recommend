package com.ncut.practice;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.jdbc.MySQLJDBCDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.JDBCDataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by zhouning on 2017/12/13.
 * desc:要求：基于MySQL中的电影评分的数据，使用Mahout为每个用户推荐3部电影
 */
public class MysqlDataMovieRecommend {
    private MysqlDataMovieRecommend() {
    }

    public static void main(String[] args) throws TasteException, IOException {
        File resultFile = new File(System.getProperty("java.io.tmpdir"), "MysqlMovieRcomed.txt");

        //Mysql Connection
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setDatabaseName("mahout");
        mysqlDataSource.setServerName("localhost");
        mysqlDataSource.setUser("root");
        mysqlDataSource.setPassword("root");
        mysqlDataSource.setAutoReconnect(true);
        mysqlDataSource.setFailOverReadOnly(false);

        JDBCDataModel dataModel = new MySQLJDBCDataModel(mysqlDataSource, "taste_preferences", "user_id", "item_id", "preference", null);
        DataModel model = dataModel;

        //Recommendations
        UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
        UserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.5, similarity, model);
        Recommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);

        try (PrintWriter writer = new PrintWriter(resultFile)) {
            for (int userId = 1; userId <= model.getNumUsers(); userId++) {
                List<RecommendedItem> recommendedItems = recommender.recommend(userId, 3);
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
        System.out.println("Recommended for " + model.getNumUsers() + " users and saved then to " + resultFile.getAbsolutePath());
    }
}
