package cn.nuaa.ai.testPlugins;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

public class View2 extends ViewPart {
	private Text text;

	public void createPartControl(Composite parent) {
		Composite topComp = new Composite(parent, SWT.NONE);
		topComp.setLayout(new FillLayout());
		text = new Text(topComp,  SWT.MULTI);
		text.setText("Œ“ «TextøÚ");
	}

	public Text getText() {
		return text;
	}

	public void setText(Text text) {
		this.text = text;
	}

	public void setFocus() {
	}
}
