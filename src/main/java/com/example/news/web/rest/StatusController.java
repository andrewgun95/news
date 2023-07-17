package com.example.news.web.rest;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

@RestController
@RequestMapping("/api/public/")
public class StatusController {

    @GetMapping("/status")
    @ResponseStatus(HttpStatus.OK)
    public void status(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Content-Type", "application/json");
        RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
        try (PrintWriter writer = response.getWriter()) {
            writer.write(
                    String.format(
                            "{\"status\":\"OK\",\"started\":\"%s seconds ago\",\"system\":\"%s\"}",
                            (System.currentTimeMillis() - runtime.getStartTime()) / 1000,
                            runtime.getName()
                    )
            );
        }
    }

}
