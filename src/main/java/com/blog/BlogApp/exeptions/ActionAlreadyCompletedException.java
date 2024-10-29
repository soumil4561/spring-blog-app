package com.blog.BlogApp.exeptions;

public class ActionAlreadyCompletedException extends Throwable {
    public ActionAlreadyCompletedException(String s) {
        super(s);
    }
}
