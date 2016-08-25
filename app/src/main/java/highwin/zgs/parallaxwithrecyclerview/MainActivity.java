package highwin.zgs.parallaxwithrecyclerview;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import highwin.zgs.parallaxwithrecyclerview.adapter.ParallaxAdapter;
import highwin.zgs.parallaxwithrecyclerview.ui.ParallaxRecyclerView;

public class MainActivity extends AppCompatActivity {

    private List<String> mList;
    private ParallaxRecyclerView mRecyclerView;
    private View mHeaderView;
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mList = new ArrayList<>();
        init();
        mRecyclerView = (ParallaxRecyclerView) findViewById(R.id.prv);

        ParallaxAdapter parallaxAdapter = new ParallaxAdapter(this, mList);

        LayoutInflater headerInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //初始化布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        //设置布局管理器
        mRecyclerView.setLayoutManager(linearLayoutManager);

        //拿到header view
        mHeaderView = headerInflater.inflate(getResources().getIdentifier("view_header", "layout", getPackageName()), mRecyclerView, false);

        //拿到header view 里面的一张图片
        mImageView = (ImageView) mHeaderView.findViewById(R.id.iv);

        //添加header view
        parallaxAdapter.addHeaderView(mHeaderView);
        //添加footer view
        parallaxAdapter.addFooterView(((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(getResources().getIdentifier("view_footer", "layout", getPackageName()), mRecyclerView, false));

        //设置适配器
        mRecyclerView.setAdapter(parallaxAdapter);


        //当header 布局完成的监听，目的是拿到图片宽高
        mHeaderView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mHeaderView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                mRecyclerView.setImageView(mImageView);
            }
        });
    }

    /**
     * 初始化数据
     */
    private void init() {
        String[] sCheeseStrings = Cheeses.sCheeseStrings;
        for (int i = 0; i < sCheeseStrings.length; i++) {
            mList.add(sCheeseStrings[i]);
        }
    }
}
