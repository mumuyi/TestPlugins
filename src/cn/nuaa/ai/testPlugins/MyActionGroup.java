package cn.nuaa.ai.testPlugins;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.actions.ActionGroup;
import org.eclipse.ui.dialogs.ListDialog;
import org.eclipse.ui.internal.IWorkbenchGraphicConstants;
import org.eclipse.ui.internal.WorkbenchImages;

public class MyActionGroup extends ActionGroup {
	// 加入按钮
	public void fillActionBars(IActionBars actionBars) {
		if (actionBars == null)
			return;
		IToolBarManager toolBar = actionBars.getToolBarManager();
		toolBar.add(new Action1());
		toolBar.add(new Action2());
	}

	// 加入下拉菜单、右键弹出菜单
	public void fillContextMenu(IMenuManager menu) {
		if (menu == null)
			return;
		menu.add(new Action1());
		menu.add(new Action2());
	}

	private class Action1 extends Action {
		@SuppressWarnings("restriction")
		public Action1() {
			ImageDescriptor imageDesc = WorkbenchImages
					.getImageDescriptor(IWorkbenchGraphicConstants.IMG_ETOOL_NEW_PAGE);
			// .getImageDescriptor(IWorkbenchGraphicConstants.IMG_ETOOL_HOME_NAV);
			// 这个地放原码是想在在View1上显示一个"小房子"的图标.但是我参看IWorkbenchGraphicConstants的源码
			// 没有IMG_ETOOL_HOME_NAV这个常量字段.导入Eclipse也报错,我就 换了一个.
			setHoverImageDescriptor(imageDesc);
			setText("Action1");
		}

		public void run() {
		}
	}

	private class Action2 extends Action {
		@SuppressWarnings("restriction")
		public Action2() {
			ImageDescriptor imageDesc = WorkbenchImages
					.getImageDescriptor(IWorkbenchGraphicConstants.IMG_ETOOL_IMPORT_WIZ);
			setHoverImageDescriptor(imageDesc);
			setText("Action2");
		}

		public void run() {
			//new LanguageDialog(null).open();
		}
	}
}
