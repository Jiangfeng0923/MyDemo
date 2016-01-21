package com.feng.demo.mydemos;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.feng.demo.mydemos.layout.SymmetricalLayout;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;

import static com.feng.demo.utils.MyLogUtils.JLog;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
    }

    public void onClick(View v) {
       // testSymmetricalLayout(v);
    }

    private void round() {
        Math.round(11.5);
    }

    /*向一大小为100的数组中随机插入1-100的自然数（无重复）*/
    private void random() {
        ArrayList<Integer> array = new ArrayList<>();
        Random random = new Random();
        int times = 100;
        while (times > 0) {
            int next = 1 + random.nextInt(100);
            Integer integer = Integer.valueOf(next);
            if (!array.contains(integer)) {
                array.add(integer);
                times--;
            }
        }
        JLog("size=" + array.size());
        JLog(array);
    }


    private void readFile() {
        try {
            FileInputStream inStream = this.openFileInput("a.txt");
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length = -1;
            while ((length = inStream.read(buffer)) != -1) {
                stream.write(buffer, 0, length);
            }
            stream.close();
            inStream.close();
            EditText editText = (EditText) findViewById(R.id.edit);
            editText.setText(stream.toString());
            Toast.makeText(TestActivity.this, "Loaded", Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            return;
        }
    }

    public void saveFile() {
        try {
            FileOutputStream outStream = this.openFileOutput("a.txt", Context.MODE_WORLD_READABLE);
            EditText editText = (EditText) findViewById(R.id.edit);
            byte[] bytes = editText.getText().toString().getBytes();
            outStream.write(bytes);
            outStream.close();
            Toast.makeText(TestActivity.this, "Saved", Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            return;
        } catch (IOException e) {
            return;
        }
    }

    private void sharedPreferences() {
        SharedPreferences sp = getSharedPreferences("MySharedPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("key1", 9);
        editor.apply();
    }

    private void hashSet() {
        HashSet<String> set = new HashSet<>();
        set.add("123");
        set.add("abc");
        set.add("123");
        JLog(set);
    }

    private void linkedList() {
        LinkedList<String> linkedList = new LinkedList<>();
        linkedList.add("1234");
        linkedList.add("abcd");
        linkedList.toString();
        JLog(linkedList);
    }


}
