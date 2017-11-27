package model;

import java.io.Serializable;

public class LibraryMember extends Person implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String mbrId;

	public LibraryMember(String mbrId, String f, String l, String t, Address a) {
		super(f, l, t, a);
		this.mbrId = mbrId;
	}

	public String getMemberId() {
		return mbrId;
	}

	@Override
	public String toString() {
		return "LibraryMember [mbrId=" + mbrId + ": " + super.getFirstName() + ", " + super.getLastName() + ", "
				+ super.getTelephone() + ", " + super.getAddress() + "]";
	}

}
