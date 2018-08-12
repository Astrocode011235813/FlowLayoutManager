package ru.astrocode.sample;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

import ru.astrocode.flm.FLMFlowLayoutManager;

public class ActivityMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int item_spacing = getResources().getDimensionPixelSize(R.dimen.spacing_between_items);
        int lines_spacing = getResources().getDimensionPixelSize(R.dimen.spacing_between_lines);

        final Adapter adapter = new Adapter(this);
        final FLMFlowLayoutManager layoutManager =
                new FLMFlowLayoutManager(FLMFlowLayoutManager.VERTICAL,Gravity.CENTER,item_spacing,lines_spacing);

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private final class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
        private final static int sBtnCloseSize = 16;
        private final static int sBtnCloseLeftMargin = 5;

        private final Context mContext;
        private final int mBtnCloseSize,mBtnCloseLeftMargin;

        private ArrayList<String> mData;


        public Adapter(Context context) {
            mContext = context;
            mData = new ArrayList<>(Arrays.asList(context.getResources().getStringArray(R.array.Countries)));
            mBtnCloseSize = Math.round(sBtnCloseSize*context.getResources().getDisplayMetrics().density);
            mBtnCloseLeftMargin = Math.round(sBtnCloseLeftMargin*context.getResources().getDisplayMetrics().density);
        }

        @NonNull
        @Override
        public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            ConstraintLayout view = new ConstraintLayout(mContext);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            view.setBackgroundResource(R.drawable.shape_chips);

            TextView title = new TextView(mContext);
            title.setGravity(Gravity.CENTER);
            title.setTextColor(Color.BLACK);
            title.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
            title.setId(R.id.textView);

            ConstraintLayout.LayoutParams lp = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);

            view.addView(title,lp);

            AppCompatImageButton imageButton = new AppCompatImageButton(mContext);
            imageButton.setBackgroundResource(R.drawable.shape_chips_close_btn);
            imageButton.setImageResource(R.drawable.ic_close);
            imageButton.setScaleType(ImageView.ScaleType.FIT_XY);
            imageButton.setId(R.id.imageButton);

            lp = new ConstraintLayout.LayoutParams(mBtnCloseSize,mBtnCloseSize);
            lp.leftMargin = mBtnCloseLeftMargin;
            lp.leftToRight = R.id.textView;

            view.addView(imageButton,lp);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {
            holder.mText.setText(mData.get(position));
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        final class ViewHolder extends RecyclerView.ViewHolder {
            TextView mText;
            ImageButton mDeleteButton;

            public ViewHolder(View itemView) {
                super(itemView);

                mText = (TextView)itemView.findViewById(R.id.textView);

                mDeleteButton = (ImageButton)itemView.findViewById(R.id.imageButton);
                mDeleteButton.setVisibility(View.VISIBLE);
                mDeleteButton.setOnClickListener(mListener);
            }

            private final View.OnClickListener mListener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mData.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                }
            };
        }
    }
}
