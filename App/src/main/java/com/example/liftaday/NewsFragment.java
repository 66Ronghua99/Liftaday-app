package com.example.liftaday;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.liftaday.utils.Helper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static com.example.liftaday.SplashScreen.content;
import static com.example.liftaday.SplashScreen.description;
import static com.example.liftaday.SplashScreen.imageUrl;
import static com.example.liftaday.SplashScreen.title;
import static com.example.liftaday.SplashScreen.url;

public class NewsFragment extends Fragment {
    private View view;
    private ViewPager viewPager;
    private List newsList;//Arraylist is completely different from List

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_news, container, false);
        init();
        return view;
    }

    private void init(){
        viewPager = (ViewPager)view.findViewById(R.id.viewPager);
        //inflate three news view into view1 2 3 respectively
        LayoutInflater layoutInflater = getLayoutInflater();
        View view1 = layoutInflater.inflate(R.layout.news1,null);
        View view2 = layoutInflater.inflate(R.layout.news2,null);
        View view3 = layoutInflater.inflate(R.layout.news3,null);


        //add views into the list
        newsList = new ArrayList<View>();
        newsList.add(view1);
        newsList.add(view2);
        newsList.add(view3);

        ImageView newsImageView1 = (ImageView)view1.findViewById(R.id.imageView4);
        ImageView newsImageView2 = (ImageView)view2.findViewById(R.id.imageView5);
        ImageView newsImageView3 = (ImageView)view3.findViewById(R.id.imageView6);
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        Callable c1 = new MyCallable(SplashScreen.imageUrl[0]);
        Callable c2 = new MyCallable(SplashScreen.imageUrl[1]);
        Callable c3 = new MyCallable(SplashScreen.imageUrl[2]);
        Future f1 = executorService.submit(c1);
        Future f2 = executorService.submit(c2);
        Future f3 = executorService.submit(c3);
        try {
            Bitmap bimage1 = (Bitmap) f1.get();
            Bitmap bimage2 = (Bitmap)f2.get();
            Bitmap bimage3 = (Bitmap)f3.get();
            newsImageView1.setImageBitmap(bimage1);
            newsImageView2.setImageBitmap(bimage2);
            newsImageView3.setImageBitmap(bimage3);
        } catch (Exception e) {
            e.printStackTrace();
        }
        TextView title1 = (TextView)view1.findViewById(R.id.textView14);
        TextView title2 = (TextView)view2.findViewById(R.id.textView16);
        TextView title3 = (TextView)view3.findViewById(R.id.textView18);
        TextView content1 = (TextView)view1.findViewById(R.id.textView15);
        TextView content2 = (TextView)view2.findViewById(R.id.textView17);
        TextView content3 = (TextView)view3.findViewById(R.id.textView19);
        if(title1==null){
            Log.i("tag","title is null");
        }
        title1.setText(SplashScreen.title[0]);
        title2.setText(SplashScreen.title[1]);
        title3.setText(SplashScreen.title[2]);
        if(content[0]==null)
            content1.setText(String.format("%s \n\nSee more details on BBC News.", SplashScreen.description[0]));
        else
            content1.setText(String.format("  Abstract: %s\n  %s", description[0], SplashScreen.content[0]));
        if(content[1]==null)
            content2.setText(String.format("%s \n\nSee more details on BBC News.", SplashScreen.description[1]));
        else
            content2.setText(String.format("  Abstract: %s\n  %s", description[1], SplashScreen.content[1]));
        if(content[2]==null)
            content3.setText(String.format("%s \n\nSee more details on BBC News.", SplashScreen.description[2]));
        else
            content3.setText(String.format("  Abstract: %s\n  %s", description[2], SplashScreen.content[2]));
        content1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(url[0]);
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                intent.setData(uri);
                startActivity(intent);
            }
        });
        content2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(url[1]);
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                intent.setData(uri);
                startActivity(intent);
            }
        });
        content3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(url[2]);
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                intent.setData(uri);
                startActivity(intent);
            }
        });

        //use adapter to inflate the viewpager
        PagerAdapter pagerAdapter = new NewsAdapter(newsList);
//        Log.i("tag",Integer.toString(newsList.size()));
        viewPager.setAdapter(pagerAdapter);
    }


    private Bitmap getBitmapFromURL(String src) {
        try {
            Log.e("src",src);
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            Log.e("Bitmap","returned");
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Exception",e.getMessage());
            return null;
        }
    }
    class MyCallable implements Callable {
        private String url;

        MyCallable(String oid) {
            this.url = oid;
        }

        @Override
        public Object call() throws Exception {
            Bitmap bimage = getBitmapFromURL(url);
            return bimage;
        }
    }



}
