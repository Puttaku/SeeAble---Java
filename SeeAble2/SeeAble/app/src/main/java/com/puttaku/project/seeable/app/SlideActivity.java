package com.puttaku.project.seeable.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.text.Html;
import android.widget.TextView;
import android.view.View;
import android.support.v7.app.AppCompatActivity;

public class SlideActivity extends AppCompatActivity {

//    private lateinit var slideViewPager: ViewPager
//    private lateinit var dotsLayout: LinearLayout
//    private lateinit var sliderAdapter: SliderAdapter
    private int currentPage = 0;
    LinearLayout dotsLayout;
    private TextView[] dot;
    ViewPager slideViewPager;
    Button btn_next;
    Button btn_prev;
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slide_screen);
        dotsLayout = findViewById(R.id.dotsLayout);
        slideViewPager = findViewById(R.id.slideViewPager);
        btn_next = findViewById(R.id.btn_next);
        btn_prev = findViewById(R.id.btn_prev);
        SliderAdapter adapter = new SliderAdapter(this);
        slideViewPager.setAdapter(adapter);
        //Add Indicator
        addIndicator(0);
        slideViewPager.addOnPageChangeListener(listener);
        btn_next.setOnClickListener (new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoRegister();
                slideViewPager.setCurrentItem(currentPage+1);
            }

        });
        btn_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skipRegister();
            }

        });
    }

    private void addIndicator(int position) {

        dot = new TextView[4];
        dotsLayout.removeAllViews();

        for (int i = 0;i < dot.length;i++){
            dot[i] =new TextView(this);
            dot[i].setText(Html.fromHtml("&#8226;"));
            dot[i].setTextSize(35.0f);
            dot[i].setTextColor(getResources().getColor(R.color.colorTransWhite));

            dotsLayout.addView(dot[i]);
        }
        if(dot.length > 0)
            dot[position].setTextColor(getResources().getColor(R.color.colorWhite));
    }

    ViewPager.OnPageChangeListener listener = new ViewPager.OnPageChangeListener() {
        @Override public void onPageScrollStateChanged(int p0) {

        }

        @Override public void onPageScrolled(int p0, float p1, int p2) {
        }

        @Override public void onPageSelected(int p0) {
            currentPage = p0;
            addIndicator(p0);

                if(p0 == 0){
                    btn_next.setEnabled(true);
                    btn_prev.setEnabled(true);
                    btn_prev.setVisibility(View.VISIBLE);
                    btn_prev.setText("SKIP");
                    btn_next.setText("NEXT >");
                }
                else if(p0 == dot.length - 1){
                    btn_next.setEnabled(true);
                    btn_prev.setEnabled(true);
                    btn_prev.setVisibility(View.VISIBLE);

                    btn_next.setText("LET'S START");
                    btn_prev.setText("SKIP");
                }
                else{
                    btn_next.setEnabled(true);
                    btn_prev.setEnabled(true);
                    btn_prev.setVisibility(View.VISIBLE);

                    btn_next.setText("NEXT >");
                    btn_prev.setText("SKIP");

                }
        }
    };
    void gotoRegister() {
        if(currentPage == dot.length - 1 && btn_next.getText() == "LET'S START"){
            Intent intent = new Intent(this,SoundregisActivity.class);
            startActivity(intent);
        }
    }
    void skipRegister () {
        Intent intent = new Intent(this,SoundregisActivity.class);
        startActivity(intent);
        finish();
    }
}
