package com.love.candy.server;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.love.candy.aidl.Book;
import com.love.candy.aidl.IBookManager;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Created by lichao
 * @desc
 * @time 2018/5/25 14:06
 * 邮箱：lichao@voole.com
 */

public class MyService extends Service {
    //可以解决并发的list
    CopyOnWriteArrayList<Book> books ;
    @Override
    public void onCreate() {
        super.onCreate();
        books = new CopyOnWriteArrayList<>();
        books.add(new Book(0,"开发艺术人生"));
        books.add(new Book(1,"Android群英传"));

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new BookBind();
    }

    /**
     *
     */
    private class  BookBind extends IBookManager.Stub {
        @Override
        public List<Book> getBookList() throws RemoteException {
            return books;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            books.add(book);
            Log.d("lichao"," service addBook " + book.bookName);
        }
    }
}
