package com.example.libapp;



import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
//import android.widget.Toast;

public class BookListActivity extends Activity implements OnItemClickListener {
    /*----ListView MVCʵ��----*/
    // model
	
	public final static String EXTRA_MESSAGE1="com.example.test.MESSAGE1";
	public final static String EXTRA_MESSAGE2="com.example.test.MESSAGE2";
    List<String> data;
    // view
    ListView lv;
    // controller
    ArrayAdapter<String> adapter;
    int size = 3;
    // ��ʼ�����
    private void initWidget() {
        lv = (ListView) findViewById(R.id.list);
        lv.setOnItemClickListener(this);
    }
    // ��ʼ��������
    private void initData() {
        if (lv == null)
            return;
        // ��һ������ȡ����Դ��model��
        data = new ArrayList<String>();
        appendData();
        adapter = new ArrayAdapter<String>(this, R.layout.simple_text,
                R.id.text1, data);
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             

        lv.setAdapter(adapter);
    }
    // �������
    private void appendData() {
        if (data == null)
            return;
        bookList bkList = new bookList();
        for(int i=0;i<bkList.number;i++)
        {
        	data.add(bkList.name[i]);
        }
        
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msearch);
        initWidget();
        initData();
    }
    
    @Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
            long id) {
    	Intent intent = new Intent(this, DisplayMessageActivity.class);
    	intent.putExtra(EXTRA_MESSAGE1, "item");
    	intent.putExtra(EXTRA_MESSAGE2, parent.getItemAtPosition(position).toString());
    	startActivity(intent);

    }
}
