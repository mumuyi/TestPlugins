package cn.nuaa.ai.testPlugins;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

public class ChinaEditorInput implements IEditorInput {

	// ����true����򿪸ñ༭�������������Eclipse���˵����ļ���
	// ���²�������򿪵��ĵ����С�����flase�򲻳��������С�
	@Override
	public boolean exists() {
		return true;
	}

	// �༭����������ͼ�꣬����������Ҫ��ChinaEditor����
	// setTitleImage�������ã����ܳ����ڱ������С�
	@Override
	public ImageDescriptor getImageDescriptor() {
		// return
		// WorkbenchImages.getImageDescriptor(IWorkbenchGraphicConstants.IMG_ETOOL_HOME_NAV);
		return null;
	}

	// �༭������������ʾ���ƣ��������getImageDescriptorһ��ҲҪ
	// ��ChinaEditor����setPartName�������ã����ܳ����ڱ������С�
	@Override
	public String getName() {
		return "�й��ı༭��";
	}

	// �༭����������С������ʾ���֣�������getName������ChinaEditor��������
	@Override
	public String getToolTipText() {
		return "������ͼ1�б��е��й����Ӧ�ı༭��";
	}

	// ����һ�������������汾�༭��������״̬�Ķ���
	@Override
	public IPersistableElement getPersistable() {
		return null;
	}

	// �õ�һ���༭����������
	// IAdaptable a = new ChinaEditorInput();
	// IFoo x = (IFoo)a.getAdapter(IFoo.class);
	// if (x != null) [��x����IFoo������....]
	@Override
	public Object getAdapter(Class adapter) {
		return null;
	}
}
