package cn.nuaa.ai.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.fieldassist.AutoCompleteField;
import org.eclipse.jface.fieldassist.TextContentAdapter;
import org.eclipse.jface.text.ITextOperationTarget;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.PlatformUI;

public class Mysub1CommandHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent arg0) throws ExecutionException {
		System.out.println("Mysub1CommandHandler");
		IEditorReference[] editorReferences = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
				.getEditorReferences();
		IEditorPart part = editorReferences[0].getEditor(false);
		if (part != null) {
			ITextOperationTarget target = (ITextOperationTarget) part.getAdapter(ITextOperationTarget.class);
			if (target instanceof ITextViewer) {
				// 获取相关信息;
				ITextViewer textViewer = (ITextViewer) target;

				textViewer.getTextWidget();

				new AutoCompleteField(textViewer.getTextWidget(), new TextContentAdapter(),
						new String[] { "我是一个程序猿", "我要加班", "我爱编程", "你是谁?", "你在干什么" });

			}
		}

		return null;
	}

}
