package com.rabigol.wowmoney.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.rabigol.wowmoney.App;
import com.rabigol.wowmoney.R;
import com.rabigol.wowmoney.adapters.ViewPagerAdapter;
import com.rabigol.wowmoney.fragments.ViewPagerFragment;

import java.util.ArrayList;

public class StartActivity extends AppCompatActivity implements ViewPagerFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        if (App.getInstance().getAppState() == App.APP_STATE_LOGGED) {
            startActivity(new Intent(this, MainActivity.class));
            finish(); // TODO: think about this
            return;
        } else {

            Button skipButton = (Button) findViewById(R.id.skip_button);
            skipButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent loginStarterIntent = new Intent(getBaseContext(), AuthActivity.class);
                    startActivity(loginStarterIntent);
                }
            });

            final ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
            ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(
                    getSupportFragmentManager()
            );

            pagerAdapter.addItem(ViewPagerFragment.newInstance("Start to control your money", "Write down all of your expenses and income", R.drawable.tutorial_icons_notedown));
            pagerAdapter.addItem(ViewPagerFragment.newInstance("Feel free!", "Unlimited accounts and currencies", R.drawable.tutorial_icons_currencies));
            pagerAdapter.addItem(ViewPagerFragment.newInstance("Analyze your expenses", "Look at your income and expenses in easy charts", R.drawable.tutorial_icons_analysis));
            viewPager.setAdapter(pagerAdapter);

            LinearLayout dotsContainer = (LinearLayout) findViewById(R.id.dots_container);
            final ArrayList<ImageView> dots = new ArrayList<>();
            for (int i = 0; i < pagerAdapter.getCount(); i++) {
                ImageView imageView = (ImageView) getLayoutInflater().inflate(R.layout.dot_layout, null);
                final int finalI = i;
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewPager.setCurrentItem(finalI, false);
                    }
                });
                if (i == 0) {
                    imageView.setBackgroundResource(R.drawable.dot_active);
                }
                dots.add(imageView);
                dotsContainer.addView(imageView);
            }

            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    for (int i = 0; i < dots.size(); i++) {
                        if (i != position) {
                            dots.get(i).setBackgroundResource(R.drawable.dot_inactive);
                        } else {
                            dots.get(i).setBackgroundResource(R.drawable.dot_active);
                        }
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }
    }
}
