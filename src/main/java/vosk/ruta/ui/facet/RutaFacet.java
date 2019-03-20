package vosk.ruta.ui.facet;

import com.intellij.facet.*;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
          model.commit();
        }
      }
    }
  }

}

