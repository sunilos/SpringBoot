package com.sunilos.springboot.bean;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Document bean. It is persistent object.
 * 
 * Contains information of document
 * 
 * @author Sunil Sahu
 * @Copyright (c) SunilOS Infotech Pvt Ltd
 */

@Entity
@Table(name = "ST_DOC")
public class DocumentSummary extends BaseDocument {

}