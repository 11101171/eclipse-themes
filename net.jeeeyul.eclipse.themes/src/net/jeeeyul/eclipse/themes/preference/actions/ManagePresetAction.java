package net.jeeeyul.eclipse.themes.preference.actions;

import net.jeeeyul.eclipse.themes.preference.internal.JTPreperencePage;
import net.jeeeyul.eclipse.themes.preference.preset.internal.JTPresetPreferencePage;

import org.eclipse.ui.dialogs.PreferencesUtil;

public class ManagePresetAction extends AbstractPreferenceAction {

	public ManagePresetAction(JTPreperencePage page) {
		super(page);
		setText("Mange Presets...");
	}
	
	@Override
	public void run() {
		PreferencesUtil.createPreferenceDialogOn(getPage().getShell(), JTPresetPreferencePage.ID, null, null);
	}

}
