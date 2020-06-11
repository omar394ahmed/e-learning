package com.example.demo.enrollments;

public enum RequestSet {

   waiting(0) , Pending(1) , Accepted(2) ;

   public  int value ;

    RequestSet(final int value) {
        this.value = value;
    }
}
