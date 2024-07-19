package com.kim.spark.sql;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import scala.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvgAgeBuffer implements Serializable {
    private Long total;
    private Long count;
}
