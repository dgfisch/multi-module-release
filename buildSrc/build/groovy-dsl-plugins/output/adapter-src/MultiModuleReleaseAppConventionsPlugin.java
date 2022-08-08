//CHECKSTYLE:OFF
import org.gradle.util.GradleVersion;
import org.gradle.groovy.scripts.BasicScript;
import org.gradle.groovy.scripts.ScriptSource;
import org.gradle.groovy.scripts.TextResourceScriptSource;
import org.gradle.internal.resource.StringTextResource;
/**
 * Precompiled multi-module-release.app-conventions script plugin.
 **/
public class MultiModuleReleaseAppConventionsPlugin implements org.gradle.api.Plugin<org.gradle.api.internal.project.ProjectInternal> {
    private static final String MIN_SUPPORTED_GRADLE_VERSION = "5.0";
    public void apply(org.gradle.api.internal.project.ProjectInternal target) {
        assertSupportedByCurrentGradleVersion();
        try {
            Class<? extends BasicScript> pluginsBlockClass = Class.forName("cp_precompiled_MultiModuleReleaseAppConventions").asSubclass(BasicScript.class);
            BasicScript pluginsBlockScript = pluginsBlockClass.getDeclaredConstructor().newInstance();
            pluginsBlockScript.setScriptSource(scriptSource(pluginsBlockClass));
            pluginsBlockScript.init(target, target.getServices());
            pluginsBlockScript.run();
            target.getPluginManager().apply("multi-module-release.java-conventions");
            target.getPluginManager().apply("pl.allegro.tech.build.axion-release");
            target.getPluginManager().apply("com.dorongold.task-tree");


            Class<? extends BasicScript> precompiledScriptClass = Class.forName("precompiled_MultiModuleReleaseAppConventions").asSubclass(BasicScript.class);
            BasicScript script = precompiledScriptClass.getDeclaredConstructor().newInstance();
            script.setScriptSource(scriptSource(precompiledScriptClass));
            script.init(target, target.getServices());
            script.run();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
  }
  private static ScriptSource scriptSource(Class<?> scriptClass) {
      return new TextResourceScriptSource(new StringTextResource(scriptClass.getSimpleName(), ""));
  }
  private static void assertSupportedByCurrentGradleVersion() {
      if (GradleVersion.current().getBaseVersion().compareTo(GradleVersion.version(MIN_SUPPORTED_GRADLE_VERSION)) < 0) {
          throw new RuntimeException("Precompiled Groovy script plugins require Gradle "+MIN_SUPPORTED_GRADLE_VERSION+" or higher");
      }
  }
}
//CHECKSTYLE:ON
