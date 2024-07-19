package com.kim.spark.sql;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CityCount implements Serializable, Comparable<CityCount>{
    private String cityName;
    private Long count;

    @Override
    public int compareTo(@NotNull CityCount o) {
        return Math.toIntExact(o.getCount() - this.count);
    }
}
