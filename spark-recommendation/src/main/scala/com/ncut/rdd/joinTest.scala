package com.ncut.rdd

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by zhouning on 2017/12/14.
  * desc: 
  */
object joinTest {

  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setAppName("join").setMaster("local")
    val sc = new SparkContext(conf)
    sc.setLogLevel("WARN")

    val idName = sc.parallelize(Array((1, "zhangsan"), (2, "lisi"), (3, "wangwu")))
    val idAge = sc.parallelize(Array((1, 30), (2, 29), (4, 21)))
    val joined = idAge.join(idAge)
    joined.collect().foreach(println)

    sc.stop()
  }


}
