package com.safeinstruments.test;

import com.weighbridge.WeighingMachineSimpleRead;

public class TestRun {
	/**
	 * 
	 */
	public static void main(String[] args) {
		// call weight service
		if(WeighingMachineSimpleRead.init()) {
			System.out.println("Initialization is done. A thread is dispatched to do the task.");
		} else{
			System.out.println("Initialization not done. Program Finished.");
		}
	}
}