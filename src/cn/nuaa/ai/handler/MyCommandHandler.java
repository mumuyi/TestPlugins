package cn.nuaa.ai.handler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

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

import cn.nuaa.ai.search.BWTCodeLines;
import cn.nuaa.ai.search.MyFormat;
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
				int line = getCurrentLineNum(code, textViewer.getSelectedRange().x);
				System.out.println(
						"!!!!!!!!!!!!!!!!!!!!! code num " + getCurrentLineNum(code, textViewer.getSelectedRange().x));
				System.out.println("doc.get:");
				System.out.println(doc2.get());
				System.out.println(addCursor(doc2.get(),line));
				//查询;
				//List<String> results = search(addCursor(doc2.get(),line));
				List<String> results = search(doc2.get());
				//显示结果;
				IViewPart viewpart2 = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView("cn.nuaa.ai.testPlugins.View2");
				View2 view2 = (View2) viewpart2;
				for(int i = 0;i < view2.getText().size();i++){
					Text text = view2.getText().get(i);
					text.setText(results.get(i));	
				}
			}
		}
	}

	/**
	 * 从服务器端获取结果;
	 * */
	private static List<String> search(String code){
		MyFormat.singleFormat(code);
		try {
			writeFileContent("F:\\Java\\TestPlugins\\code\\0.txt",new StringBuffer(code));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return BWTCodeLines.runningDemo2();
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
	
	private static String addCursor(String code, int line){
		String newCode = "";
		int temp = 0;
		for (int i = 0; i < code.length(); i++) {
			if (code.charAt(i) == '\n') {
				temp++;
			}
			newCode += code.charAt(i);
			if(temp == line){
				newCode += ("mycursorposition");
				temp ++;
			}
		}
		return newCode;
	}
	
	/**
	 * 写入文件;
	 * */
	private static boolean writeFileContent(String filepath, StringBuffer buffer) throws IOException {
		Boolean bool = false;
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		FileOutputStream fos = null;
		PrintWriter pw = null;
		try {
			File file = new File(filepath);// 文件路径(包括文件名称)

			fos = new FileOutputStream(file);
			pw = new PrintWriter(fos);
			pw.write(buffer.toString().toCharArray());
			pw.flush();
			bool = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 不要忘记关闭
			if (pw != null) {
				pw.close();
			}
			if (fos != null) {
				fos.close();
			}
			if (br != null) {
				br.close();
			}
			if (isr != null) {
				isr.close();
			}
			if (fis != null) {
				fis.close();
			}
		}
		return bool;
	}
}
