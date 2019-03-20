package vosk.ruta.ui.facet;

import com.intellij.facet.Facet;
import com.intellij.facet.FacetType;
import com.intellij.facet.FacetTypeId;
import com.intellij.openapi.module.JavaModuleType;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import vosk.ruta.RutaIcons;

import javax.swing.*;

/**
 * @author Konstantin Bulenkov
 */
public class RutaFacetType extends FacetType<RutaFacet, RutaFacetConfiguration> {
  public final static FacetTypeId<RutaFacet> FACET_TYPE_ID = new FacetTypeId<RutaFacet>("UIMARuta");

  public RutaFacetType() {
    super(FACET_TYPE_ID, RutaModuleSettings.FACET_ID, RutaModuleSettings.FACET_NAME);
  }

  @Override
  public RutaFacetConfiguration createDefaultConfiguration() {
    return new RutaFacetConfiguration();
  }

  @Override
  public RutaFacet createFacet(@NotNull final Module module,
                               final String name,
                               @NotNull final RutaFacetConfiguration configuration,
                               @Nullable final Facet underlyingFacet) {
    return new RutaFacet(this, module, name, configuration, underlyingFacet);
  }

  @Override
  public boolean isSuitableModuleType(final ModuleType moduleType) {
    return moduleType instanceof JavaModuleType;
  }

  @Override
  public Icon getIcon() {
    return RutaIcons.FILE;
  }

  public static RutaFacetType getInstance() {
    return findInstance(RutaFacetType.class);
  }

}
