package com.example.meetyou.myapplication.view.pagerecyclerview.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ckr.pageview.adapter.BasePageAdapter;
import com.example.meetyou.myapplication.R;
import com.example.meetyou.myapplication.view.pagerecyclerview.model.Item;


/**
 * Created by PC大佬 on 2018/1/14.
 */

public class MainAdapter extends BasePageAdapter<Item, MainAdapter.MainHolder> {
	private static final String TAG = "MainAdapter";
	public static final int MIN_VALUE = 6;
	private boolean isShowDeleteIcon;
	private int mLayoutId;

	public MainAdapter(Context context, @LayoutRes int itemLayoutId) {
		super(context);
		mLayoutId = itemLayoutId;
	}

	@Override
	public int getItemCount() {
		int itemCount = super.getItemCount();
		if (mIsLooping) {
			return itemCount == 0 ? 0 : Math.max(itemCount * 2, MIN_VALUE);
		}
		return itemCount;
	}

	@Override
	protected int getLayoutId(int viewType) {
		return mLayoutId;
	}

	@Override
	protected MainHolder getViewHolder(View itemView, int viewType) {
		return new MainHolder(itemView);
	}

	@Override
	protected void convert(MainHolder holder, final int position, Item originItem, final int adjustedPosition, Item item) {
		if (item == null) {
			holder.relativeLayout.setVisibility(View.INVISIBLE);
			holder.itemView.setOnLongClickListener(null);
			holder.itemView.setOnClickListener(null);
			holder.imageButton.setOnClickListener(null);
		} else {
			holder.relativeLayout.setVisibility(View.VISIBLE);
			holder.textView.setText(item.getName());

			holder.itemView.setOnLongClickListener(new OnItemLongClickListener(adjustedPosition));
			holder.itemView.setOnClickListener(new OnItemClickListener(adjustedPosition));
			holder.imageButton.setOnClickListener(new OnItemClickListener(adjustedPosition));
			holder.imageButton.setVisibility(isShowDeleteIcon ? View.VISIBLE : View.GONE);
		}
	}

	class MainHolder extends RecyclerView.ViewHolder {
		private TextView textView;
		private RelativeLayout relativeLayout;
		private ImageButton imageButton;
		private ImageView imageView;

		public MainHolder(View itemView) {
			super(itemView);
			textView = (TextView) itemView.findViewById(R.id.textView);
			relativeLayout = (RelativeLayout) itemView.findViewById(R.id.relativeLayout);
			imageButton = (ImageButton) itemView.findViewById(R.id.imageButton);
			imageView = (ImageView) itemView.findViewById(R.id.imageView);
		}
	}

	class OnItemLongClickListener implements View.OnLongClickListener {
		private int mPosition;

		public OnItemLongClickListener(int position) {
			mPosition = position;
		}

		@Override
		public boolean onLongClick(View v) {
			isShowDeleteIcon = !isShowDeleteIcon;
			notifyDataSetChanged();
			return true;
		}
	}

	class OnItemClickListener implements View.OnClickListener {
		private int mTargetPos;

		public OnItemClickListener(int adjustedPos) {
			mTargetPos = adjustedPos;
		}

		@Override
		public void onClick(View v) {
			int id = v.getId();
			if (id == R.id.imageButton) {
				removeItem(mTargetPos);
			} else {
				Toast.makeText(mContext, "position:" + mTargetPos, Toast.LENGTH_SHORT).show();
			}
		}
	}
}
