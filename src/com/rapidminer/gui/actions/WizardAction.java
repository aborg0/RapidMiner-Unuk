/*
 *  RapidMiner
 *
 *  Copyright (C) 2001-2013 by Rapid-I and the contributors
 *
 *  Complete list of developers available at our web site:
 *
 *       http://rapid-i.com
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Affero General Public License for more details.
 *
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see http://www.gnu.org/licenses/.
 */
package com.rapidminer.gui.actions;

import java.awt.event.ActionEvent;

import com.rapidminer.gui.MainUIState;
import com.rapidminer.gui.dialog.TemplateWizardDialog;
import com.rapidminer.gui.tools.ResourceAction;


/**
 * Start the corresponding action.
 * 
 * @author Ingo Mierswa
 */
public class WizardAction extends ResourceAction {

	private static final long serialVersionUID = -7497168731539521695L;

	private MainUIState mainFrame;
	
	public WizardAction(MainUIState mainFrame) {
		super("wizard");
		this.mainFrame = mainFrame;
	}

	public void actionPerformed(ActionEvent e) {
        this.mainFrame.getPerspectives().showPerspective("design");
		new TemplateWizardDialog().setVisible(true);
	}
}
