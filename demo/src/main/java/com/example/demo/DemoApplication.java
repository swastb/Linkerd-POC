package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}

@RestController
class DataController{

    @GetMapping("/info")
	public ResponseEntity<Info> getInformation(@RequestHeader HttpHeaders headers){
        System.out.println("userid is "+headers.get("userid"));
        System.out.println("l5d-hello header "+headers.get("l5d-hello"));
        System.out.println("token header "+headers.get("token"));

        return new ResponseEntity<Info>(new Info("Hello "), HttpStatus.OK);
    }

    @GetMapping("/token/{userid}")
    public ResponseEntity<String> generateToken(@PathVariable String userid){
        System.out.println("userid is "+userid);
        return new ResponseEntity<String>(userid+"--"+Math.random() * 5,HttpStatus.OK);
    }

}


class Token implements Serializable{
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}


class Info implements Serializable{

    public Info(String msg){
        this.msg =msg;
    }

    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
