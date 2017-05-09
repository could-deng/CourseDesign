package com.example.coursedesign.fragment;

import java.io.File;

import uk.co.senab.photoview.PhotoViewAttacher;
import uk.co.senab.photoview.PhotoViewAttacher.OnPhotoTapListener;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.coursedesign.R;
import com.example.coursedesign.activity.ImagePagerActivity;
import com.example.coursedesign.utils.ImageLoaderHelper;
import com.facebook.drawee.view.SimpleDraweeView;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

/**
 * 单张图片显示Fragment
 */
public class ImageDetailFragment extends Fragment
{
    private static final String TAG = "ImageDetailFragment";
    private String mImageUrl;
    private int localOrNet;
    private SimpleDraweeView mImageView;
    private ProgressBar progressBar;
    private PhotoViewAttacher mAttacher;

    public static ImageDetailFragment newInstance(String imagePath, int localOrNet)
    {
        final ImageDetailFragment instance = new ImageDetailFragment();
        final Bundle bundle = new Bundle();
        bundle.putString("url", imagePath);
        // 路径种类（url或者本地文件路径）
        bundle.putInt("kind", localOrNet);
        instance.setArguments(bundle);
        return instance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mImageUrl = getArguments() != null ? getArguments().getString("url") : null;
        localOrNet = getArguments() != null ? getArguments().getInt("kind") : null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        final View v = inflater.inflate(R.layout.image_detail_fragment, container, false);
        mImageView = (SimpleDraweeView) v.findViewById(R.id.image);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        mAttacher = new PhotoViewAttacher((ImageView)mImageView);
        mAttacher.setOnLongClickListener(new OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View arg0)
            {
                // TODO Auto-generated method stub
                return false;
            }
        });
        mAttacher.setOnPhotoTapListener(new OnPhotoTapListener()
        {
            @Override
            public void onPhotoTap(View arg0, float arg1, float arg2)
            {
                getActivity().finish();
            }
        });

        progressBar = (ProgressBar) v.findViewById(R.id.loading);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        progressBar.setVisibility(View.VISIBLE);
        if (localOrNet == ImagePagerActivity.IMAGE_FROM_NET)
        {
            mImageView.setImageURI(Uri.parse(mImageUrl));
            progressBar.setVisibility(View.GONE);
            mAttacher.update();
        }
        else if (localOrNet == ImagePagerActivity.IMAGE_FROM_LOCAL)
        {
            if (new File(mImageUrl) != null)
            {
                mImageView.setImageURI(Uri.fromFile(new File(mImageUrl)));
                progressBar.setVisibility(View.GONE);
                mAttacher.update();
            }
        }

    }
}
