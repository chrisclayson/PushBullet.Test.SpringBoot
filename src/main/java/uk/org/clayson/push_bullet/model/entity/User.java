package uk.org.clayson.push_bullet.model.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.keyvalue.annotation.KeySpace;

@KeySpace("user")
public class User {
	
	@Id
	private String username ;
	private String accessToken ;
	private LocalDateTime createTime ;
	private long numOfNotificationsPushed ;
	
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
	public long getNumOfNotificationsPushed() {
		return numOfNotificationsPushed;
	}
	public void setNumOfNotificationsPushed(long numOfNotificationsPushed) {
		this.numOfNotificationsPushed = numOfNotificationsPushed;
	}

	
}
