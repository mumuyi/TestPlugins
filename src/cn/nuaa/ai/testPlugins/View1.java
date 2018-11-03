package cn.nuaa.ai.testPlugins;

import java.awt.im.InputContext;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.BadPositionCategoryException;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentListener;
import org.eclipse.jface.text.ITextOperationTarget;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.help.IWorkbenchHelpSystem;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.texteditor.ITextEditor;

public class View1 extends ViewPart {
	private List list; // 将列表写成类的实例变量，以扩大它的可访问范围
	// 注意这个List并不是java.util包下的.而是org.eclipse.swt.widgets.List;包下的.

	public void createPartControl(Composite parent) {
		IWorkbenchHelpSystem help = PlatformUI.getWorkbench().getHelpSystem();
		help.setHelp(parent, "cn.nuaa.ai.testPlugins");
		Composite topComp = new Composite(parent, SWT.NONE);
		
		GridLayout layoutComposite = new GridLayout();
		layoutComposite.numColumns = 2;
		layoutComposite.marginHeight = 1;
		topComp.setLayout(layoutComposite);
		
		//topComp.setLayout(new GridLayout(1,false));
		topComp.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,true,2,2));
		

		Text input = new Text(topComp,SWT.BORDER);
		input.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		input.setText("test input");
		
        Button button = new Button(topComp, SWT.PUSH);  
        button.setText("Search"); 
        button.setLayoutData(new GridData(GridData.FILL));
        button.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseUp(MouseEvent arg0) {
				
				
			}
			
			@Override
			public void mouseDown(MouseEvent arg0) {
				list.add("API Invoke Path 1");
				list.add("API Invoke Path 2");
				list.add("API Invoke Path 3");
				list.add("API Invoke Path 4");
				list.add("API Invoke Path 5");
				list.add("API Invoke Path 6");
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent arg0) {
				
				
			}
		});
		

		list = new List(topComp, SWT.BORDER);
        GridData gridData=new GridData();
        gridData.horizontalSpan=2;
        gridData.horizontalAlignment=SWT.FILL;
        gridData.verticalAlignment=SWT.FILL;
        gridData.grabExcessVerticalSpace=true;
		list.setLayoutData(gridData);
		//list.setLayoutData(new GridData(GridData.FILL));

		// 列表选择事件监听
		list.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				
			}
		});

		list.addMouseListener(new MouseAdapter() {

			public void mouseDoubleClick(MouseEvent e) {

			}
		});

		// 加入导航栏按钮、下拉菜单、右键菜单
		MyActionGroup actionGroup = new MyActionGroup();
		fillViewAction(actionGroup);// 加入视图的导航栏按钮
		fillViewMenu(actionGroup);// 加入视图的下拉菜单
		fillListMenu(actionGroup);// 加入视图的下拉菜单
		
	}

	// 加入视图的导航栏按钮
	private void fillViewAction(MyActionGroup actionGroup) {
		IActionBars bars = getViewSite().getActionBars();
		actionGroup.fillActionBars(bars);
	}

	// 加入视图的下拉菜单
	private void fillViewMenu(MyActionGroup actionGroup) {
		IMenuManager manager = getViewSite().getActionBars().getMenuManager();
		actionGroup.fillContextMenu(manager);
	}

	// 加入列表List的右键菜单
	private void fillListMenu(MyActionGroup actionGroup) {
		MenuManager manger = new MenuManager();
		Menu menu = manger.createContextMenu(list);
		list.setMenu(menu);
		actionGroup.fillContextMenu(manger);
	}

	@Override
	public void setFocus() {
	}

	@Override
	public Object getAdapter(Class arg0) {
		// TODO Auto-generated method stub
		return null;
	}
}
