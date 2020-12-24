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
import org.eclipse.jgit.treewalk.TreeWalk;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * related links:
 * - https://gist.github.com/rherrmann/5341e735ce197f306949fc58e9aed141
 * - https://github.com/centic9/jgit-cookbook
 */
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

        final Ref preTag = repository.findRef("art-20201023002");
        final Ref afterTag = repository.findRef("art-20201221001");

        final List<DiffEntry> diffs = git.diff()
                .setNewTree(getCanonicalTreeParser(afterTag.getObjectId()))
                .setOldTree(getCanonicalTreeParser(preTag.getObjectId()))
                .call();

        for (DiffEntry diff : diffs) {
            System.out.println("changed: " + diff.getNewPath());
            System.out.println("old: " + diff.getOldPath());
        }

        getRefFileList(preTag).forEach(System.out::println);
    }

    /**
     * get commit ref file list
     *
     * @param ref
     * @return
     * @throws IOException
     */
    private List<String> getRefFileList(Ref ref) throws IOException {

        try (final TreeWalk treeWalk = new TreeWalk(git.getRepository());
             final RevWalk walk = new RevWalk(repository)
        ) {

            final RevCommit aCommit = walk.parseCommit(ref.getObjectId());
            treeWalk.addTree(aCommit.getTree());
            treeWalk.setRecursive(true);

            List<String> filePaths = new ArrayList<>();

            while (treeWalk.next()) {
                filePaths.add(treeWalk.getPathString());
            }
            return filePaths;
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
