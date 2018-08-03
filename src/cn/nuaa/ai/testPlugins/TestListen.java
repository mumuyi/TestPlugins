package cn.nuaa.ai.testPlugins;

import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.IStartup;
import org.eclipse.ui.IWindowListener;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

public class TestListen implements IStartup{

	@Override
	public void earlyStartup() {
	    IWorkbench wb = PlatformUI.getWorkbench();
	    wb.addWindowListener(generateWindowListener());
	    
	}

	private IWindowListener generateWindowListener() 
	{
	    return new IWindowListener() {
	        @Override
	        public void windowOpened(IWorkbenchWindow window) {
	            IWorkbenchPage activePage = window.getActivePage(); 
	            activePage.addPartListener(generateIPartListener2());
	        }

	        @Override
	        public void windowDeactivated(IWorkbenchWindow window) {}

	        @Override
	        public void windowClosed(IWorkbenchWindow window) {}

	        @Override
	        public void windowActivated(IWorkbenchWindow window) {}
	    };
	}
	
	private IPartListener2 generateIPartListener2() 
	{
	    return new IPartListener2() {

	        private void checkPart(IWorkbenchPartReference partRef) {
	        IWorkbenchPart part = partRef.getPart(false);
	            if (part instanceof IEditorPart)
	            {
	                IEditorPart editor = (IEditorPart) part;
	                IEditorInput input = editor.getEditorInput();
	                //if (editor instanceof ITextEditor && input instanceof FileEditorInput)  //double check.  Error Editors can also bring up this call
	                //{
	                //   IDocument document=(((ITextEditor)editor).getDocumentProvider()).getDocument(input);
	                //    document.addDocumentListener(/* your listener from above*/);
	                //}
	            }
	        }

	        @Override
	        public void partOpened(IWorkbenchPartReference partRef) {
	            checkPart(partRef);
	        }

	        @Override
	        public void partInputChanged(IWorkbenchPartReference partRef) 
	        {
	            checkPart(partRef);
	        }           

	        @Override
	        public void partVisible(IWorkbenchPartReference partRef){}

	        @Override
	        public void partHidden(IWorkbenchPartReference partRef) {}

	        @Override
	        public void partDeactivated(IWorkbenchPartReference partRef)  {}

	        @Override
	        public void partClosed(IWorkbenchPartReference partRef) {}

	        @Override
	        public void partBroughtToTop(IWorkbenchPartReference partRef) {}

	        @Override
	        public void partActivated(IWorkbenchPartReference partRef) {}
	    };
	}
}
