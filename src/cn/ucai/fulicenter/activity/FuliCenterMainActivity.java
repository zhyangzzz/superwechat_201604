package cn.ucai.fulicenter.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import cn.ucai.fulicenter.R;

/**
 * Created by Administrator on 2016/8/1.
 */
public class FuliCenterMainActivity extends BaseActivity{
    private static final String TAG = FuliCenterMainActivity.class.getSimpleName();
    RadioButton rbNewGood,rbBoutique,rbCategory,rbCart,rbPersonalCenter;
    TextView tvCartHint;
    RadioButton[] mrbTabs;
    int index;
    int currentIndex;
    NewGoodFragment mNewGoodFragment;
    BoutiqueFragment mBoutiqueFragment;
    boolean add = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fulicenter_main);
        initView();

    }

    private void initView() {
        rbNewGood = (RadioButton) findViewById(R.id.layout_new_good);
        rbBoutique = (RadioButton) findViewById(R.id.layout_boutique);
        rbCategory = (RadioButton) findViewById(R.id.layout_category);
        rbCart = (RadioButton) findViewById(R.id.layout_cart);
        rbPersonalCenter = (RadioButton) findViewById(R.id.layout_personal_center);

        tvCartHint = (TextView) findViewById(R.id.tvCartHint);
        mrbTabs = new RadioButton[5];
        mrbTabs[0] = rbNewGood;
        mrbTabs[1] = rbBoutique;
        mrbTabs[2] = rbCategory;
        mrbTabs[3] = rbCart;
        mrbTabs[4] = rbPersonalCenter;

        mNewGoodFragment = new NewGoodFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, mNewGoodFragment)
//                .add(R.id.fragment_container, contactListFragment)
//                .hide(contactListFragment)
                .show(mNewGoodFragment)
                .commit();
    }

    public void onCheckedChange(View view){
        switch (view.getId()){
            case R.id.layout_new_good:
                index = 0;
                if(!add==false) {
                    mNewGoodFragment = new NewGoodFragment();
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.fragment_container, mNewGoodFragment)
                            .show(mNewGoodFragment)
                            .commit();
                }else{
                    getSupportFragmentManager().beginTransaction()
                            .hide(mBoutiqueFragment)
                            .show(mNewGoodFragment)
                            .commit();
                }
                break;
            case R.id.layout_boutique:
                index = 1;
                if(!add==false) {
                    mBoutiqueFragment = new BoutiqueFragment();
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.fragment_container, mBoutiqueFragment)
                            .show(mBoutiqueFragment)
                            .commit();
                }else{
                    getSupportFragmentManager().beginTransaction()
                            .hide(mNewGoodFragment)
                            .show(mBoutiqueFragment)
                            .commit();
                }
                break;
            case R.id.layout_category:
                index = 2;
                break;
            case R.id.layout_cart:
                index = 3;
                break;
            case R.id.layout_personal_center:
                index = 4;
                break;
        }
        Log.e(TAG,"index="+index+",currentIndex="+currentIndex);
        if (index!=currentIndex){
            setRadioButtonStatus(index);
            currentIndex = index;
        }
    }

    private void setRadioButtonStatus(int index) {
        for (int i=0;i<mrbTabs.length;i++){
            if (index==i){
                mrbTabs[i].setChecked(true);
            }else{
                mrbTabs[i].setChecked(false);
            }
        }
    }
}
