package com.ncut.example;

import org.apache.mahout.cf.taste.impl.model.GenericUserPreferenceArray;
import org.apache.mahout.cf.taste.model.Preference;
import org.apache.mahout.cf.taste.model.PreferenceArray;

/**
 * Created by zhouning on 2017/12/12.
 * desc:实例1：Preferences -- 创建user-item偏好数据，并输出
 */
public class CreatePreferenceArray {

    private CreatePreferenceArray() {
    }

    public static void main(String[] args) {
        PreferenceArray user1Pref = new GenericUserPreferenceArray(2);
        user1Pref.setUserID(0, 1L);
        user1Pref.setItemID(0, 101L);
        user1Pref.setValue(0, 3.0f);

        user1Pref.setItemID(1, 102L);
        user1Pref.setValue(1, 4.0f);
        Preference preference = user1Pref.get(1);
        System.out.println(user1Pref);
    }


}
