package com.example.acer.mychating;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
private FirebaseAuth mAuth;
private android.support.v7.widget.Toolbar mtoolbar;
private ViewPager mViewPager;
private TabLayout mTabLayout;
private TabsPageAdapter myTabsPageAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //-------
        mAuth = FirebaseAuth.getInstance();
        //--------
        mViewPager = (ViewPager)findViewById(R.id.main_tabs_pagers);
        myTabsPageAdapter = new TabsPageAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(myTabsPageAdapter);
        mTabLayout=(TabLayout)findViewById(R.id.main_tabs);
        mTabLayout.setupWithViewPager(mViewPager);
        //-------
        mtoolbar = (android.support.v7.widget.Toolbar)findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setTitle("myChAt");

}



    @Override
    //If user is auth.. the main will display , if he not the android will lanch startPage(login/sign)
    protected void onStart() {
        super.onStart();
        FirebaseUser currentuser = mAuth.getCurrentUser();
        if(currentuser==null){
          logoutUser();
        }


    }

    private void logoutUser() {
        Intent startPageIntent = new Intent(MainActivity.this,StartPage.class);
        //ashan lama ykon fi Main we yroh L StartPage and press back feha , m yrajah l main wa hoa msh msgal lsa
        startPageIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(startPageIntent);
        finish();
    }

    @Override
    //menu
    public boolean onCreateOptionsMenu(Menu menu) {
         super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.logout_menu) {
            mAuth.signOut();
            logoutUser();

        }
        if(item.getItemId()==R.id.setting_menu){
            Intent i = new Intent(MainActivity.this,Setting.class);
            startActivity(i);

        }
        if(item.getItemId()==R.id.main_all_user_button){
            Intent i = new Intent(MainActivity.this,AllUserActivity.class);
            startActivity(i);
        }
return true; }
}
