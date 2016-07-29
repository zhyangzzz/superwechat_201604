package cn.ucai.fulicenter.bean;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by Administrator on 2016/7/29.
 */
public class CartBean implements Serializable{

    /**
     * id : 7672
     * userName : 7672
     * goodsId : 7672
     * count : 2
     * checked : true
     */

    private int id;
    private int userName;
    private int goodsId;
    private int count;
    private boolean checked;
    private GoodDetailsBean[] goods;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserName() {
        return userName;
    }

    public void setUserName(int userName) {
        this.userName = userName;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public GoodDetailsBean[] getGoods() {
        return goods;
    }

    public void setGoods(GoodDetailsBean[] goods) {
        this.goods = goods;
    }

    @Override
    public String toString() {
        return "CartBean{" +
                "id=" + id +
                ", userName=" + userName +
                ", goodsId=" + goodsId +
                ", count=" + count +
                ", checked=" + checked +
                ", goods=" + Arrays.toString(goods) +
                '}';
    }
}
