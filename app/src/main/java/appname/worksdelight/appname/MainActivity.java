package appname.worksdelight.appname;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements ViewPager.OnPageChangeListener {
    ViewPager pager;
    PagerSlidingTabStrip tabs;
    private int previousPage = 0;
    List<Fragment> fragment;
TextView app_name;
    private int[] imageResId = { R.drawable.time, R.drawable.a_1, R.drawable.a_8,
            R.drawable.a_15, R.drawable.a_22 };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.topbar));
        }
        pager=(ViewPager)findViewById(R.id.pager);
        tabs = (PagerSlidingTabStrip)findViewById(R.id.tabs);
        tabs.setShouldExpand(true);
        LinearLayout view = (LinearLayout) tabs.getChildAt(0);
        app_name=(TextView)findViewById(R.id.app_name) ;
        Typeface face = Typeface.createFromAsset(getAssets(),
                "fonts/Android Insomnia Regular.ttf");
        app_name.setTypeface(face);
        tabs.setOnPageChangeListener(this);

        pager = (ViewPager) findViewById(R.id.pager);
        //adapter = new MyPagerAdapter(getSupportFragmentManager());
        fragment = new ArrayList<Fragment>();
        fragment.add(new Fragment1());
        fragment.add(new Fragment2());
        fragment.add(new Fragment3());
        fragment.add(new Fragment4());
        fragment.add(new Fragment5());



       // pager.setAdapter(new CustomPagerAdapter(getSupportFragmentManager(),fragment));
        pager.setAdapter(new MainAdapter(getSupportFragmentManager(),imageResId));

        tabs.setViewPager(pager);
        tabs.setIndicatorColor(Color.parseColor("#ffffff"));


    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        ((LinearLayout) tabs.getChildAt(0)).getChildAt(previousPage).setSelected(false);
        // set the selected page to state_selected = true
        ((LinearLayout) tabs.getChildAt(0)).getChildAt(state).setSelected(true);
        // remember the current page
        previousPage = state;

    }


   /* //---------------------------------PAGER ADAPTER------------------------
    public class CustomPagerAdapter extends FragmentPagerAdapter implements PagerSlidingTabStrip.IconTabProvider {
        List<Fragment> fragment;
        private int[] imageResId = { R.drawable.selectore_tab1, R.drawable.selectore_tab2, R.drawable.selectore_tab3,
                R.drawable.selectore_tab4, R.drawable.selectore_tab5,R.drawable.selectore_tab6  };

        public CustomPagerAdapter(FragmentManager fm, List<Fragment> fragment) {
            super(fm);
            this.fragment = fragment;
            // TODO Auto-generated constructor stub
        }

        @Override
        public Fragment getItem(int position) {

            return this.fragment.get(position);
        }

        @Override
        public int getCount() {

            return fragment.size();
        }
        @Override
        public int getPageIconResId(int position) {
            // TODO Auto-generated method stub

            return imageResId[position];
        }




    }
*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
