//package vosk.ruta.ui.framework;
//
//import com.intellij.framework.FrameworkTypeEx;
//import com.intellij.framework.addSupport.FrameworkSupportInModuleConfigurable;
//import com.intellij.framework.addSupport.FrameworkSupportInModuleProvider;
//import com.intellij.icons.AllIcons;
//import com.intellij.ide.util.frameworkSupport.FrameworkSupportModel;
//import com.intellij.openapi.module.Module;
//import com.intellij.openapi.module.ModuleType;
//import com.intellij.openapi.roots.ModifiableModelsProvider;
//import com.intellij.openapi.roots.ModifiableRootModel;
//import com.intellij.openapi.roots.ui.configuration.FacetsProvider;
//import org.jetbrains.annotations.NotNull;
//import org.jetbrains.annotations.Nullable;
//import vosk.ruta.ui.facet.RutaFacet;
//
//import javax.swing.*;
//
//public class RutaFramework extends FrameworkTypeEx {
//    public static final String FRAMEWORK_ID = "Ruta";
//    protected RutaFramework() {
//        super(FRAMEWORK_ID);
//    }
//
//    @NotNull
//    @Override
//    public String getPresentableName() {
//        return "UIMA Ruta";
//    }
//
//    @NotNull
//    @Override
//    public Icon getIcon() {
//        return AllIcons.Providers.Apache;
//    }
//
//    @NotNull
//    @Override
//    public FrameworkSupportInModuleProvider createProvider() {
//        return  new FrameworkSupportInModuleProvider() {
//            @NotNull
//            @Override
//            public FrameworkTypeEx getFrameworkType() {
//                return RutaFramework.this;
//            }
//
//            @NotNull
//            @Override
//            public FrameworkSupportInModuleConfigurable createConfigurable(@NotNull FrameworkSupportModel model) {
//                return new FrameworkSupportInModuleConfigurable() {
//                    @Nullable
//                    @Override
//                    public JComponent createComponent() {
//
//                        return new RutaVersionSelectionComponent(null);
//                    }
//
//                    @Override
//                    public void addSupport(@NotNull Module module, @NotNull ModifiableRootModel model, @NotNull ModifiableModelsProvider provider) {
//                        RutaFacet.setupFacet(module);
//                        //do what you want here: setup a library, generate a specific file, etc
//                    }
//                };
//            }
//
//            @Override
//            public boolean isEnabledForModuleType(@NotNull ModuleType type) {
//
//                return true;
//
//            }
//            @Override
//            public boolean isSupportAlreadyAdded(@NotNull Module module, @NotNull FacetsProvider facetsProvider) {
//                return RutaFacet.getInstance(module) !=null;
//            }
//        };
//    }
//}