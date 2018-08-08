package cn.nuaa.ai.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextOperationTarget;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PlatformUI;

import cn.nuaa.ai.testPlugins.View2;

public class MyCommandHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent arg0) throws ExecutionException {
		System.out.println("MyCommandHandler");
		getContent();
		return null;
	}

	public static void getContent() {
		IEditorReference[] editorReferences = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
				.getEditorReferences();
		System.err.println("!!!!!!!!!!!!!!!!!!!! editorReferences length " + editorReferences.length);
		if (editorReferences[0].isDirty()) {
			System.err.println("!!!!!!!!!!!!!!!!!!!!    " + editorReferences[0].getName() + " is dirty");
		}
		//IEditorPart part = editorReferences[0].getEditor(false);
		IEditorPart part = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		if (part != null) {
			ITextOperationTarget target = (ITextOperationTarget) part.getAdapter(ITextOperationTarget.class);
			if (target instanceof ITextViewer) {
				//��ȡ�����Ϣ;
				ITextViewer textViewer = (ITextViewer) target;
				System.out.println("textViewer.getSelectedRange().x " + textViewer.getSelectedRange().x);
				System.out.println("textViewer.getSelectedRange().y " + textViewer.getSelectedRange().y);
				IDocument doc2 = textViewer.getDocument();
				String code = doc2.get();
				
				//��������кܶ����õ���Ϣ,������Ȼû�õ�,��������Ҫ���Կ�һ��;
				textViewer.getTextWidget();
				
				System.out.println(
						"!!!!!!!!!!!!!!!!!!!!! code num " + getCurrentLineNum(code, textViewer.getSelectedRange().x));
				System.out.println("doc.get:");
				System.out.println(doc2.get());
				
				//��ѯ;
				String results = search(code);
				
				//��ʾ���;
				IViewPart viewpart2 = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView("cn.nuaa.ai.testPlugins.View2");
				View2 view2 = (View2) viewpart2;
				for(int i = 0;i < view2.getText().size();i++){
					Text text = view2.getText().get(i);
					text.setText(results);	
				}
			}
		}
	}

	/**
	 * �ӷ������˻�ȡ���;
	 * */
	private static String search(String code){
		return code;
	}
	
	
	/**
	 * ���ص�ǰ�����������; ��Ŵ�0��ʼ;
	 */
	private static int getCurrentLineNum(String code, int offset) {
		if (code.length() < offset) {
			return -1;
		}
		int line = 0;
		for (int i = 0; i < offset; i++) {
			if (code.charAt(i) == '\n') {
				line++;
			}
		}
		return line;
	}
}
