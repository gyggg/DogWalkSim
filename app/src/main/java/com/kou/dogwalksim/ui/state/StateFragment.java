package com.kou.dogwalksim.ui.state;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kou.dogwalksim.MainActivity;
import com.kou.dogwalksim.R;
import com.kou.dogwalksim.dog.Bag;
import com.kou.dogwalksim.dog.Item;

import java.util.Map;

public class StateFragment extends Fragment {

    private StateViewModel stateViewModel;
    private RecyclerView rvBag;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        stateViewModel =
                ViewModelProviders.of(this).get(StateViewModel.class);

        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        final TextView tvName = root.findViewById(R.id.tv_name);
        final TextView tvAge = root.findViewById(R.id.tv_age);
        final TextView tvLove = root.findViewById(R.id.tv_love);
        final TextView tvConDistance = root.findViewById(R.id.tv_con_distance);
        rvBag = root.findViewById(R.id.rv_bag);


        rvBag.setHasFixedSize(true);
        rvBag.setLayoutManager(new LinearLayoutManager(this.getContext()));
        rvBag.setAdapter(new ListAdapter(MainActivity.state.getBag()));

        stateViewModel.getmState().observe(getViewLifecycleOwner(), state -> {
            tvName.setText(String.format(state.getName()));
            tvAge.setText(state.getAge());
            tvConDistance.setText(String.format("合計距離：%.2f m",state.getTotalDistance()));
            tvLove.setText(String.format("愛情度：%d / 100", state.getLove()));
        });
        return root;
    }

    static class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

        Bag bag;

        public ListAdapter(Bag bag) {
            this.bag = bag;
        }
        @NonNull
        @Override
        public ListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_bag_item, parent, false);
            final ViewHolder holder = new ViewHolder(view);
            return holder;
        }
        @Override
        public void onBindViewHolder(@NonNull ListAdapter.ViewHolder holder, int position) {
            Map.Entry<Item, Integer> item = (Map.Entry<Item, Integer>) (bag.entrySet().toArray())[position];
            holder.tvName.setText(item.getKey().getName());
            holder.tvNum.setText(String.format("%d　個", item.getValue()));
        }
        @Override
        public int getItemCount() {
            return bag.size();
        }

        static class ViewHolder extends RecyclerView.ViewHolder {
            TextView tvName;
            TextView tvNum;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                tvName = itemView.findViewById(R.id.tv_item_name);
                tvNum = itemView.findViewById(R.id.tv_item_num);
            }
            public TextView getTvName() {
                return tvName;
            }
            public void setTvName(TextView tvName) {
                this.tvName = tvName;
            }
            public TextView getTvNum() {
                return tvNum;
            }
            public void setTvNum(TextView tvNum) {
                this.tvNum = tvNum;
            }
        }
    }
}
