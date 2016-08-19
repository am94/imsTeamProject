package com.revature.dataAccess;

import java.util.Date;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;

import com.revature.beans.Address;
import com.revature.beans.CategoryDescription;
import com.revature.beans.Client;
import com.revature.beans.ClientType;
import com.revature.beans.Invoice;
import com.revature.beans.Order;
import com.revature.beans.Product;
import com.revature.beans.State;

public class DataLayerAccess
{
	private Session session;
	private ManagementDAO dao;
	private static Logger log = Logger.getRootLogger();
	
	//I changed this for testing, we should change it back to how it was.
	//DON'T WORRY, WE WILL.
	
	public DataLayerAccess()
	{
		super();
		
		log.info("Get the session");
		
		session = SessionFactoryManager.getInstance().openSession();
		
		log.info("Got the session");
		
		dao = new ManagementDAO(session);
		
	}
	//close session
	public void close()
	{
		log.info("Closing the session");
		if(session != null)
			session.close();

		log.info("Closed the session");
	}
	
	///INSERT STATEMENT///
	
	public void createObject(Object obj)
	{
		log.info("Open transaction.");
		Transaction tx = session.beginTransaction();
		
		try
		{
			dao.insertObject(obj); // can call multiple daos or dao methods
			tx.commit(); // commit 
			log.info("Transaction completed successfully.");
		}
		
		catch(Throwable t)
		{// or
			tx.rollback();// rollback (depending on result)
			log.error("Transaction rolled back.");
		}
	}
	
	/////////////////////CLIENT MANAGEMENT/////////////////////////
	public boolean terminateClient(Client obj){
		
		log.info("'Terminating' client");
		Transaction tx = session.beginTransaction();
		
		try{
			
			dao.deleteClient(obj);
			tx.commit();
			log.info("Client 'termination' successful");
			return true;
		}catch(Throwable t){
			tx.rollback();
			log.error("Client 'termination' failed");
			return false;
		}
	}
	
	public Set<Client> getAllClients(){
		log.info("Getting All Clients");
		Set<Client> currentClients = dao.getAllClients();
		
		return currentClients;
	}
	
	public Set<Client> allClients(){
		log.info("Getting All Clients");
		Set<Client> allClients = dao.getAllClients();
		
		return allClients;
	}

///////////////////// END CLIENT MANAGEMENT /////////////////////////
	
	//////////////////// INVOICE SECTION /////////////////////////////
	
	public Set<Invoice> getAllInvoices(){
		log.info("Getting All Invoices");
		Set<Invoice> invoices = dao.getInvoices();
		
		return invoices;
	}
	
	public Set<Order> getClientInvoices(int clientId){
		log.info("Getting Orders based on Client Id");
		
		Transaction tx = session.beginTransaction();
		
		try{
			Set<Order> orders = dao.getClientInvoices(clientId);
			tx.commit();
			log.info("Orders grab successful");
			return orders;
		}catch(Throwable t){
			tx.rollback();
			log.error("Orders grab failed");
			return null;
		}
		
	}
	
	/////////////////// END INVOICE SECTION ///////////////////////
	
	////////////////// BEGIN DELETING PRODUCT ///////////////////////
	public void deleteProduct(Product product) {
		log.info("'Terminating' client");
		Transaction tx = session.beginTransaction();
		
		try{
			
			dao.deleteProduct(product);
			tx.commit();
			log.info("Product 'deletion' successful");
		}catch(Throwable t){
			tx.rollback();
			log.error("Product 'deletion' failed");
		}
		
	}
	
	///////////////// END DELETING PRODUCT //////////////////////////
	
	
////////////////// BEGIN INCOMING AND OUTGOING SECTION //////////////////	
	//in-going and outgoing invoices
	
	//  client type list
	public Set<ClientType> clientTypeList(){
		log.info("Getting All Client Types");
		Set<ClientType> types = dao.getTypes();
		
		 return types;
	}
	
	//	client list
	public Set<Client> clientList(int clientTypeId){
		log.info("Getting List of Clients based on Client Type");
		Set<Client> clients = dao.getClients(clientTypeId);
		return clients;
		
	}
	//drop down of items
	public Set<Product> getProducts(){
		log.info("Getting List of all Products");
		Set<Product> products = dao.getAllProducts();
		
		return products;
	}
	
	//retrieve newly created order
	public Order getNewOrder(Date orderDate) {
		log.info("Getting Newly Created Order");
		Order order = dao.getCurrentOrder(orderDate);
		return order;
	}
	
	//add or subtract quantity
	public void change(int amount, int productId){
		log.info("Changing the quantity of a product based on the product id");
		Transaction tx = session.beginTransaction();
		
		try{
			dao.changeStock(amount, productId);
			tx.commit();
			log.info("Transaction successful for Product quantity change");
		}catch(Throwable t){
			tx.rollback();
			log.info("Transaction failed for Product quantity change");
		}
		return;
	
	}
	
	//create invoice
	public void createInvoice(Invoice invoice){
		dao.insertObject(invoice);
	}
	
	////////////////END INCOMING AND OUTGOING SECTION //////////////////	
	
	///////////////////////// ADD NEW ITEMS/PRODUCTS ///////////////////////////////
	
	public Set<CategoryDescription> getCatChoice(String categoryChoice) {
		log.info("Get list of Category Description for the type of category.");
		Set<CategoryDescription> catDesc = dao.getCatChoice(categoryChoice);
		return catDesc;
	}
	public Product getNewProduct(String newItemName) {
		log.info("Get newly created product based on the name.");
		Product product = dao.getNewProduct(newItemName);
		return product;
	}
	//////////////////////// END ADD NEW ITEMS/PRODUCTS //////////////////////////
	
	////// ADDITIONS TO ADDING CLIENTS ///////
	public State getState(String stateName) {
		log.info("Get State.");
		State state = dao.getState(stateName);
		return state;
	}
	public Address getAddress(State state) {
		log.info("Get Address based on State code.");
		Address address = dao.getAddress(state);
		
		return address;
	}
	public ClientType getClientType(int clientTypeId) {
		log.info("Get Client Type.");
		ClientType clientType = dao.getClientType(clientTypeId);
		
		return clientType;
	}
	////// END ADDITIONS TO ADDING CLIENTS ///////
	

	
	
}
