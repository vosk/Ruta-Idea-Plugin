package vosk.ruta.ui.maven;

import com.intellij.openapi.module.Module;
import org.jetbrains.idea.maven.project.MavenProject;
import org.jetbrains.idea.maven.project.MavenProjectsManager;
import org.jetbrains.idea.maven.server.MavenEmbedderWrapper;

import java.util.Collections;
import java.util.List;

import static org.jetbrains.idea.maven.project.MavenEmbeddersManager.FOR_DOWNLOAD;

public class RutaVersion {

    public static List<String> getRutaVersionsFromMavenModule(Module module){
        MavenProjectsManager instance = MavenProjectsManager.getInstance(module.getProject());
        MavenProject project=instance.findProject(module);

        MavenEmbedderWrapper embedder = instance.getEmbeddersManager().getEmbedder(project, FOR_DOWNLOAD);
//                MavenRemoteRepository central = new MavenRemoteRepository("central", null, "https://repo1.maven.org/maven2/", null, null, null);
        try {
            List<String> strings = embedder.retrieveVersions("org.apache.uima", "ruta-core", project.getRemoteRepositories());
            return strings;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }
}
