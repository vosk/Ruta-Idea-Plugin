package vosk.ruta.ui.maven;

import com.intellij.jarRepository.JarRepositoryManager;
import com.intellij.jarRepository.RemoteRepositoriesConfiguration;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.LibraryOrderEntry;
import com.intellij.openapi.roots.ModifiableRootModel;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.roots.OrderEntry;
import com.intellij.openapi.roots.impl.libraries.LibraryEx;
import com.intellij.openapi.roots.libraries.*;
import com.intellij.openapi.roots.ui.configuration.libraryEditor.NewLibraryEditor;
import com.intellij.openapi.roots.ui.configuration.projectRoot.ProjectLibrariesConfigurable;
import com.intellij.openapi.roots.ui.configuration.projectRoot.StructureLibraryTableModifiableModelProvider;
import org.jetbrains.concurrency.Promise;
import org.jetbrains.idea.maven.utils.library.RepositoryLibraryDescription;
import vosk.ruta.ui.facet.RutaFacetConfiguration;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

public class RutaDependency {
    public static final String GROUPID = "org.apache.uima";
    public static final String ARTIFACTID = "ruta-core";

    public static Promise<Collection<String>> getRutaVersionsFromMaven(Module module) {

        Promise<Collection<String>> availableVersions =
                JarRepositoryManager.getAvailableVersions(module.getProject(),
                        RepositoryLibraryDescription.findDescription(GROUPID, ARTIFACTID));
        return availableVersions;
    }

    public static String getActiveRutaVersionFromMavenModule(Module module) {
        OrderEntry[] contentEntries = ModuleRootManager.getInstance(module).getOrderEntries();
        List<LibraryOrderEntry> rutaEntries = getRutaEntries(contentEntries);
        if (rutaEntries.size() > 0) {
            String[] split = Objects.requireNonNull(rutaEntries.get(0).getLibraryName()).split(":");
            if (split.length > 0) {
                return split[split.length - 1];
            }
        }
        return null;
    }

    public static Library addDependencyToProject(Project project, RutaFacetConfiguration configuration) {
        String libName = GROUPID + ":" + ARTIFACTID + ":" + configuration.getState().rutaVersion;

        LibraryTable projectLibraryTable = LibraryTablesRegistrar.getInstance().getLibraryTable(project);
        Library libraryByName = projectLibraryTable.getLibraryByName(libName);
        if (libraryByName != null) {
            return libraryByName;
        }
//        return null;

        NewLibraryConfiguration newLibraryConfiguration = JarRepositoryManager.resolveAndDownload(project,
                libName,
                false,
                false,
                true,
                null,
                RemoteRepositoriesConfiguration.getInstance(project).getRepositories()

        );
        if (newLibraryConfiguration == null) {
            return null;
        }
//
        LibraryTable.ModifiableModel moduleLibraryTableModifiableModel = projectLibraryTable.getModifiableModel();
        Library library = moduleLibraryTableModifiableModel.createLibrary(newLibraryConfiguration.getDefaultLibraryName());

        final LibraryType<?> libraryType = newLibraryConfiguration.getLibraryType();

        final NewLibraryEditor editor = new NewLibraryEditor(libraryType, newLibraryConfiguration.getProperties());
        newLibraryConfiguration.addRoots(editor);
        final Library.ModifiableModel model = library.getModifiableModel();
        editor.applyTo((LibraryEx.ModifiableModelEx) model);
        model.commit();
//
        moduleLibraryTableModifiableModel.commit();
//
        return library;

    }

    public static List<LibraryOrderEntry> getRutaEntries(OrderEntry[] orderEntries) {
        return Arrays.stream(orderEntries)
                .filter(orderEntry -> orderEntry instanceof LibraryOrderEntry)
                .map(orderEntry -> (LibraryOrderEntry) orderEntry)
                .filter(lib -> lib.getLibrary() == null || lib.getLibrary().getName().startsWith(GROUPID + ":" + ARTIFACTID))
                .collect(Collectors.toList());

    }

    public static void clearDependency(Project project, Module module, String keepVersion) {
        ModifiableRootModel modifiableModel = ModuleRootManager.getInstance(module).getModifiableModel();

        OrderEntry[] orderEntries = modifiableModel.getOrderEntries();
        List<LibraryOrderEntry> libraries = getRutaEntries(orderEntries);

        libraries.forEach(modifiableModel::removeOrderEntry);
        modifiableModel.commit();

        StructureLibraryTableModifiableModelProvider modelProvider = ProjectLibrariesConfigurable.getInstance(project).getModelProvider();
//
//        LibraryTable projectLibraryTable
//                = LibraryTablesRegistrar.getInstance().getLibraryTable(project);
        cleanLibraryTable(modelProvider, keepVersion);
    }

    public static void cleanLibraryTable(StructureLibraryTableModifiableModelProvider table, String keepVersion) {

//        WriteAction.run(() -> {


            LibraryTable.ModifiableModel modifiableModel = table.getModifiableModel();
            Library[] libraries = modifiableModel.getLibraries();
            List<Library> toDelete = asList(libraries).stream()
                    .filter(lib -> lib.getName().startsWith(GROUPID + ":" + ARTIFACTID))
                    .filter(lib -> !lib.getName().contains(keepVersion))
                    .collect(Collectors.toList());
            toDelete.forEach(library -> {
                try {
                    modifiableModel.removeLibrary(library);
                } catch (Exception e) {
                    e.printStackTrace();

                }
            });
            modifiableModel.commit();
//        });
    }
}
