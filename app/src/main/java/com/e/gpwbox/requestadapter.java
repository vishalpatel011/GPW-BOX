package com.e.gpwbox;

import android.content.Context;
import android.content.Intent;
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

public class requestadapter extends RecyclerView.Adapter<requestadapter.requestviewholder>
{
    Context mContext;
    List<studentrg> mData;
    List<studentrg> mDataFiltered;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String userid, ROLE;
    FirebaseAuth firebaseAuth;
    DataSnapshot dataSnapshot;
    public requestadapter(Context mContext, List<studentrg> mData) {
        this.mContext = mContext;
        this.mData = mData;
        this.mDataFiltered = mData;
    }
    @NonNull
    @Override
    public requestviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout;
        layout = LayoutInflater.from(mContext).inflate(R.layout.request, parent, false);


        return new requestadapter.requestviewholder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull requestviewholder holder, final int position) {
        final studentrg uploadCurrent = mData.get(position);

        holder.container.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fade_transition_animation));

        holder.tv_title.setText(mData.get(position).getName());
        holder.tv_content.setText(mData.get(position).getEnno());
        holder.tv_date.setText(mData.get(position).getDept());


        holder.tv_title.setText(mDataFiltered.get(position).getName());
        holder.tv_content.setText(mDataFiltered.get(position).getEnno());
        holder.tv_date.setText(mDataFiltered.get(position).getDept());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               Intent vm = new Intent(v.getContext(), srview.class);
                final String fid = mData.get(position).getUserid();
                String title = uploadCurrent.getName();
                String content = uploadCurrent.getEnno();
                String date = uploadCurrent.getDept();
                String email = uploadCurrent.getEmail();
                String mobile = uploadCurrent.getMobileno();
                String pass = uploadCurrent.getPassword();
                String role=uploadCurrent.getRole();
String userid=uploadCurrent.getUserid();



                vm.putExtra("title", title);
                vm.putExtra("content", content);
                vm.putExtra("date", date);
                vm.putExtra("email", email);
                vm.putExtra("mobile",mobile);
                vm.putExtra("pass",pass);
                vm.putExtra("role",role);
                vm.putExtra("userid",userid);
                mContext.startActivity(vm);
            }



        });
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
                    List<studentrg> isFiltered=new ArrayList<>();
                    for (studentrg row : mData){
                        if(row.getName().toLowerCase().contains(Key.toLowerCase())){
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
                mDataFiltered=(List<studentrg>)results.values;
                notifyDataSetChanged();

            }
        } ;




    }
    public class requestviewholder extends RecyclerView.ViewHolder{

        TextView tv_title,tv_content,tv_date;
        LinearLayout container;


        public requestviewholder(@NonNull View itemView) {
            super(itemView);
            container=itemView.findViewById(R.id.ll1);
            tv_title=itemView.findViewById(R.id.tv_title);
            tv_content=itemView.findViewById(R.id.tv_description);
            tv_date=itemView.findViewById(R.id.tv_date);
        }
    }
}
