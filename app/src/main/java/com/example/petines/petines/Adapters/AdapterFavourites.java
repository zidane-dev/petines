package com.example.petines.petines.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.petines.petines.Model.Favourite;
import com.example.petines.petines.Model.Pets;
import com.example.petines.petines.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterFavourites extends RecyclerView.Adapter<AdapterFavourites.MyViewHolder> implements Filterable {

    public List<Favourite> pets;
    List<Favourite> petsFilter;
    private Context context;
    private RecyclerViewClickListener mListener;
    CustomFilterFavourite filter;

    public AdapterFavourites(List<Favourite> pets, Context context, RecyclerViewClickListener listener) {
        this.pets = pets;
        this.petsFilter = pets;
        this.context = context;
        this.mListener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new MyViewHolder(view, mListener);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        holder.mName.setText(pets.get(position).getPets().getName());
        holder.mType.setText(pets.get(position).getPets().getBreed() + " / "
                + pets.get(position).getPets().getSpecies());
        holder.mDate.setText(pets.get(position).getPets().getBirth());
        //holder.mPicture.setImageResource(Integer.getInteger(pets.get(position).getPicture()));

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.skipMemoryCache(true);
        requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
        requestOptions.placeholder(R.drawable.logo);
        requestOptions.error(R.drawable.logo);

        Glide.with(context)
                .load(pets.get(position).getPets().getPicture())
                .apply(requestOptions)
                .into(holder.mPicture);

        final Boolean love = pets.get(position).getLiked();

        if (love){
            holder.mLove.setImageResource(R.drawable.likeon);
        } else {
            holder.mLove.setImageResource(R.drawable.likeof);
        }

    }

    @Override
    public int getItemCount() {
        return pets.size();
    }

    @Override
    public Filter getFilter() {
        if (filter==null) {
            filter=new CustomFilterFavourite((ArrayList<Favourite>) petsFilter,this);

        }
        return filter;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private RecyclerViewClickListener mListener;
        private CircleImageView mPicture;
        private ImageView mLove;
        private TextView mName, mType, mDate;
        private RelativeLayout mRowContainer;

        public MyViewHolder(View itemView, RecyclerViewClickListener listener) {
            super(itemView);
            mPicture = itemView.findViewById(R.id.picture);
            mName = itemView.findViewById(R.id.name);
            mType = itemView.findViewById(R.id.type);
            mLove = itemView.findViewById(R.id.love);
            mDate = itemView.findViewById(R.id.date);
            mRowContainer = itemView.findViewById(R.id.row_container);

            mListener = listener;
            mRowContainer.setOnClickListener(this);
            mLove.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.row_container:
                    mListener.onRowClick(mRowContainer, getAdapterPosition());
                    break;
                case R.id.love:
                    mListener.onLoveClick(mLove, getAdapterPosition());
                    break;
                default:
                    break;
            }
        }
    }

    public interface RecyclerViewClickListener {
        void onRowClick(View view, int position);
        void onLoveClick(View view, int position);
    }

}
