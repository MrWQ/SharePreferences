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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.prefs.Preferences;

public class MainActivity extends AppCompatActivity {
    private final static String fileName="config";
    private final static String filename = "file.txt";
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
        Button fileRead = (Button) findViewById(R.id.fileRead);
        Button fileWrite = (Button) findViewById(R.id.fileWrite);
        //编辑框
        final EditText editTextName =(EditText)findViewById(R.id.editTextName);
        final EditText editTextNum =(EditText)findViewById(R.id.editTextNum);
        //显示框
        final TextView textView=(TextView)findViewById(R.id.textView);
        //保存 sharepreferfences方式
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
        //读取    sharepreferences方式
        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=preferences.getString(keyName,null);
                String num=preferences.getString(keyNum,null);
                if (name != null || num != null) {
                    textView.setText("name:"+name+"\nnum:"+num);
                    //Toast.makeText(MainActivity.this,"name:"+name+"\nnum:"+num,Toast.LENGTH_LONG).show();
                }else Toast.makeText(MainActivity.this,"无数据",Toast.LENGTH_LONG).show();

            }
        });
        //清除    sharepreferences方式
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
        //读 file 方式
        fileRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputStream in = null;
                try {
                    FileInputStream fileinputstream = openFileInput(filename);
                    in = new BufferedInputStream(fileinputstream);
                    int c;
                    StringBuilder stringbuilder = new StringBuilder("");
                    try {
                        while ((c = in.read()) != -1) {
                            stringbuilder.append((char) c);
                            // Toast.makeText(MainActivity.this,stringbuilder.toString(),Toast.LENGTH_LONG).show();
                            textView.setText(stringbuilder.toString());
                        }
                    } finally {
                        if (in != null) {
                            in.close();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        //写 file方式
        fileWrite.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                OutputStream out = null;
                try {
                    FileOutputStream fileOutputStream = openFileOutput(filename, MODE_APPEND);
                    out = new BufferedOutputStream(fileOutputStream);
                    String content = editTextName.getText().toString();
                    try {
                        out.write(content.getBytes());
                        // out.flush();
                    } finally {
                        if (out != null)
                            out.close();
                        textView.setText("写入" + filename + "成功");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
