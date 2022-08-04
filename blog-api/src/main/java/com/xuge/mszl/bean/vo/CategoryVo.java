package com.xuge.mszl.bean.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * author: yjx
 * Date :2022/8/115:49
 **/
@Data
public class CategoryVo {
//  @JsonSerialize(using = ToStringSerializer.class)
  private String id;

  private String avatar;

  private String categoryName;
  private String description;
}
