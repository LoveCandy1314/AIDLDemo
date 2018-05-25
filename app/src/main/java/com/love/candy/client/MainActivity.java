package com.love.candy.client;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.love.candy.R;
import com.love.candy.aidl.Book;
import com.love.candy.aidl.IBookManager;
import com.love.candy.server.MyService;

import java.util.concurrent.CopyOnWriteArrayList;

public class MainActivity extends AppCompatActivity {
    IBookManager iBookManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this, MyService.class);
        bindService(intent,connection,BIND_AUTO_CREATE);
    }

    /**
     * 连接器，通过调用Binder的方法去操作对象
     */
    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iBookManager =  IBookManager.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            iBookManager=null;
        }
    };

    public void addBook(View view) {
        Book book = new Book(3,"啥破玩意");
        try {
            if (iBookManager!=null){
                iBookManager.addBook(book);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void listBook(View view) {
        if(iBookManager!=null){
            CopyOnWriteArrayList<Book> arrayList = new CopyOnWriteArrayList();
            try {
                arrayList = (CopyOnWriteArrayList<Book>) iBookManager.getBookList();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            if (arrayList!=null){
                for (Book book : arrayList) {
                    Log.d("lichao","bookName is "+book.bookName);
                }
            }
        }
    }
}
