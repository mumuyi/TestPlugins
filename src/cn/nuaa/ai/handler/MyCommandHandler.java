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
				//获取相关信息;
				ITextViewer textViewer = (ITextViewer) target;
				System.out.println("textViewer.getSelectedRange().x " + textViewer.getSelectedRange().x);
				System.out.println("textViewer.getSelectedRange().y " + textViewer.getSelectedRange().y);
				IDocument doc2 = textViewer.getDocument();
				String code = doc2.get();
				
				//这个里面有很多有用的信息,现在虽然没用到,后续有需要可以看一下;
				textViewer.getTextWidget();
				
				System.out.println(
						"!!!!!!!!!!!!!!!!!!!!! code num " + getCurrentLineNum(code, textViewer.getSelectedRange().x));
				System.out.println("doc.get:");
				System.out.println(doc2.get());
				
				//查询;
				String results = search(code);
				
				//显示结果;
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
	 * 从服务器端获取结果;
	 * */
	private static String search(String code){
		return code;
	}
	
	
	/**
	 * 返回当前光标所在行数; 编号从0开始;
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
