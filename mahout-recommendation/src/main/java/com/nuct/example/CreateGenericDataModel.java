package com.nuct.example;

import org.apache.mahout.cf.taste.impl.common.FastByIDMap;
import org.apache.mahout.cf.taste.impl.model.GenericDataModel;
import org.apache.mahout.cf.taste.impl.model.GenericUserPreferenceArray;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.PreferenceArray;

/**
 * Created by zhouning on 2017/12/12.
 * desc:实例2：data nodel -- 所有用户的偏好数据如何保存？
 */
public class CreateGenericDataModel {

    private CreateGenericDataModel() {
    }

    public static void main(String[] args) {
        FastByIDMap<PreferenceArray> preferences = new FastByIDMap<>();
        PreferenceArray user1Pref = new GenericUserPreferenceArray(2);
        user1Pref.setUserID(0, 1L);
        user1Pref.setItemID(0, 101L);
        user1Pref.setValue(0, 3.0f);

        user1Pref.setItemID(1, 102L);
        user1Pref.setValue(1, 4.0f);

        PreferenceArray user2Pref = new GenericUserPreferenceArray(2);
        user2Pref.setUserID(0, 2L);
        user2Pref.setItemID(0, 101L);
        user2Pref.setValue(0, 3.0f);

        user2Pref.setItemID(1, 102L);
        user2Pref.setValue(1, 4.0f);

        preferences.put(1L, user1Pref);
        preferences.put(2L, user2Pref);

        DataModel model = new GenericDataModel(preferences);
        System.out.println(model);
    }

}
