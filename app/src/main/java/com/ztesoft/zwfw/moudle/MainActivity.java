package com.ztesoft.zwfw.moudle;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ztesoft.zwfw.MainFragment;
import com.ztesoft.zwfw.MyTaskFragment;
import com.ztesoft.zwfw.R;
import com.ztesoft.zwfw.base.BaseActivity;
import com.ztesoft.zwfw.user.PersonalFragment;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private MainFragment mainFragment;
    private MyTaskFragment myTaskFragment;
    private PersonalFragment personalFragment;
    private static final int TAG_FRAG_MYTASK= 0;
    private static final int TAG_FRAG_MAIN = 1;
    private static final int TAG_FRAG_PERSONAL = 2;

    private int curTag = 0;

    TextView tabTextWdbj;
    TextView tabTextPersonal;
    ImageView tabImgWdbj;
    ImageView tabImgGrzx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.tab_wdbj).setOnClickListener(this);
        findViewById(R.id.tab_home).setOnClickListener(this);
        findViewById(R.id.tab_personal).setOnClickListener(this);
        tabTextWdbj = (TextView) findViewById(R.id.tab_text_wdbj);
        tabTextPersonal = (TextView) findViewById(R.id.tab_text_personal);
        tabImgWdbj = (ImageView) findViewById(R.id.img_wdbj);
        tabImgGrzx = (ImageView) findViewById(R.id.img_grzx);

        if(savedInstanceState == null){
            curTag = TAG_FRAG_MAIN;
        }else{
            curTag = savedInstanceState.getInt("curTag",TAG_FRAG_MAIN);
        }
        setTab(curTag);
    }



    private void setTab(int tag) {
        FragmentTransaction fm = getSupportFragmentManager().beginTransaction();
        hideAllFragMent(fm);
        switch (tag){
            case TAG_FRAG_MYTASK:
                if(myTaskFragment == null){
                    myTaskFragment = MyTaskFragment.newInstance();
                    fm.add(R.id.fragContainer, myTaskFragment);
                }
                fm.show(myTaskFragment);
                break;
            case TAG_FRAG_MAIN:
                if(mainFragment== null){
                    mainFragment = MainFragment.newInstance();
                    fm.add(R.id.fragContainer,mainFragment);
                }
                fm.show(mainFragment);
                break;
            case TAG_FRAG_PERSONAL:
                if(personalFragment== null){
                    personalFragment = PersonalFragment.newInstance();
                    fm.add(R.id.fragContainer,personalFragment);
                }
                fm.show(personalFragment);
                break;

        }
        fm.commit();
        curTag = tag;
        updateBottom();
    }

    private void updateBottom() {
        tabTextWdbj.setTextColor(getResources().getColor(R.color.bottom_tab_color));
        tabImgWdbj.setImageResource(R.mipmap.ic_wdbj);
        tabTextPersonal.setTextColor(getResources().getColor(R.color.bottom_tab_color));
        tabImgGrzx.setImageResource(R.mipmap.ic_grzx);
        switch (curTag){
            case TAG_FRAG_MYTASK:
                tabTextWdbj.setTextColor(getResources().getColor(R.color.white));
                tabImgWdbj.setImageResource(R.mipmap.ic_wdbj_tab);
                break;
            case TAG_FRAG_MAIN:
              //  tabTextHome.setTextColor(getResources().getColor(android.R.color.holo_orange_dark));
                break;
            case TAG_FRAG_PERSONAL:
                tabTextPersonal.setTextColor(getResources().getColor(R.color.white));
                tabImgGrzx.setImageResource(R.mipmap.ic_grzx_tab);
                break;
        }
    }

    private void hideAllFragMent(FragmentTransaction fm) {

        if(myTaskFragment != null)
            fm.hide(myTaskFragment);

        if(mainFragment != null)
            fm.hide(mainFragment);

        if(personalFragment != null)
            fm.hide(personalFragment);

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("curTag",curTag);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tab_wdbj:
                if(curTag != TAG_FRAG_MYTASK)
                    setTab(TAG_FRAG_MYTASK);
                break;
            case R.id.tab_home:
                if(curTag != TAG_FRAG_MAIN)
                    setTab(TAG_FRAG_MAIN);
                break;

            case R.id.tab_personal:
                if(curTag != TAG_FRAG_PERSONAL)
                    setTab(TAG_FRAG_PERSONAL);
                break;
        }
    }
}
