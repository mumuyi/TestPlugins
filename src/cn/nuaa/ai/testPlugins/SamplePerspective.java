package cn.nuaa.ai.testPlugins;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class SamplePerspective implements IPerspectiveFactory {
	// ����IPageLayout������͸��ͼ�Ĳ��ֹ�����
	@Override
	public void createInitialLayout(IPageLayout layout) {
		// �õ���͸��ͼ�ı༭�ռ��ʶ
		String editorArea = layout.getEditorArea();
		// ��͸��ͼ�󲿴���һ���ռ䣬��������ͼ1���������С�
		// "left"�Ǵ˿ռ�ı�ʶ��IPageLayout.LEFTָ���˿ռ���͸��ͼ�����е�λ�ÿ���
		// 0.2f ָ�˿ռ�ռ��͸��ͼ20%�Ŀ�ȣ�editorArea ָʹ��͸��ͼ�ı༭�ռ�
		IFolderLayout left = layout.createFolder("left", IPageLayout.LEFT, 0.2f, editorArea);
		left.addView("cn.nuaa.ai.testPlugins.View1"); // ����Ϊplugin.xml�С���ͼ1����id��ʶ
		// ������ͼ2�����뵽͸��ͼ�ĵײ�
		IFolderLayout bottom = layout.createFolder("bottom", IPageLayout.BOTTOM, 0.8f, editorArea);
		bottom.addView(View2.class.getName());// �������ǰ���ͼ��idȡ�ɺ���ȫ��һ��������Ҳ����������д��
		// ����ǰ�����actionset�����˵�����������ť�����뵽��͸��ͼ����Ҫ��plugin.xml��
		// ����action�����н�visible="false"�ſ��ó�Ч������ʱ������͸��ͼ��action���õ�
		// ���˵�����������ť����������ڽ����ϣ�ֻ�д򿪱�͸��ͼ�Ż���֡�
		layout.addActionSet("myplugin.actionSet");// ����ΪactionSet��plugin.xml�е�id��ʶ
	}

}