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
		//新建父类视图;
		Composite topComp = new Composite(parent, SWT.NONE);
		topComp.setLayout(new FillLayout());
		
		//获取相关设置;
		ps = PlatformUI.getPreferenceStore();// 取得一个IPreferenceStore对象
		String ResultNumStr = ps.getString(ContextPreferencePage.ResultNum_KEY);
		if (ResultNumStr == null || ResultNumStr.trim().equals(""))
			resultNum = Integer.parseInt(ContextPreferencePage.ResultNum_DEFAULT);
		else
			resultNum = Integer.parseInt(ResultNumStr);
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!resultNum: " + resultNum);
		
		//设置页面布局;
		GridLayout layoutComposite = new GridLayout();
		layoutComposite.numColumns = resultNum;
		layoutComposite.marginHeight = 1;
		layoutComposite.makeColumnsEqualWidth = true;
		topComp.setLayout(layoutComposite);
		
		//设置每一列的布局;
        GridData gridData=new GridData();
        gridData.horizontalAlignment=SWT.FILL;
        gridData.verticalAlignment=SWT.FILL;
        gridData.grabExcessVerticalSpace=true;
        gridData.grabExcessHorizontalSpace=true;
		
		//添加页面元素;
		for(int i = 0;i < resultNum;i++){
			Text text = new Text(topComp,  SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
			text.setText("我是Text框" + i);
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
