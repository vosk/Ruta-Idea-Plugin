package vosk.ruta.ui.facet;

import com.intellij.facet.*;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.*;
import com.intellij.openapi.roots.libraries.Library;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VfsUtilCore;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import vosk.ruta.ui.maven.RutaDependency;

/**
 * @author Konstantin Bulenkov
 */
public class RutaFacet extends Facet<RutaFacetConfiguration> {
    public RutaFacet(@NotNull final FacetType facetType,
                     @NotNull final Module module,
                     final String name,
                     @NotNull final RutaFacetConfiguration configuration,
                     Facet underlyingFacet) {
        super(facetType, module, name, configuration, underlyingFacet);
    }

    @Nullable
    public static RutaFacet getInstance(Module module) {
        return FacetManager.getInstance(module).getFacetByType(RutaFacetType.FACET_TYPE_ID);
    }

    public static void setupFacet(Module module) {
        final String facetId = RutaFacetType.getInstance().getStringId();
        if (!StringUtil.isEmptyOrSpaces(facetId)) {

            final FacetManager facetManager = FacetManager.getInstance(module);
            final FacetType<?, ?> type = FacetTypeRegistry.getInstance().findFacetType(facetId);

            if (type != null) {

                if (facetManager.getFacetByType(type.getId()) == null) {
                    final ModifiableFacetModel model = facetManager.createModifiableModel();

                    final RutaFacet facet = (RutaFacet) facetManager.addFacet(type, type.getDefaultFacetName(), null);
//          facet.getConfiguration().setSdk(sdk);
                    model.addFacet(facet);
//                    model.commit();
                }
            }
        }
    }


    public static void initializeProjectStructure(RutaFacet facet) {
        Module module = facet.getModule();
        Project project = module.getProject();
        ModuleRootManager moduleRootManager = ModuleRootManager.getInstance(module);
        RutaFacetConfiguration  configuration = facet.getConfiguration();

        System.out.println(moduleRootManager.getContentRoots());
        generateFolders(facet, module);

        initializeModuleLibraries(module, project, configuration);


//        });






    }

    private static void generateFolders(RutaFacet facet, Module module) {
        VirtualFile[] roots = ModuleRootManager.getInstance(module).getContentRoots();
        if (roots.length < 1) {
        }
        VirtualFile root = roots[0];

        try {
            VirtualFile script = root.findChild("script");
            if(script == null){
                script = root.createChildDirectory(facet, "script");
            }

            ModifiableRootModel model = ModuleRootManager.getInstance(module).getModifiableModel();
            ContentEntry entry = findContentEntry(model, script);
            entry.addSourceFolder(script, false);//TODO need to create JpsModueSourceRootType ?
            model.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void initializeModuleLibraries(Module module, Project project, RutaFacetConfiguration configuration) {
        RutaDependency.clearDependency(project,module,configuration.getState().rutaVersion);

//        WriteAction.run(() -> {
        Library library = RutaDependency.addDependencyToProject(project, configuration);
        if(library!=null){
//                ModuleRootModificationUtil.addDependency(module, library);
            ModifiableRootModel modifiableModel = ModuleRootManager.getInstance(module).getModifiableModel();
            LibraryOrderEntry libraryOrderEntry = modifiableModel.addLibraryEntry(library);
            libraryOrderEntry.setExported(false);
            libraryOrderEntry.setScope(DependencyScope.COMPILE);
            modifiableModel.commit();

        }
    }

    @Nullable
    public static ContentEntry findContentEntry(@NotNull ModuleRootModel model, @NotNull VirtualFile vFile) {
        final ContentEntry[] contentEntries = model.getContentEntries();
        for (ContentEntry contentEntry : contentEntries) {
            final VirtualFile contentEntryFile = contentEntry.getFile();
            if (contentEntryFile != null && VfsUtilCore.isAncestor(contentEntryFile, vFile, false)) {
                return contentEntry;
            }
        }
        return null;
    }
}

