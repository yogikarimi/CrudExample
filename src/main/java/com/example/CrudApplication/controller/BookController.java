package com.example.CrudApplication.controller;

import com.example.CrudApplication.model.Book;
import com.example.CrudApplication.repository.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class BookController {

    @Autowired
    private BookRepo bookRepo;


    @GetMapping("/getAllBooks")
    public ResponseEntity<List<Book>> getAllBooks(){
    try{
    List<Book> bookList = new ArrayList<>();
    bookRepo.findAll().forEach(bookList :: add);

    if(bookList.isEmpty()){
        return new ResponseEntity<>(bookList, HttpStatus.NO_CONTENT);
    }
    return new ResponseEntity<>(HttpStatus.OK);

    }catch (Exception ex){
        return  new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    }
    @GetMapping("/getBookById/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable long id){
    Optional<Book>  bookData= bookRepo.findById(id);
    if(bookData.isPresent()){
        return new ResponseEntity<>(bookData.get(),HttpStatus.OK);
    }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
   @PostMapping("/addBook")
    public ResponseEntity<Book> addBook(@RequestBody Book book){
        Book bookObj=bookRepo.save(book);
        return new ResponseEntity<>(bookObj,HttpStatus.OK);
    }
    @PostMapping("/updateBookById/{id}")
    public ResponseEntity<Book> updateBookById(@PathVariable long id, @RequestBody Book newBookData){
        Optional<Book>  oldBookData =  bookRepo.findById(id);
        if(oldBookData.isPresent()){
            Book updatedBookData=oldBookData.get();
            updatedBookData.setTitle(newBookData.getTitle());
            updatedBookData.setAuthor(newBookData.getAuthor());
            Book bookObj=bookRepo.save(updatedBookData);
            return new ResponseEntity<>(bookObj,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/deleteBookById/{id}")
    public ResponseEntity<HttpStatus> deleteBookById(@PathVariable long id){
        bookRepo.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
