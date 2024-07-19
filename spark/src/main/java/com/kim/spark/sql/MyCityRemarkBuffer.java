package com.kim.spark.sql;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import scala.Serializable;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyCityRemarkBuffer implements Serializable {
    private Long count;
    private Map<String, Long> cityMap;

}
