package com.qianmo.beziershopcart;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2017/4/10 0010.
 * E-Mail：543441727@qq.com
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<ShopBean> datas;
    private Context mContext;
    private ShopOnClickListtener mShopOnClickListtener;

    public MyAdapter(List<ShopBean> datas, Context mContext) {
        this.datas = datas;
        this.mContext = mContext;
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_shop_menu, parent, false));
    }

    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder holder, final int position) {
        holder.shop_name.setText(datas.get(position).getTitle());
        holder.shop_price.setText(datas.get(position).getPrice());
        holder.count.setText(datas.get(position).getCount() + "");

        holder.ic_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mShopOnClickListtener != null) {
                    mShopOnClickListtener.add(v, position);
                }
            }
        });
        holder.ic_reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mShopOnClickListtener != null) {
                    mShopOnClickListtener.remove(v, position);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView shop_name;
        TextView shop_price;
        TextView count;
        ImageView ic_add;
        ImageView ic_reduce;

        public ViewHolder(View itemView) {
            super(itemView);
            shop_name = (TextView) itemView.findViewById(R.id.tv_title);
            shop_price = (TextView) itemView.findViewById(R.id.tv_price);
            count = (TextView) itemView.findViewById(R.id.tv_count);
            ic_add = (ImageView) itemView.findViewById(R.id.iv_add);
            ic_reduce = (ImageView) itemView.findViewById(R.id.iv_remove);
        }
    }

    public ShopOnClickListtener getShopOnClickListtener() {
        return mShopOnClickListtener;
    }

    public void setShopOnClickListtener(ShopOnClickListtener mShopOnClickListtener) {
        this.mShopOnClickListtener = mShopOnClickListtener;
    }


    public interface ShopOnClickListtener {

        void add(View view, int position);

        void remove(View view, int position);
    }
}
