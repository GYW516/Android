package com.gyw.application.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gyw.application.AboutAppActivity;
import com.gyw.application.CallServiceActivity;
import com.gyw.application.LoginActivity;
import com.gyw.application.PrivatePolicyActivity;
import com.gyw.application.R;
import com.gyw.application.SystemNotifyActivity;
import com.gyw.application.UpdatePwdActivity;
import com.gyw.collector.ActivityCollector;

public class MineFragment extends Fragment {

    private SharedPreferences sp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ActivityCollector.addActivity(getActivity());
        View view = inflater.inflate(R.layout.fragment_mine, container, false);

        //获取sharePreference
        sp = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);

        //修改密码
        view.findViewById(R.id.change_pwd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), UpdatePwdActivity.class);
                startActivity(intent);
            }
        });

        //联系客服
        view.findViewById(R.id.call_service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getActivity(), CallServiceActivity.class));
            }
        });

        //系统通知
        view.findViewById(R.id.system_notify).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SystemNotifyActivity.class));
            }
        });

        //隐私政策
        view.findViewById(R.id.privacy_policy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), PrivatePolicyActivity.class));
            }
        });

        //关于app
        view.findViewById(R.id.about_APP).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AboutAppActivity.class));
            }
        });

        //退出登录
        view.findViewById(R.id.back_app).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(getContext())
                        .setTitle("温馨提示")
                        .setMessage("确定要退出登录吗?")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //删除缓存里的东西
                                SharedPreferences.Editor edit = sp.edit();
                                edit.remove("account");
                                edit.remove("password");
                                edit.remove("is_login");
                                edit.commit();
                                //退出登录
                                getActivity().finish();
                                //打开登录页面
                                Intent intent = new Intent(getActivity(), LoginActivity.class);
                                startActivity(intent);
                            }
                        })
                        .show();
            }
        });
        return view;
    }

}