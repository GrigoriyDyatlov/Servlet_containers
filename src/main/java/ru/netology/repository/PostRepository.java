package ru.netology.repository;

import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;


public class PostRepository {
    protected final ConcurrentHashMap<Long, Post> posts = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(0);

    public List<Post> all() {
        return posts.values().stream().toList();
    }

    public Optional<Post> getById(long id) {
        return Optional.ofNullable(posts.get(id));
    }

    public Post save(Post post) {
        if (post.getId() == 0) {
            post.setId(idGenerator.getAndIncrement());
            posts.put(post.getId(), post);
            return post;
        } else if (post.getId() != 0) {
            if (getById(post.getId()).isPresent()) {
                getById(post.getId())
                        .get()
                        .setContent(post.getContent());
            }
            return post;
        } else throw new NotFoundException();
    }

    public void removeById(long id) {
        posts.remove(id);
    }
}
