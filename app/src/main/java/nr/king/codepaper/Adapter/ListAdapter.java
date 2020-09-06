package nr.king.codepaper.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import nr.king.codepaper.Common.Common;
import nr.king.codepaper.R;
import nr.king.codepaper.viewwall;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder>{
public Context context;
public List<nr.king.codepaper.Model.List> listList;

    public ListAdapter(Context context, List<nr.king.codepaper.Model.List> listList) {
        this.context = context;
        this.listList = listList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.listcard,parent,false);
        int height=parent.getMeasuredHeight()/2;
        view.setMinimumHeight(height);

        return new ListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final nr.king.codepaper.Model.List lister= listList.get(position);









//checkPost();

        if (lister.isSale()){
            Glide.with(context)
                    .load(lister.getImage())
                    .into(holder.imageView);
            holder.lock.setVisibility(View.VISIBLE);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                final Query reference= FirebaseDatabase.getInstance().getReference("Foods").orderByChild("menuId").equalTo(Common.CATEGORY_ID_SELECTED);
//reference.addValueEventListener(new ValueEventListener() {
//    @Override
//    public void onDataChange(@NonNull DataSnapshot snapshot) {
//        Intent intent=new Intent(context, viewwall.class);
//        Common.LIST_WALLPAER=lister.getImage();
//        Common.List_ID=reference.getRef().getKey();
//                Common.LIST_NAME=lister.getName();
//        Common.CATEGORY_ID_SELECTED=lister.getMenuId();
//
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(intent);
//    }
//
//    @Override
//    public void onCancelled(@NonNull DatabaseError error) {
//
//    }
//});



                    Intent intent=new Intent(context, viewwall.class);
                    Common.LIST_WALLPAER=lister.getImage();
                    Common.List_ID=lister.getPostid();
                    Common.Sale=lister.isSale();
                    Common.LIST_NAME=lister.getName();
                    Common.User_IMAGE=lister.getImageUrl();
                    Common.USER_NAME=lister.getUserName();
                    Common.USER_ID=lister.getPublisherid();
                    Common.CATEGORY_ID_SELECTED=lister.getMenuId();

                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);








                }
            });




        }



        else if (lister.getBuyiedby()!=null){
            if (lister.getBuyiedby().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                Glide.with(context)
                        .load(lister.getImage())
                        .into(holder.imageView);
                holder.lock.setVisibility(View.GONE);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                final Query reference= FirebaseDatabase.getInstance().getReference("Foods").orderByChild("menuId").equalTo(Common.CATEGORY_ID_SELECTED);
//reference.addValueEventListener(new ValueEventListener() {
//    @Override
//    public void onDataChange(@NonNull DataSnapshot snapshot) {
//        Intent intent=new Intent(context, viewwall.class);
//        Common.LIST_WALLPAER=lister.getImage();
//        Common.List_ID=reference.getRef().getKey();
//                Common.LIST_NAME=lister.getName();
//        Common.CATEGORY_ID_SELECTED=lister.getMenuId();
//
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(intent);
//    }
//
//    @Override
//    public void onCancelled(@NonNull DatabaseError error) {
//
//    }
//});



                        Intent intent=new Intent(context, viewwall.class);
                        Common.LIST_WALLPAER=lister.getImage();
                        Common.List_ID=lister.getPostid();
                        Common.LIST_NAME=lister.getName();
                        Common.User_IMAGE=lister.getImageUrl();
                        Common.USER_NAME=lister.getUserName();
                        Common.USER_ID=lister.getPublisherid();
                        Common.CATEGORY_ID_SELECTED=lister.getMenuId();

                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);








                    }
                });



            }

        }

        else if (lister.getPublisherid().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){

            Glide.with(context)
                    .load(lister.getImage())
                    .into(holder.imageView);
            holder.lock.setVisibility(View.GONE);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                final Query reference= FirebaseDatabase.getInstance().getReference("Foods").orderByChild("menuId").equalTo(Common.CATEGORY_ID_SELECTED);
//reference.addValueEventListener(new ValueEventListener() {
//    @Override
//    public void onDataChange(@NonNull DataSnapshot snapshot) {
//        Intent intent=new Intent(context, viewwall.class);
//        Common.LIST_WALLPAER=lister.getImage();
//        Common.List_ID=reference.getRef().getKey();
//                Common.LIST_NAME=lister.getName();
//        Common.CATEGORY_ID_SELECTED=lister.getMenuId();
//
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(intent);
//    }
//
//    @Override
//    public void onCancelled(@NonNull DatabaseError error) {
//
//    }
//});



                    Intent intent=new Intent(context, viewwall.class);
                    Common.LIST_WALLPAER=lister.getImage();
                    Common.List_ID=lister.getPostid();
                    Common.LIST_NAME=lister.getName();
                    Common.User_IMAGE=lister.getImageUrl();
                    Common.USER_NAME=lister.getUserName();
                    Common.USER_ID=lister.getPublisherid();
                    Common.CATEGORY_ID_SELECTED=lister.getMenuId();

                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);








                }
            });




        }


        else {
            Glide.with(context)
                    .load(lister.getImage())
                    .into(holder.imageView);



            holder.lock.setVisibility(View.GONE);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                final Query reference= FirebaseDatabase.getInstance().getReference("Foods").orderByChild("menuId").equalTo(Common.CATEGORY_ID_SELECTED);
//reference.addValueEventListener(new ValueEventListener() {
//    @Override
//    public void onDataChange(@NonNull DataSnapshot snapshot) {
//        Intent intent=new Intent(context, viewwall.class);
//        Common.LIST_WALLPAER=lister.getImage();
//        Common.List_ID=reference.getRef().getKey();
//                Common.LIST_NAME=lister.getName();
//        Common.CATEGORY_ID_SELECTED=lister.getMenuId();
//
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(intent);
//    }
//
//    @Override
//    public void onCancelled(@NonNull DatabaseError error) {
//
//    }
//});



                    Intent intent=new Intent(context, viewwall.class);
                    Common.LIST_WALLPAER=lister.getImage();
                    Common.List_ID=lister.getPostid();
                    Common.LIST_NAME=lister.getName();
                    Common.User_IMAGE=lister.getImageUrl();
                    Common.USER_NAME=lister.getUserName();
                    Common.USER_ID=lister.getPublisherid();
                    Common.CATEGORY_ID_SELECTED=lister.getMenuId();

                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);








                }
            });
        }


//        else{
//            Glide.with(context)
//                    .load(lister.getImage())
//                    .into(holder.imageView);
//
//
//
//
//        }










    }

//    private void checkPost() {
//
//        if (lister.isSale()){
//            Glide.with(context)
//                    .load(lister.getImage())
//                    .into(holder.imageView);
//            holder.lock.setVisibility(View.VISIBLE);
//            holder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
////                final Query reference= FirebaseDatabase.getInstance().getReference("Foods").orderByChild("menuId").equalTo(Common.CATEGORY_ID_SELECTED);
////reference.addValueEventListener(new ValueEventListener() {
////    @Override
////    public void onDataChange(@NonNull DataSnapshot snapshot) {
////        Intent intent=new Intent(context, viewwall.class);
////        Common.LIST_WALLPAER=lister.getImage();
////        Common.List_ID=reference.getRef().getKey();
////                Common.LIST_NAME=lister.getName();
////        Common.CATEGORY_ID_SELECTED=lister.getMenuId();
////
////        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////        context.startActivity(intent);
////    }
////
////    @Override
////    public void onCancelled(@NonNull DatabaseError error) {
////
////    }
////});
//
//
//
//                    Intent intent=new Intent(context, viewwall.class);
//                    Common.LIST_WALLPAER=lister.getImage();
//                    Common.List_ID=lister.getPostid();
//                    Common.Sale=lister.isSale();
//                    Common.LIST_NAME=lister.getName();
//                    Common.User_IMAGE=lister.getImageUrl();
//                    Common.USER_NAME=lister.getUserName();
//                    Common.USER_ID=lister.getPublisherid();
//                    Common.CATEGORY_ID_SELECTED=lister.getMenuId();
//
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    context.startActivity(intent);
//
//
//
//
//
//
//
//
//                }
//            });
//
//
//
//
//        }
//
//
//
//        else if (lister.getBuyiedby()!=null){
//            if (lister.getBuyiedby().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
//                Glide.with(context)
//                        .load(lister.getImage())
//                        .into(holder.imageView);
//                holder.lock.setVisibility(View.GONE);
//                holder.itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
////                final Query reference= FirebaseDatabase.getInstance().getReference("Foods").orderByChild("menuId").equalTo(Common.CATEGORY_ID_SELECTED);
////reference.addValueEventListener(new ValueEventListener() {
////    @Override
////    public void onDataChange(@NonNull DataSnapshot snapshot) {
////        Intent intent=new Intent(context, viewwall.class);
////        Common.LIST_WALLPAER=lister.getImage();
////        Common.List_ID=reference.getRef().getKey();
////                Common.LIST_NAME=lister.getName();
////        Common.CATEGORY_ID_SELECTED=lister.getMenuId();
////
////        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////        context.startActivity(intent);
////    }
////
////    @Override
////    public void onCancelled(@NonNull DatabaseError error) {
////
////    }
////});
//
//
//
//                        Intent intent=new Intent(context, viewwall.class);
//                        Common.LIST_WALLPAER=lister.getImage();
//                        Common.List_ID=lister.getPostid();
//                        Common.LIST_NAME=lister.getName();
//                        Common.User_IMAGE=lister.getImageUrl();
//                        Common.USER_NAME=lister.getUserName();
//                        Common.USER_ID=lister.getPublisherid();
//                        Common.CATEGORY_ID_SELECTED=lister.getMenuId();
//
//                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        context.startActivity(intent);
//
//
//
//
//
//
//
//
//                    }
//                });
//
//
//
//            }
//
//        }
//
//        else if (lister.getPublisherid().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
//
//            Glide.with(context)
//                    .load(lister.getImage())
//                    .into(holder.imageView);
//            holder.lock.setVisibility(View.GONE);
//            holder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
////                final Query reference= FirebaseDatabase.getInstance().getReference("Foods").orderByChild("menuId").equalTo(Common.CATEGORY_ID_SELECTED);
////reference.addValueEventListener(new ValueEventListener() {
////    @Override
////    public void onDataChange(@NonNull DataSnapshot snapshot) {
////        Intent intent=new Intent(context, viewwall.class);
////        Common.LIST_WALLPAER=lister.getImage();
////        Common.List_ID=reference.getRef().getKey();
////                Common.LIST_NAME=lister.getName();
////        Common.CATEGORY_ID_SELECTED=lister.getMenuId();
////
////        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////        context.startActivity(intent);
////    }
////
////    @Override
////    public void onCancelled(@NonNull DatabaseError error) {
////
////    }
////});
//
//
//
//                    Intent intent=new Intent(context, viewwall.class);
//                    Common.LIST_WALLPAER=lister.getImage();
//                    Common.List_ID=lister.getPostid();
//                    Common.LIST_NAME=lister.getName();
//                    Common.User_IMAGE=lister.getImageUrl();
//                    Common.USER_NAME=lister.getUserName();
//                    Common.USER_ID=lister.getPublisherid();
//                    Common.CATEGORY_ID_SELECTED=lister.getMenuId();
//
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    context.startActivity(intent);
//
//
//
//
//
//
//
//
//                }
//            });
//
//
//
//
//        }
//
//
//        else if (lister.isSale){
//            Glide.with(context)
//                    .load(lister.getImage())
//                    .into(holder.imageView);
//            holder.lock.setVisibility(View.GONE);
//            holder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
////                final Query reference= FirebaseDatabase.getInstance().getReference("Foods").orderByChild("menuId").equalTo(Common.CATEGORY_ID_SELECTED);
////reference.addValueEventListener(new ValueEventListener() {
////    @Override
////    public void onDataChange(@NonNull DataSnapshot snapshot) {
////        Intent intent=new Intent(context, viewwall.class);
////        Common.LIST_WALLPAER=lister.getImage();
////        Common.List_ID=reference.getRef().getKey();
////                Common.LIST_NAME=lister.getName();
////        Common.CATEGORY_ID_SELECTED=lister.getMenuId();
////
////        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////        context.startActivity(intent);
////    }
////
////    @Override
////    public void onCancelled(@NonNull DatabaseError error) {
////
////    }
////});
//
//
//
//                    Intent intent=new Intent(context, viewwall.class);
//                    Common.LIST_WALLPAER=lister.getImage();
//                    Common.List_ID=lister.getPostid();
//                    Common.LIST_NAME=lister.getName();
//                    Common.User_IMAGE=lister.getImageUrl();
//                    Common.USER_NAME=lister.getUserName();
//                    Common.USER_ID=lister.getPublisherid();
//                    Common.CATEGORY_ID_SELECTED=lister.getMenuId();
//
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    context.startActivity(intent);
//
//
//
//
//
//
//
//
//                }
//            });
//
//
//
//        }
//
//
//
//    }

    @Override
    public int getItemCount() {
        return listList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
       public ImageView imageView,lock;
        public ViewHolder(@NonNull View itemview) {
            super(itemview);

            imageView=itemview.findViewById(R.id.catergoryimage);

lock=itemview.findViewById(R.id.lock);

        }
    }
}
