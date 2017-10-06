package uk.org.merg.jfcu.modulemodel;

import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

public class NvChoiceListModel implements ListModel<NvItem> {

	private NvChoice nvChoice;

	public NvChoiceListModel(NvChoice nvChoice) {
		this.nvChoice = nvChoice;
	}
	
	@Override
	public void addListDataListener(ListDataListener arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public NvItem getElementAt(int i) {
		return nvChoice.getNvitems().get(i);
	}

	@Override
	public int getSize() {
		return nvChoice.getNvitems().size();
	}

	@Override
	public void removeListDataListener(ListDataListener arg0) {
		// TODO Auto-generated method stub

	}

}
