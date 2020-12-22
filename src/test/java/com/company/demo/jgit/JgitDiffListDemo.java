package com.company.demo.jgit;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

public class JgitDiffListDemo {


    private Git git;
    private Repository repository;
    private final static String localDir = "/home/timyang/Downloads/git-download";
    private final static String repoName = "articles";
    final Path homePath = Paths.get(localDir, repoName);

    @Before
    public void setUp() throws GitAPIException, IOException {
        if (Files.notExists(homePath)) {
            proxySetting();

            try {
                Git.cloneRepository()
                        .setURI("https://github.com/71mY4ng/articles.git")
                        .setDirectory(homePath.toFile())
                        .call();
            } catch (GitAPIException e) {
                e.printStackTrace();
                throw e;
            }
        }

        this.git = Git.open(homePath.toFile());
        this.repository = git.getRepository();
    }

    @After
    public void tearDown() {

        this.git.close();
        this.repository.close();
    }

    @Test
    public void testJGit() throws GitAPIException, IOException {
//        final List<Ref> list = git.tagList().call();

        final List<DiffEntry> diffs = git.diff().call();
        for (DiffEntry diff : diffs) {
            System.out.println("changed: " + diff.getNewPath());
        }
    }

    @Test
    public void testJGitDiffTag() throws GitAPIException, IOException {

        final Ref refA = repository.findRef("art-20201023002");
        final Ref refB = repository.findRef("art-20201221001");

        final List<DiffEntry> diffs = git.diff().setNewTree(getCanonicalTreeParser(refB.getObjectId()))
                .setOldTree(getCanonicalTreeParser(refA.getObjectId()))
                .call();

        for (DiffEntry diff : diffs) {
            System.out.println("changed: " + diff.getNewPath());
            System.out.println("old: " + diff.getOldPath());
        }
    }

    private AbstractTreeIterator getCanonicalTreeParser(ObjectId commitId) throws IOException {
        try (RevWalk walk = new RevWalk(git.getRepository())) {
            RevCommit commit = walk.parseCommit(commitId);
            ObjectId treeId = commit.getTree().getId();
            try (ObjectReader reader = git.getRepository().newObjectReader()) {
                return new CanonicalTreeParser(null, reader, treeId);
            }
        }
    }

    private void proxySetting() {
        ProxySelector.setDefault(new ProxySelector() {
            final ProxySelector delegate = ProxySelector.getDefault();

            @Override
            public List<Proxy> select(URI uri) {
                final String uriStr = uri.toString();

                if (uriStr.contains("github")
                        && (uriStr.contains("https") || uriStr.contains("http"))) {

                    return Collections.singletonList(new Proxy(Proxy.Type.HTTP,
                            InetSocketAddress.createUnresolved("localhost", 8888)));
                }

                return delegate == null ? Collections.singletonList(Proxy.NO_PROXY) : delegate.select(uri);
            }

            @Override
            public void connectFailed(URI uri, SocketAddress sa, IOException ioe) {
                if (uri == null || sa == null || ioe == null) {
                    System.err.println("Proxy: Argument can't be null");
                }
            }
        });
    }
}
