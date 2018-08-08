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
		Display display = Display.getDefault();// ��ȡDisplay
		Shell shell = new Shell();
		shell.setSize(300, 150); // ���ô����С
		shell.setLocation(500, 200);// ���ô���λ��
		shell.setText("�Զ���ȫ");// ���ñ���

		// Text�ؼ�
		Text text = new Text(shell, SWT.BORDER);// ���һ��Text�ؼ�
		text.setBounds(50, 20, 180, 28);// ����Shellû�мӲ���,������������λ�úʹ�С

		// Text�ؼ����Զ���ȫ
		new AutoCompleteField(text, new TextContentAdapter(),
				new String[] { "����һ������Գ", "��Ҫ�Ӱ�", "�Ұ����", "����˭?", "���ڸ�ʲô" });

		// Combo�ؼ�
		Combo combo = new Combo(shell, SWT.DROP_DOWN);// ���һ��Combo�ؼ�
		combo.setBounds(50, 60, 180, 28);// ����Combo�ؼ���λ�úʹ�С
		String[] items = new String[] { "java����", "C����", "C++����", "C#����", "JavaScript�ű�����" };
		combo.setItems(items);// ����Combo�����ѡ��
		// Combo�ؼ����Զ���ȫ
		new AutoCompleteField(combo, new ComboContentAdapter(), items);

		shell.open();
		while (!shell.isDisposed()) {// ѭ�����ô���ص�
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}
}
