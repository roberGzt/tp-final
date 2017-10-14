package com.TpFinal.services;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ PersonaServiceNuevoTest.class, ContratoServiceTest.class, ProvinciaServiceTest.class,
	 InmuebleServiceTest.class,
	PublicacionServiceTest.class })
public class AllTestsService {
}