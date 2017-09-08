package com.example.administrator.sharep;

import android.content.SharedPreferences;
import android.preference.Preference;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.prefs.Preferences;

public class MainActivity extends AppCompatActivity {
    private final static String fileName="config";
    private  final static String keyNum ="num";
    private final static String keyName ="name";
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //实例
        preferences =getSharedPreferences(fileName,MODE_PRIVATE);
        editor=preferences.edit();
        //按钮
        Button read=(Button) findViewById(R.id.read);
        Button write=(Button) findViewById(R.id.write);
        Button clear=(Button)findViewById(R.id.clear);
        //编辑框
        final EditText editTextName =(EditText)findViewById(R.id.editTextName);
        final EditText editTextNum =(EditText)findViewById(R.id.editTextNum);
        //显示框
        final TextView textView=(TextView)findViewById(R.id.textView);
        //保存
        write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Name=editTextName.getText().toString();
                String Num=editTextNum.getText().toString();
                editor.putString(keyNum,Num);
                editor.putString(keyName,Name);
                editor.apply();
                String name=preferences.getString(keyName,null);
                String num=preferences.getString(keyNum,null);
                textView.setText("name:"+name+"\nnum:"+num);
            }
        });
        //读取
        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=preferences.getString(keyName,null);
                String num=preferences.getString(keyNum,null);
                if(name!=null && num!=null){
                    textView.setText("name:"+name+"\nnum:"+num);
                    //Toast.makeText(MainActivity.this,"name:"+name+"\nnum:"+num,Toast.LENGTH_LONG).show();
                }else Toast.makeText(MainActivity.this,"无数据",Toast.LENGTH_LONG).show();

            }
        });
        //清除
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=preferences.getString(keyName,null);
                String num=preferences.getString(keyNum,null);
                editor.clear();
                editor.apply();
                textView.setText("null");
            }
        });
    }
}
