package org.eclipse.emf.henshin.statespace.explorer.actions;

import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.henshin.statespace.StateSpaceManager;
import org.eclipse.emf.henshin.statespace.explorer.StateSpaceExplorerPlugin;
import org.eclipse.emf.henshin.statespace.impl.StateSpaceManagerImpl;
import org.eclipse.emf.henshin.statespace.resource.StateSpaceResource;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

/**
 * Abstract action for state space files.
 * @author Christian Krause
 */
public abstract class AbstractStateSpaceFileAction implements IObjectActionDelegate {
	
	// State space file:
	private IFile file;
	
	// State space manager:
	private StateSpaceManager manager;
	
	// Shell:
	private Shell shell;
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction, org.eclipse.jface.viewers.ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		file = null;
		manager = null;
		if (selection instanceof IStructuredSelection) {
			Object first = ((IStructuredSelection) selection).getFirstElement();
			if (first instanceof IFile && StateSpaceResource.FILE_EXTENSION.equals(((IFile) first).getFileExtension())) {
				file = (IFile) first;
			}
		}
		action.setEnabled(file!=null);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.IObjectActionDelegate#setActivePart(org.eclipse.jface.action.IAction, org.eclipse.ui.IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart part) {
		shell = part.getSite().getShell();
	}
	
	/**
	 * Get the current shell.
	 * @return Shell.
	 */
	protected Shell getShell() {
		return shell;
	}
	
	/**
	 * Get the state space file.
	 * @return The state space file.
	 */
	protected IFile getStateSpaceFile() {
		return file;
	}
	
	/**
	 * Get the state space manager.
	 * @return The state space manager.
	 */
	protected StateSpaceManager getStateSpaceManager() {
		
		if (manager==null) {
			
			ResourceSet resourceSet = new ResourceSetImpl();
			URI uri = URI.createPlatformResourceURI(file.getFullPath().toString(), false);
			StateSpaceResource resource = null;

			try {
				// Perform the loading:
				resource = (StateSpaceResource) resourceSet.getResource(uri, true);
				manager = new StateSpaceManagerImpl(resource.getStateSpace());
			}
			catch (Throwable e) {
				StateSpaceExplorerPlugin.getInstance().logError("Error loading state space", e);
				MessageDialog.openError(shell, "Load State Space", "Error loading state space. See the error log for mor information.");
			}
		}
		
		return manager;
		
	}
	
	/**
	 * Set the state space manager to be used.
	 * @param manager State space manager.
	 */
	public void setStateSpaceManager(StateSpaceManager manager) {
		this.manager = manager;
	}
	
	/**
	 * Save the state space resource.
	 */
	protected void saveStateSpace() {
		if (manager!=null) {
			
			// Perform saving:
			Resource resource = manager.getStateSpace().eResource();
			try {
				resource.save(null);
			} catch (IOException e) {
				StateSpaceExplorerPlugin.getInstance().logError("Error saving state space", e);
				MessageDialog.openError(shell, "Load State Space", "Error saving state space. See the error log for mor information.");				
			}
			
			// Free some memory:
			manager = null;
			System.gc();
		}
	}
	
	/**
	 * Helper class for saving the state space resource after a job has finished.
	 */
	class StateSpaceResourceSaveAdapter extends JobChangeAdapter {
		
		@Override
		public void done(IJobChangeEvent event) {
			shell.getDisplay().asyncExec(new Runnable() {
				public void run() {
					saveStateSpace();
				}
			});
		}
	};
}
