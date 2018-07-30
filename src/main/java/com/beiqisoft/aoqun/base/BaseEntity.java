package com.beiqisoft.aoqun.base;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import lombok.Data;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@MappedSuperclass 
public class BaseEntity  implements Serializable{
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss:SSS")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	protected Date ctime = new Date();
	/**记录人*/
	@Size(max=60)
	protected String recorder;
	/**操作人*/
	@Size(max=30)
	protected String operator;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;
	@Transient @JsonIgnore
	protected Integer pageNum = 0;
	@Transient @JsonIgnore
	protected Date startDate;
	@Transient @JsonIgnore
	protected Date endDate;
}
