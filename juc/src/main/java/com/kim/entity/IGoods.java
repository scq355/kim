package com.kim.entity;

import com.kim.utils.RandomUtil;

public interface IGoods extends Comparable<IGoods>{
    void setId(int id);

    enum Type {
        PET,FOOD,CLOTHES;

        public static Type randType() {
            return values()[RandomUtil.randInMod(values().length) - 1];
        }
    }

    int getID();

    float getPrice();

    void setPrice();

    String getName();

    int getAmount();

    Type getType();
}
