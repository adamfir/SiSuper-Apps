package com.example.vitorizkiimanda.sisuper_apps.provider;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vitorizkiimanda.sisuper_apps.R;

public class OnboardingAdapter extends PagerAdapter{

    Context context;
    LayoutInflater layoutInflater;

    public OnboardingAdapter(Context context){
        this.context = context;
    }

    //Arrays
    public int[] onboarding_images = {
            R.drawable.maskot,
            R.drawable.onboard2,
            R.drawable.onboard3,
            R.drawable.onboard4,
            R.drawable.onboard5
    };

    public String[] onboarding_headings = {
            "Sistem Informasi Sinkronasi",
            "Kegiatan",
            "Undangan",
            "Pajak & Zakat",
            "Daftar Harga"
    };

    public String[] onboarding_description = {
            "UMKM-Pemerintah-Rakyat",
            "Temukan Informasi Kegiatan Terkini dari Pemerintah",
            "Dapatkan Undangan Pelatihan dari Pemerintah",
            "Ketahui Jumlah Pajak dan Zakat Terkini dari Usaha",
            "Cek Harga Bahan Baku Terkini dan Buat Daftar Sesuai Kebutuhan"
    };

    //Arrays ^^^

    @Override
    public int getCount() {
        return onboarding_headings.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.onboarding_item, container, false);

        ImageView onboardingImangeVIew =  view.findViewById(R.id.onboarding_image);
        TextView onboardingHeading = view.findViewById(R.id.onboarding_heading);
        TextView onboardingDescription = view.findViewById(R.id.onboarding_description);

        onboardingImangeVIew.setImageResource(onboarding_images[position]);
        onboardingHeading.setText(onboarding_headings[position]);
        onboardingDescription.setText(onboarding_description[position]);

        container.addView(view);

        return view;
    };

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
