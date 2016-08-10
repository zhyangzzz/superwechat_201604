package cn.ucai.fulicenter.task;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.List;
import java.util.Map;

import cn.ucai.fulicenter.FuliCenterApplication;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.bean.CartBean;
import cn.ucai.fulicenter.bean.MessageBean;
import cn.ucai.fulicenter.bean.Result;
import cn.ucai.fulicenter.bean.UserAvatar;
import cn.ucai.fulicenter.data.OkHttpUtils2;
import cn.ucai.fulicenter.utils.Utils;

/**
 * Created by Administrator on 2016/7/20.
 */
public class UpdateCartTask {
    private static final String TAG = UpdateCartTask.class.getSimpleName();
    CartBean mCart;
    Context mContext;

    public UpdateCartTask(Context context, CartBean cart) {
        mContext = context;
        this.mCart = cart;
    }

    public void execute(){
        final List<CartBean> cartList = FuliCenterApplication.getInstance().getCartList();
        if (cartList.contains(mCart)){
            if (mCart.getCount()>0){
                //更新购物车数据
                updateCart(new OkHttpUtils2.OnCompleteListener<MessageBean>() {
                    @Override
                    public void onSuccess(MessageBean result) {
                        if (result!=null&&result.isSuccess()){
                            cartList.set(cartList.indexOf(mCart),mCart);
                            mContext.sendStickyBroadcast(new Intent("update_cart_list"));
                        }
                    }

                    @Override
                    public void onError(String error) {
                        Log.e(TAG,"error="+error);
                    }
                });
            }else{
                //删除购物车数据
                deleteCart(new OkHttpUtils2.OnCompleteListener<MessageBean>() {
                    @Override
                    public void onSuccess(MessageBean result) {
                        if (result!=null&&result.isSuccess()){
                            cartList.remove(cartList.indexOf(mCart));
                            mContext.sendStickyBroadcast(new Intent("update_cart_list"));
                        }
                    }

                    @Override
                    public void onError(String error) {
                        Log.e(TAG,"error="+error);
                    }
                });
            }
        }else{
            //新增购物车数据
            addCart(new OkHttpUtils2.OnCompleteListener<MessageBean>() {
                @Override
                public void onSuccess(MessageBean result) {
                    if (result!=null&&result.isSuccess()){
                        mCart.setId(Integer.valueOf(result.getMsg()));
                        cartList.add(mCart);
                        mContext.sendStickyBroadcast(new Intent("update_cart_list"));
                    }
                }

                @Override
                public void onError(String error) {
                    Log.e(TAG,"error="+error);
                }
            });
        }
    }
    private void updateCart(OkHttpUtils2.OnCompleteListener<MessageBean> listener){
        OkHttpUtils2<MessageBean> utils = new OkHttpUtils2<MessageBean>();
        utils.setRequestUrl(I.REQUEST_UPDATE_CART)
                .addParam(I.Cart.ID,String.valueOf(mCart.getId()))
                .addParam(I.Cart.COUNT,String.valueOf(mCart.getCount()))
                .addParam(I.Cart.IS_CHECKED,String.valueOf(mCart.isChecked()))
                .targetClass(MessageBean.class)
                .execute(listener);
    }
    private void deleteCart(OkHttpUtils2.OnCompleteListener<MessageBean> listener){
        OkHttpUtils2<MessageBean> utils = new OkHttpUtils2<MessageBean>();
        utils.setRequestUrl(I.REQUEST_DELETE_CART)
                .addParam(I.Cart.ID,String.valueOf(mCart.getId()))
                .targetClass(MessageBean.class)
                .execute(listener);
    }
    private void addCart(OkHttpUtils2.OnCompleteListener<MessageBean> listener){
        OkHttpUtils2<MessageBean> utils = new OkHttpUtils2<MessageBean>();
        utils.setRequestUrl(I.REQUEST_ADD_CART)
                .addParam(I.Cart.GOODS_ID,String.valueOf(mCart.getGoods().getGoodsId()))
                .addParam(I.Cart.COUNT,String.valueOf(mCart.getCount()))
                .addParam(I.Cart.IS_CHECKED,String.valueOf(mCart.isChecked()))
                .addParam(I.Cart.USER_NAME,FuliCenterApplication.getInstance().getUserName())
                .targetClass(MessageBean.class)
                .execute(listener);
    }
}
