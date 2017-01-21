package eu.marcocattaneo.stargazerstest.data;

public class GithubProfile {

    private String user;

    private String repo;

    public GithubProfile(String user, String repo) {
        this.user = user;
        this.repo = repo;
    }

    public String getUser() {
        return user;
    }

    public String getRepo() {
        return repo;
    }
}
