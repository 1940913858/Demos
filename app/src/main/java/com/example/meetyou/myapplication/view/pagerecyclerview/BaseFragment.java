package com.example.meetyou.myapplication.view.pagerecyclerview;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ckr.pageview.transform.BaseTransformer;


/**
 * Created by PC大佬 on 2018/1/14.
 */

public abstract class BaseFragment extends Fragment {
	protected View view;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		view = inflater.inflate(getContentLayoutId(), container, false);
		init();
		return view;
	}

	@CallSuper
	@Override
	public void onResume() {
		super.onResume();
		if (getUserVisibleHint()) {
			onVisible();
		}
	}

	protected abstract int getContentLayoutId();

	protected abstract void init();

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		if (isVisibleToUser&&isResumed()) {
			onVisible();
		} else {
			onInvisible();
		}
	}

	protected void onVisible() {
	}

	protected void onInvisible() {
	}

	protected void addData(int index) {}

	protected void jumpToPage(int page) {}

	public abstract void refreshFragment(BaseTransformer baseTransformer);

}
