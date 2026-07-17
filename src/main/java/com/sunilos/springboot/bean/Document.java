package com.sunilos.springboot.bean;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

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