package com.e.gpwbox;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class HODAdapter extends RecyclerView.Adapter<HODAdapter.HODviewholder> implements Filterable {
    Context mContext;
    List<Message> mData;
    List<Message> mDataFiltered;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String userid, ROLE;
    FirebaseAuth firebaseAuth;
    DataSnapshot dataSnapshot;

    public HODAdapter(Context mContext, List<Message> mData) {
        this.mContext = mContext;
        this.mData = mData;
        this.mDataFiltered = mData;
    }

    @NonNull
    @Override
    public HODviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout;
        layout = LayoutInflater.from(mContext).inflate(R.layout.item_news, parent, false);


        return new HODAdapter.HODviewholder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull HODviewholder holder, final int position) {
        final Message uploadCurrent = mData.get(position);
        holder.container.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fade_transition_animation));



        holder.tv_title.setText(mDataFiltered.get(position).getMsgtype());
        holder.tv_content.setText(mDataFiltered.get(position).getMsg());
        holder.tv_date.setText(mDataFiltered.get(position).getTime());
        holder.tv_department.setText(mDataFiltered.get(position).getUdept());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent vm = new Intent(v.getContext(), hodview.class);
                final String fid = mData.get(position).getMsgid();
                String title = uploadCurrent.getMsgtype();
                String content = uploadCurrent.getMsg();
                String date = uploadCurrent.getMode();
                String time = uploadCurrent.getTime();
                String uname = uploadCurrent.getUname();
                String uenno = uploadCurrent.getUenno();
                String udept = uploadCurrent.getUdept();
                String complaintid=uploadCurrent.getUm();



                vm.putExtra("title", title);
                vm.putExtra("content", content);
                vm.putExtra("date", date);
                vm.putExtra("time", time);
                vm.putExtra("uname", uname);
                vm.putExtra("uenno", uenno);
                vm.putExtra("udept", udept);
                vm.putExtra("msgiddd",fid);
                vm.putExtra("compid",complaintid);

                mContext.startActivity(vm);

            }


        });
    }


    @Override
    public int getItemCount() {
        return mDataFiltered.size();
    }
    public Filter getFilter() {


        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String Key=constraint.toString();
                if(Key.isEmpty()){ mDataFiltered=mData;}else{
                    List<Message>isFiltered=new ArrayList<>();
                    for (Message row : mData){
                        if(row.getMsgtype().toLowerCase().contains(Key.toLowerCase())){
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
                mDataFiltered=(List<Message>)results.values;
                notifyDataSetChanged();

            }
        } ;




    }
    public class HODviewholder extends RecyclerView.ViewHolder{

        TextView tv_title,tv_content,tv_date,tv_role,tv_department;
        LinearLayout container;


        public HODviewholder(@NonNull View itemView) {
            super(itemView);
            container=itemView.findViewById(R.id.ll1);
            tv_title=itemView.findViewById(R.id.tv_title);
            tv_content=itemView.findViewById(R.id.tv_description);
            tv_date=itemView.findViewById(R.id.tv_date);
            tv_department=itemView.findViewById(R.id.tv_department);
        }
    }
}


