package com.societhelp.parser.csv.kbank;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.societhelp.core.util.ParserUtils;

/**
 * Created by dsharma on 11/4/17.
 */

public class KBankStatementPojo {

	private final long slNo;
	private final Date date;
	private final String description;
	private final String refNumber;
	private final double amount;
	private final String type;
	private final double balance;
	private final String name;

	private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	public KBankStatementPojo(long slNo, String strDate, String description, String refNumber, double amount,
			String type, double balance) throws ParseException {
		this.slNo = slNo;
		this.date = sdf.parse(strDate);
		this.description = description;
		this.refNumber = refNumber;
		this.amount = amount;
		this.type = type;
		this.balance = balance;
		this.name = ParserUtils.getNameFromDescription(this.description);
	}

	public Date getDate() {
		return date;
	}

	public String getType() {
		return type;
	}

	public long getSlNo() {
		return slNo;
	}

	public String getDescription() {
		return description;
	}

	public String getRefNumber() {
		return refNumber;
	}

	public double getAmount() {
		return amount;
	}

	public SimpleDateFormat getSdf() {
		return sdf;
	}

	public double getBalance() {
		return balance;
	}

	public String getName() {
		return name;
	}

	public boolean isCredit() {
		return type.toLowerCase().equals("cr");
	}

	@Override
	public String toString() {
		return "KotakStatementPojo [slNo=" + slNo + ", date=" + date + ", description=" + description + ", refNumber="
				+ refNumber + ", amount=" + amount + ", type=" + type + ", balance=" + balance + ", name=" + name + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(amount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(balance);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((refNumber == null) ? 0 : refNumber.hashCode());
		result = prime * result + ((sdf == null) ? 0 : sdf.hashCode());
		result = prime * result + (int) (slNo ^ (slNo >>> 32));
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KBankStatementPojo other = (KBankStatementPojo) obj;
		if (Double.doubleToLongBits(amount) != Double.doubleToLongBits(other.amount))
			return false;
		if (Double.doubleToLongBits(balance) != Double.doubleToLongBits(other.balance))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (refNumber == null) {
			if (other.refNumber != null)
				return false;
		} else if (!refNumber.equals(other.refNumber))
			return false;
		if (sdf == null) {
			if (other.sdf != null)
				return false;
		} else if (!sdf.equals(other.sdf))
			return false;
		if (slNo != other.slNo)
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

}
