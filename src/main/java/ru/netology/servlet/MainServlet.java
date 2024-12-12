package ru.netology.servlet;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.netology.controller.PostController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MainServlet extends HttpServlet {
    protected final String postsPath = "/api/posts";
    private PostController controller;

    @Override
    public void init() {
        final var context = new AnnotationConfigApplicationContext("ru.netology");
        controller = context.getBean(PostController.class);

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final var path = req.getRequestURI();
        if (path.equals(postsPath)) {
            controller.all(resp);
        } else if (path.matches(postsPath + "/\\d+")) {
            // easy way
            final var id = Long.parseLong(path.substring(path.lastIndexOf("/")));
            controller.getById(id, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final var path = req.getRequestURI();
        if (path.equals(postsPath)) {
            controller.save(req.getReader(), resp);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final var path = req.getRequestURI();
        if (path.matches(postsPath + "/\\d+")) {
            // easy way
            final var id = Long.parseLong(path.substring(path.lastIndexOf("/")));
            controller.removeById(id, resp);
            return;
        }
        resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
    }
}

