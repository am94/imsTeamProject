package com.revature.dataAccess;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionFactoryManager 
{

	// One SF per database
	private static SessionFactory sf;
	private static Logger log = Logger.getRootLogger();
	
	private SessionFactoryManager(){}
	
	public static synchronized SessionFactory getInstance()
	{
		if(sf == null)
		{
			log.info("get Session Factory");
			// Instantiation of SessionFactory (3.6)
			sf = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
		}
		
		return sf;
	}
}