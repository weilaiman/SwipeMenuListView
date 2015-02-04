package com.baoyz.swipemenulistview;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.WrapperListAdapter;

import com.baoyz.swipemenulistview.SwipeMenuListView.OnMenuItemClickListener;
import com.baoyz.swipemenulistview.SwipeMenuView.OnSwipeItemClickListener;

/**
 * 
 * @author baoyz
 * @date 2014-8-24
 * @modify larry.wei@gmail.com
 * @date 2015-2-4
 * 
 */
public class SwipeMenuAdapter implements WrapperListAdapter,
		OnSwipeItemClickListener {

	private ListAdapter mAdapter;
	private Context mContext;
	private OnMenuItemClickListener onMenuItemClickListener;
	private View customMenuView;

	public SwipeMenuAdapter(Context context, ListAdapter adapter) {
		this(context, adapter, null);
	}
	
	public SwipeMenuAdapter(Context context, ListAdapter adapter, View menuView) {
		mAdapter = adapter;
		mContext = context;
		customMenuView = menuView;
	}

	@Override
	public int getCount() {
		return mAdapter.getCount();
	}

	@Override
	public Object getItem(int position) {
		return mAdapter.getItem(position);
	}

	@Override
	public long getItemId(int position) {
		return mAdapter.getItemId(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		SwipeMenuLayout layout = null;
		if (convertView == null) {
			View contentView = mAdapter.getView(position, convertView, parent);
			SwipeMenu menu = new SwipeMenu(mContext);
			menu.setViewType(mAdapter.getItemViewType(position));
			createMenu(menu);
			SwipeMenuView menuView = new SwipeMenuView(menu,
					(SwipeMenuListView) parent);
			menuView.setOnSwipeItemClickListener(this);
			SwipeMenuListView listView = (SwipeMenuListView) parent;
			layout = new SwipeMenuLayout(contentView, menuView,
					listView.getCloseInterpolator(),
					listView.getOpenInterpolator());
			layout.setPosition(position);
		} else {
			layout = (SwipeMenuLayout) convertView;
			layout.closeMenu();
			layout.setPosition(position);
			View view = mAdapter.getView(position, layout.getContentView(),
					parent);
		}
		updateMenuView(position, layout.getMenuView());
		return layout;
	}

	/**
	 * TO BE OVERRIDE
	 * */
	public void updateMenuView(int position, SwipeMenuView menuView) {
		
	}
	
	/**
	 * TO BE OVERRIDE
	 * */
	public void createMenu(SwipeMenu menu) {
		
	}

	@Override
	public void onItemClick(SwipeMenuView view, SwipeMenu menu, int index) {
		if (onMenuItemClickListener != null) {
			onMenuItemClickListener.onMenuItemClick(view.getPosition(), menu,
					index);
		}
	}

	public void setOnMenuItemClickListener(
			OnMenuItemClickListener onMenuItemClickListener) {
		this.onMenuItemClickListener = onMenuItemClickListener;
	}

	@Override
	public void registerDataSetObserver(DataSetObserver observer) {
		mAdapter.registerDataSetObserver(observer);
	}

	@Override
	public void unregisterDataSetObserver(DataSetObserver observer) {
		mAdapter.unregisterDataSetObserver(observer);
	}

	@Override
	public boolean areAllItemsEnabled() {
		return mAdapter.areAllItemsEnabled();
	}

	@Override
	public boolean isEnabled(int position) {
		return mAdapter.isEnabled(position);
	}

	@Override
	public boolean hasStableIds() {
		return mAdapter.hasStableIds();
	}

	@Override
	public int getItemViewType(int position) {
		return mAdapter.getItemViewType(position);
	}

	@Override
	public int getViewTypeCount() {
		return mAdapter.getViewTypeCount();
	}

	@Override
	public boolean isEmpty() {
		return mAdapter.isEmpty();
	}

	@Override
	public ListAdapter getWrappedAdapter() {
		return mAdapter;
	}

}
