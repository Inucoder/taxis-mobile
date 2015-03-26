package com.wirwing.githubapplication.services;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by Irving on 23/03/2015.
 */
public interface GithubApi {

    public static final String API_ENDPOINT = "https://api.github.com";

    @GET("/repos/{owner}/{repo}/commits")
    public void repoCommits(@Path("owner") String owner, @Path("repo") String repo, Callback<?> callback);

}