package cn.nuaa.ai.testPlugins;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class SamplePerspective implements IPerspectiveFactory {
	// 参数IPageLayout是用于透视图的布局管理器
	@Override
	public void createInitialLayout(IPageLayout layout) {
		// 得到本透视图的编辑空间标识
		String editorArea = layout.getEditorArea();
		// 在透视图左部创建一个空间，并将“视图1”放入其中。
		// "left"是此空间的标识；IPageLayout.LEFT指出此空间在透视图布局中的位置靠左；
		// 0.2f 指此空间占用透视图20%的宽度；editorArea 指使用透视图的编辑空间
		IFolderLayout left = layout.createFolder("left", IPageLayout.LEFT, 0.2f, editorArea);
		left.addView("cn.nuaa.ai.testPlugins.View1"); // 参数为plugin.xml中“视图1”的id标识
		// 将“视图2”加入到透视图的底部
		IFolderLayout bottom = layout.createFolder("bottom", IPageLayout.BOTTOM, 0.8f, editorArea);
		bottom.addView(View2.class.getName());// 由于我们把视图的id取成和类全名一样，所以也可以用这种写法
		// 将以前定义的actionset（主菜单、工具栏按钮）加入到本透视图。这要在plugin.xml文
		// 件的action设置中将visible="false"才看得出效果，这时打开其他透视图，action设置的
		// 主菜单、工具栏按钮将不会出现在界面上，只有打开本透视图才会出现。
		layout.addActionSet("myplugin.actionSet");// 参数为actionSet在plugin.xml中的id标识
	}

}