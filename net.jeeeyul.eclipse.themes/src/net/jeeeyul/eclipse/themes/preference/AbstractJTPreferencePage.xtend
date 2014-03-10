package net.jeeeyul.eclipse.themes.preference

import net.jeeeyul.eclipse.themes.preference.internal.PreperencePageHelper
import net.jeeeyul.eclipse.themes.rendering.JTabSettings
import net.jeeeyul.swtend.SWTExtensions
import org.eclipse.swt.custom.CTabFolder
import org.eclipse.swt.widgets.Composite
import org.eclipse.swt.widgets.Control
import org.eclipse.swt.graphics.Image

/**
 * Abstract Preference Page for Theme plugin
 * 
 * @since 2.0.0
 */
abstract class AbstractJTPreferencePage {

	/**
	 * namse of page
	 */
	@Property String name

	/**
	 * image of page
	 */
	@Property Image image
	
	new(){
		
	}

	new(String name) {
		this.name = name
	}
	
	public def void init(extension PreperencePageHelper helper){
		
	}

	public abstract def Control createContents(Composite parent, extension SWTExtensions swtExtensions, extension PreperencePageHelper helper);

	public abstract def void updatePreview(CTabFolder folder, JTabSettings renderSettings, extension SWTExtensions swtExtensions, extension PreperencePageHelper helper)

	public abstract def void load(JThemePreferenceStore store, extension SWTExtensions swtExtensions, extension PreperencePageHelper helper)

	public abstract def void save(JThemePreferenceStore store, extension SWTExtensions swtExtensions, extension PreperencePageHelper helper)

	public abstract def void dispose(extension SWTExtensions swtExtensions, extension PreperencePageHelper helper)
}
