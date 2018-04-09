package uk.org.clayson.push_bullet.model.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class UserDTO {

	private String username ;
	private String accessToken ;
	
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime createTime ;

	public long numOfNotificationsPushed ;
	
	public long getNumOfNotificationsPushed() {
		return numOfNotificationsPushed;
	}
	public void setNumOfNotificationsPushed(long numOfNotificationsPushed) {
		this.numOfNotificationsPushed = numOfNotificationsPushed;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public LocalDateTime getCreateTime() {
		return createTime;
	}
	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}
	
}
