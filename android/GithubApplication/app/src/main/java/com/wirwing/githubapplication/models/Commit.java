package com.wirwing.githubapplication.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Irving on 23/03/2015.
 */
public class Commit {

    @Expose
    private String url;

    @Expose
    private Author author;

    @Expose
    private InnerCommit innerCommit;

    class Author{

        @Expose
        @SerializedName("avatar_url")
        private String avatarUrl;

    }

    class InnerCommit{

        @Expose
        private String message;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    class CommitAuthor{

        @Expose
        private String name;
        @Expose
        private String email;


    }

}
