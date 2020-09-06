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

import java.util.ArrayList;
import java.util.List;

import nr.king.codepaper.Common.Common;
import nr.king.codepaper.R;
import nr.king.codepaper.viewwall;

public class StageredRecyclerView extends RecyclerView.Adapter<StageredRecyclerView.ViewHolder>{

    List<nr.king.codepaper.Model.List> listList=new ArrayList<>();
    Context context;

    public StageredRecyclerView( Context context,List<nr.king.codepaper.Model.List> listList) {
        this.context = context;
        this.listList = listList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.carder,parent,false);
//        int height=parent.getMeasuredHeight()/2;
//        parent.setMinimumHeight(height);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final nr.king.codepaper.Model.List lister=listList.get(position);
//     if (list.isSale()){
//         Glide.with(context)
//                 .load(list.getImage())
//                 .into(holder.imageView);
//         holder.lock.setVisibility(View.VISIBLE);
//         holder.itemView.setOnClickListener(new View.OnClickListener() {
//             @Override
//             public void onClick(View view) {
//
//
////                 SharedPreferences.Editor editor=context.getSharedPreferences("PREFS",Context.MODE_PRIVATE).edit();
////                 editor.putString(Common.List_ID,list.getPostid());
////                 editor.putString(Common.USER_ID,list.getPublisherid());
////                 editor.putString(Common.User_IMAGE,list.getImageUrl());
////                 editor.putString(Common.USER_NAME,list.getUserName());
////                 editor.putString(Common.LIST_WALLPAER,list.getImage());
////                 editor.putString( Common.CATEGORY_ID_SELECTED,list.getMenuId());
////)
////                 editor.apply();
//
//
//
//
////               Intent intent=new Intent(context,viewrandi.class);
////             Common.List_ID=list.getPostid();
////               intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
////               context.startActivity(intent);
////
//
//
//                 Intent intent=new Intent(context, viewwall.class);
//                 Common.LIST_WALLPAER=lister.getImage();
//                 Common.List_ID=lister.getPostid();
//                 Common.Sale=lister.isSale();
//                 Common.LIST_NAME=lister.getName();
//                 Common.User_IMAGE=lister.getImageUrl();
//                 Common.USER_NAME=lister.getUserName();
//                 Common.USER_ID=lister.getPublisherid();
//                 Common.CATEGORY_ID_SELECTED=lister.getMenuId();
//
//                 intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                 context.startActivity(intent);
//
//
//
//
//
//
//
//
//             }
//
//         });
//
//
//
//
//
//     }
//
//     else if (lister.getPublisherid()=="codepaper303@gmail.com"){
//         holder.lock.setVisibility(View.GONE);
//
//         holder.itemView.setOnClickListener(new View.OnClickListener() {
//             @Override
//             public void onClick(View view) {
//
//
////                    SharedPreferences.Editor editor=context.getSharedPreferences("PREFS",Context.MODE_PRIVATE).edit();
////                    editor.putString("postid",list.getPostid());
////                    editor.putString("publisherid",list.getPublisherid());
////                    editor.putString("imageUrl",list.getImageUrl());
////                    editor.putString("userName",list.getUserName());
////                    editor.putString("image",list.getImage());
////                    editor.apply();
////                    ((FragmentActivity)context).getSupportFragmentManager()
////                            .beginTransaction()
////                            .replace(R.id.fralay,new viewrandi()).commit();
//
//
//
//
////               Intent intent=new Intent(context,viewrandi.class);
////             Common.List_ID=list.getPostid();
////               intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
////               context.startActivity(intent);
////
//
//                 Intent intent=new Intent(context, viewwall.class);
//                 Common.LIST_WALLPAER=lister.getImage();
//                 Common.List_ID=lister.getPostid();
//                 Common.Sale=lister.isSale();
//                 Common.LIST_NAME=lister.getName();
//                 Common.User_IMAGE=lister.getImageUrl();
//                 Common.USER_NAME=lister.getUserName();
//                 Common.USER_ID=lister.getPublisherid();
//                 Common.CATEGORY_ID_SELECTED=lister.getMenuId();
//
//                 intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                 context.startActivity(intent);
//
//             }
//
//         });
//
//     }
//
//
//
//   else{
//
//         Glide.with(context)
//                 .load(list.getImage())
//                 .into(holder.imageView);
//         holder.lock.setVisibility(View.GONE);
//
//         holder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//
////                    SharedPreferences.Editor editor=context.getSharedPreferences("PREFS",Context.MODE_PRIVATE).edit();
////                    editor.putString("postid",list.getPostid());
////                    editor.putString("publisherid",list.getPublisherid());
////                    editor.putString("imageUrl",list.getImageUrl());
////                    editor.putString("userName",list.getUserName());
////                    editor.putString("image",list.getImage());
////                    editor.apply();
////                    ((FragmentActivity)context).getSupportFragmentManager()
////                            .beginTransaction()
////                            .replace(R.id.fralay,new viewrandi()).commit();
//
//
//
//
////               Intent intent=new Intent(context,viewrandi.class);
////             Common.List_ID=list.getPostid();
////               intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
////               context.startActivity(intent);
////
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
//                }
//
//            });
//        }








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




    }

    @Override
    public int getItemCount() {
        return listList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView imageView,lock;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
           this.imageView=itemView.findViewById(R.id.catergoryimage);
           this.lock=itemView.findViewById(R.id.lock);
        }
    }

}
