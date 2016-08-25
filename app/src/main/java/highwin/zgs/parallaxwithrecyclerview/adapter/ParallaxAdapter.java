package highwin.zgs.parallaxwithrecyclerview.adapter;

import android.content.Context;
import android.support.annotation.IntDef;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

/**
 * User: zgsHighwin
 * Email: 799174081@qq.com Or 799174081@gmail.com
 * Description:
 * Create-Time: 2016/8/25 8:51
 */
public class ParallaxAdapter extends RecyclerView.Adapter<ParallaxAdapter.ParallaxHolder> {

    private View mHeaderView;
    private View mFooterView;
    private LayoutInflater mInflater;

    private List<String> mList;

    public ParallaxAdapter(Context context, List<String> list) {
        this.mList = list;
        mInflater = LayoutInflater.from(context);
    }

    private static final int HEADER_VIWE = 0X00000001;
    private static final int FOOTER_VIWE = 0X00000002;
    private static final int NORMALER_VIWE = 0X00000003;

    @IntDef({HEADER_VIWE, FOOTER_VIWE, NORMALER_VIWE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface YTPE_MODE {
    }

    /**
     * add header view
     *
     * @param v
     */
    public void addHeaderView(View v) {
        if (v == null) {
            throw new IllegalArgumentException("header view must be not null");
        }
        this.mHeaderView = v;
        notifyItemInserted(0);
    }

    /**
     * add footer view
     *
     * @param v
     */
    public void addFooterView(View v) {
        if (v == null) {
            throw new IllegalArgumentException("footer view must be not null");
        }
        if (mList == null) {
            throw new NullPointerException("list is null");
        }
        this.mFooterView = v;

        if (mList.size() == 0) {
            notifyItemInserted(0);
        } else {
            notifyItemInserted(mList.size() - 1);
        }
    }

    @ParallaxAdapter.YTPE_MODE
    @Override
    public int getItemViewType(int position) {
        if (mHeaderView == null && mFooterView == null) {
            return ParallaxAdapter.NORMALER_VIWE;
        }

        if (mHeaderView != null && position == 0) {
            return ParallaxAdapter.HEADER_VIWE;
        }

        if (mFooterView != null && position == getItemCount() - 1) {
            return ParallaxAdapter.FOOTER_VIWE;
        }

        return ParallaxAdapter.NORMALER_VIWE;
    }

    @Override
    public ParallaxHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeaderView != null && viewType == ParallaxAdapter.HEADER_VIWE) {
            return new ParallaxHolder(mHeaderView);
        }

        if (mFooterView != null && viewType == ParallaxAdapter.FOOTER_VIWE) {
            return new ParallaxHolder(mFooterView);
        }
        View normalView = mInflater.inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ParallaxHolder(normalView);
    }

    @Override
    public void onBindViewHolder(ParallaxHolder holder, int position) {
        if (getItemViewType(position) == ParallaxAdapter.NORMALER_VIWE) {
            holder.tv.setText(mList.get(position-1));
        } else if (getItemViewType(position) == ParallaxAdapter.HEADER_VIWE) {
            return;
        } else if (getItemViewType(position) == ParallaxAdapter.FOOTER_VIWE) {
            return;
        } else {

        }
    }

    @Override
    public int getItemCount() {
        if (mHeaderView == null && mFooterView == null) {
            return mList.size();
        } else if (mHeaderView == null && mFooterView != null) {
            return mList.size() + 1;
        } else if (mHeaderView != null && mFooterView == null) {
            return mList.size() + 1;
        } else {
            return mList.size() + 2;
        }
    }

    public class ParallaxHolder extends RecyclerView.ViewHolder {
        TextView tv;

        public ParallaxHolder(View itemView) {
            super(itemView);
            if (itemView == mHeaderView || itemView == mFooterView) {
                return;
            } else {
                tv = (TextView) itemView.findViewById(android.R.id.text1);
            }
        }
    }
}
