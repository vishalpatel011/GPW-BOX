package com.e.gpwbox;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class facultyAdapter  extends RecyclerView.Adapter<facultyAdapter.facultyviewholder>
{
    Context mContext;
    List<facultyrg> mData;
    List<facultyrg> mDataFiltered;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String userid, ROLE;
    FirebaseAuth firebaseAuth;
    DataSnapshot dataSnapshot;
    public facultyAdapter(Context mContext, List<facultyrg> mData) {
        this.mContext = mContext;
        this.mData = mData;
        this.mDataFiltered = mData;
    }
    @NonNull
    @Override
    public facultyviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout;
        layout = LayoutInflater.from(mContext).inflate(R.layout.request, parent, false);


        return new facultyAdapter.facultyviewholder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull facultyviewholder holder, int position) {
        final facultyrg uploadCurrent = mData.get(position);

        holder.container.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fade_transition_animation));

        holder.tv_title.setText(mData.get(position).getFactname());
        holder.tv_content.setText(mData.get(position).getStaffid());
        holder.tv_date.setText(mData.get(position).getFdept());


        holder.tv_title.setText(mDataFiltered.get(position).getFactname());
        holder.tv_content.setText(mDataFiltered.get(position).getStaffid());
        holder.tv_date.setText(mDataFiltered.get(position).getFdept());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
    public Filter getFilter() {


        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String Key=constraint.toString();
                if(Key.isEmpty()){ mDataFiltered=mData;}else{
                    List<facultyrg> isFiltered=new ArrayList<>();
                    for (facultyrg row : mData){
                        if(row.getFactname().toLowerCase().contains(Key.toLowerCase())){
                            isFiltered.add(row);
                        }
                    }
                    mDataFiltered=isFiltered;
                }
                FilterResults filterResults=new FilterResults();
                filterResults.values=mDataFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mDataFiltered=(List<facultyrg>)results.values;
                notifyDataSetChanged();

            }
        } ;




    }
    public class facultyviewholder extends RecyclerView.ViewHolder{

        TextView tv_title,tv_content,tv_date;
        LinearLayout container;


        public facultyviewholder(@NonNull View itemView) {
            super(itemView);
            container=itemView.findViewById(R.id.ll1);
            tv_title=itemView.findViewById(R.id.tv_title);
            tv_content=itemView.findViewById(R.id.tv_description);
            tv_date=itemView.findViewById(R.id.tv_date);
        }
    }
}


