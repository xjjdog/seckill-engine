package com.github.xjjdog.sf.common.model;

/**
 * 返回状态
 */
public interface RepStatus {

   /**
    * 成功
    */
   Integer SUCCESS = 1;

   /**
    * 失败
    */
   Integer FAIL = 0;


   /**
    * 错误
    */
   Integer ERROR = 2;


}
