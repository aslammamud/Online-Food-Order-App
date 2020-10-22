package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.Model.Cart;
import com.example.myapplication.Prevalent.CartViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminUserProductsActivity extends AppCompatActivity {
    private RecyclerView productsList;
    RecyclerView.LayoutManager layoutManager;
    private DatabaseReference cartListRef;
    private String userID = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user_products);
        userID = getIntent().getStringExtra("uid");
        productsList = findViewById(R.id.products_list);
        productsList.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        productsList.setLayoutManager(layoutManager);
        cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List").child("Admin view").child(userID).child("Products");

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Cart> options=
                new FirebaseRecyclerOptions.Builder<Cart>()
                        .setQuery(cartListRef,Cart.class)
                        .build();
        FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter = new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder holder, int position, @NonNull Cart model) {

                holder.txtProductQuantity.setText("Quantity = "+model.getQuantity());
                holder.txtProductPrice.setText("Price = "+model.getPrice()+" $");
                holder.txtProductName.setText(model.getPname());
            }

            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items_layout,parent,false);
                CartViewHolder holder = new CartViewHolder(view);
                return holder;
            }
        };
        productsList.setAdapter(adapter);
        adapter.startListening();
    }
}