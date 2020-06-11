package com.example.demo.exceptions;

import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
public class ResponsePayload {

    private String Status ;
    private String Body ;

    public ResponsePayload(final String status, final String body) {
        this.Status = status;
        this.Body = body;
    }

    public String getStatus() {
        return this.Status;
    }

    public String getBody() {
        return this.Body;
    }
}
