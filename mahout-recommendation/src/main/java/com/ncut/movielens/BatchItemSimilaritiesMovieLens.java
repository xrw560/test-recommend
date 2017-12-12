package com.ncut.movielens;

import org.apache.mahout.cf.taste.model.DataModel;

import java.io.File;

/**
 * Created by Zhou Ning on 2017/12/12.
 * <p>
 * Desc:
 */
public class BatchItemSimilaritiesMovieLens {
    private BatchItemSimilaritiesMovieLens(){
    }

    public static void main(String[] args) {

        File resultFile = new File(System.getProperty("java.io.tmpdir"),"similarities.csv");

        DataModel dataModel = new MovieLensDataModel(new File(""))
    }
} 