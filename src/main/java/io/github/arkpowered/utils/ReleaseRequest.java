package io.github.arkpowered.utils;

import com.google.gson.annotations.SerializedName;

public class ReleaseRequest {
    @SerializedName("tag_name")
    private final String tagName;
    private final String name;
    private final String body;
    private final boolean draft;
    private final boolean prerelease;
    @SerializedName("target_commitish")
    private final String targetCommitish;
    @SerializedName("make_latest")
    private final String makeLatest;

    public ReleaseRequest(String tagName, String name, String body, boolean draft, boolean prerelease, String targetCommitish, String makeLatest) {
        this.tagName = tagName;
        this.name = name;
        this.body = body;
        this.draft = draft;
        this.prerelease = prerelease;
        this.targetCommitish = targetCommitish;
        this.makeLatest = makeLatest;
    }
}
