package com.ncut.bookcrossing;

import org.apache.mahout.cf.taste.common.Refreshable;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.IDRescorer;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import java.util.Collection;
import java.util.List;

/**
 * Created by Zhou Ning on 2017/12/12.
 * <p>
 * Desc:
 */
public class BXRecommender implements Recommender {
    private Recommender recommender;

    public BXRecommender(DataModel dataModel) throws TasteException {
        UserSimilarity similarity = new EuclideanDistanceSimilarity(dataModel);
        UserNeighborhood neighborhood = new NearestNUserNeighborhood(100, 0.2, similarity, dataModel, 0.2);
        recommender = new GenericUserBasedRecommender(dataModel, neighborhood, similarity);
    }

    @Override
    public List<RecommendedItem> recommend(long userID, int howMany) throws TasteException {
        return recommender.recommend(userID, howMany, (IDRescorer) null, false);
    }

    @Override
    public List<RecommendedItem> recommend(long userID, int howMany, boolean includeKnownItems) throws TasteException {
        return recommender.recommend(userID, howMany, (IDRescorer) null, includeKnownItems);
    }

    @Override
    public List<RecommendedItem> recommend(long userID, int howMany, IDRescorer rescorer) throws TasteException {
        return recommender.recommend(userID, howMany, rescorer, false);
    }

    @Override
    public List<RecommendedItem> recommend(long userID, int howMany, IDRescorer rescorer, boolean includeKnownItems) throws TasteException {
        return recommender.recommend(userID, howMany, rescorer, includeKnownItems);
    }

    @Override
    public float estimatePreference(long userID, long itemID) throws TasteException {
        return recommender.estimatePreference(userID, itemID);
    }

    @Override
    public void setPreference(long userID, long itemID, float value) throws TasteException {
        recommender.setPreference(userID, itemID, value);
    }

    @Override
    public void removePreference(long userID, long itemID) throws TasteException {
        recommender.removePreference(userID, itemID);
    }

    @Override
    public DataModel getDataModel() {
        return recommender.getDataModel();
    }

    @Override
    public void refresh(Collection<Refreshable> alreadyRefreshed) {
        recommender.refresh(alreadyRefreshed);
    }
}