package ru.netology.repository;

import org.springframework.stereotype.Repository;
import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class PostRepositoryImpl implements PostRepository {
    protected final ConcurrentHashMap<Long, Post> posts = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(0);

    @Override
    public List<Post> all() {
        return posts.values().stream().toList();
    }

    @Override
    public Optional<Post> getById(long id) {
        return Optional.ofNullable(posts.get(id));
    }

    @Override
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

    @Override
    public void removeById(long id) {
        posts.remove(id);
    }
}
