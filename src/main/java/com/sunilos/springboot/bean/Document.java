package com.sunilos.springboot.bean;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * Document bean. It is persistent object.
 * 
 * It contains LOB column to store file contents
 * 
 * @author Sunil Sahu
 * @Copyright (c) SunilOS Infotech Pvt Ltd
 * 
 */

@Entity
@Table(name = "ST_DOC")
public class Document extends BaseDocument {

	@Column(name = "DOC")
	@Lob
	@Basic(fetch = FetchType.LAZY)
	private byte[] doc;

	public byte[] getDoc() {
		return doc;
	}

	public void setDoc(byte[] doc) {
		this.doc = doc;
	}

}