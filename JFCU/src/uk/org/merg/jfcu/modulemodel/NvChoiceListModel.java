package uk.org.merg.jfcu.modulemodel;

import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

public class NvChoiceListModel implements ListModel<Item> {

	private Choice nvChoice;

	public NvChoiceListModel(Choice nvChoice) {
		this.nvChoice = nvChoice;
	}
	
	@Override
	public void addListDataListener(ListDataListener arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public Item getElementAt(int i) {
		return nvChoice.getItems().get(i);
	}

	@Override
	public int getSize() {
		return nvChoice.getItems().size();
	}

	@Override
	public void removeListDataListener(ListDataListener arg0) {
		// TODO Auto-generated method stub

	}

}
