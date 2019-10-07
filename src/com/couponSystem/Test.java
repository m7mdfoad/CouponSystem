package com.couponSystem;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Scanner;

import javax.swing.JOptionPane;

import com.couponSystem.beans.Category;
import com.couponSystem.beans.Company;
import com.couponSystem.beans.Coupon;
import com.couponSystem.beans.Customer;
import com.couponSystem.classes.ClientType;
import com.couponSystem.classes.CouponExpirationDailyJob;
import com.couponSystem.classes.CouponSystemException;
import com.couponSystem.classes.EndDate;
import com.couponSystem.dbdao.CompaniesDBDAO;
import com.couponSystem.dbdao.CustomerDBDAO;
import com.couponSystem.facade.AdminFacade;
import com.couponSystem.facade.CompanyFacade;
import com.couponSystem.facade.CustomerFacade;
import com.couponSystem.pool.ConnectionPool;
import com.couponSystem.pool.LoginManager;

public class Test {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws CouponSystemException {

		Scanner sc = new Scanner(System.in);
		Scanner sc1 = new Scanner(System.in);
		String op = "";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		CouponExpirationDailyJob couponExpirationDailyJob = new CouponExpirationDailyJob();
		Thread expire = new Thread(couponExpirationDailyJob);
		couponExpirationDailyJob.setThread(expire);
		expire.start();
		CompaniesDBDAO codb = new CompaniesDBDAO();
		CustomerDBDAO cudb = new CustomerDBDAO();
		String[] options = { "ADMINISTRATOR", "COMPANY", "CUSTOMER" };
		LoginManager lm = LoginManager.getInstance();
		System.out.println("enter username: ");
		String user = sc.nextLine();
		System.out.println("enter password: ");
		String pass = sc.nextLine();
		System.out.println("enter type: ");
		String type = (String) JOptionPane.showInputDialog(null, "Choose one", "Input", JOptionPane.INFORMATION_MESSAGE,
				null, options, options[0]);
		AdminFacade af = null; // af = adminFacade
		CompanyFacade cof = null;// cof = companyFacade
		CustomerFacade cuf = null;// cuf = customerFacade

		boolean adminF = false;
		boolean compF = false;
		boolean custF = false;
		if (user.equals("admin") && pass.equals("1234")) {
			adminF = true;
		} else if (codb.isCompanyExists(user, pass)) {
			compF = true;
		} else if (cudb.isCustomerExists(user, pass)) {
			custF = true;
		}
		if (adminF == true) {
			af = (AdminFacade) lm.login(user, pass, ClientType.valueOf(type));
		} else if (compF == true) {
			cof = (CompanyFacade) lm.login(user, pass, ClientType.valueOf(type));
		} else if (custF == true) {
			cuf = (CustomerFacade) lm.login(user, pass, ClientType.valueOf(type));
		} else
			main(args);
		while (!op.equals("quit")) {
			op = sc.nextLine();
			if (adminF == true) {
				if (op.equals("getAllCompanies")) {
					try {
						af.getAllCompanies();
					} catch (CouponSystemException e) {
						throw new CouponSystemException("we can't get all companies", e);
					}
				}

				if (op.equals("addCompany")) {
					try {
						System.out.println("enter company name: ");
						String name = sc.nextLine();
						System.out.println("enter company email: ");
						String email = sc.nextLine();
						System.out.println("enter company password: ");
						String password = sc.nextLine();
						if (af.isCompanyExists(email, password) == true) {
							System.out.println("the company is exists");
						} else {
							Company company = new Company(name, email, password);
							af.addCompany(company);
						}
					} catch (CouponSystemException e) {
						throw new CouponSystemException("we can't get all companies", e);
					}
				}
				if (op.equals("addCustomer")) {
					try {
						System.out.println("enter fiest name: ");
						String first = sc.nextLine();
						System.out.println("enter last name: ");
						String last = sc.nextLine();
						System.out.println("enter email: ");
						String email = sc.nextLine();
						System.out.println("enter password: ");
						String password = sc.nextLine();
						if (af.isCustomerExists(email, password)) {
							System.out.println("customer is exists");
						} else {
							Customer customer = new Customer(first, last, email, password);
							af.addCustomer(customer);
						}
					} catch (CouponSystemException e) {
						throw new CouponSystemException("we can't add customer", e);
					}
				}
				if (op.equals("addCoupon")) {
					try {
						System.out.println("enter title : ");
						String title = sc.nextLine();
						System.out.println("enter category type: ");
						System.out.println("categories:");
						System.out.println("FOOD,ELECTRICITY,RESTAURANT,SOFTWARES,TOURS,SERVICE,"
								+ "FORNITURE,ACCESSORIES,INTERNET,CELLULAR,CARS,PARTS,BOOKS,VACATION");
						Category cat = Category.valueOf(sc.nextLine().toUpperCase());
						System.out.println("enter start date yyyy-mm-dd: ");
						Date start = Date.valueOf(sc.nextLine());
						System.out.println("enter amount:");
						EndDate ed = new EndDate(start);
						Date end = Date.valueOf(simpleDateFormat.format(ed.newDate(start)));
						int amount = sc1.nextInt();
						System.out.println("enter price: ");
						double price = sc.nextDouble();
						System.out.println("enter image path: ");
						String images1 = sc.nextLine();
						System.out.println("enter the company id: ");
						int compid = sc1.nextInt();
						System.out.println("enter category id: ");
						System.out.println("FOOD=1");
						System.out.println("ELECTRICITY=2");
						System.out.println("RESTAURANT= 3");
						System.out.println("SOFTWARES=4");
						System.out.println("TOURS= 5");
						System.out.println("SERVICE=6");
						System.out.println("FORNITURE=7");
						System.out.println("ACCESSORIES=8");
						System.out.println("INTERNET=9");
						System.out.println("CELLULAR=10");
						System.out.println("CARS=11");
						System.out.println("PARTS=12");
						System.out.println("BOOKS=13");
						System.out.println("VACATION=14");
						int catid = sc1.nextInt();
						System.out.println("enter description: ");
						String desc = sc.nextLine();
						if (af.couponExists(title)) {
							System.out.println("coupon is exists");
						} else {
							Coupon coupon = new Coupon(cat, title, desc, start, end, amount, price, images1, compid,
									catid);
							af.addCupon(coupon);
						}
					} catch (CouponSystemException e) {
						throw new CouponSystemException("adding coupon failed", e);
					}
				}
				if (op.equals("updateCompany")) {
					try {
						System.out.println("enter current email:");
						String oemail = sc.nextLine();
						System.out.println("enter current password: ");
						String opassword = sc.nextLine();
						if (af.isCompanyExists(oemail, opassword)) {
							System.out.println("enter current id: ");
							int id = sc1.nextInt();
							System.out.println("enter new name: ");
							String name1 = sc.nextLine();
							System.out.println("enter new email:");
							String email1 = sc.nextLine();
							System.out.println("enter new password: ");
							String password1 = sc.nextLine();

							Company company = new Company(id, name1, email1, password1);
							af.updateCompany(company);
						} else {
							System.out.println("company dosn't exists");
						}
					} catch (CouponSystemException e) {
						throw new CouponSystemException("something went wrong updating company", e);
					}
				}
				if (op.equals("updateCustomer")) {
					try {
						System.out.println("enter current email:");
						String oemail = sc.nextLine();
						System.out.println("enter current password: ");
						String opassword = sc.nextLine();
						if (af.isCustomerExists(oemail, opassword)) {
							System.out.println("enter the current id");
							int id = sc1.nextInt();
							System.out.println("enter new first name: ");
							String first = sc.nextLine();
							System.out.println("enter new last name: ");
							String last = sc.nextLine();
							System.out.println("enter new email:");
							String email = sc.nextLine();
							System.out.println("enter new password: ");
							String password = sc.nextLine();
							Customer customer = new Customer(id, first, last, email, password);
							af.updateCustomer(customer);
						} else {
							System.out.println("customer dosn't exists");
						}
					} catch (CouponSystemException e) {
						throw new CouponSystemException("we can't get all companies", e);
					}
				}
				if (op.equals("updateCoupon")) {
					try {
						System.out.println("enter coupon id: ");
						int id = sc1.nextInt();
						System.out.println("enter title : ");
						String title = sc.nextLine();
						System.out.println("enter category type: ");
						Category cat = Category.valueOf(sc.nextLine().toUpperCase());
						System.out.println("enter start date yyyy-mm-dd: ");
						Date start = Date.valueOf(sc.nextLine());
						EndDate ed = new EndDate(start);
						Date end = Date.valueOf(simpleDateFormat.format(ed.newDate(start)));
						System.out.println("enter amount:");
						int amount = sc1.nextInt();
						System.out.println("enter price: ");
						double price = sc.nextDouble();
						System.out.println("enter image path: ");
						String image = sc.nextLine();
						System.out.println("enter the company id: ");
						int compid = sc1.nextInt();
						System.out.println("enter category id: ");
						int catid = sc1.nextInt();
						System.out.println("enter description: ");
						String desc = sc.nextLine();
						Coupon coupon = new Coupon(id, cat, title, desc, start, end, amount, price, image, compid,
								catid);
						af.updateCoupon(coupon);

					} catch (CouponSystemException e) {
						throw new CouponSystemException("we can't get all companies", e);
					}
				}
				if (op.equals("getOneCompany")) {
					try {
						System.out.println("enter company ID: ");
						System.out.println(af.getOneCompany(sc1.nextInt()));

					} catch (CouponSystemException e) {
						throw new CouponSystemException("we can't get all companies", e);
					}
				}
				if (op.equals("getOneCustomer")) {
					try {
						System.out.println("enter customer ID: ");
						System.out.println(af.getOneCustomer(sc1.nextInt()));

					} catch (CouponSystemException e) {
						throw new CouponSystemException("we can't get all companies", e);
					}
				}
				if (op.equals("getOneCoupon")) {
					try {
						System.out.println("input coupon ID: ");
						System.out.println(af.getOneCoupon(sc1.nextInt()));

					} catch (CouponSystemException e) {
						throw new CouponSystemException("we can't get all companies", e);
					}
				}
				if (op.equals("getAllCustomers")) {
					try {
						System.out.println(af.getAllCustomers());

					} catch (CouponSystemException e) {
						throw new CouponSystemException("we can't get all companies", e);
					}
				}
				if (op.equals("getAllCoupons")) {
					try {
						System.out.println(af.getAllCoupons());
					} catch (CouponSystemException e) {
						throw new CouponSystemException("we can't get all companies", e);
					}
				}
				if (op.equals("getCompanyCoupons")) {
					try {
						System.out.println("input company ID: ");
						System.out.println(af.getCompanyCoupons(sc1.nextInt()));
					} catch (CouponSystemException e) {
						throw new CouponSystemException("we can't get all companies", e);
					}
				}
				if (op.equals("deleteCompany")) {
					try {
						System.out.println("enter company ID: ");
						af.deleteCompany(sc1.nextInt());
					} catch (CouponSystemException e) {
						throw new CouponSystemException("we can't get all companies", e);
					}
				}
				if (op.equals("deleteCustomer")) {
					try {
						System.out.println("input customer ID: ");
						af.deleteCustomer(sc1.nextInt());
					} catch (CouponSystemException e) {
						throw new CouponSystemException("we can't get all companies", e);
					}
				}
				if (op.equals("deleteCoupon")) {
					try {
						System.out.println("input coupon ID: ");
						af.deleteCoupon(sc1.nextInt());
					} catch (CouponSystemException e) {
						throw new CouponSystemException("we can't get all companies", e);
					}
				}
				if (op.equals("isCustomerExists")) {
					System.out.println("enter email: ");
					String email = sc.nextLine();
					System.out.println("enter password: ");
					String password = sc.nextLine();
					System.out.println(cuf.isCustomerExists(email, password));
				}

				if (op.equals("help")) {

					UIDemo ui = new UIDemo();
					ui.mainAdmin();

				}

			} else if (compF == true) {
				if (op.equals("help")) {
					UIDemo ui = new UIDemo();
					ui.mainCompany();
				}
				if (op.equals("addCoupon")) {
					System.out.println("enter title : ");
					String title = sc.nextLine();
					System.out.println("enter category type: ");
					Category cat = Category.valueOf(sc.nextLine().toUpperCase());
					System.out.println("enter start date yyyy-mm-dd: ");
					Date start = Date.valueOf(sc.nextLine());
					EndDate ed = new EndDate(start);
					Date end = Date.valueOf(simpleDateFormat.format(ed.newDate(start)));
					System.out.println("enter amount:");
					int amount = sc1.nextInt();
					System.out.println("enter price: ");
					double price = sc.nextDouble();
					System.out.println("enter image path: ");
					String image = sc.nextLine();
					System.out.println("enter the company id: ");
					int compid = sc1.nextInt();
					System.out.println("enter category id: ");
					int catid = sc1.nextInt();
					System.out.println("enter description: ");
					String desc = sc.nextLine();
					Coupon coupon = new Coupon(cat, title, desc, start, end, amount, price, image, compid, catid);
					cof.addCoupon(coupon);
				}
				if (op.equals("updateCoupon")) {
					System.out.println("enter coupon id: ");
					int id = sc1.nextInt();
					System.out.println("enter title : ");
					String title = sc.nextLine();
					System.out.println("enter category type: ");
					Category cat = Category.valueOf(sc.nextLine().toUpperCase());
					System.out.println("enter start date yyyy-mm-dd: ");
					Date start = Date.valueOf(sc.nextLine());
					EndDate ed = new EndDate(start);
					Date end = Date.valueOf(simpleDateFormat.format(ed.newDate(start)));
					System.out.println("enter amount:");
					int amount = sc1.nextInt();
					System.out.println("enter price: ");
					double price = sc.nextDouble();
					System.out.println("enter image path: ");
					String image = sc.nextLine();
					System.out.println("enter the company id: ");
					int compid = sc1.nextInt();
					System.out.println("enter category id: ");
					int catid = sc1.nextInt();
					System.out.println("enter description: ");
					String desc = sc.nextLine();
					Coupon coupon = new Coupon(id, cat, title, desc, start, end, amount, price, image, compid, catid);
					cof.updateCoupon(coupon);
				}
				if (op.equals("deleteCoupon")) {
					System.out.println("enter coupon id to be deleted:");
					cof.deleteCoupon(sc.nextInt());
				}
				if (op.equals("CompanyCoupons")) {
					cof.getCompanyCoupons();
				}
				if (op.equals("getCompany")) {
					cof.getCompanyDetails();
				}
			} else if (custF == true) {
				if (op.equals("help")) {
					UIDemo ui = new UIDemo();
					ui.maincustomer();
				}
				if (op.equals("purCoupon")) {
					System.out.println("enter coupon id: ");
					int id = sc1.nextInt();
					System.out.println("enter title : ");
					String title = sc.nextLine();
					System.out.println("enter description: ");
					String desc = sc.nextLine();
					System.out.println("enter start date yyyy-mm-dd: ");
					Date start = Date.valueOf(sc.nextLine());
					EndDate ed = new EndDate(start);
					Date end = Date.valueOf(simpleDateFormat.format(ed.newDate(start)));
					System.out.println("enter amount:");
					int amount = sc1.nextInt();
					System.out.println("enter price: ");
					double price = sc.nextDouble();
					System.out.println("enter category type: ");
					System.out.println("enter image path: ");
					String image = sc.nextLine();
					Category cat = Category.valueOf(sc.nextLine().toUpperCase());
					System.out.println("enter the company id: ");
					int compid = sc1.nextInt();
					System.out.println("enter category id: ");
					int catid = sc1.nextInt();
					Coupon coupon = new Coupon(id, cat, title, desc, start, end, amount, price, image, compid, catid);
					cuf.purCoupon(coupon);
				}
				if (op.equals("getCoupon")) {
					cuf.getCustomerCoupons();
				}
				if (op.equals("getcustomer")) {
					cuf.getCustomerDetails();
				}

			}
		}
		if (op.equals("quit")) {
			System.out.println("good bye");
			couponExpirationDailyJob.stop();
			try {
				expire.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			ConnectionPool.getInstance().closeAllConnections();
		}

	}

}
