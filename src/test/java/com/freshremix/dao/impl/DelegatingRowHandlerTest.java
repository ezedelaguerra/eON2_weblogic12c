package com.freshremix.dao.impl;

import org.testng.annotations.Test;

import com.freshremix.dao.DelegateDataRowHandler;

public class DelegatingRowHandlerTest {

	private class TestDelegateEntity{
		
	}
	
	private class TestDelegateRowHandler implements DelegateDataRowHandler<TestDelegateEntity>{

		@Override
		public void handleRow(TestDelegateEntity row) throws Exception {
			throw new Exception();
		}
	}
	
	@Test(expectedExceptions=Exception.class)
	public void testHandleRowThrowsException(){
		DelegatingRowHandler<TestDelegateEntity> testDelegate = new DelegatingRowHandler<TestDelegateEntity>();
		
		DelegateDataRowHandler<TestDelegateEntity> delegate = new TestDelegateRowHandler();
		testDelegate.addDelegate(delegate);
		testDelegate.handleRow(null);
	}

	@Test(expectedExceptions=Exception.class)
	public void testAddDelegateThrowsException(){
		DelegatingRowHandler<TestDelegateEntity> testDelegate = new DelegatingRowHandler<TestDelegateEntity>();
		
		testDelegate.addDelegate(null);
	}

	@Test(expectedExceptions=RuntimeException.class)
	public void testNoRegisteredDelegateThrowsException(){
		DelegatingRowHandler<TestDelegateEntity> testDelegate = new DelegatingRowHandler<TestDelegateEntity>();
		
		testDelegate.handleRow(null);
	}

}
