package com.dcc.sdkcentral;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test/success")
    public TestResponse success() {
        return new TestResponse("SDK Central", "Success response is working");
    }

    @GetMapping("/test/failed")
    public TestResponse failed() {
        throw new RuntimeException();
    }

    @GetMapping("/test/error")
    public TestResponse error() {
        throw new RuntimeException("Something went wrong");
    }

    public record TestResponse(String name, String message) {}
}
