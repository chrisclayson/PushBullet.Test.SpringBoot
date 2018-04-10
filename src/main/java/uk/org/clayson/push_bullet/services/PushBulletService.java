package uk.org.clayson.push_bullet.services;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import uk.org.clayson.push_bullet.exception.PushBulletException;
import uk.org.clayson.push_bullet.model.dto.NotificationDTO;
import uk.org.clayson.push_bullet.model.entity.User;
import uk.org.clayson.push_bullet.model.pushbullet.Push;

@Service
public class PushBulletService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	public static String PUSH_BULLET_API_URL = "https://api.pushbullet.com/v2/pushes";
	public static String PUSH_BULLET_ACCESS_TOKEN_HEADER = "Access-Token";
	
	private URI uri ;
	
	public PushBulletService () throws URISyntaxException {
		this.uri = new URI(PushBulletService.PUSH_BULLET_API_URL);
	}

	/**
	 * Sends a push "note" request to PushBullet synchronously and returns the push identify.
	 * 
	 * @param user 
	 * @param notification
	 * @return
	 * @throws PushBulletException
	 */
	public String sendNotification(User user, NotificationDTO notification) throws PushBulletException {

		Push push = new Push();
		push.setBody(notification.getBody());
		push.setTitle(notification.getTitle());
		
		try {
			return this.sendPush(user, push);
		}catch(HttpClientErrorException ex) {
			log.error(ex.getMessage(), ex);
			if ( ex.getStatusCode() == HttpStatus.UNAUTHORIZED ) throw new PushBulletException(String.format("User %s has an invalid access token", user.getUsername()));
			
			throw new PushBulletException(ex.getMessage(), ex);
		}
	}

	private RequestEntity<Push> constructRequest(User user, Push push){
		HttpHeaders headers = new HttpHeaders();
		headers.add(PushBulletService.PUSH_BULLET_ACCESS_TOKEN_HEADER, user.getAccessToken());
		RequestEntity<Push> request = new RequestEntity<Push>(push, headers, HttpMethod.POST, this.uri);

		return request;
	}
	
	private String sendPush(User user, Push push) throws PushBulletException {
		RestTemplate template = new RestTemplate();
		ResponseEntity<String> response = template.exchange(this.constructRequest(user, push), String.class);

		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode root = mapper.readTree(response.getBody());
			JsonNode identity = root.path("iden");
			log.info(String.format("PushBullet push created with identity %s", identity.textValue()));
			return identity.textValue();
		} catch (IOException ex) {
			log.debug(response.getBody());
			throw new PushBulletException("Error Parsing PushBullet Response", ex);
		}
	}
}

