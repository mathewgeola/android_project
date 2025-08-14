package com.example.studyapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.studyapplication.databinding.ActivityFragmentDemoBinding;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class FragmentDemoActivity extends AppCompatActivity {
    private static final String TAG = "FragmentDemoActivity";
    private ActivityFragmentDemoBinding binding;

    private Fragment[] mFragments;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_fragment_demo);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding = ActivityFragmentDemoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mFragments = new Fragment[]{HomeFragment.newInstance("", ""), MyFragment.newInstance("", "")};

        initView();
    }


    private void initView() {
        TabLayout.Tab homeTab = binding.tablayout.newTab();
        homeTab.setCustomView(R.layout.tab_item);
        if (homeTab.getCustomView() != null) {
            View view = homeTab.getCustomView();

            TextView textView = view.findViewById(R.id.textview);
            textView.setText("首页");

            ImageView imageView = view.findViewById(R.id.imageview);
            imageView.setImageResource(R.drawable.home);
        }
        binding.tablayout.addTab(homeTab);


        TabLayout.Tab myTab = binding.tablayout.newTab();
        myTab.setCustomView(R.layout.tab_item);
        if (myTab.getCustomView() != null) {
            View view = myTab.getCustomView();

            TextView textView = view.findViewById(R.id.textview);
            textView.setText("我的");

            ImageView imageView = view.findViewById(R.id.imageview);
            imageView.setImageResource(R.drawable.my);
        }
        binding.tablayout.addTab(myTab);

        binding.tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Log.d(TAG, "onTabSelected: position = " + position);

                onTabItemSelected(position);

                for (int i = 0; i < binding.tablayout.getTabCount(); i++) {
                    TabLayout.Tab t = binding.tablayout.getTabAt(i);
                    if (t != null) {
                        View view = t.getCustomView();
                        if (view != null) {
                            TextView textView = view.findViewById(R.id.textview);
                            if (i == position) {
                                textView.setTextColor(getResources().getColor(com.google.android.material.R.color.design_default_color_primary, getTheme()));
                            } else {
                                textView.setTextColor(getResources().getColor(R.color.black, getTheme()));
                            }
                        }
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Log.d(TAG, "onTabUnselected: position = " + position);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Log.d(TAG, "onTabReselected: position = " + position);
            }
        });

        binding.callHomeFragmentMethodButton.setOnClickListener(v -> {
            List<Fragment> fragments = getSupportFragmentManager().getFragments();
            if (!fragments.isEmpty()) {
                Fragment fragment = getSupportFragmentManager().getFragments().get(0);
                if (fragment instanceof HomeFragment) {
                    ((HomeFragment) fragment).methodInFragment();
                }
            }
        });
    }

    private void onTabItemSelected(int position) {
        Fragment fragment = mFragments[position];
        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, fragment).commit();
    }

    public void methodInActivity() {
        Log.d(TAG, "methodInActivity: ");
    }
}