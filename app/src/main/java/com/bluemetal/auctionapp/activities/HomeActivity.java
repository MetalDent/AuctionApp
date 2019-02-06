package com.bluemetal.auctionapp.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bluemetal.auctionapp.R;
import com.bluemetal.auctionapp.models.Post;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView navigationView;
    private RecyclerView recyclerView;

    private DatabaseReference mDatabase;
    private FirebaseRecyclerAdapter<Post, PostViewHolder> mAdapter;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Posts");

        mDrawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.nav_view);
        recyclerView = findViewById(R.id.recycler_view_home);

        progressBar = findViewById(R.id.progress_bar_home);

        navigationView.setNavigationItemSelectedListener(this);

        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart() {
        super.onStart();

        progressBar.setVisibility(View.VISIBLE);
        progressBar.bringToFront();
        recyclerView.setVisibility(View.GONE);

        FirebaseRecyclerOptions<Post> options = new FirebaseRecyclerOptions.Builder<Post>()
                .setQuery(mDatabase, Post.class)
                .build();


        mAdapter = new FirebaseRecyclerAdapter<Post, PostViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull PostViewHolder holder, int position, @NonNull Post model) {
                holder.itemName.setText(model.getItemName());
                holder.itemDesc.setText(model.getDescription());
                holder.baseBid.setText("Base Bid: Rs." + model.getBaseBid());
                holder.startTime.setText("Start Time: " + model.getStartTime());
                holder.endTime.setText("End Time: " + model.getEndTime());

                Picasso.get().load(model.getImageUrl()).into(holder.imageView);
            }

            @NonNull
            @Override
            public PostViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.model_post, viewGroup, false);

                return new PostViewHolder(view);
            }
        };

        mAdapter.startListening();
        recyclerView.setAdapter(mAdapter);

        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAdapter.stopListening();
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {
        TextView itemName;
        TextView itemDesc;
        TextView baseBid;
        TextView startTime;
        TextView endTime;
        ImageView imageView;

        public PostViewHolder(View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.item_name);
            itemDesc = itemView.findViewById(R.id.description);
            baseBid = itemView.findViewById(R.id.base_bid);
            startTime = itemView.findViewById(R.id.start_time);
            endTime = itemView.findViewById(R.id.end_time);
            imageView = itemView.findViewById(R.id.image_view);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.add) {

             Intent AddPostActivity = new Intent(HomeActivity.this, AddPostActivity.class);
             startActivity(AddPostActivity);

        } else if (id == R.id.logout) {

            FirebaseAuth.getInstance().signOut();
            Intent loginActivity = new Intent(HomeActivity.this, LogInActivity.class);
            startActivity(loginActivity);
            finish();

        }

        DrawerLayout drawer = findViewById(R.id.drawer);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}


