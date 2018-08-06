package cn.nuaa.ai.testPlugins;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class DBPreferencePage extends PreferencePage implements IWorkbenchPreferencePage, ModifyListener {
	// Ϊ�ı�����������ֵ
	public static final String Automatic_KEY = "$Automatic_KEY";
	public static final String ResultNum_KEY = "$ResultNum_KEY";
	public static final String ContextLength_KEY = "$ContextLength_KEY";
	public static final String MinSimilarity_KEY = "$MinSimilarity_KEY";
	// Ϊ�ı���ֵ��������Ĭ��ֵ
	public static final Boolean Automatic_DEFAULT = true;
	public static final String ResultNum_DEFAULT = "5";
	public static final String ContextLength_DEFAULT = "10";
	public static final String MinSimilarity_DEFAULT = "0.85";
	// ���������ı���
	private Text resultNumText, contextLengthText, minSimilarityText;
	private Button  automaticCheck;
	// ����һ��IPreferenceStore����
	private IPreferenceStore ps;

	// �ӿ�IWorkbenchPreferencePage�ķ������������ʼ�����ڴ˷���������һ��
	// PreferenceStore�����ɴ˶����ṩ�ı���ֵ�Ķ���/д������
	public void init(IWorkbench workbench) {
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
	}

	// ����Ľ��洴������
	protected Control createContents(Composite parent) {
		Composite topComp = new Composite(parent, SWT.NONE);
		GridLayout layoutComposite = new GridLayout();
		layoutComposite.numColumns = 2;
		layoutComposite.marginHeight = 20;
		layoutComposite.verticalSpacing = 30;
		topComp.setLayout(layoutComposite);

		// ���������ı������ǩ
		new Label(topComp, SWT.NONE).setText("�Ƿ������Զ��Ƽ�:");
		automaticCheck = new Button(topComp, SWT.CHECK);
		automaticCheck.setText("����");
		automaticCheck.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		new Label(topComp, SWT.NONE).setText("��ʾ�������:");
		resultNumText = new Text(topComp, SWT.BORDER);
		resultNumText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		new Label(topComp, SWT.NONE).setText("�����ĳ���:");
		contextLengthText = new Text(topComp, SWT.BORDER);
		contextLengthText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		new Label(topComp, SWT.NONE).setText("������ƶ�����:");
		minSimilarityText = new Text(topComp, SWT.BORDER);
		minSimilarityText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		// ȡ����ǰ�����ֵ�������õ��ı����С����ȡ��ֵΪ��ֵ����ִ���������Ĭ��ֵ��
		ps = getPreferenceStore();// ȡ��һ��IPreferenceStore����
		String Automatic = ps.getString(Automatic_KEY);
		if (Automatic == null || Automatic.trim().equals("")){
			automaticCheck.setSelection(Automatic_DEFAULT);
		}
		else{
			if(Automatic.equals("true")){
				automaticCheck.setSelection(true);
			}else{
				automaticCheck.setSelection(false);
			}
		}

		String ResultNum = ps.getString(ResultNum_KEY);
		if (ResultNum == null || ResultNum.trim().equals(""))
			resultNumText.setText(ResultNum_DEFAULT);
		else
			resultNumText.setText(ResultNum);

		String ContextLength = ps.getString(ContextLength_KEY);
		if (ContextLength == null || ContextLength.trim().equals(""))
			contextLengthText.setText(ContextLength_DEFAULT);
		else
			contextLengthText.setText(ContextLength);

		String MinSimilarity = ps.getString(MinSimilarity_KEY);
		if (MinSimilarity == null || MinSimilarity.trim().equals(""))
			minSimilarityText.setText(MinSimilarity_DEFAULT);
		else
			minSimilarityText.setText(MinSimilarity);
		
		// ����¼���������this�����࣬��Ϊ����ʵ����ModifyListener�ӿڳ��˼�����
		resultNumText.addModifyListener(this);
		contextLengthText.addModifyListener(this);
		minSimilarityText.addModifyListener(this);
		return topComp;
	}

	// ʵ����ModifyListener�ӿڵķ������������ı����з����޸�ʱ��ִ�д˷�����
	// �����ж�����ֵ��������֤������ȷ��������Ӧ�á�����ťʹ��
	public void modifyText(ModifyEvent e) {
		String errorStr = null;// ��ԭ������Ϣ���
		if (resultNumText.getText().trim().length() == 0) {
			errorStr = "����Ϊ�գ�";
		} else if (contextLengthText.getText().trim().length() == 0) {
			errorStr = "����Ϊ�գ�";
		} else if (minSimilarityText.getText().trim().length() == 0) {
			errorStr = "����Ϊ�գ�";
		}
		setErrorMessage(errorStr);// errorStr=nullʱ��ԭΪ��������ʾ����
		setValid(errorStr == null);// ��ȷ������ť
		getApplyButton().setEnabled(errorStr == null);// ��Ӧ�á���ť
	}

	// ���෽������������ԭĬ��ֵ����ťʱ��ִ�д˷�����ȡ��Ĭ��ֵ���õ��ı�����
	protected void performDefaults() {
		automaticCheck.setSelection(Automatic_DEFAULT);
		resultNumText.setText(ResultNum_DEFAULT);
		contextLengthText.setText(ContextLength_DEFAULT);
		minSimilarityText.setText(MinSimilarity_DEFAULT);
	}

	// ���෽����������Ӧ�á���ťʱִ�д˷��������ı���ֵ���沢�����ɹ�����ʾ��Ϣ
	protected void performApply() {
		doSave(); // �Զ��巽������������
		MessageDialog.openInformation(getShell(), "��Ϣ", "�ɹ������޸�!");
	}

	// ���෽����������ȷ������ťʱִ�д˷��������ı���ֵ���沢�����ɹ�����ʾ��Ϣ
	public boolean performOk() {
		doSave();
		MessageDialog.openInformation(getShell(), "��Ϣ", "�޸����´�������Ч");
		return true; // true��ʾ�ɹ��˳�
	}

	// �Զ��巽���������ı����ֵ
	private void doSave() {
		ps.setValue(Automatic_KEY, "" + automaticCheck.getSelection());
		ps.setValue(ResultNum_KEY, resultNumText.getText());
		ps.setValue(ContextLength_KEY, contextLengthText.getText());
		ps.setValue(MinSimilarity_KEY, minSimilarityText.getText());
	}
}
