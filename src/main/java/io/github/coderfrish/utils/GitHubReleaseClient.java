package io.github.coderfrish.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import okhttp3.*;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class GitHubReleaseClient {
    private static final Gson GSON = new Gson();
    private final String token;
    private final String baseUrl;
    private final OkHttpClient client;

    public GitHubReleaseClient(String token, String baseUrl) {
        this.token = token;
        this.baseUrl = baseUrl;
        this.client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();
    }

    public GitHubReleaseClient(String token) {
        this(token, "https://api.github.com");
    }

    public String createRelease(String owner, String repo, ReleaseRequest request) throws IOException {
        String url = String.format("%s/repos/%s/%s/releases", baseUrl, owner, repo);
        String json = GSON.toJson(request);

        Request httpRequest = new Request.Builder().url(url)
                .post(RequestBody.create(json, MediaType.parse("application/json")))
                .addHeader("Authorization", "token " + token)
                .addHeader("Accept", "application/vnd.github.v3+json")
                .build();

        try (Response response = client.newCall(httpRequest).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Failed to create release: " + response.body().string());
            }

            JsonObject responseJson = GSON.fromJson(response.body().string(), JsonObject.class);
            return responseJson.get("id").getAsString();
        }
    }

    public void uploadAsset(String owner, String repo, String releaseId, File file, String contentType) throws IOException {
        String uploadUrl = String.format("https://uploads.github.com/repos/%s/%s/releases/%s/assets?name=%s",
                owner, repo, releaseId, file.getName());

        RequestBody requestBody = RequestBody.create(file, MediaType.parse(contentType));

        Request request = new Request.Builder()
                .url(uploadUrl)
                .post(requestBody)
                .addHeader("Authorization", "token " + token)
                .addHeader("Accept", "application/vnd.github.v3+json")
                .addHeader("Content-Type", contentType)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Failed to upload asset: " + response.body().string());
            }

            System.out.println("Asset uploaded successfully: " + file.getName());
        }
    }

    public String fetchRepoBranch(String owner, String repo) throws IOException {
        String repoUrl = String.format("%s/repos/%s/%s", baseUrl, owner, repo);
        Request request = new Request.Builder().url(repoUrl).get()
                .addHeader("Accept", "application/vnd.github.v3+json")
                .addHeader("Authorization", "Bearer " + token)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Failed to fetch repository branch: " + response.body().string());
            }

            JsonObject responseJson = GSON.fromJson(response.body().string(), JsonObject.class);
            return responseJson.get("default_branch").getAsString();
        }
    }
}
