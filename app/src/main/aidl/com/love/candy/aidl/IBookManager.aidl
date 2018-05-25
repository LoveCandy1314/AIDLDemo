// IBookManager.aidl
package com.love.candy.aidl;
import com.love.candy.aidl.Book;
// Declare any non-default types here with import statements

interface IBookManager {
        List<Book> getBookList();
        void addBook(in Book book);
}
