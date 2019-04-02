package com.company;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class ApiService {

    private static ApiService _instance = null;

    private final String POSTS = "https://jsonplaceholder.typicode.com/posts/";
    private final String COMMENTS = "https://jsonplaceholder.typicode.com/comments/";
    private final String ALBUMS = "https://jsonplaceholder.typicode.com/albums/";
    private final String PHOTOS = "https://jsonplaceholder.typicode.com/photos/";
    private final String TODOS = "https://jsonplaceholder.typicode.com/todos/";
    private final String USERS = "https://jsonplaceholder.typicode.com/users/";

    private ApiService() {
    }

    public static synchronized ApiService getInstance() {
        if (_instance == null)
            _instance = new ApiService();
        return _instance;
    }

    public List<Post> getPosts() {
        String response = sendRequest(POSTS, null, null);
        List<Post> postList = new Gson()
                .fromJson(response, new TypeToken<ArrayList<Post>>(){}
                .getType());
        return postList;
    }

    public Post getPostById(int id) {
        String response = sendRequest(POSTS + id, null, null);
        return new Gson().fromJson(response, Post.class);
    }

    public List<Comment> getComments() {
        String response = sendRequest(COMMENTS, null, null);
        List<Comment> commentList = new Gson()
                .fromJson(response, new TypeToken<ArrayList<Comment>>(){}
                .getType());
        return commentList;
    }

    public Comment getCommentById(int id) {
        String response = sendRequest(COMMENTS + id, null, null);
        return new Gson().fromJson(response, Comment.class);
    }

    public List<Album> getAlbums() {
        String response = sendRequest(ALBUMS, null, null);
        List<Album> albumsList = new Gson()
                .fromJson(response, new TypeToken<ArrayList<Album>>(){}
                .getType());
        return albumsList;
    }

    public Album getAlbumById(int id) {
        String response = sendRequest(ALBUMS + id, null, null);
        return new Gson().fromJson(response, Album.class);
    }

    public List<Photo> getPhotos() {
        String response = sendRequest(PHOTOS, null, null);
        List<Photo> photosList = new Gson()
                .fromJson(response, new TypeToken<ArrayList<Photo>>(){}
                        .getType());
        return photosList;
    }

    public Photo getPhotoById(int id) {
        String response = sendRequest(PHOTOS + id, null, null);
        return new Gson().fromJson(response, Photo.class);
    }

    public List<Todo> getTodos() {
        String response = sendRequest(TODOS, null, null);
        List<Todo> todosList = new Gson()
                .fromJson(response, new TypeToken<ArrayList<Todo>>(){}
                        .getType());
        return todosList;
    }

    public Todo getTodoById(int id) {
        String response = sendRequest(TODOS + id, null, null);
        return new Gson().fromJson(response, Todo.class);
    }

    public List<User> getUsers() {
        String response = sendRequest(USERS, null, null);
        List<User> usersList = new Gson()
                .fromJson(response, new TypeToken<ArrayList<User>>(){}
                        .getType());
        return usersList;
    }

    public User getUserById(int id) {
        String response = sendRequest(USERS + id, null, null);
        return new Gson().fromJson(response, User.class);
    }

    private String sendRequest(@NotNull String url, @Nullable Map<String, String> headers, @Nullable String body) {
        String result = null;
        HttpURLConnection urlConnection = null;
        try {
            URL requestUrl = new URL(url);
            urlConnection = (HttpURLConnection) requestUrl.openConnection();
            urlConnection.setReadTimeout(20000);
            urlConnection.setConnectTimeout(20000);
            urlConnection.setRequestMethod("GET"); // optional, GET already by default

            if (body != null && !body.isEmpty()) {
                urlConnection.setDoOutput(true);
                urlConnection.setRequestMethod("POST"); // optional, setDoOutput(true) set value to POST
                DataOutputStream outputStream = new DataOutputStream(urlConnection.getOutputStream());
                outputStream.writeBytes(body);
                outputStream.flush();
                outputStream.close();
            }

            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    urlConnection.addRequestProperty(entry.getKey(), entry.getValue());
                }
            }

            int status = urlConnection.getResponseCode();
            System.out.println("Status code:" + status);

            if (status == HttpURLConnection.HTTP_OK) {
                result = getStringFromStream(urlConnection.getInputStream());
                Map<String, List<String>> responseHeaders = urlConnection.getHeaderFields();
                //System.out.println("Headers: " + responseHeaders);
            }
        } catch (Exception e) {
            System.out.println("sendRequest failed" + e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return result;
    }

    private String getStringFromStream(InputStream inputStream) throws IOException {
        final int BUFFER_SIZE = 4096;
        ByteArrayOutputStream resultStream = new ByteArrayOutputStream(BUFFER_SIZE);
        byte[] buffer = new byte[BUFFER_SIZE];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            resultStream.write(buffer, 0, length);
        }
        return resultStream.toString("UTF-8");
    }
}
