package com.TpFinal;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.TpFinal.Integracion.dao.AllTestsDAO;
import com.TpFinal.Integracion.services.AllTestsService;
import com.TpFinal.UnitTests.AllUtilsTest;
import com.TpFinal.UnitTests.dto.AllDTO;

@RunWith(Suite.class)
@SuiteClasses({AllTestsDAO.class,AllTestsService.class,AllUtilsTest.class,AllDTO.class})
public class AllTests {
	
}
