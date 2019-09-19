package com.couponSystem.classes;

import com.couponSystem.dao.CouponsDAO;
import com.couponSystem.dbdao.CouponDBDAO;
import com.couponSystem.pool.ConnectionPool;

public class CouponExpirationDailyJob implements Runnable {

	private boolean quit = false;
	private CouponsDAO couponsDAO = new CouponDBDAO();
	ConnectionPool connectionPool;

	public CouponExpirationDailyJob() {

		try {
			connectionPool = ConnectionPool.getInstance();
		} catch (CouponSystemException e) {
			System.out.println("cannot connect the thread");
			e.printStackTrace();
		}

	}

	public void run() {
		while (!quit) {
			try {

				couponsDAO.deleteCoupon(couponsDAO.getExpired());

				Thread.sleep(1000 * 60 * 60 * 24);

			} catch (CouponSystemException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
				break;
			}
		}

	}

	public void stop() {
		System.out.println("stopped");
		quit = true;

	}
}
