package vosk.ruta.ui.module;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleConfigurationEditor;
import com.intellij.openapi.roots.ui.configuration.ModuleConfigurationEditorProviderEx;
import com.intellij.openapi.roots.ui.configuration.ModuleConfigurationState;
import vosk.ruta.ui.facet.RutaFacetType;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class RutaModuleConfigurationEditorProvider implements ModuleConfigurationEditorProviderEx {
    @Override
    public boolean isCompleteEditorSet() {
        return false;
    }

    @Override
    public ModuleConfigurationEditor[] createEditors(ModuleConfigurationState state) {
        Module[] modules = state.getModulesProvider().getModules();
        List<RutaModuleConfigurationEditor> collect = Arrays.stream(modules)
                .map(module -> state.getFacetsProvider().getFacetsByType(module, RutaFacetType.FACET_TYPE_ID))
                .filter(facet -> facet != null)
                .flatMap(Collection::stream)
                .map(facet -> new RutaModuleConfigurationEditor(facet.getModule(),facet,facet.getConfiguration().getState()))
                .collect(Collectors.toList());
        return collect.toArray(new ModuleConfigurationEditor[0]);
//        state.getFacetsProvider().
//        return new ModuleConfigurationEditor[0];
    }
}
