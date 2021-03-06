package cn.nuaa.ai.testPlugins;

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
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
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

public class ViewExample extends ViewPart {
	private List list; // 将列表写成类的实例变量，以扩大它的可访问范围
	// 注意这个List并不是java.util包下的.而是org.eclipse.swt.widgets.List;包下的.

	public void createPartControl(Composite parent) {
		IWorkbenchHelpSystem help = PlatformUI.getWorkbench().getHelpSystem();
		help.setHelp(parent, "cn.com.kxh.myplugin.buttonHelpId");
		Composite topComp = new Composite(parent, SWT.NONE);
		topComp.setLayout(new FillLayout());
		list = new List(topComp, SWT.BORDER);
		list.add("中国");
		list.add("美国");
		list.add("法国");
		// 列表选择事件监听
		list.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				// 由IWorkbenchPage获得view2对象
				IWorkbenchPage wbp = getViewSite().getPage();
				// 在插件中IWorkbenchPage对象比较重要,这里再给出一种获得此对象的通用的方法.
				// Activator.getDefault().getWorkbench().getActiveWorkbenchWindow().getActivePage();
				IViewPart view2 = wbp.findView("cn.nuaa.ai.testPlugins.View2");
				// 这个地方的参数是"视图2"在plugin.xml中的id标识.由此可见plugin.xml文件在插件中的地位是极其重要的.
				// 将当前选择的列表项显示在文本框中
				Text text = ((View2) view2).getText().get(0);
				text.setText(list.getSelection()[0]);
			}
		});

		list.addMouseListener(new MouseAdapter() {
			//private ChinaEditorInput chinaEditorInput = new ChinaEditorInput();
			//private UsaEditorInput usaEditorInput = new UsaEditorInput();
			//private FranceEditorInput franceEditorInput = new FranceEditorInput();

			public void mouseDoubleClick(MouseEvent e) {
				/*
				 * // 根据不同列表项得到其相应的editorInput对象和editorID，其中 //
				 * editorID指该编辑器在plugin.xml文件中设置id标识值 List list = (List)
				 * e.getSource();// 由MouseEvent得到列表对象 String listStr =
				 * list.getSelection()[0];// 得到当前列表项的字符 IEditorInput editorInput
				 * = null; String editorID = null; if (listStr.equals("中国")) {
				 * editorInput = chinaEditorInput; editorID =
				 * "hello.ChinaEditor"; } else if (listStr.equals("美国")) {
				 * editorInput = usaEditorInput; editorID = "hello.UsaEditor"; }
				 * else if (listStr.equals("法国")) { editorInput =
				 * franceEditorInput; editorID = "hello.FranceEditor"; } //
				 * 如果editorInput或editorID为空则中断返回 if (editorInput == null ||
				 * editorID == null){ System.err.println(
				 * "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"); return; }
				 * // 取得IWorkbenchPage，并搜索使用editorInput对象对应的编辑器 IWorkbenchPage
				 * workbenchPage = getViewSite().getPage(); IEditorPart editor =
				 * workbenchPage.findEditor(editorInput); //
				 * 如果此编辑器已经存在，则将它设为当前的编辑器（最顶端），否则 // 重新打开一个编辑器 if (editor !=
				 * null) { workbenchPage.bringToTop(editor); } else { try {
				 * workbenchPage.openEditor(editorInput, editorID); } catch
				 * (PartInitException e2) { e2.printStackTrace(); } }
				 */
				IEditorReference[] editorReferences = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
						.getActivePage().getEditorReferences();
				System.err.println("!!!!!!!!!!!!!!!!!!!! editorReferences length " + editorReferences.length);
				if (editorReferences[0].isDirty()) {
					System.err.println("!!!!!!!!!!!!!!!!!!!!    " + editorReferences[0].getName() + " is dirty");
				}
				IEditorPart part = editorReferences[0].getEditor(false);
				System.err.println("part.getEditorInput() " + part.getEditorInput());
				System.err.println("editorReferences[0].getEditor(false).getEditorInput().getImageDescriptor() " + editorReferences[0].getEditor(false).getEditorInput().getImageDescriptor());
				System.err.println("editorReferences[0].getEditor(false).getEditorInput().getToolTipText() " + editorReferences[0].getEditor(false).getEditorInput().getToolTipText());
				
				
				IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
				IDocument doc = ((ITextEditor)part).getDocumentProvider().getDocument(part.getEditorInput());
				/*
				try {
					System.out.println("doc.getPositions(String) " + doc.getPositions("main"));
				} catch (BadPositionCategoryException e1) {
					e1.printStackTrace();
				}
				*/
				/*
				try {
					System.out.println("doc.getLineOffset(int) " + doc.getLineOffset(1));
				} catch (BadLocationException e1) {
					e1.printStackTrace();
				}
				try {
					System.out.println("doc.getLineOfOffset(0) " + doc.getLineOfOffset(0));
				} catch (BadLocationException e1) {
					e1.printStackTrace();
				}
				try {
					System.out.println("doc.getLineDelimiter(0) " + doc.getLineDelimiter(0));
				} catch (BadLocationException e1) {
					e1.printStackTrace();
				}*/
				/*
				System.out.println("doc.get:");
				System.out.println(doc.get());
				doc.addDocumentListener(new IDocumentListener() {
					
					@Override
					public void documentChanged(DocumentEvent arg0) {
						System.out.println("typing char: " + arg0.getText());
					}
					
					@Override
					public void documentAboutToBeChanged(DocumentEvent arg0) {
						
					}
				});
				*/
				
				IEditorPart editorPart = getSite().getPage().getActiveEditor();
				if (editorPart != null) {
				    ITextOperationTarget target = (ITextOperationTarget)editorPart.getAdapter(ITextOperationTarget.class);
				    if (target instanceof ITextViewer) {
				        ITextViewer textViewer = (ITextViewer)target;
				        System.out.println("textViewer.getSelectedRange().x " + textViewer.getSelectedRange().x);
				        System.out.println("textViewer.getSelectedRange().y " + textViewer.getSelectedRange().y);
				        System.out.println("textViewer.getTopIndex() " + textViewer.getTopIndex());
				        System.out.println("textViewer.getTopIndexStartOffset() " + textViewer.getTopIndexStartOffset());
				        System.out.println("textViewer.getSelectionProvider().getSelection() " + textViewer.getSelectionProvider().getSelection());
				        IDocument doc2 = textViewer.getDocument();
				        String code = doc2.get();
				        System.out.println("!!!!!!!!!!!!!!!!!!!!! code num " + getCurrentLineNum(code,textViewer.getSelectedRange().x));
				    } 
				}
				
				
				
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

	/**
	 * 返回当前光标所在行数;
	 * 编号从0开始;
	 * */
	private static int getCurrentLineNum(String code,int offset){
		if(code.length() < offset){
			return -1;
		}
		int line = 0;
		for(int i = 0;i < offset;i++){
			if(code.charAt(i) == '\n'){
				line ++;
			}
		}
		return line;
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
