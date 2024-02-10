package com.example.work;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.work.Adapter.ViewPagerAdapter;
import com.example.work.fragment.MusicFragment;
import com.example.work.fragment.NoteFragment;
import com.example.work.fragment.PersonFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

   private ViewPager viewPager;
   private BottomNavigationView bottomNavigationView;
   private List<Fragment>fragments=new ArrayList<>();
   @SuppressLint("MissingInflatedId")
   @Override
    protected void onCreate(Bundle savedInstanceState){
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_login);

       viewPager=(ViewPager) findViewById(R.id.view_pager);
       bottomNavigationView=(BottomNavigationView) findViewById(R.id.bottomNavigation);
       bottomNavigationView.setItemIconTintList(null);
       bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
       fragments=new ArrayList<>();
       fragments.add(new MusicFragment());
       fragments.add(new NoteFragment());
       fragments.add(new PersonFragment());

      ViewPagerAdapter viewpagerAdapter=new ViewPagerAdapter(getSupportFragmentManager(),fragments);
      viewPager.setAdapter(viewpagerAdapter);
      bottomNavigationView.getMenu().getItem(2).setChecked(true);
      viewPager.setCurrentItem(2);
      viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
         @Override
         public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

         }

         @Override
         public void onPageSelected(int position) {
            bottomNavigationView.getMenu().getItem(position).setChecked(true);

         }

         @Override
         public void onPageScrollStateChanged(int state) {

         }
      });

   }

   private BottomNavigationView.OnNavigationItemSelectedListener
   mOnNavigationItemSelectedListener=new BottomNavigationView.OnNavigationItemSelectedListener() {
      @Override
      public boolean onNavigationItemSelected(@NonNull MenuItem item) {
         switch (item.getItemId()) {
            case R.id.main_music:
               viewPager.setCurrentItem(0);
               return true;
            case R.id.main_note:
               viewPager.setCurrentItem(1);
               return true;
            case R.id.main_user:
               viewPager.setCurrentItem(2);
               return true;
            default:
         }
         return false;
      }
   };
   @Override
   public void onBackPressed(){
      super.onBackPressed();
      finish();
   }

}


