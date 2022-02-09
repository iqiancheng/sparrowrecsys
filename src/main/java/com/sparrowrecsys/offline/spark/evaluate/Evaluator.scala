package com.sparrowrecsys.offline.spark.evaluate

import org.apache.spark.ml.linalg.DenseVector
import org.apache.spark.mllib.evaluation.BinaryClassificationMetrics
import org.apache.spark.sql.DataFrame

/*
SparkML提供了用于评估管道质量的工具
做机器学习需要有数据管道的提供特征给模型进行训练
*/
class Evaluator {
  def evaluate(predictions:DataFrame):Unit = {

    import  predictions.sparkSession.implicits._

    val scoreAndLabels = predictions.select("label", "probability").map { row =>
      (row.apply(1).asInstanceOf[DenseVector](1), row.getAs[Int]("label").toDouble)
    }

    val metrics = new BinaryClassificationMetrics(scoreAndLabels.rdd)

    println("AUC under PR = " + metrics.areaUnderPR())
    println("AUC under ROC = " + metrics.areaUnderROC())
  }
}
