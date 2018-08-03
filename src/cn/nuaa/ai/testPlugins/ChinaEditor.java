package cn.nuaa.ai.testPlugins;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;

public class ChinaEditor extends EditorPart {
	private boolean dirty = true; // �༭���Ƿ�Ϊ��ı�ʶ

	// Editor�ĳ�ʼ��������������ǰ�����ǹ̶������
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		System.out.println("init");
		setSite(site);
		setInput(input);
		// ��һ������Editor����������ʾ���ƣ�����������plugin.xml�е�name����
		// setPartName(input.getName());
		// ��һ������Editor��������ͼ�꣬������Զ�ʹ��һ��Ĭ�ϵ�ͼ��
		// setTitleImage(input.getImageDescriptor().createImage());
	}

	// �ڴ˷����д���Editor�еĽ������
	public void createPartControl(Composite parent) {
		System.out.println("createPartControl");
		Composite topComp = new Composite(parent, SWT.NONE);
		topComp.setLayout(new FillLayout());
		Text text = new Text(topComp, SWT.BORDER);
		text.setText("�й�֮�༭��");

		text.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				// ����༭�����ࣨ��û���޸ģ������־���ಢˢ�½���״̬
				if (!isDirty()) {
					setDirty(true);
					firePropertyChange(IEditorPart.PROP_DIRTY);
				}
			}
		});
	}

	// ����Ĵ�����������ַ����У�����Ctrl+S��ʱ��ִ�д˷�����
	// �������Ǳ�־Ϊ���༰ˢ�½���״̬
	public void doSave(IProgressMonitor monitor) {
		if (isDirty()) {
			// ��������༭���¼�������루ʡ�ԣ�
			setDirty(false);
			firePropertyChange(IEditorPart.PROP_DIRTY);
		}
	}

	// �Ƿ��������Ϊ��,false������
	public boolean isSaveAsAllowed() {
		return false;
	}

	// �����Ϊ���Ĵ���д�����������ʵ����
	public void doSaveAs() {
	}

	// dirty��ʶ��set�������ɴ˷������ñ༭��Ϊ��
	public void setDirty(boolean dirty) {
		this.dirty = dirty;
	}

	// �༭���������Ƿ����ˡ�true��,false����
	public boolean isDirty() {
		return dirty;
	}

	// ���༭����ý���ʱ��ִ�д˷�����������ʵ��
	public void setFocus() {
	}
}
