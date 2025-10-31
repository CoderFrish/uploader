package io.github.arkpowered;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.jetbrains.annotations.NotNull;

public class UploaderPlugin implements Plugin<Project> {
    @Override
    public void apply(@NotNull Project target) {
        target.getExtensions().create("uploader", UploaderExtension.class);
        target.getTasks().register("uploadRelease", UploadReleaseTask.class);
    }
}
