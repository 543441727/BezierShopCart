package com.qianmo.beziershopcart;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Point;
import android.graphics.PointF;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MyAdapter.ShopOnClickListtener {
    private Context mContext = MainActivity.this;

    private RelativeLayout main_layout;
    private RecyclerView mRecyclerView;
    private ImageView mImageViewShopCat;
    private List<ShopBean> datas;
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        final Window window = getWindow();
//        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();


    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);
        mImageViewShopCat = (ImageView) findViewById(R.id.shopping_cart);
        main_layout = (RelativeLayout) findViewById(R.id.main_layout);
        initData();
    }

    private void initData() {
        datas = new ArrayList<>();
        datas.add(new ShopBean("面包", "1.00", 10));
        datas.add(new ShopBean("蛋挞", "1.00", 10));
        datas.add(new ShopBean("牛奶", "1.00", 10));
        datas.add(new ShopBean("肠粉", "1.00", 10));
        datas.add(new ShopBean("绿茶饼", "1.00", 10));
        datas.add(new ShopBean("花卷", "1.00", 10));
        datas.add(new ShopBean("包子", "1.00", 10));

        datas.add(new ShopBean("粥", "1.00", 10));
        datas.add(new ShopBean("炒饭", "1.00", 10));
        datas.add(new ShopBean("炒米粉", "1.00", 10));
        datas.add(new ShopBean("炒粿条", "1.00", 10));
        datas.add(new ShopBean("炒牛河", "1.00", 10));
        datas.add(new ShopBean("炒菜", "1.00", 10));

        datas.add(new ShopBean("淋菜", "1.00", 10));
        datas.add(new ShopBean("川菜", "1.00", 10));
        datas.add(new ShopBean("湘菜", "1.00", 10));
        datas.add(new ShopBean("粤菜", "1.00", 10));
        datas.add(new ShopBean("赣菜", "1.00", 10));
        datas.add(new ShopBean("东北菜", "1.00", 10));

        datas.add(new ShopBean("淋菜", "1.00", 10));
        datas.add(new ShopBean("川菜", "1.00", 10));
        datas.add(new ShopBean("湘菜", "1.00", 10));
        datas.add(new ShopBean("粤菜", "1.00", 10));
        datas.add(new ShopBean("赣菜", "1.00", 10));
        datas.add(new ShopBean("东北菜", "1.00", 10));

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.addItemDecoration(new RecyclerViewDivider(mContext, LinearLayoutManager.VERTICAL, 50, ContextCompat.getColor(mContext, R.color.colorAccent)));
        myAdapter = new MyAdapter(datas, mContext);
        mRecyclerView.setAdapter(myAdapter);
        myAdapter.setShopOnClickListtener(this);
    }


    @Override
    public void add(final View view, int position) {
        //贝塞尔起始数据点
        int[] startPosition = new int[2];
        //贝塞尔结束数据点
        int[] endPosition = new int[2];
        //控制点
        int[] recyclerPosition = new int[2];

        view.getLocationInWindow(startPosition);
        mImageViewShopCat.getLocationInWindow(endPosition);
        mRecyclerView.getLocationInWindow(recyclerPosition);

        PointF startF = new PointF();
        PointF endF = new PointF();
        PointF controllF = new PointF();

        startF.x = startPosition[0];
        startF.y = startPosition[1] - recyclerPosition[1];
        endF.x = endPosition[0];
        endF.y = endPosition[1] - recyclerPosition[1];
        controllF.x = endF.x;
        controllF.y = startF.y;

        final ImageView imageView = new ImageView(this);
        main_layout.addView(imageView);
        imageView.setImageResource(R.mipmap.ic_add_circle_blue_700_36dp);
        imageView.getLayoutParams().width = getResources().getDimensionPixelSize(R.dimen.item_dish_circle_size);
        imageView.getLayoutParams().height = getResources().getDimensionPixelSize(R.dimen.item_dish_circle_size);
        imageView.setVisibility(View.VISIBLE);
        imageView.setX(startF.x);
        imageView.setY(startF.y);

        ValueAnimator valueAnimator = ValueAnimator.ofObject(new BezierTypeEvaluator(controllF), startF, endF);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF pointF = (PointF) animation.getAnimatedValue();
                imageView.setX(pointF.x);
                imageView.setY(pointF.y);
                Log.i("wangjtiao", "viewF:" + view.getX() + "," + view.getY());
            }
        });


        ObjectAnimator objectAnimatorX = new ObjectAnimator().ofFloat(mImageViewShopCat, "scaleX", 0.6f, 1.0f);
        ObjectAnimator objectAnimatorY = new ObjectAnimator().ofFloat(mImageViewShopCat, "scaleY", 0.6f, 1.0f);
        objectAnimatorX.setInterpolator(new AccelerateInterpolator());
        objectAnimatorY.setInterpolator(new AccelerateInterpolator());
        AnimatorSet set = new AnimatorSet();
        set.play(objectAnimatorX).with(objectAnimatorY).after(valueAnimator);
        set.setDuration(800);
        set.start();
    }

    @Override
    public void remove(View view, int position) {
        Toast.makeText(mContext, "减", Toast.LENGTH_SHORT).show();
    }
}
