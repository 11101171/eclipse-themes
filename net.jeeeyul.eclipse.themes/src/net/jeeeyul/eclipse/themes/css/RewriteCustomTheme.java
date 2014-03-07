package net.jeeeyul.eclipse.themes.css;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import net.jeeeyul.eclipse.themes.JThemesCore;

import org.eclipse.e4.ui.css.core.dom.ExtendedCSSRule;
import org.eclipse.e4.ui.css.core.dom.ExtendedDocumentCSS;
import org.eclipse.e4.ui.css.core.engine.CSSEngine;
import org.eclipse.e4.ui.css.swt.dom.WidgetElement;
import org.eclipse.swt.widgets.Display;
import org.w3c.css.sac.SelectorList;
import org.w3c.dom.css.CSSRuleList;
import org.w3c.dom.css.CSSStyleSheet;
import org.w3c.dom.css.DocumentCSS;
import org.w3c.dom.stylesheets.StyleSheet;
import org.w3c.dom.stylesheets.StyleSheetList;

/**
 * re-generate "chrome.css" and replace with existing style sheet.
 * 
 * @author Jeeeyul
 * @since 1.2
 */
@SuppressWarnings("restriction")
public class RewriteCustomTheme {
	public static final String CUSTOM_THEME_FIRST_SELECTOR = "*[class=\"jeeeyul-custom-theme\"]";

	private Display display = Display.getDefault();

	public void rewrite() {
		try {
			CSSEngine cssEngine = WidgetElement.getEngine(display);
			ExtendedDocumentCSS documentCSS = (ExtendedDocumentCSS) cssEngine.getDocumentCSS();

			StyleSheet chromeSheet = findChromeSheet(documentCSS);

			CustomThemeGenerator generator = new CustomThemeGenerator(JThemesCore.getDefault().getPreferenceStore());

			String newCSSContent = generator.generate().toString();
			StyleSheet newSheet = cssEngine.parseStyleSheet(new StringReader(newCSSContent));

			StyleSheetList oldSheetList = documentCSS.getStyleSheets();
			List<StyleSheet> newSheetList = new ArrayList<StyleSheet>();

			for (int i = 0; i < oldSheetList.getLength(); i++) {
				StyleSheet oldSheet = oldSheetList.item(i);
				if (oldSheet != chromeSheet) {
					if (!newSheetList.contains(oldSheet))
						newSheetList.add(oldSheet);
				} else {
					if (!newSheetList.contains(newSheet))
						newSheetList.add(newSheet);
				}
			}

			documentCSS.removeAllStyleSheets();
			for (StyleSheet each : newSheetList) {
				documentCSS.addStyleSheet(each);
			}

			cssEngine.reapply();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * find and returns a {@link StyleSheet} which represent "chrome.css".
	 * 
	 * @param documentCSS
	 * @return
	 * @since 1.2
	 */
	private StyleSheet findChromeSheet(DocumentCSS documentCSS) {
		StyleSheet chromeSheet = null;
		StyleSheetList styleSheets = documentCSS.getStyleSheets();
		SearchChromeSheet: for (int i = 0; i < styleSheets.getLength(); i++) {
			StyleSheet eachSheet = styleSheets.item(i);
			if (eachSheet instanceof CSSStyleSheet) {
				CSSRuleList cssRules = ((CSSStyleSheet) eachSheet).getCssRules();
				for (int j = 0; j < cssRules.getLength(); j++) {
					ExtendedCSSRule rule = (ExtendedCSSRule) cssRules.item(j);
					SelectorList selectorList = rule.getSelectorList();
					String selectorText = selectorList.item(0).toString();
					if (CUSTOM_THEME_FIRST_SELECTOR.equals(selectorText)) {
						chromeSheet = eachSheet;
						break SearchChromeSheet;
					}
				}
			}

		}
		return chromeSheet;
	}
}