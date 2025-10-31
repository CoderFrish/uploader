# uploader

A Gradle Plugin for upload assets to github release.

## How to use.

Apply Plugin

```kotlin
plugins {
	id("io.github.arkpowered.uploader") version "0.1.0"
}
```

Configure

```kotlin
uploader {
    token = "<Token>" // Your GitHub Token
    name = "test release - v0.1" // release name
    tagName = "test" // release tag
    body = "Hello World!!!!" // release body
    targetCommitish = "ver/1.21.10" // barnch name
    prerelease = false // Are you make it pre release.
    draft = false // Are you make it draft.
    makeLatest = "false" // Are you make it latest?
    
    assets = listOf<File>(
        file("test.java"),
        file("test.jar")
    ) // File list by you upload.

    repository {
        name = "Tralux" // Your Repository Name
        owner = "TraiumMC" // Your Repository owner
    }
}
```
