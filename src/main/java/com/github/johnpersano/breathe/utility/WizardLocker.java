package com.github.johnpersano.breathe.utility;

public interface WizardLocker {
	
	public void lockPreviousButton();
	
	public void lockNextButton();

	public void lockViewPager();
	
	public void unlockPreviousButton();
	
	public void unlockNextButton();

	public void unlockViewPager();

}
