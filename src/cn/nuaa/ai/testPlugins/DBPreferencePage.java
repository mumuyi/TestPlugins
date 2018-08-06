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
	// 为文本框定义三个键值
	public static final String Automatic_KEY = "$Automatic_KEY";
	public static final String ResultNum_KEY = "$ResultNum_KEY";
	public static final String ContextLength_KEY = "$ContextLength_KEY";
	public static final String MinSimilarity_KEY = "$MinSimilarity_KEY";
	// 为文本框值定义三个默认值
	public static final Boolean Automatic_DEFAULT = true;
	public static final String ResultNum_DEFAULT = "5";
	public static final String ContextLength_DEFAULT = "10";
	public static final String MinSimilarity_DEFAULT = "0.85";
	// 定义三个文本框
	private Text resultNumText, contextLengthText, minSimilarityText;
	private Button  automaticCheck;
	// 定义一个IPreferenceStore对象
	private IPreferenceStore ps;

	// 接口IWorkbenchPreferencePage的方法，它负责初始化。在此方法中设置一个
	// PreferenceStore对象，由此对象提供文本框值的读入/写出方法
	public void init(IWorkbench workbench) {
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
	}

	// 父类的界面创建方法
	protected Control createContents(Composite parent) {
		Composite topComp = new Composite(parent, SWT.NONE);
		GridLayout layoutComposite = new GridLayout();
		layoutComposite.numColumns = 2;
		layoutComposite.marginHeight = 20;
		layoutComposite.verticalSpacing = 30;
		topComp.setLayout(layoutComposite);

		// 创建三个文本框及其标签
		new Label(topComp, SWT.NONE).setText("是否启动自动推荐:");
		automaticCheck = new Button(topComp, SWT.CHECK);
		automaticCheck.setText("启动");
		automaticCheck.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		new Label(topComp, SWT.NONE).setText("显示结果数量:");
		resultNumText = new Text(topComp, SWT.BORDER);
		resultNumText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		new Label(topComp, SWT.NONE).setText("上下文长度:");
		contextLengthText = new Text(topComp, SWT.BORDER);
		contextLengthText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		new Label(topComp, SWT.NONE).setText("最低相似度设置:");
		minSimilarityText = new Text(topComp, SWT.BORDER);
		minSimilarityText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		// 取出以前保存的值，并设置到文本框中。如果取出值为空值或空字串，则填入默认值。
		ps = getPreferenceStore();// 取得一个IPreferenceStore对象
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
		
		// 添加事件监听器。this代表本类，因为本类实现了ModifyListener接口成了监听器
		resultNumText.addModifyListener(this);
		contextLengthText.addModifyListener(this);
		minSimilarityText.addModifyListener(this);
		return topComp;
	}

	// 实现自ModifyListener接口的方法，当三个文本框中发生修改时将执行此方法。
	// 方法中对输入值进行了验证并将“确定”、“应用”两按钮使能
	public void modifyText(ModifyEvent e) {
		String errorStr = null;// 将原错误信息清空
		if (resultNumText.getText().trim().length() == 0) {
			errorStr = "不能为空！";
		} else if (contextLengthText.getText().trim().length() == 0) {
			errorStr = "不能为空！";
		} else if (minSimilarityText.getText().trim().length() == 0) {
			errorStr = "不能为空！";
		}
		setErrorMessage(errorStr);// errorStr=null时复原为正常的提示文字
		setValid(errorStr == null);// “确定”按钮
		getApplyButton().setEnabled(errorStr == null);// “应用”按钮
	}

	// 父类方法。单击“复原默认值”按钮时将执行此方法，取出默认值设置到文本框中
	protected void performDefaults() {
		automaticCheck.setSelection(Automatic_DEFAULT);
		resultNumText.setText(ResultNum_DEFAULT);
		contextLengthText.setText(ContextLength_DEFAULT);
		minSimilarityText.setText(MinSimilarity_DEFAULT);
	}

	// 父类方法。单击“应用”按钮时执行此方法，将文本框值保存并弹出成功的提示信息
	protected void performApply() {
		doSave(); // 自定义方法，保存设置
		MessageDialog.openInformation(getShell(), "信息", "成功保存修改!");
	}

	// 父类方法。单击“确定”按钮时执行此方法，将文本框值保存并弹出成功的提示信息
	public boolean performOk() {
		doSave();
		MessageDialog.openInformation(getShell(), "信息", "修改在下次启动生效");
		return true; // true表示成功退出
	}

	// 自定义方法。保存文本框的值
	private void doSave() {
		ps.setValue(Automatic_KEY, "" + automaticCheck.getSelection());
		ps.setValue(ResultNum_KEY, resultNumText.getText());
		ps.setValue(ContextLength_KEY, contextLengthText.getText());
		ps.setValue(MinSimilarity_KEY, minSimilarityText.getText());
	}
}
