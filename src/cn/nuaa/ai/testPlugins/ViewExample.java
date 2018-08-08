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
	private List list; // ���б�д�����ʵ�����������������Ŀɷ��ʷ�Χ
	// ע�����List������java.util���µ�.����org.eclipse.swt.widgets.List;���µ�.

	public void createPartControl(Composite parent) {
		IWorkbenchHelpSystem help = PlatformUI.getWorkbench().getHelpSystem();
		help.setHelp(parent, "cn.com.kxh.myplugin.buttonHelpId");
		Composite topComp = new Composite(parent, SWT.NONE);
		topComp.setLayout(new FillLayout());
		list = new List(topComp, SWT.BORDER);
		list.add("�й�");
		list.add("����");
		list.add("����");
		// �б�ѡ���¼�����
		list.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				// ��IWorkbenchPage���view2����
				IWorkbenchPage wbp = getViewSite().getPage();
				// �ڲ����IWorkbenchPage����Ƚ���Ҫ,�����ٸ���һ�ֻ�ô˶����ͨ�õķ���.
				// Activator.getDefault().getWorkbench().getActiveWorkbenchWindow().getActivePage();
				IViewPart view2 = wbp.findView("cn.nuaa.ai.testPlugins.View2");
				// ����ط��Ĳ�����"��ͼ2"��plugin.xml�е�id��ʶ.�ɴ˿ɼ�plugin.xml�ļ��ڲ���еĵ�λ�Ǽ�����Ҫ��.
				// ����ǰѡ����б�����ʾ���ı�����
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
				 * // ���ݲ�ͬ�б���õ�����Ӧ��editorInput�����editorID������ //
				 * editorIDָ�ñ༭����plugin.xml�ļ�������id��ʶֵ List list = (List)
				 * e.getSource();// ��MouseEvent�õ��б���� String listStr =
				 * list.getSelection()[0];// �õ���ǰ�б�����ַ� IEditorInput editorInput
				 * = null; String editorID = null; if (listStr.equals("�й�")) {
				 * editorInput = chinaEditorInput; editorID =
				 * "hello.ChinaEditor"; } else if (listStr.equals("����")) {
				 * editorInput = usaEditorInput; editorID = "hello.UsaEditor"; }
				 * else if (listStr.equals("����")) { editorInput =
				 * franceEditorInput; editorID = "hello.FranceEditor"; } //
				 * ���editorInput��editorIDΪ�����жϷ��� if (editorInput == null ||
				 * editorID == null){ System.err.println(
				 * "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"); return; }
				 * // ȡ��IWorkbenchPage��������ʹ��editorInput�����Ӧ�ı༭�� IWorkbenchPage
				 * workbenchPage = getViewSite().getPage(); IEditorPart editor =
				 * workbenchPage.findEditor(editorInput); //
				 * ����˱༭���Ѿ����ڣ�������Ϊ��ǰ�ı༭������ˣ������� // ���´�һ���༭�� if (editor !=
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

		// ���뵼������ť�������˵����Ҽ��˵�
		MyActionGroup actionGroup = new MyActionGroup();
		fillViewAction(actionGroup);// ������ͼ�ĵ�������ť
		fillViewMenu(actionGroup);// ������ͼ�������˵�
		fillListMenu(actionGroup);// ������ͼ�������˵�
	}

	// ������ͼ�ĵ�������ť
	private void fillViewAction(MyActionGroup actionGroup) {
		IActionBars bars = getViewSite().getActionBars();
		actionGroup.fillActionBars(bars);
	}

	// ������ͼ�������˵�
	private void fillViewMenu(MyActionGroup actionGroup) {
		IMenuManager manager = getViewSite().getActionBars().getMenuManager();
		actionGroup.fillContextMenu(manager);
	}

	// �����б�List���Ҽ��˵�
	private void fillListMenu(MyActionGroup actionGroup) {
		MenuManager manger = new MenuManager();
		Menu menu = manger.createContextMenu(list);
		list.setMenu(menu);
		actionGroup.fillContextMenu(manger);
	}

	/**
	 * ���ص�ǰ�����������;
	 * ��Ŵ�0��ʼ;
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
}
