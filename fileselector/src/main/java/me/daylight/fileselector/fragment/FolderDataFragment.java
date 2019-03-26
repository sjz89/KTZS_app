package me.daylight.fileselector.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import me.daylight.fileselector.R;
import me.daylight.fileselector.adapter.FolderDataRecycleAdapter;
import me.daylight.fileselector.model.FileInfo;

/**
 * Created by yis on 2018/4/17.
 */

public class FolderDataFragment extends Fragment {

    private RecyclerView rvDoc;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_doc, container, false);
        rvDoc = rootView.findViewById(R.id.rv_doc);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initData();
    }

    private void initData() {
        Bundle bundle = this.getArguments();

        List<FileInfo> data = bundle.getParcelableArrayList("file_data");
        boolean isImage = bundle.getBoolean("is_image");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        //设置RecyclerView 布局
        rvDoc.setLayoutManager(linearLayoutManager);
        FolderDataRecycleAdapter pptListAdapter = new FolderDataRecycleAdapter(getActivity(), data, isImage);
        pptListAdapter.setOnItemClickListener(((view, position) -> {
            Intent intent=new Intent();
            intent.putExtra("filepath",data.get(position).getFilePath());
            getActivity().setResult( Activity.RESULT_OK,intent);
            getActivity().finish();
        }));
        rvDoc.setAdapter(pptListAdapter);
    }
}
