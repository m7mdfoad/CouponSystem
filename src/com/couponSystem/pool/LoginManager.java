package com.couponSystem.pool;

import java.util.Scanner;

import javax.swing.JOptionPane;

import com.couponSystem.classes.ClientType;
import com.couponSystem.classes.CouponSystemException;
import com.couponSystem.dao.CompaniesDAO;
import com.couponSystem.dao.CouponsDAO;
import com.couponSystem.dao.CustomersDAO;
import com.couponSystem.dbdao.CompaniesDBDAO;
import com.couponSystem.dbdao.CouponDBDAO;
import com.couponSystem.dbdao.CustomerDBDAO;
import com.couponSystem.facade.AdminFacade;
import com.couponSystem.facade.ClientFacade;
import com.couponSystem.facade.CompanyFacade;
import com.couponSystem.facade.CustomerFacade;

public class LoginManager {

	private static LoginManager instance;
	Scanner sc = new Scanner(System.in);

	private LoginManager() {

	}

	public static LoginManager getInstance() {

		if (instance == null) {
			instance = new LoginManager();
		}
		return instance;
	}

	public ClientFacade login(String email, String password, ClientType clientType) throws CouponSystemException {

		CompaniesDAO companiesDao = new CompaniesDBDAO();
		CustomersDAO customersDao = new CustomerDBDAO();
		CouponsDAO couponsDao = new CouponDBDAO();
		if (clientType == ClientType.ADMINISTRATOR) {
			AdminFacade adminFacade = new AdminFacade(companiesDao, customersDao, couponsDao);
			if (adminFacade.login(email, password)) {
				System.out.println("you are connected to the admin account");
				return adminFacade;
			}
		} else if (clientType == ClientType.COMPANY || companiesDao.isCompanyExists(email, password)) {
			CompanyFacade companyFacade = new CompanyFacade(companiesDao, customersDao, couponsDao);
			if (companyFacade.login(email, password)) {
				System.out.println("you are connected to the company account");
				return companyFacade;
			}
		} else if (clientType == ClientType.CUSTOMER || customersDao.isCustomerExists(email, password)) {
			CustomerFacade customerFacade = new CustomerFacade(companiesDao, customersDao, couponsDao);
			if (customerFacade.login(email, password)) {
				System.out.println("you are connected to the customer account");
				return customerFacade;
			}
		} else {
			System.out.println("wrong username or password");
			String[] options = { "ADMINISTRATOR", "COMPANY", "CUSTOMER" };
			System.out.println("enter username: ");
			String user = sc.nextLine();
			System.out.println("enter password: ");
			String pass = sc.nextLine();
			System.out.println("enter type: ");
			String type = (String) JOptionPane.showInputDialog(null, "Choose one", "Input",
					JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
			login(user, pass, ClientType.valueOf(type));
		}
		return null;

	}
}