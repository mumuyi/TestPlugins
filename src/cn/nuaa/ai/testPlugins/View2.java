package cn.nuaa.ai.testPlugins;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

public class View2 extends ViewPart {
	private List<Text> textList = new ArrayList<Text>();
	private IPreferenceStore ps;
	private int resultNum = 1;
	
	public void createPartControl(Composite parent) {
		//�½�������ͼ;
		Composite topComp = new Composite(parent, SWT.NONE);
		topComp.setLayout(new FillLayout());
		
		//��ȡ�������;
		ps = PlatformUI.getPreferenceStore();// ȡ��һ��IPreferenceStore����
		String ResultNumStr = ps.getString(ContextPreferencePage.ResultNum_KEY);
		if (ResultNumStr == null || ResultNumStr.trim().equals(""))
			resultNum = Integer.parseInt(ContextPreferencePage.ResultNum_DEFAULT);
		else
			resultNum = Integer.parseInt(ResultNumStr);
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!resultNum: " + resultNum);
		
		//����ҳ�沼��;
		GridLayout layoutComposite = new GridLayout();
		layoutComposite.numColumns = resultNum;
		layoutComposite.marginHeight = 1;
		layoutComposite.makeColumnsEqualWidth = true;
		topComp.setLayout(layoutComposite);
		
		//����ÿһ�еĲ���;
        GridData gridData=new GridData();
        gridData.horizontalAlignment=SWT.FILL;
        gridData.verticalAlignment=SWT.FILL;
        gridData.grabExcessVerticalSpace=true;
        gridData.grabExcessHorizontalSpace=true;
		
		//���ҳ��Ԫ��;
		for(int i = 0;i < resultNum;i++){
			Text text = new Text(topComp,  SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
			text.setText("����Text��" + i);
			text.setLayoutData(gridData);
			textList.add(text);
		}
	}

	public List<Text> getText() {
		return textList;
	}

	public void setText(List<Text> textlist) {
		this.textList = textlist;
	}

	public void setFocus() {
	}
}
