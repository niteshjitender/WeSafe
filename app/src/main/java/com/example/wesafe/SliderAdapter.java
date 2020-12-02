package com.example.wesafe;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class SliderAdapter extends PagerAdapter {
    Context context ;
    LayoutInflater layoutInflater ;

    public SliderAdapter(Context context){
        this.context = context ;
    }

    //Arrays
    public int[] slide_images = {
            R.drawable.ambulance,
            R.drawable.location,
            R.drawable.family
    } ;

    public String[] slide_headings = {
            "Accident Detection",
            "Location Retrieval",
            "Informing Loved Ones"
    } ;

    public String[] slide_desc = {
            "WeSafe, A Road Safety Application. In case of any mishappening it can detect that...",
            "It will get the current location accident even if internet is not working...",
            "Inform the loved ones by sending message and your current location..."
    } ;
    @Override
    public int getCount() {
        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (RelativeLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE) ;
        View view = layoutInflater.inflate(R.layout.slide_layout, container, false) ;

        @SuppressLint("WrongViewCast") ImageView slideImageView = (ImageView) view.findViewById(R.id.imageView) ;
        TextView slideHeading = (TextView) view.findViewById(R.id.tvHeading) ;
        TextView slideDescription = (TextView) view.findViewById(R.id.tvDescription) ;

        slideImageView.setImageResource(slide_images[position]);
        slideHeading.setText(slide_headings[position]);
        slideDescription.setText(slide_desc[position]);

        container.addView(view) ;

        return view ;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout)object) ;
    }
}
