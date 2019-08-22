package com.example.tenmscardbutton;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class TenMSButton extends CardView {

    private float DEFAULT_IMAGE_LOGO_WIDTH = dpToPx(40);
    private float DEFAULT_IMAGE_LOGO_HEIGHT = dpToPx(24);
    private float DEFAULT_CORNAR_RADIUS = dpToPx(6);
    private TextView textView;
    private ImageView imageView;
    private Drawable cardBackground;
    private ConstraintLayout relativeLayout;
    private Context context;
    private int defaultTextColor=0;
    private int defaultCardBackground;
    private ImageView imageView_left, imageView_right;
    private float imageLogoWidth, imageLogoHeight;
    private int leftImageSrc,rightImageSrc;
    private final int DEFAULT_IMAGE_RESOURCE = 0;


    public TenMSButton(@NonNull Context context) {
        super(context);

        init();


    }

    public TenMSButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
        TypedArray arr = context.obtainStyledAttributes(attrs, R.styleable.MyCustomButtonView);
        readArray(arr);
        arr.recycle();
    }

    public TenMSButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();

        TypedArray arr = context.obtainStyledAttributes(attrs, R.styleable.MyCustomButtonView);
        readArray(arr);
        arr.recycle();
    }

    public void init() {

        LayoutInflater.from(getContext()).inflate(R.layout.my_custom_view, this);
        relativeLayout = findViewById(R.id.card_relative);
        textView = findViewById(R.id.title_tv);
        imageView_right = findViewById(R.id.right_iv);
        imageView_left = findViewById(R.id.left_iv);
        defaultTextColor = ContextCompat.getColor(getContext(), android.R.color.black);
        defaultCardBackground =  ContextCompat.getColor(getContext(), R.color.white);


    }
    private void readArray(TypedArray arr) {
        int cornarRadius = arr.getInteger(R.styleable.MyCustomButtonView_cornerRadius, (int) DEFAULT_CORNAR_RADIUS);


        boolean nameBold = arr.getBoolean(R.styleable.MyCustomButtonView_nameBold, false);
        boolean hidePictureRight = arr.getBoolean(R.styleable.MyCustomButtonView_hidePictureRight, true);
        boolean hidePictureLeft = arr.getBoolean(R.styleable.MyCustomButtonView_hidePictureLeft, true);
        leftImageSrc = arr.getResourceId(R.styleable.MyCustomButtonView_leftImageSrc, DEFAULT_IMAGE_RESOURCE);
        rightImageSrc = arr.getResourceId(R.styleable.MyCustomButtonView_rightImageSrc, DEFAULT_IMAGE_RESOURCE);
        String text = arr.getString(R.styleable.MyCustomButtonView_text);
        imageLogoWidth = arr.getDimension(R.styleable.MyCustomButtonView_imageLogoWidthDp, DEFAULT_IMAGE_LOGO_WIDTH);
        imageLogoHeight = arr.getDimension(R.styleable.MyCustomButtonView_imageLogoHeightDp, DEFAULT_IMAGE_LOGO_HEIGHT);

        Log.d("ARR_", "readArray: "+rightImageSrc);
        Log.d("ARR_", "readArray: "+leftImageSrc);
        if (!TextUtils.isEmpty(text)){
            load(text, false);
        }

        if (nameBold) {
            textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
        }
        if (hidePictureLeft) {
            imageView_left.setVisibility(View.INVISIBLE);
        }else{
            imageView_left.setVisibility(View.VISIBLE);
        }
        if (hidePictureRight) {
            imageView_right.setVisibility(View.INVISIBLE);
        }else{
            imageView_right.setVisibility(View.VISIBLE);
        }
        cardBackground = getMultiColourAttr(getContext(), arr, R.styleable.MyCustomButtonView_cardBackground, defaultCardBackground);
        relativeLayout.setBackground(cardBackground);

        if (leftImageSrc == DEFAULT_IMAGE_RESOURCE)
            imageView_left.setVisibility(INVISIBLE);
        else {
            imageView_left.setImageResource(leftImageSrc);
            imageView_left.setVisibility(VISIBLE);
        }
        if (rightImageSrc == DEFAULT_IMAGE_RESOURCE)
            imageView_right.setVisibility(INVISIBLE);
        else {
            imageView_right.setImageResource(rightImageSrc);
            imageView_right.setVisibility(VISIBLE);
        }


        int color = arr.getColor(R.styleable.MyCustomButtonView_textColor, defaultTextColor);
        textView.setTextColor(color);

        ViewGroup.LayoutParams layoutParamsRight = imageView_right.getLayoutParams();
        layoutParamsRight.width = (int) imageLogoWidth;
        layoutParamsRight.height = (int) imageLogoHeight;
        imageView_right.setLayoutParams(layoutParamsRight);
        imageView_right.setAdjustViewBounds(true);

        ViewGroup.LayoutParams layoutParamsLeft = imageView_right.getLayoutParams();
        layoutParamsLeft.width = (int) imageLogoWidth;
        layoutParamsLeft.height = (int) imageLogoHeight;
        imageView_right.setLayoutParams(layoutParamsLeft);
        imageView_right.setAdjustViewBounds(true);

        setRadius(dpToPx(cornarRadius));
    }

    public void load(String text, int img) {

        textView.setText(text);
        imageView_right.setImageResource(img);

    }
    public void load(String text, boolean isChecked) {
        textView.setText(text);
        if (isChecked) {
            imageView_right.setVisibility(VISIBLE);
        }else{
            imageView_right.setVisibility(INVISIBLE);
        }
    }
    public void toggleCheck() {
        int isVisible = imageView_right.getVisibility();
        if (isVisible == View.VISIBLE) {
            imageView_right.setVisibility(View.INVISIBLE);
        }else{
            imageView_right.setVisibility(View.VISIBLE);
        }
    }
    public void checkedBtn(boolean isChecked) {
        if (isChecked) {
            imageView_right.setVisibility(View.VISIBLE);
        }else{
            imageView_right.setVisibility(View.INVISIBLE);
        }
    }

    public void setClickListener(View.OnClickListener onClickListener)
    {
        this.setOnClickListener(onClickListener);
    }
    protected static Drawable getMultiColourAttr(@NonNull Context context, @NonNull TypedArray typed,
                                                 int index, int defaultColor)
    {
        TypedValue colorValue = new TypedValue();
        typed.getValue(index, colorValue);

        if (colorValue.type == TypedValue.TYPE_NULL)
            return new ColorDrawable(defaultColor);

        else if ((colorValue.type == TypedValue.TYPE_REFERENCE) || (colorValue.type == TypedValue.TYPE_STRING))
            return ContextCompat.getDrawable(context, colorValue.resourceId);

        else
            // It must be a single color
            return new ColorDrawable(colorValue.data);
    }
    public void setCenterText() {
        textView.setGravity(Gravity.CENTER);
    }
    public void setLeftImageWidth(int width)
    {
        // Set image logo height
        imageLogoWidth = dpToPx(width);
        ViewGroup.LayoutParams layoutParams = imageView_left.getLayoutParams();
        layoutParams.width = (int) imageLogoWidth;
        layoutParams.height = (int) imageLogoHeight;
        imageView_left.setLayoutParams(layoutParams);
        imageView_left.setAdjustViewBounds(true);
    }

    public void setLeftImageHeight(int height)
    {
        // Set image logo height
        imageLogoHeight = height;
        ViewGroup.LayoutParams layoutParams = imageView_left.getLayoutParams();
        layoutParams.width = (int) imageLogoWidth;
        layoutParams.height = (int) imageLogoHeight;
        imageView_left.setLayoutParams(layoutParams);
        imageView_left.setAdjustViewBounds(true);
    }
    public void setRightImageWidth(int width)
    {
        // Set image logo height
        imageLogoWidth = dpToPx(width);
        ViewGroup.LayoutParams layoutParams = imageView_right.getLayoutParams();
        layoutParams.width = (int) imageLogoWidth;
        layoutParams.height = (int) imageLogoHeight;
        imageView_right.setLayoutParams(layoutParams);
        imageView_right.setAdjustViewBounds(true);
    }

    public void setRightImageHeight(int height)
    {
        // Set image logo height
        imageLogoHeight = height;
        ViewGroup.LayoutParams layoutParams = imageView_right.getLayoutParams();
        layoutParams.width = (int) imageLogoWidth;
        layoutParams.height = (int) imageLogoHeight;
        imageView_right.setLayoutParams(layoutParams);
        imageView_right.setAdjustViewBounds(true);
    }
    public int dpToPx(int dp)
    {
        float density = getContext().getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }
}
