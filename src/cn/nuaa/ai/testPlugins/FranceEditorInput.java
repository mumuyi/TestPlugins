package cn.nuaa.ai.testPlugins;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

public class FranceEditorInput implements IEditorInput {

	public boolean exists() {
		return false;
	}

	public ImageDescriptor getImageDescriptor() {
		return null;
	}

	public String getName() {
		return "�����ı༭��";
	}

	public IPersistableElement getPersistable() {
		return null;
	}

	public String getToolTipText() {
		return "������ͼ1�б��еķ������Ӧ�ı༭��";
	}

	public Object getAdapter(Class adapter) {
		return null;
	}

}
