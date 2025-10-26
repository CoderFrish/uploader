package io.github.coderfrish;

import io.github.coderfrish.utils.GitHubReleaseClient;
import io.github.coderfrish.utils.ReleaseRequest;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.util.Objects;

public class UploadReleaseTask extends DefaultTask {
    private final UploaderExtension extension;

    public UploadReleaseTask() {
        setGroup("publishing");
        extension = getProject().getExtensions().getByType(UploaderExtension.class);
    }

    @TaskAction
    public void uploadRelease() {
        try {
            Objects.requireNonNull(extension.token, "Token not be null.");
            GitHubReleaseClient client = new GitHubReleaseClient(extension.token);
            String repoName = extension.repository.name;
            String repoOwner = extension.repository.owner;

            Objects.requireNonNull(repoName, "Repository name not be null.");
            Objects.requireNonNull(repoOwner, "Repository owner not be null.");
            Objects.requireNonNull(extension.tagName, "Tag name not be null.");

            if (extension.name == null) {
                extension.name = extension.tagName;
            }

            if (extension.targetCommitish == null) {
                extension.targetCommitish = client.fetchRepoBranch(repoOwner, repoName);
            }

            ReleaseRequest request = new ReleaseRequest(
                    extension.tagName,
                    extension.name,
                    extension.body,
                    extension.draft,
                    extension.prerelease,
                    extension.targetCommitish,
                    extension.makeLatest
            );

            String id = client.createRelease(repoOwner, repoName, request);

            for (File asset : extension.assets) {
                client.uploadAsset(repoOwner, repoName, id, asset, " application/octet-stream");
            }
        } catch (Exception e) {
            throw new UploaderException(e);
        }
    }
}
