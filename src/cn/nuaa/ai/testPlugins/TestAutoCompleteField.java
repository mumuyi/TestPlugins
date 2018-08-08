package cn.nuaa.ai.testPlugins;

import org.eclipse.jface.fieldassist.AutoCompleteField;
import org.eclipse.jface.fieldassist.ComboContentAdapter;
import org.eclipse.jface.fieldassist.TextContentAdapter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class TestAutoCompleteField {
	public static void main(String[] args) {
		Display display = Display.getDefault();// 获取Display
		Shell shell = new Shell();
		shell.setSize(300, 150); // 设置窗体大小
		shell.setLocation(500, 200);// 设置窗体位置
		shell.setText("自动补全");// 设置标题

		// Text控件
		Text text = new Text(shell, SWT.BORDER);// 添加一个Text控件
		text.setBounds(50, 20, 180, 28);// 由于Shell没有加布局,这里设置它的位置和大小

		// Text控件的自动补全
		new AutoCompleteField(text, new TextContentAdapter(),
				new String[] { "我是一个程序猿", "我要加班", "我爱编程", "你是谁?", "你在干什么" });

		// Combo控件
		Combo combo = new Combo(shell, SWT.DROP_DOWN);// 添加一个Combo控件
		combo.setBounds(50, 60, 180, 28);// 设置Combo控件的位置和大小
		String[] items = new String[] { "java语言", "C语言", "C++语言", "C#语言", "JavaScript脚本语言" };
		combo.setItems(items);// 设置Combo组件的选项
		// Combo控件的自动补全
		new AutoCompleteField(combo, new ComboContentAdapter(), items);

		shell.open();
		while (!shell.isDisposed()) {// 循环不让窗体关掉
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}
}
