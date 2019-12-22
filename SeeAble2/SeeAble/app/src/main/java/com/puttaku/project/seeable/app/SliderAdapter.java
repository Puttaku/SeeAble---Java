package com.puttaku.project.seeable.app;
//import library for PagerAdapter
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

//class SliderAdapter(context : Context) :  PagerAdapter(){
////
//////    private var mDataset = arrayOf<String>()
////    private lateinit var mContext : Context
////
////    override fun getCount(): Int {
////        return slide_subheader.size
////    }
////
////    init {
////        mContext = context
////    }
////
//////    Data
////    var slide_images : IntArray = intArrayOf(
////        R.drawable.icon_car_blind,R.drawable.icon_alert_ear,R.drawable.icon_fall,R.drawable.icon_information
////    )
////
////    var slide_subheader = arrayOf(R.string.subheading_slide_page_1,R.string.subheading_slide_page_2,R.string.subheading_slide_page_3,R.string.subheading_slide_page_4)
////
////    var slide_content = arrayOf(R.string.content_page_1,R.string.content_page_2,R.string.content_page_3,R.string.content_page_4)
////
////    override fun isViewFromObject(view: View, `object`: Any): Boolean {
////        return false
////    }
////
////    override fun instantiateItem(container: ViewGroup, position: Int): Any {
////        val layoutInflater: LayoutInflater = LayoutInflater.from(mContext)
////        val view: View = layoutInflater.inflate(R.layout.slide_content, container, false)
////        val slideSubHeaderView : TextView = view.findViewById(R.id.sub_heading)
////        val slideContentView : TextView = view.findViewById(R.id.content)
////        val slideImageView : ImageView = view.findViewById(R.id.imageView)
////        return super.instantiateItem(container, position)
////    }
////
////    override public fun destroyItem (container: ViewGroup, position: Int,`object`: Any) {
////        container.removeView(`object` as RelativeLayout)
////    }
////}
public class SliderAdapter extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;
    //Array List
    private int[] header = {R.string.heading_slide,R.string.heading_slide,R.string.heading_slide,R.string.heading_slide};
    private int[] subHeader = {R.string.subheading_slide_page_1,R.string.subheading_slide_page_2,R.string.subheading_slide_page_3,R.string.subheading_slide_page_4};
    private int[] description = {R.string.content_page_1,R.string.content_page_2,R.string.content_page_3,R.string.content_page_4};
    private int[] imgRes = {R.drawable.icon_car_blind,R.drawable.icon_alert_ear,R.drawable.icon_fall,R.drawable.icon_information};
    TextView tvHeader;
    TextView tvSubHeader;
    TextView tvDesc;
    ImageView img;
    public SliderAdapter(Context context) {
        this.context = context;
    }

    @Override public boolean isViewFromObject(View view,Object o) {
        return view == o;
    }
    @Override
    public int getCount(){
        return header.length;
    }

    @Override public Object instantiateItem(ViewGroup container,int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_content,container,false);

        tvHeader = view.findViewById(R.id.heading);
        tvSubHeader = view.findViewById(R.id.sub_heading);
        tvDesc = view.findViewById(R.id.content);
        img = view.findViewById(R.id.imageView);

        tvHeader.setText(header[position]);
        tvSubHeader.setText(subHeader[position]);
        tvDesc.setText(description[position]);
        img.setImageResource(imgRes[position]);

        container.addView(view);
        return view;
    }

    @Override public void destroyItem(ViewGroup container,int position, Object object) {
        container.removeView((RelativeLayout)object);
    }

}