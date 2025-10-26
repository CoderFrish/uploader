package me.coderfrish.test;

import io.github.coderfrish.utils.GitHubReleaseClient;
import io.github.coderfrish.utils.ReleaseRequest;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class GitHubReleaseTest {
    @Test
    public void test() throws IOException {
        ReleaseRequest request = new ReleaseRequest(
                "test",
                "test release",
                "Hello World!!",
                false,
                true,
                "ver/1.21.10",
                "false"
        );

        GitHubReleaseClient client = new GitHubReleaseClient("xxxx");
//        String id = client.createRelease("TraiumMC", "Tralux", request);

//        client.uploadAsset("TraiumMC", "Tralux", id,
//                new File("D:\\uploader\\src\\test\\resources\\TEST_RESOURCE"), "text/plain");

        String s = client.fetchRepoBranch("TraiumMC", "Tralux");
        System.out.println(s);
    }
}
