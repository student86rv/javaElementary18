package com.company;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        ApiService service = ApiService.getInstance();

        //List<Post> posts = service.getPosts();
        //System.out.println(posts);

        Post post = service.getPostById(10);
        System.out.println(post);
        System.out.println();

//        List<Comment> comments = service.getComments();
//        System.out.println(comments);

//        List<User> users = service.getUsers();
//        System.out.println(users);

        User user = service.getUserById(2);
        System.out.println(user);
    }
}
