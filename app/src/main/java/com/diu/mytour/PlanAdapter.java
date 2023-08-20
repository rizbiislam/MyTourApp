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
    List<Plan> mData;

    public PlanAdapter(Context mContext, List<Plan> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(mContext).inflate(R.layout.row_plan_item, parent, false);
        return new MyViewHolder(row);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tvCaption.setText(mData.get(position).getPlanCaption());
        holder.tvPrice.setText(mData.get(position).getPlanPrice());
        Glide.with(mContext).load(mData.get(position).getPlanImage()).into(holder.imgplan);
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

                    planDetailActivity.putExtra("planCaption", mData.get(position).getPlanCaption());
                    planDetailActivity.putExtra("planContact", mData.get(position).getPlanContact());
                    planDetailActivity.putExtra("planName", mData.get(position).getPlanName());
                    planDetailActivity.putExtra("planPrice", mData.get(position).getPlanPrice());
                    planDetailActivity.putExtra("planContact2", mData.get(position).getPlanContact2());
                    planDetailActivity.putExtra("planDescription", mData.get(position).getPlanDescription());
                    planDetailActivity.putExtra("planImage", mData.get(position).getPlanImage());
                    planDetailActivity.putExtra("planID", mData.get(position).getPlanID());
                    planDetailActivity.putExtra("expireDate", mData.get(position).getExpireDate());
                    long planDate = (long) mData.get(position).getPlanDate();
                    planDetailActivity.putExtra("planDate", planDate);
                    planDetailActivity.putExtra("planStatus",mData.get(position).getPlanStatus());
                    mContext.startActivity(planDetailActivity);
                }
            });
        }
    }
}
