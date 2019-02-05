package com.bluemetal.auctionapp.activities;

import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.bluemetal.auctionapp.R;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mDrawerLayout = findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(mToggle.onOptionsItemSelected(item)) {
            if (id == R.id.db) {

                Intent DashboardActivity = new Intent(HomeActivity.this, DashboardActivity.class);
                startActivity(DashboardActivity);

            } else if (id == R.id.pro) {

                Intent ProfileActivity = new Intent(HomeActivity.this, ProfileActivity.class);
                startActivity(ProfileActivity);

            } else if (id == R.id.add) {

                Intent MyProductsActivity = new Intent(HomeActivity.this, MyProductsActivity.class);
                startActivity(MyProductsActivity);

            } else if (id == R.id.logout) {

                FirebaseAuth.getInstance().signOut();
                Intent loginActivity = new Intent(HomeActivity.this, LogInActivity.class);
                startActivity(loginActivity);
                finish();

            }
            return true;
        }

        DrawerLayout drawer = findViewById(R.id.drawer);
        drawer.closeDrawer(GravityCompat.START);
        return super.onOptionsItemSelected(item);
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


