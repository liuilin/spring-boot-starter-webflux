package com.liumulin.webflux.controller;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@WebServlet(asyncSupported = true,urlPatterns = "/asyncServlet")
public class AsyncServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long startTime = System.currentTimeMillis();

        // 开启异步操作，获取异步上下文（完成异步线程间的通信）
        AsyncContext ac = request.startAsync();
        // 通过异步上下文获取 NIO（非阻塞）请求与响应
        ServletRequest req = ac.getRequest();
        ServletResponse res = ac.getResponse();
        // 异步执行耗时操作
        CompletableFuture.runAsync(() -> doSomething(ac, req, res));

        long stopTime = System.currentTimeMillis();
        System.out.println("同步操作业务耗时：" + (stopTime - startTime));
    }

    // 耗时操作
    private void doSomething(AsyncContext ac, ServletRequest request, ServletResponse response) {

        // 暂停一会儿线程
        try { TimeUnit.SECONDS.sleep(5); } catch (InterruptedException e) { e.printStackTrace(); }
        try {
            response.getWriter().append("done");
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 业务代码处理完毕，通知结束..
        ac.complete();
    }

}
