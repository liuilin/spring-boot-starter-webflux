package com.liumulin.webflux.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@WebServlet("/syncServlet")
public class SyncServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long startTime = System.currentTimeMillis();
        doSomething(request, response);
        long stopTime = System.currentTimeMillis();
        System.out.println("同步操作业务耗时：" + (stopTime - startTime));
    }

    // 耗时操作
    public void doSomething(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 暂停一会儿线程
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        response.getWriter().println("Done!");
    }
}
