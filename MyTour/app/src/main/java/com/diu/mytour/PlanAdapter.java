package com.diu.mytour;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.MyViewHolder> {
    Context mContext;
    List<Plan> mData ;

    public PlanAdapter(Context mContext, List<Plan> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(mContext).inflate(R.layout.row_plan_item,parent,false);
        return new MyViewHolder(row);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tvCaption.setText(mData.get(position).getplanCaption());
        holder.tvPrice.setText(mData.get(position).getplanPrice());
        Glide.with(mContext).load(mData.get(position).getplanImage()).into(holder.imgplan);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvCaption, tvPrice;
        ImageView imgplan;

        public MyViewHolder(View itemView) {
            super(itemView);

            tvCaption = itemView.findViewById(R.id.row_plan_title);
            tvPrice = itemView.findViewById(R.id.row_plan_price);
            imgplan = itemView.findViewById(R.id.row_plan_img);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent planDetailActivity = new Intent(mContext, PlanDetailActivity.class);
                    int position = getAdapterPosition();

                    planDetailActivity.putExtra("planCaption",mData.get(position).getplanCaption());
                    planDetailActivity.putExtra("planContact",mData.get(position).getplanContact());
                    planDetailActivity.putExtra("planName",mData.get(position).getplanName());
                    planDetailActivity.putExtra("planPrice",mData.get(position).getplanPrice());
                    planDetailActivity.putExtra("planContact2",mData.get(position).getplanContact2());
                    planDetailActivity.putExtra("planDescription",mData.get(position).getplanDescription());
                    planDetailActivity.putExtra("planImage",mData.get(position).getplanImage());
                    planDetailActivity.putExtra("planID",mData.get(position).getplanID());
                    long planDate  = (long) mData.get(position).getplanDate();
                    planDetailActivity.putExtra("planDate",planDate) ;
                    mContext.startActivity(planDetailActivity);

                }
            });
        }
    }
}
