package com.gyw.application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gyw.Utils.HttpUtils;
import com.gyw.db.UserDbHelper;
import com.gyw.entity.UserInfo;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class CallServiceActivity extends AppCompatActivity {

    private final int VIEW_TYPE = 0xb01;
    private final int VIEW_TYPE_LEFT = -10;
    private final int VIEW_TYPE_RIGHT = -11;

    private final int MESSAGE = 0xb02;

    private ArrayList<HashMap<Integer, Object>> items = null;   //信息数据源
    private SharedPreferences sp;                               //获取当前登录的账户
    private SharedPreferences ap;                               //这是获取聊天记录的
    private String username;                                    //获取当前登录账户的用户名
    private String chat;                                        //缓存聊天文件的名称
    private Gson gson = new Gson();                             //将对象转化为json字符串


    //ai说的话(到时候这括号里是要传参的String)
    private void AiSpeak(String msg){
        HashMap<Integer,Object> map = new HashMap<>();
        map.put(VIEW_TYPE,VIEW_TYPE_LEFT);
        map.put(MESSAGE,msg);
        items.add(map);
    }

    //缓存聊天记录
    private void cache(){
        String itemJson = gson.toJson(items);
        //存储字符串到SharePreference
        SharedPreferences.Editor edit = ap.edit();
        edit.putString("chat",itemJson);
        edit.commit();
    }

    //json数据转换
    private void JsonToBean(ArrayList<HashMap<Integer,Object>> list){
        Integer integer = new Integer(0xb01);
        for (HashMap<Integer, Object> hashMap : list) {
            Object value = hashMap.get(integer);
            if(value instanceof Double){
                Number number = (Number) value;
                int newValue = number.intValue();
                hashMap.put(integer,newValue);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_service);

        //初始化list和item
        final ListView listView = findViewById(R.id.chat_list);
        items = new ArrayList<HashMap<Integer,Object>>();

        //获取当前登录用户的用户名
        sp = getSharedPreferences("user",MODE_PRIVATE);
        username = sp.getString("account", "");
        //每一个用户都有一个唯一的缓存chat
        chat = username + "_chat";
        ap = getSharedPreferences(chat,MODE_PRIVATE);

        //获取聊天记录
        String chatJsonItem = ap.getString("chat", "");
        Type type = new TypeToken<ArrayList<HashMap<Integer, Object>>>() {
        }.getType();
        items = gson.fromJson(chatJsonItem,type);
        JsonToBean(items);

        final MsgAdapter adapter = new MsgAdapter(this,-1);
        listView.setAdapter(adapter);

        final EditText msg = findViewById(R.id.msgEditText);
        //发送信息
        findViewById(R.id.msgSend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //我说的话
                final String message = msg.getText().toString().trim();
                if(TextUtils.isEmpty(message)){
                    Toast.makeText(CallServiceActivity.this, "对不起，您还未发送任何消息！", Toast.LENGTH_SHORT).show();
                    return;
                }
                HashMap<Integer,Object> map = new HashMap<Integer,Object>();
                map.put(VIEW_TYPE,VIEW_TYPE_RIGHT);
                map.put(MESSAGE,message);
                items.add(map);

                new Thread(){
                    public void run(){
                        String msg = HttpUtils.sendMessage(message);
                        //ai说的话
                        AiSpeak(msg);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //适配器更新
                                adapter.notifyDataSetChanged();
                                //自动滑到最底部
                                listView.setSelection(ListView.FOCUS_DOWN);
                            }
                        });
                    }
                }.start();
                //原来的输入框要变为空
                msg.setText(null);

            }
        });

        //清空聊天记录操作
        findViewById(R.id.clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CallServiceActivity.this);
                // 设置标题
                builder.setTitle("温馨提示");
                //设置信息
                builder.setMessage("你确定要清空聊天内容吗？");
                // 设置按钮及其点击监听器
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //清空item
                        items.clear();
                        //适配器更新
                        adapter.notifyDataSetChanged();
                        //做一个缓存
                        cache();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //取消的话不做任何处理，直接return
                        return;
                    }
                });
                // 创建并显示AlertDialog
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        //拨打电话监听
        findViewById(R.id.call_phone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //电话页面是系统自带的
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:18036237965"));
                startActivity(intent);
            }
        });

        //发送短信
        findViewById(R.id.call_message).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //短信页面是系统自带的
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("smsto:18036237965"));
                intent.putExtra("sms_body","你是谁？");
                startActivity(intent);
            }
        });


        //返回到我的界面
        findViewById(R.id.service_call).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cache();
                finish();
            }
        });

    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }


    /*信息适配器*/
    public class MsgAdapter extends ArrayAdapter {

        //Android中用于将XML布局文件转换为对应的视图对象的类
        private LayoutInflater layoutInflater;

        public MsgAdapter(@NonNull Context context, int resource) {
            super(context, resource);
            layoutInflater = LayoutInflater.from(context);
        }

        @Override
        public View getView(int pos, View convertView, ViewGroup parent) {
            int type = getItemViewType(pos);
            String msg = getItem(pos);
            //处理视图，判断消息是用户的还是ai智能助手的
            switch (type) {
                case VIEW_TYPE_LEFT:
                    convertView = layoutInflater.inflate(R.layout.item_chat_left, null);
                    TextView textLeft = (TextView) convertView.findViewById(R.id.textView);
                    textLeft.setText(msg);
                    break;

                case VIEW_TYPE_RIGHT:
                    convertView = layoutInflater.inflate(R.layout.item_chat_right, null);
                    TextView textRight = (TextView) convertView.findViewById(R.id.textView);
                    textRight.setText(msg);
                    break;
            }
            return convertView;
        }

        @Override
        public String getItem(int pos) {
            String s = items.get(pos).get(MESSAGE) + "";
            return s;
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public int getItemViewType(int pos) {
            int type = (Integer) items.get(pos).get(VIEW_TYPE);
            return type;
        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }


    }


}