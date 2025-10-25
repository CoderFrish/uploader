package io.github.coderfrish;

import io.github.coderfrish.utils.GitHubReleaseClient;
import io.github.coderfrish.utils.ReleaseRequest;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

import java.io.File;

public class UploadReleaseTask extends DefaultTask {
    private final UploaderExtension extension;

    public UploadReleaseTask() {
        setGroup("publishing");
        extension = getProject().getExtensions().getByType(UploaderExtension.class);
    }

    @TaskAction
    public void uploadRelease() {
        try {
            String repoName = extension.repository.name;
            String repoOwner = extension.repository.owner;

            if (extension.repository.name == null) {
                throw new NullPointerException("repository name not be null");
            }

            if (extension.repository.owner == null) {
                throw new NullPointerException("repository owner not be null");
            }

            if (extension.tagName == null) {
                throw new NullPointerException("tag name not be null.");
            }

            if (extension.name == null) {
                extension.name = extension.tagName;
            }

            if (extension.targetCommitish == null) {
                throw new NullPointerException("target commitish not be null.");
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

            GitHubReleaseClient client = new GitHubReleaseClient(
                    extension.token
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
