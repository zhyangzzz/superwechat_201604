package cn.ucai.fulicenter.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.ucai.fulicenter.D;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.activity.BoutiqueChildActivity;
import cn.ucai.fulicenter.bean.BoutiqueBean;
import cn.ucai.fulicenter.bean.CartBean;
import cn.ucai.fulicenter.task.UpdateCartTask;
import cn.ucai.fulicenter.utils.ImageUtils;
import cn.ucai.fulicenter.view.FooterViewHolder;

/**
 * Created by Administrator on 2016/8/9.
 */
public class CartAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    Context mContext;
    List<CartBean> mCartList;
    CartViewHolder mCartViewHolder;
    boolean isMore;

    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean more) {
        isMore = more;
    }

    public CartAdapter(Context context, List<CartBean> list) {
        mContext = context;
        mCartList = new ArrayList<CartBean>();
        mCartList.addAll(list);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        RecyclerView.ViewHolder holder =
                new CartViewHolder(inflater.inflate(R.layout.item_cart,parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CartViewHolder){
            mCartViewHolder = (CartViewHolder) holder;
            final CartBean cart = mCartList.get(position);
            mCartViewHolder.cbCartSelected.setChecked(cart.isChecked());
            if (cart.getGoods()!=null) {
                ImageUtils.setGoodThumb(mContext, mCartViewHolder.ivCartThumb, cart.getGoods().getGoodsThumb());
                mCartViewHolder.tvCartGoodName.setText(cart.getGoods().getGoodsName());
                mCartViewHolder.tvCartCount.setText("(" + cart.getCount() + ")");
                mCartViewHolder.tvCartPrice.setText(cart.getGoods().getCurrencyPrice());
            }
            mCartViewHolder.cbCartSelected.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    cart.setChecked(isChecked);
                    new UpdateCartTask(mContext,cart).execute();
                }
            });
            mCartViewHolder.ivCartAdd.setOnClickListener(new ChangeCountListener(cart,1));
            mCartViewHolder.ivCartDel.setOnClickListener(new ChangeCountListener(cart,-1));
        }
    }

    @Override
    public int getItemCount() {
        return mCartList!=null?mCartList.size():0;
    }

    @Override
    public int getItemViewType(int position) {
            return I.TYPE_ITEM;
    }

    public void initItem(List<CartBean> list) {
        if (mCartList!=null){
            mCartList.clear();
        }
        mCartList.addAll(list);
        notifyDataSetChanged();
    }

    public void addItem(List<CartBean> list) {
        mCartList.addAll(list);
        notifyDataSetChanged();
    }

    class CartViewHolder extends RecyclerView.ViewHolder{
        CheckBox cbCartSelected;
        ImageView ivCartThumb;
        TextView tvCartGoodName;
        ImageView ivCartAdd;
        TextView tvCartCount;
        ImageView ivCartDel;
        TextView tvCartPrice;
        public CartViewHolder(View itemView) {
            super(itemView);
            cbCartSelected = (CheckBox) itemView.findViewById(R.id.cb_cart_selected);
            ivCartThumb = (ImageView) itemView.findViewById(R.id.iv_cart_thumb);
            tvCartGoodName = (TextView) itemView.findViewById(R.id.tv_cart_good_name);
            ivCartAdd = (ImageView) itemView.findViewById(R.id.iv_cart_add);
            ivCartDel = (ImageView) itemView.findViewById(R.id.iv_cart_del);
            tvCartCount = (TextView) itemView.findViewById(R.id.tv_cart_count);
            tvCartPrice = (TextView) itemView.findViewById(R.id.tv_cart_price);

        }
    }
    class ChangeCountListener implements View.OnClickListener{
        CartBean cartBean;
        int setCount;
        public ChangeCountListener(CartBean cart, int count) {
            this.cartBean = cart;
            this.setCount = count;
        }
        @Override
        public void onClick(View view) {
           this.cartBean.setCount(cartBean.getCount()+setCount);
            Log.e("listener","count="+cartBean.getCount());
            new UpdateCartTask(mContext,cartBean).execute();
        }
    }

}
