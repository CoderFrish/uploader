package io.github.arkpowered;

import org.gradle.api.Action;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class UploaderExtension {
    public String token;

    public String name;

    public String tagName;

    public String targetCommitish;

    public String body = "";

    public boolean draft = false;

    public boolean prerelease = false;

    /* Value: true、false、legacy */
    public String makeLatest = "true";

    public List<File> assets = new ArrayList<>();

    public final Repository repository = new Repository();

    public void repository(Action<Repository> action) {
        action.execute(repository);
    }

    public static class Repository {
        public String name;

        public String owner;
    }
}
