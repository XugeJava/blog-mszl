package com.xuge.mszl.bean.params;

import lombok.Data;

/**
 * author: yjx
 * Date :2022/7/2811:35
 **/
@Data
public class PageParams {
  private int page = 1;

  private int pageSize = 5;

  private Long categoryId;

  private Long tagId;

  private String year;

  private String month;

  public String getMonth(){
    if (this.month != null && this.month.length() == 1){
      return "0"+this.month;
    }
    return this.month;
  }
}
