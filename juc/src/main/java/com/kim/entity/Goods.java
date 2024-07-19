package com.kim.entity;

import com.kim.utils.RandomUtil;

import java.util.concurrent.atomic.AtomicInteger;

public class Goods implements IGoods{

    protected float price;
    protected int id;
    protected String goodName;
    protected int amount;

    protected IGoods.Type goodType;

    private static int goodNo;


    protected Goods() {
        this.id = ++goodNo;
        this.amount = 1;
        this.price =0;
        this.goodName = "未知商品";
    }

    public static IGoods productOne() {
        return productByType(Type.randType());
    }

    private static IGoods productByType(Type type) {
        switch (type){
            case PET:
                return new GoodsPet();
            case FOOD:
                return new GoodsFood();
            case CLOTHES:
                return new GoodsClothes();
        }
        return new Goods();
    }

    @Override
    public String toString() {
        return "商品{" + "id=" + getID() + ",name=" + getName() + ",price=" + getPrice() + "}";
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getID() {
        return id;
    }

    @Override
    public float getPrice() {
        return price;
    }

    @Override
    public void setPrice() {
        this.price = price;
    }

    @Override
    public String getName() {
        return goodName;
    }

    @Override
    public int getAmount() {
        return amount;
    }

    @Override
    public Type getType() {
        return goodType;
    }

    @Override
    public int compareTo(IGoods o) {
        if (o == null) {
            throw new NullPointerException("goods is null");
        }
        return this.id - o.getID();
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Goods goods = (Goods) o;
        return id == goods.id;
    }

    private static class GoodsPet extends Goods {
        private final static AtomicInteger PET_NO = new AtomicInteger(0);

        public GoodsPet() {
            super();
            this.goodType = Type.CLOTHES;
            this.goodName = "宠物-" + PET_NO.incrementAndGet();
            price = RandomUtil.randInRange(1000, 10000);
            amount = RandomUtil.randInMod(5);
        }
    }

    private static class GoodsClothes extends Goods {
        private final static AtomicInteger CLOTHES_NO = new AtomicInteger(0);
        public GoodsClothes() {
            super();
            this.goodType = Type.CLOTHES;
            this.goodName = "宠物衣服-" + CLOTHES_NO.incrementAndGet();
            price = RandomUtil.randInRange(50, 100);
            amount = RandomUtil.randInMod(5);
        }
    }



    private static class GoodsFood extends Goods {
        private final static AtomicInteger FOOD_NO = new AtomicInteger(0);
        public GoodsFood() {
            super();
            this.goodType = Type.CLOTHES;
            this.goodName = "宠物两室-" + FOOD_NO.incrementAndGet();
            price = RandomUtil.randInRange(50, 100);
            amount = RandomUtil.randInMod(5);
        }
    }


}
