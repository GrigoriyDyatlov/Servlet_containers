package ru.netology.repository;

import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


public class PostRepository {
    protected final List<Post> posts = Collections.synchronizedList(new ArrayList<>());
    protected int idGenerator = 0;

    public List<Post> all() {
        return posts;
    }

    public Optional<Post> getById(long id) {
        return posts.stream()
                .filter(o -> o.getId() == id)
                .findFirst();
    }

    public Post save(Post post) {
        if (post.getId() == 0) {
            post.setId(idGenerator);
            idGenerator++;
            posts.add(post);
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
        for (int i = 0; i < posts.size(); i++) {
            if (posts.get(i).getId() == id) {
                posts.remove(i);
                break;
            }
        }
    }
}
